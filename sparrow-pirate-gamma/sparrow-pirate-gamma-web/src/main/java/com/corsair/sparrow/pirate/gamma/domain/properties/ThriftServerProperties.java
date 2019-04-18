package com.corsair.sparrow.pirate.gamma.domain.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jack
 */
@Data
@ConfigurationProperties(prefix = "thrift.server",ignoreInvalidFields = true)
public class ThriftServerProperties {

    private String zkList;
}
