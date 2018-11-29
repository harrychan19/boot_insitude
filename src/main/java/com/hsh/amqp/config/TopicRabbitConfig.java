package com.hsh.amqp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hushihai
 * @version V1.0, 2018/11/9
 */
@Configuration
public class TopicRabbitConfig {

    @Bean
    public Queue topicLemon() {
        return new Queue(QueueConfig.LEMON);
    }

    @Bean
    public Queue topicApple() {
        return new Queue(QueueConfig.APPLE);
    }

    @Bean
    public Queue topicOrange() {
        return new Queue(QueueConfig.ORANGE);
    }

    @Bean
    public Queue topicOrangeSweet() {
        return new Queue(QueueConfig.ORANGE_SWEET);
    }

    @Bean
    public Queue topicAppleSweet() {
        return new Queue(QueueConfig.APPLE_SWEET);
    }

    /**
     * 定义个topic交换器
     * @return
     */
    @Bean
    TopicExchange topicExchange() {
        // 定义一个名为fanoutExchange的fanout交换器
        return new TopicExchange(QueueConfig.TOPIC_EXCHANGE);
    }

    /**
     * 将定义的topicA队列与topicExchange交换机绑定
     * @return
     */
    @Bean
    public Binding bindingTopicExchangeWithLemon() {
        return BindingBuilder.bind(topicLemon()).to(topicExchange()).with("topic.lemon");
    }

    /**
     * 将定义的topicB队列与topicExchange交换机绑定
     * @return
     */
    @Bean
    public Binding bindingTopicExchangeWithApple() {
        return BindingBuilder.bind(topicApple()).to(topicExchange()).with("topic.apple.#");
    }

    /**
     * 将定义的topicC队列与topicExchange交换机绑定
     * @return
     */
    @Bean
    public Binding bindingTopicExchangeWithOrange() {
        return BindingBuilder.bind(topicOrange()).to(topicExchange()).with("topic.*.orange");
    }
}
