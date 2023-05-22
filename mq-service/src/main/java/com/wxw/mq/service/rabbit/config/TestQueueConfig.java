package com.wxw.mq.service.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestQueueConfig {

    public static final String TEST_EXCHANGE_NAME        = "test.topic.exchange";
    public static final String TEST_QUEUE_NAME           = "test.queue";
    public static final String TEST_ROUTING_KEY          = "test.#";

    //创建交换机
    @Bean("testExchange")
    public Exchange testExchange() {
        return ExchangeBuilder
                .topicExchange(TEST_EXCHANGE_NAME) //设置交换机名称
                .durable(true) //是否持久化
                .autoDelete() //自动删除
                .build();
    }

    //创建队列
    @Bean("testQueue4RabbitMq")
    public Queue testQueue4RabbitMq() {
        return QueueBuilder
                .durable(TEST_QUEUE_NAME) //创建持久的队列
                .autoDelete() //最终队列会自动删除
                .build();
    }

    //绑定队列和交换机
    @Bean
    public Binding bindQueueAndExchange(@Qualifier("testQueue4RabbitMq") Queue queue, @Qualifier("testExchange") Exchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(TEST_ROUTING_KEY) //路由Key
                .noargs();
    }

}
