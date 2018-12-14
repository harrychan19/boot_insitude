package com.hsh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author hushihai
 * @version V1.0, 2018/11/6
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@MapperScan(basePackages = "com.hsh.dao")
public class BootInsitudeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootInsitudeApplication.class,args);
    }
}
