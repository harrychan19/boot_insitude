package com.hsh.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * @author hushihai
 * @version V1.0, 2018/12/23
 */
@Configuration
@PropertySource("classpath:zookeeper.properties")
@ImportResource("classpath:spring-job.xml")
public class ElasticJobConfig {

}