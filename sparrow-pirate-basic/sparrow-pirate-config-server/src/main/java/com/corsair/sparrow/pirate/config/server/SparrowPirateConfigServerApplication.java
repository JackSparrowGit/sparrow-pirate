package com.corsair.sparrow.pirate.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author jack
 */
@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class SparrowPirateConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SparrowPirateConfigServerApplication.class, args);
    }

}
