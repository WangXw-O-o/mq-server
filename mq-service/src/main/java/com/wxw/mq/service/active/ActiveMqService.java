package com.wxw.mq.service.active;

public interface ActiveMqService {

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
