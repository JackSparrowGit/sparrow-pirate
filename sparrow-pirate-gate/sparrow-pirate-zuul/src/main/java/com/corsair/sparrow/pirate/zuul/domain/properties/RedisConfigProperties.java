package com.corsair.sparrow.pirate.zuul.domain.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jack
 */
@Data
@ConfigurationProperties(prefix = "spring.redis",ignoreInvalidFields = true,ignoreUnknownFields = true)
public class RedisConfigProperties {

    private String host;
    private Integer port;
    private Integer database;
    private String password;
}
