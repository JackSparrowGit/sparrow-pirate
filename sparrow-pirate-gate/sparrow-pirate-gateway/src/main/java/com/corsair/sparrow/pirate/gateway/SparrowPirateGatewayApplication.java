package com.corsair.sparrow.pirate.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author jack
 */
@RestController
@SpringCloudApplication
public class SparrowPirateGatewayApplication {

    /**
     * 代码方式配置路由
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/alpha/**")
//                        .filters(f->f.stripPrefix(1).retry(2))
                        .uri("lb://sparrow-pirate-alpha"))
                .build();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(SparrowPirateGatewayApplication.class, args);
    }


}
