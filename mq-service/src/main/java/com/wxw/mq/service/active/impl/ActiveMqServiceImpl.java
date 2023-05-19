package com.wxw.mq.service.active.impl;

import com.wxw.mq.service.active.ActiveMqService;
import com.wxw.mq.service.active.config.ActiveMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;

@Service
@Slf4j
public class ActiveMqServiceImpl implements ActiveMqService {

    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Resource(name = "testTopic")
    private Destination destinationTopic;
    @Resource(name = "testQueue")
    private Destination destinationQueue;

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
