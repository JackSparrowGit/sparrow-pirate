package com.corsair.sparrow.pirate.conf.config;

import com.corsair.sparrow.pirate.conf.properties.RedisConfigProperties;
import com.corsair.sparrow.pirate.core.constant.GlobalConstant;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Map;

/**
 * @author jack
 */
@Slf4j
@Configuration
@EnableCaching
@EnableConfigurationProperties(value = RedisConfigProperties.class)
public class RedisCacheConfig extends CachingConfigurerSupport {

    private static final String REDISSON_ADDRESS_PREFIX = "redis://";

    @Autowired
    private RedisConfigProperties redisConfigProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        lettuceConnectionFactory.setDatabase(redisConfigProperties.getDatabase());
        lettuceConnectionFactory.setHostName(redisConfigProperties.getHost());
        lettuceConnectionFactory.setPort(redisConfigProperties.getPort());
        lettuceConnectionFactory.setPassword(redisConfigProperties.getPassword());
        return lettuceConnectionFactory;
    }

    /**
     * Redisson分布式锁
     * 参考: https://github.com/redisson/redisson/wiki/%E7%9B%AE%E5%BD%95
     * 四种模式:
     *    Cluster(集群)
     * 　　Sentinel servers(哨兵)
     * 　　Master/Slave servers(主从)
     * 　　Single server(单机)
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(name = "redissonClient")
    public RedissonClient redissonClient(){
        Config config = new Config();
        // 此处为单机模式
        config.useSingleServer()
                .setAddress(this.getRedissonAddress(redisConfigProperties.getHost(),redisConfigProperties.getPort()))
                .setDatabase(redisConfigProperties.getDatabase())
                .setPassword(redisConfigProperties.getPassword())
                ;
        return Redisson.create(config);
    }

    private String getRedissonAddress(String host,Integer port){
        return REDISSON_ADDRESS_PREFIX + host +":"+port;
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String,Object> redisTemplate(){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();

        // key serializer
        StringRedisSerializer stringRedisSerializer = TedisCacheManager.STRING_REDIS_SERIALIZER;
        // value serializer
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = TedisCacheManager.JACKSON_2_JSON_REDIS_SERIALIZER;

        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);

        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        redisTemplate.setDefaultSerializer(genericJackson2JsonRedisSerializer);

        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        return new SimpleCacheErrorHandler(){
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                log.error("cache : {}, key :{}, handleCacheGetError :{}",cache,key,exception);
                super.handleCacheGetError(exception, cache, key);
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                log.error("cache : {}, key :{}, value : {}, handleCachePutError :{}",cache,key,value,exception);
                super.handleCachePutError(exception, cache, key, value);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                log.error("cache : {}, key :{}, handleCacheEvictError :{}",cache,key,exception);
                super.handleCacheEvictError(exception, cache, key);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                log.error("cache : {}, handleCacheClearError :{}",cache,exception);
                super.handleCacheClearError(exception, cache);
            }
        };
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheWriter redisCacheWrapper = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        return new TedisCacheManager(
                redisCacheWrapper,
                this.getRedisCacheConfigurationWithTtl(GlobalConstant.DEFAULT_CACHE_DURATION),
                this.getRedisCacheConfigurationMap(),
                true
        );
    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = Maps.newHashMap();
        redisCacheConfigurationMap.put("UserInfoList",this.getRedisCacheConfigurationWithTtl(3000));
        redisCacheConfigurationMap.put("UserInfoListAnother",this.getRedisCacheConfigurationWithTtl(18000));
        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration
                .serializeKeysWith(TedisCacheManager.STRING_SERIALIZATION_PAIR)
                .serializeValuesWith(TedisCacheManager.JACKSON_2_SERIALIZATION_PAIR)
                .entryTtl(Duration.ofSeconds(seconds));
        return redisCacheConfiguration;
    }
}
