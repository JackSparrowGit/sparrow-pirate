package com.corsair.sparrow.pirate.gamma.config;

import com.corsair.sparrow.pirate.gamma.domain.properties.ThriftServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jack
 */
@Configuration
@EnableConfigurationProperties(value = ThriftServerProperties.class)
public class ThriftServerConfig {

    @Autowired
    private ThriftServerProperties thriftServerProperties;

    @Bean
    public ThriftServerRegistry register() {
        ThriftServerRegistry register = new ThriftServerRegistry();
        register.setZkServerList(thriftServerProperties.getZkList());
        register.setServerHost(thriftServerProperties.getHost());
        register.setServerPort(thriftServerProperties.getPort());
        return register;
    }
}
