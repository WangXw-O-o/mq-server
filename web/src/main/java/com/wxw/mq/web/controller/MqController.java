package com.wxw.mq.web.controller;

import com.wxw.mq.service.active.ActiveMqService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
public class MqController {

    @Resource
    private ActiveMqService activeMqService;


    @GetMapping("/hello")
    public String hello() {
        return "H E L L O ===> " + UUID.randomUUID();
    }

    @GetMapping("/rabbit/mq/send/queue/{message}")
    public String sendToQueue(@PathVariable("message") String message) {
        boolean result = activeMqService.sendStringMessageToQueue(message);
        return "消息发送Queue结果：" + (result ? "成功！" : "失败！");
    }

    @GetMapping("/rabbit/mq/send/topic/{message}")
    public String test(@PathVariable("message") String message) {
        boolean result = activeMqService.sendStringMessageToTopic(message);
        return "消息发送Topic结果：" + (result ? "成功！" : "失败！");
    }

    @GetMapping("/rabbit/mq/consumer")
    public String consumer() {
        String result = activeMqService.consumerStringMessage();
        return "队列数据：" + result;
    }

}
