package com.corsair.sparrow.pirate.conf.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * @author jack
 */
@Configuration
public class MultipartConfig {

    /**
     * 富文本请求临时存放文件夹
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
        String location = System.getProperty("user.dir") + "/data/tmp";
        File tempFile = new File(location);
        if (!tempFile.exists()){
            tempFile.mkdirs();
        }
        multipartConfigFactory.setLocation(location);
        return multipartConfigFactory.createMultipartConfig();
    }
}
