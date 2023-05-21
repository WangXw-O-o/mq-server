package com.wxw.mq.service.active;

public interface ActiveMqService {

    /**
     * 不使用SpringJMS发送消息，JMS规范方式发送消息流程
     * @param message
     * @return
     */
    boolean sendByJMS(String message, String queueName);

    /**
     * JMS规范方式接收消息流程
     * @return
     */
    String consumerByJMS(String queueName);

    /**
     * 发送字符串消息到队列
     * @param message
     * @return
     */
    boolean sendStringMessageToQueue(String message);

    /**
     * 发送字符串消息到主题
     * @param message
     * @return
     */
    boolean sendStringMessageToTopic(String message);

    /**
     * 获取队列数据
     * @return
     */
    String consumerStringMessage();

}
