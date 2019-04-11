package com.corsair.sparrow.pirate.dashboard.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author jack
 */
@EnableDiscoveryClient
@EnableHystrixDashboard
@EnableTurbine
@SpringBootApplication
public class SparrowPirateDashboardTurbineApplication {

    public static void main(String[] args) {
        SpringApplication.run(SparrowPirateDashboardTurbineApplication.class, args);
    }

}
