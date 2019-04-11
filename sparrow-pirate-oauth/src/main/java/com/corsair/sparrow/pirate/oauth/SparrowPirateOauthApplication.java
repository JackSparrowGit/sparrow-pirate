package com.corsair.sparrow.pirate.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jack
 */
@SpringBootApplication
//@EnableDiscoveryClient
public class SparrowPirateOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SparrowPirateOauthApplication.class, args);
    }

}
