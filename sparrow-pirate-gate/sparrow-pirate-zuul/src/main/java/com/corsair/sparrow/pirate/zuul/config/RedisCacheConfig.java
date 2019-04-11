package com.corsair.sparrow.pirate.zuul.config;

import com.corsair.sparrow.pirate.zuul.domain.properties.RedisConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author jack
 */
@Slf4j
@Configuration
@EnableCaching
@EnableConfigurationProperties(value = RedisConfigProperties.class)
public class RedisCacheConfig {

    @Autowired
    private RedisConfigProperties redisConfigProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        lettuceConnectionFactory.setHostName(redisConfigProperties.getHost());
        lettuceConnectionFactory.setDatabase(redisConfigProperties.getDatabase());
        lettuceConnectionFactory.setPort(redisConfigProperties.getPort());
        lettuceConnectionFactory.setPassword(redisConfigProperties.getPassword());
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate(){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();

        // key serializer
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // value serializer
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);

        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        redisTemplate.setDefaultSerializer(genericJackson2JsonRedisSerializer);

        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

}
