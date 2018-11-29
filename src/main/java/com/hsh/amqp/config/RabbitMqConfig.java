package com.hsh.amqp.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hushihai
 * @version V1.0, 2018/11/8
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue queue() {
        return new Queue(QueueConfig.STUDENT_QUEUE);
    }
}
