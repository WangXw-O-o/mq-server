package com.wxw.mq.service.active.impl;

import com.wxw.mq.service.active.ActiveMqService;
import com.wxw.mq.service.active.config.ActiveMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;

@Service
@Slf4j
public class ActiveMqServiceImpl implements ActiveMqService {

    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Resource(name = "testTopic")
    private Destination destinationTopic;
    @Resource(name = "testQueue")
    private Destination destinationQueue;

    @Resource
    private ActiveMQConnectionFactory activeMQConnectionFactory;

    @Override
    public boolean sendByJMS(String message, String queueName) {
        try {
            //创建一个连接
            Connection connection = activeMQConnectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建Session会话，设置事务（开启）、应答模式（自动应答）
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //从Session创建一个队列
            Queue queue = session.createQueue(queueName);
            //从Session创建一个生产者
            MessageProducer producer = session.createProducer(queue);
            //生产者参数设置：是否持久化
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            //从Session创建一个消息
            TextMessage textMessage = session.createTextMessage(message);
            //生产者发送消息
            producer.send(textMessage);
            //关闭消息
            session.commit();

            producer.close();
            session.close();
            connection.close();
            return true;
        } catch (Exception e) {
            log.error("send error : ", e);
        }
        return false;
    }

    @Override
    public String consumerByJMS(String queueName) {
        try {
            //创建一个连接
            Connection connection = activeMQConnectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建Session会话，设置事务（开启）、应答模式（自动应答）
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //从Session创建一个队列
            Queue queue = session.createQueue(queueName);
            //从Session创建一个生产者
            MessageConsumer consumer = session.createConsumer(queue);
            //接收消息
            TextMessage textMessage = (TextMessage) consumer.receive();

            consumer.close();
            session.close();
            connection.close();

            return textMessage.getText();
        } catch (Exception e) {
            log.error("error", e);
        }
        return null;
    }

    @Override
    public boolean sendStringMessageToQueue(String message) {
        log.info("发送消息, message={}", message);
        jmsMessagingTemplate.convertAndSend(destinationQueue, message);
        return true;
    }

    @Override
    public boolean sendStringMessageToTopic(String message) {
        log.info("发送消息, message={}", message);
        jmsMessagingTemplate.convertAndSend(destinationTopic, message);
        return true;
    }

    @Override
    public String consumerStringMessage() {
        try {
            //队列没有消息则阻塞
            Message<?> receive = jmsMessagingTemplate.receive(destinationQueue);
            if (receive != null) {
                String message = (String) receive.getPayload();
                log.info("消费消息： message={}", message);
                return message;
            }
        } catch (Exception e) {
            log.error("消费消息异常：", e);
        }
        return "error";
    }
}
