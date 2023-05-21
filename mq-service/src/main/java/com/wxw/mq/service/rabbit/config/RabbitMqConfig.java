package com.wxw.mq.service.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String TEST_EXCHANGE_NAME        = "test.topic.exchange";
    public static final String TEST_QUEUE_NAME           = "test.queue";
    public static final String TEST_PUSH_QUEUE_NAME      = "test.push.queue";
    public static final String TEST_PULL_QUEUE_NAME      = "test.pull.queue";
    public static final String TEST_ROUTING_KEY          = "test.#";

    //网关限流队列
    public static final String EXCHANGE_GATEWAY_PEAK_CLIPPING = "test.gateway.topic.exchange";
    public static final String QUEUE_GATEWAY_PEAK_CLIPPING = "test.gateway.peak.clipping";
    public static final String ROUTING_KEY_GATEWAY_PEAK_CLIPPING = "test.gateway.peak.clipping.#";

    @Bean("testExchange")
    public Exchange testExchange() {
        //durable:是否持久化
        return ExchangeBuilder
                .topicExchange(TEST_EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    //队列
    @Bean("testQueue4RabbitMq")
    public Queue testQueue4RabbitMq() {
        return QueueBuilder
                .durable(TEST_QUEUE_NAME)
                .build();
    }

    /**
     * 绑定队列和交换机
     */
    @Bean
    public Binding bindQueueAndExchange(@Qualifier("testQueue4RabbitMq") Queue queue, @Qualifier("testExchange") Exchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(TEST_ROUTING_KEY)
                .noargs();
    }


    /**
     * 网关交换机
     */
    @Bean("gatewayExchange")
    public Exchange gatewayExchange() {
        return ExchangeBuilder
                .topicExchange(EXCHANGE_GATEWAY_PEAK_CLIPPING)
                .durable(true)
                .build();
    }

    /**
     * 网关削峰队列
     */
    @Bean("gatewayPeakClippingQueue")
    public Queue gatewayPeakClippingQueue() {
        return QueueBuilder
                .durable(QUEUE_GATEWAY_PEAK_CLIPPING)
                .build();
    }

    /**
     * 绑定网关交换机和网关削峰队列
     */
    @Bean
    public Binding bindGatewayExchangeAndPeakClippingQueue(@Qualifier("gatewayExchange") Exchange exchange, @Qualifier("gatewayPeakClippingQueue") Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY_GATEWAY_PEAK_CLIPPING)
                .noargs();
    }
}
