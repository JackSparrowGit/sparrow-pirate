package com.corsair.sparrow.pirate.conf.config;

import com.corsair.sparrow.pirate.core.constant.GlobalConstant;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
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
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.*;

/**
 * @author jack
 */
@Slf4j
@Configuration
@EnableCaching
@ConditionalOnClass({RedisOperations.class})
@AutoConfigureAfter({RedisAutoConfiguration.class})
@EnableConfigurationProperties(value = RedisProperties.class)
public class RedisCacheConfig extends CachingConfigurerSupport {

    private static final String REDISSON_ADDRESS_PREFIX = "redis://";

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig(){
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        if(Objects.nonNull(redisProperties.getLettuce())){
            Integer maxIdle = redisProperties.getLettuce().getPool().getMaxIdle();
            Integer minIdle = redisProperties.getLettuce().getPool().getMinIdle();
            Integer maxActive = redisProperties.getLettuce().getPool().getMaxActive();
            Long maxWait = redisProperties.getLettuce().getPool().getMaxWait().getSeconds();
            genericObjectPoolConfig.setMaxIdle(Objects.isNull(maxIdle) ? 8 : maxIdle);
            genericObjectPoolConfig.setMinIdle(Objects.isNull(minIdle) ? 1 : minIdle);
            genericObjectPoolConfig.setMaxTotal(Objects.isNull(maxActive) ? 8 : maxActive);
            genericObjectPoolConfig.setMaxWaitMillis(Objects.isNull(maxWait) ? 5000L : maxWait);
        }
        return genericObjectPoolConfig;
    }

    @Bean
    LettuceConnectionFactory lettuceConnectionFactory(){
        if(!StringUtils.isEmpty(redisProperties.getHost())){
            log.info("Redis config 配置方式 === 单机");
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase());
            redisStandaloneConfiguration.setHostName(StringUtils.isEmpty(redisProperties.getHost()) ? "localhost" : redisProperties.getHost());
            redisStandaloneConfiguration.setPort(Objects.nonNull(redisProperties.getPort()) ? redisProperties.getPort() : 6379);
            if(StringUtils.isNotEmpty(redisProperties.getPassword())){
                redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
            }
            return new LettuceConnectionFactory(redisStandaloneConfiguration);
        } else if (Objects.nonNull(redisProperties.getSentinel())){
            log.info("Redis config 配置方式 === 哨兵");
            RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
            redisSentinelConfiguration.setDatabase(redisProperties.getDatabase());
            redisSentinelConfiguration.setMaster(redisProperties.getSentinel().getMaster());
            List<RedisNode> redisNodeList = new ArrayList<>();
            if(!CollectionUtils.isEmpty(redisProperties.getSentinel().getNodes())){
                redisProperties.getSentinel().getNodes().forEach( e -> {
                    String[] nodeInfo = e.split(":");
                    redisNodeList.add(new RedisClusterNode(nodeInfo[0],Integer.parseInt(nodeInfo[1])));
                });
            }
            if(StringUtils.isNotEmpty(redisProperties.getPassword())){
                redisSentinelConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
            }
            return new LettuceConnectionFactory(redisSentinelConfiguration);
        } else if (Objects.nonNull(redisProperties.getCluster())){
            log.info("Redis config 配置方式 === 集群");
            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
            HashSet<RedisNode> redisNodeHashSet = new HashSet<>();
            List<String> redisNodeInfoList = redisProperties.getCluster().getNodes();
            redisNodeInfoList.forEach(e -> {
                e = e.replaceAll("\\s","").replaceAll("\n","");
                if(!StringUtils.equals(e,"")){
                    String[] hostInfo = e.split(":");
                    String host = hostInfo[0];
                    Integer port = Integer.parseInt(hostInfo[1]);
                    redisNodeHashSet.add(new RedisClusterNode(host,port));
                }
            });
            redisClusterConfiguration.setClusterNodes(redisNodeHashSet);
            // 跨集群执行命令时需要遵循最大重定向数量
            redisClusterConfiguration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
            if(StringUtils.isNotEmpty(redisProperties.getPassword())){
                redisClusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
            }

            LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
                    .poolConfig(genericObjectPoolConfig())
                    .build();
            return new LettuceConnectionFactory(redisClusterConfiguration,lettucePoolingClientConfiguration);
        }
        return new LettuceConnectionFactory();
    }

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory(){
//        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
//        lettuceConnectionFactory.setDatabase(redisConfigProperties.getDatabase());
//        lettuceConnectionFactory.setHostName(redisConfigProperties.getHost());
//        lettuceConnectionFactory.setPort(redisConfigProperties.getPort());
//        lettuceConnectionFactory.setPassword(redisConfigProperties.getPassword());
//        return lettuceConnectionFactory;
//    }

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
        if(!StringUtils.isEmpty(redisProperties.getHost())) {
            log.info("Redisson 配置模式 === 单机");
            config.useSingleServer()
                    .setAddress(this.getRedissonAddress(redisProperties.getHost(), redisProperties.getPort()))
                    .setDatabase(redisProperties.getDatabase())
                    .setPassword(StringUtils.isNotEmpty(redisProperties.getPassword()) ? redisProperties.getPassword() : null);

        } else if (Objects.nonNull(redisProperties.getSentinel())){
            log.info("Redisson 配置模式 === 哨兵");
            config.useSentinelServers()
                    .setDatabase(redisProperties.getDatabase())
                    .setMasterName(redisProperties.getSentinel().getMaster())
                    .addSentinelAddress(redisProperties.getSentinel().getNodes().toArray(new String[redisProperties.getSentinel().getNodes().size()]))
                    .setPassword(StringUtils.isNotEmpty(redisProperties.getPassword()) ? redisProperties.getPassword() : null)
                    .setScanInterval(10);

        } else if (Objects.nonNull(redisProperties.getCluster())){
            log.info("Redisson 配置模式 === 集群");
            config.useClusterServers()
                    .addNodeAddress(redisProperties.getCluster().getNodes().toArray(new String[redisProperties.getCluster().getNodes().size()]))
                    .setPassword(StringUtils.isNotEmpty(redisProperties.getPassword()) ? redisProperties.getPassword() : null)
                    .setScanInterval(10);
        }
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

//        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
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
    public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory){
        RedisCacheWriter redisCacheWrapper = RedisCacheWriter.nonLockingRedisCacheWriter(lettuceConnectionFactory);
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
