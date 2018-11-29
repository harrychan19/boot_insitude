package com.hsh.amqp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 发布订阅模式的配置,
 * 包括两个队列和对应的订阅者,
 * 发布者的交换机类型使用fanout(子网广播),
 * 两根网线binding用来绑定队列到交换机
 * */

/**
 * @author hushihai
 * @version V1.0, 2018/11/9
 */
@Configuration
public class PublishRabbitConfig {

    @Bean
    public Queue mathQueue() {
        return new Queue(QueueConfig.TEACHER_MATH);
    }

    @Bean
    public Queue chineseQueue() {
        return new Queue(QueueConfig.TEACHER_CHINESE);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("boot_exchange");
    }

    @Bean
    public Binding mathBinding() {
        return BindingBuilder.bind(mathQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding chineseBinding() {
        return BindingBuilder.bind(chineseQueue()).to(fanoutExchange());
    }

}
