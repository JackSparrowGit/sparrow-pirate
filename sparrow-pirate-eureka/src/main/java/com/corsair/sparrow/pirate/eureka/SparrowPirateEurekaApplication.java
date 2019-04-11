package com.corsair.sparrow.pirate.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jack
 */
@RestController
@EnableEurekaServer
@SpringBootApplication
public class SparrowPirateEurekaApplication extends SpringBootServletInitializer {

    /**
     * 用于K8s环境健康心跳检测
     * @return
     */
    @GetMapping(path = "/healthz",produces = MediaType.TEXT_PLAIN_VALUE)
    public String healthz(){
        return "ok";
    }

    public static void main(String[] args) {
        SpringApplication.run(SparrowPirateEurekaApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SparrowPirateEurekaApplication.class);
    }
}
