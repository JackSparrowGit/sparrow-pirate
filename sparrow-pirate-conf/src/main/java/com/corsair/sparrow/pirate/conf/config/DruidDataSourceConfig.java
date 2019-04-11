package com.corsair.sparrow.pirate.conf.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author jack
 */
@Configuration
@ConditionalOnClass(com.alibaba.druid.pool.DruidDataSource.class)
@ConditionalOnProperty(name = "spring.datasource.type",havingValue = "com.alibaba.druid.pool.DruidDataSource",matchIfMissing = true)
public class DruidDataSourceConfig {

    @Bean
    @ConfigurationProperties(value = "spring.datasource.druid")
    @ConditionalOnMissingBean
    public DataSource dataSource(){
        return DruidDataSourceBuilder.create().build();
    }
}
