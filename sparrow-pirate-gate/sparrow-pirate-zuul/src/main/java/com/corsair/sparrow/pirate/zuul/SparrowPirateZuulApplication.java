package com.corsair.sparrow.pirate.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author jack
 */
@EnableFeignClients
@EnableSwagger2
@EnableZuulProxy
@RestController
@SpringCloudApplication
public class SparrowPirateZuulApplication {

    @GetMapping("/")
    public String hello(){
        return "{\"respCode\":\"10000\",\"respMsg\":\"OK\",\"timestamp\":"+System.currentTimeMillis()+"}";
    }

    /**
     * 轮询
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public PatternServiceRouteMapper patternServiceRouteMapper(){
        return new PatternServiceRouteMapper(
          "(?<name>^.+)-(?<version>v.+$)",
          "${version}/${name}"
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(SparrowPirateZuulApplication.class, args);
    }

}
