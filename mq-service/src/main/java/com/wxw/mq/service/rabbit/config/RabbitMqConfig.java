package com.wxw.mq.service.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {


    public static final String TEST_PUSH_QUEUE_NAME      = "test.push.queue";
    public static final String TEST_PULL_QUEUE_NAME      = "test.pull.queue";

    //网关限流队列
    public static final String EXCHANGE_GATEWAY_PEAK_CLIPPING = "test.gateway.topic.exchange";
    public static final String QUEUE_GATEWAY_PEAK_CLIPPING = "test.gateway.peak.clipping";
    public static final String ROUTING_KEY_GATEWAY_PEAK_CLIPPING = "test.gateway.peak.clipping.#";




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
