package com.wxw.mq.service.active.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
public class ActiveMqConfig {

    private static final String USER_NAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String BROKER_URL = "failover:(tcp://127.0.0.1:61616)";

    public static final String QUEUE_TEST = "active.queue.test";
    public static final String TOPIC_TEST = "active.topic.test";

    @Bean("testQueue")
    public Queue queue() {
        return new ActiveMQQueue(QUEUE_TEST);
    }

    @Bean("testTopic")
    public Topic topic() {
        return new ActiveMQTopic(TOPIC_TEST);
    }

    /**
     * 连接工厂
     * @return
     */
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setUserName(USER_NAME);
        factory.setPassword(PASSWORD);
        factory.setBrokerURL(BROKER_URL);
        return factory;
    }

    /**
     * 监听器容器——Queue
     * @param connectionFactory
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerQueue(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // 关闭Session事务，手动确认与事务冲突
        factory.setSessionTransacted(false);
        /* 设置消息的签收模式，即消费者收到消息后怎么确认收到
         * AUTO_ACKNOWLEDGE = 1 ：自动确认
         * CLIENT_ACKNOWLEDGE = 2：客户端手动确认
         * DUPS_OK_ACKNOWLEDGE = 3： 自动批量确认
         * SESSION_TRANSACTED = 0：事务提交并确认
         * 但是在activemq补充了一个自定义的ACK模式: INDIVIDUAL_ACKNOWLEDGE = 4：单条消息确认
         * */
        factory.setSessionAcknowledgeMode(4);
        //此处设置消息重发规则，redeliveryPolicy() 中定义
        connectionFactory.setRedeliveryPolicy(redeliveryPolicy());
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    /**
     * 监听器容器——Topic
     * @param connectionFactory
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerTopic(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        // 关闭Session事务，手动确认与事务冲突
        bean.setSessionTransacted(false);
        bean.setSessionAcknowledgeMode(4);
        //设置为发布订阅方式, 默认情况下使用的生产消费者方式
        bean.setPubSubDomain(true);
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }

    /**
     * 消息的重发规则配置
     */
    @Bean
    public RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        // 是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        // 重发次数五次， 总共六次
        redeliveryPolicy.setMaximumRedeliveries(5);
        // 重发时间间隔,默认为1000ms（1秒）
        redeliveryPolicy.setInitialRedeliveryDelay(1000);
        // 重发时长递增的时间倍数2
        redeliveryPolicy.setBackOffMultiplier(2);
        // 是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        // 设置重发最大拖延时间-1表示无延迟限制
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
        return redeliveryPolicy;
    }

}
