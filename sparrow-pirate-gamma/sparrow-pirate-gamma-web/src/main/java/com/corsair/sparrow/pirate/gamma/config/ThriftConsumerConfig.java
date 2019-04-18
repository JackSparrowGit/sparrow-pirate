package com.corsair.sparrow.pirate.gamma.config;

import com.corsair.sparrow.pirate.gamma.domain.properties.ThriftServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jack
 */
@Configuration
@EnableConfigurationProperties(value = ThriftServerProperties.class)
public class ThriftConsumerConfig {

    @Autowired
    private ThriftServerProperties thriftServerProperties;

    @Bean
    public BeanPostProcessor beanPostProcessor(ThriftServerDiscovery discovery, ThriftConsumerProxy proxy) {
        ThriftRefAnnBeanPostProcessor processor = new ThriftRefAnnBeanPostProcessor();
        processor.setDiscovery(discovery);
        processor.setProxy(proxy);
        return processor;
    }

    @Bean
    public ThriftServerDiscovery discovery() {
        ThriftServerDiscovery discovery = new ThriftServerDiscovery();
        discovery.setZkServerList(thriftServerProperties.getZkList());
        return discovery;
    }

    @Bean
    public ThriftConsumerProxy proxy() {
        ThriftConsumerProxy proxy = new ThriftConsumerProxy();
        return proxy;
    }
}
