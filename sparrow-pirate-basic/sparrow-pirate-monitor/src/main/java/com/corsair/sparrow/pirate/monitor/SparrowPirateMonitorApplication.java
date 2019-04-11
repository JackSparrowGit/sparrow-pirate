package com.corsair.sparrow.pirate.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jack
 */
@EnableDiscoveryClient
@EnableAdminServer
@SpringBootApplication
public class SparrowPirateMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SparrowPirateMonitorApplication.class, args);
    }

}
