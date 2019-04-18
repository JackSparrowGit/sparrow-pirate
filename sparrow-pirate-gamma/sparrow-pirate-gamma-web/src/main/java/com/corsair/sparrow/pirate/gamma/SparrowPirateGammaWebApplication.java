package com.corsair.sparrow.pirate.gamma;

import com.corsair.sparrow.pirate.gamma.config.ThriftConsumerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author jack
 */
@Import(ThriftConsumerConfig.class)
@SpringBootApplication
public class SparrowPirateGammaWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SparrowPirateGammaWebApplication.class, args);
    }

}
