package com.corsair.sparrow.pirate.conf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jack
 */
@Data
@ConfigurationProperties(prefix = "spring.redis",ignoreInvalidFields = true)
public class RedisConfigProperties {

    private Integer database;
    private String host;
    private Integer port;
    private String password;

}
