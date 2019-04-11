package com.corsair.sparrow.pirate;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jack
 */
@SpringCloudApplication
@ComponentScan(basePackages = {"com.corsair.sparrow.pirate"})
public class SparrowPirateAlphaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparrowPirateAlphaApplication.class, args);
	}

}
