package com.corsair.sparrow.pirate.oauth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author jack
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis",ignoreUnknownFields = true)
public class RedisConfigProperties {

    private String host;
    private Integer port;
    private Integer database;
    private String password;
    private Duration timeout;

}
