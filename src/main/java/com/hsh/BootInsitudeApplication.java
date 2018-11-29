package com.hsh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author hushihai
 * @version V1.0, 2018/11/6
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class BootInsitudeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootInsitudeApplication.class,args);
    }
}
