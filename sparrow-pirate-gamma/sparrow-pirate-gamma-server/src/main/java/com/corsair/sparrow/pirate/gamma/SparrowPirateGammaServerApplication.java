package com.corsair.sparrow.pirate.gamma;

import com.corsair.sparrow.pirate.gamma.config.ThriftServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author jack
 */
@Import(ThriftServerConfig.class)
@SpringBootApplication
public class SparrowPirateGammaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SparrowPirateGammaServerApplication.class, args);
    }

}
