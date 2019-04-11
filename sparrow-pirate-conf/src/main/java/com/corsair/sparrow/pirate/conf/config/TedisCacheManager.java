package com.corsair.sparrow.pirate.conf.config;

import com.corsair.sparrow.pirate.conf.annonation.CacheExpire;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ReflectionUtils;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * @author jack
 * 重写RedisCacheManager
 */
@Slf4j
public class TedisCacheManager extends RedisCacheManager implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    private Map<String,RedisCacheConfiguration> initailCacheConfiguration = new LinkedHashMap<>();

    public static final StringRedisSerializer STRING_REDIS_SERIALIZER = new StringRedisSerializer();

    public static final GenericJackson2JsonRedisSerializer JACKSON_2_JSON_REDIS_SERIALIZER = new GenericJackson2JsonRedisSerializer();

    /**
     * key serializer pair
     */
    public static final RedisSerializationContext.SerializationPair<String> STRING_SERIALIZATION_PAIR = RedisSerializationContext.SerializationPair.fromSerializer(STRING_REDIS_SERIALIZER);

    /**
     * value serializer pair
     */
    public static final RedisSerializationContext.SerializationPair<Object> JACKSON_2_SERIALIZATION_PAIR = RedisSerializationContext.SerializationPair.fromSerializer(JACKSON_2_JSON_REDIS_SERIALIZER);


    public TedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    public TedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
    }

    @Override
    public Cache getCache(String name) {
        Cache cache =super.getCache(name);
        return new RedisCacheWrapper(cache);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        String[] beanNames = applicationContext.getBeanNamesForType(Object.class);
        for(String beanName : beanNames){
            final Class clazz = applicationContext.getType(beanName);
            add(clazz);
        }
        super.afterPropertiesSet();
    }

    @Override
    protected Collection<RedisCache> loadCaches() {
        List<RedisCache> cacheList = new LinkedList<>();
        for (Map.Entry<String,RedisCacheConfiguration> entry: initailCacheConfiguration.entrySet()){
            cacheList.add(super.createRedisCache(entry.getKey(),entry.getValue()));
        }
        return cacheList;
    }

    private void add(final Class clazz){
        ReflectionUtils.doWithMethods(clazz,method -> {
            ReflectionUtils.makeAccessible(method);
            CacheExpire cacheExpire = AnnotationUtils.findAnnotation(method,CacheExpire.class);
            if(Objects.isNull(cacheExpire)){
                return;
            }
            Cacheable cacheable = AnnotationUtils.findAnnotation(method,Cacheable.class);
            if(Objects.nonNull(cacheable)){
                add(cacheable.cacheNames(),cacheExpire);
                return;
            }
            Caching caching = AnnotationUtils.findAnnotation(method,Caching.class);
            if(Objects.nonNull(caching)){
                Cacheable[] cacheables = caching.cacheable();
                if(cacheables.length > 0){
                    for (Cacheable c: cacheables){
                        if(Objects.nonNull(cacheExpire) && Objects.nonNull(c)){
                            add(c.cacheNames(),cacheExpire);
                        }
                    }
                }else {
                    CacheConfig cacheConfig = AnnotationUtils.findAnnotation(clazz,CacheConfig.class);
                    if(Objects.nonNull(cacheConfig)){
                        add(cacheConfig.cacheNames(),cacheExpire);
                    }
                }
            }
        },method -> Objects.nonNull(AnnotationUtils.findAnnotation(method,CacheExpire.class)));
    }

    private void add(String[] cacheNames, CacheExpire cacheExpire) {
        for (String cacheName : cacheNames){
            if(Objects.isNull(cacheName) || "".equals(cacheName.trim())){
                continue;
            }
            long expire = cacheExpire.expire();
            log.info("cacheName : {}, expire : {}",cacheName,expire);
            if(expire >= 0){
                // 缓存配置
                RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofSeconds(expire))
                        .disableCachingNullValues()
                        .serializeKeysWith(STRING_SERIALIZATION_PAIR)
                        .serializeValuesWith(JACKSON_2_SERIALIZATION_PAIR);
                initailCacheConfiguration.put(cacheName,redisCacheConfiguration);
            }else {
                log.warn("cacheName: {} use default expire",cacheName);
            }
        }
    }

    protected static class RedisCacheWrapper implements Cache{
        private final Cache cache;

        RedisCacheWrapper(Cache cache){
            this.cache = cache;
        }

        @Override
        public String getName() {
            try{
                return cache.getName();
            } catch (Exception e){
                log.error("getName ====>>> msg :{}, err :{}",e.getLocalizedMessage(),e);
                return null;
            }
        }

        @Override
        public Object getNativeCache() {
            try {
                return  cache.getNativeCache();
            }catch (Exception e){
                log.error("getNativeCode ====>>>> msg :{}, err :{}",e.getLocalizedMessage(),e);
                return null;
            }
        }

        @Override
        public ValueWrapper get(Object o) {
            try {
                return cache.get(o);
            }catch (Exception e ){
                log.error("get ====>>> o:{},msg: {}, err :{}",o,e.getLocalizedMessage(),e);
                return null;
            }
        }

        @Override
        public <T> T get(Object o, Class<T> aClass) {
            try {
                return cache.get(o,aClass);
            }catch (Exception e){
                log.error("get ====>>> o:{},msg: {}, err: {}",o,e.getLocalizedMessage(),e);
                return null;
            }
        }

        @Override
        public <T> T get(Object o, Callable<T> callable) {
            try {
                return cache.get(o,callable);
            }catch (Exception e){
                log.error("get ====>>>> o:{},msg: {},err: {},callable: {}",o,e.getLocalizedMessage(),e,callable);
                return null;
            }
        }

        @Override
        public void put(Object a, Object b) {
            try {
                cache.put(a,b);
            }catch (Exception e){
                log.error("put ====>>> a:{},b:{},msg:{},err:{}",a,b,e.getLocalizedMessage(),e);
            }
        }

        @Override
        public ValueWrapper putIfAbsent(Object a, Object b) {
            try {
                return cache.putIfAbsent(a,b);
            }catch (Exception e){
                log.error("putIfAbsent ====>>> a:{},b:{},msg:{},err:{}",a,b,e.getLocalizedMessage(),e);
                return null;
            }
        }

        @Override
        public void evict(Object o) {
            try {
                cache.evict(o);
            }catch (Exception e){
                log.error("evict ====>>>> o:{},msg:{},err:{}",o,e.getLocalizedMessage(),e);
            }
        }

        @Override
        public void clear() {
            try {
                cache.clear();
            }catch (Exception e){
                log.error("clear ====>>> msg:{},err:{}",e.getLocalizedMessage(),e);
            }
        }
    }
}
