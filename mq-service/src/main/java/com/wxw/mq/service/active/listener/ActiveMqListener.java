package com.wxw.mq.service.active.listener;

import com.wxw.mq.service.active.config.ActiveMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;

@Slf4j
@Component
public class ActiveMqListener {

    @JmsListener(destination = ActiveMqConfig.QUEUE_TEST, containerFactory = "jmsListenerQueue")
    public void queueListener1(ActiveMQMessage message, Session session, String msg) throws JMSException {
        try {
            log.info("QueueListener 1 收到消息：{}", msg);
            message.acknowledge();
        } catch (Exception e) {
            session.recover();
        }
    }

    @JmsListener(destination = ActiveMqConfig.QUEUE_TEST, containerFactory = "jmsListenerQueue")
    public void queueListener2(ActiveMQMessage message, Session session, String msg) throws JMSException {
        try {
            log.info("QueueListener 2 收到消息：{}", msg);
            message.acknowledge();
        } catch (Exception e) {
            session.recover();
        }
    }


    @JmsListener(destination = ActiveMqConfig.TOPIC_TEST, containerFactory = "jmsListenerTopic")
    public void topicListener1(ActiveMQMessage message, Session session, String msg) throws JMSException {
        try {
            log.info("TopicListener - 1 收到消息：{}", msg);
            message.acknowledge();
        } catch (Exception e) {
            session.recover();
        }
    }

    @JmsListener(destination = ActiveMqConfig.TOPIC_TEST, containerFactory = "jmsListenerTopic")
    public void topicListener2(ActiveMQMessage message, Session session, String msg) throws JMSException {
        try {
            log.info("TopicListener - 2 收到消息：{}", msg);
            message.acknowledge();
        } catch (Exception e) {
            session.recover();
        }
    }

}
