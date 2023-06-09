package com.wxw.mq.web.controller;

import com.wxw.mq.service.active.ActiveMqService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping("/active/mq")
public class ActiveMqController {

    @Resource
    private ActiveMqService activeMqService;


    @GetMapping("/hello")
    public String hello() {
        return "H E L L O ===> " + UUID.randomUUID();
    }

    @GetMapping("/send/queue/jms/{message}")
    public String sendToQueueByJMS(@PathVariable("message") String message) {
        String queue = "test.queue.jms";
        //发送消息
        activeMqService.sendByJMS(message, queue);
        //接收消息
        String result = activeMqService.consumerByJMS(queue);
        return "queue:["+queue+"] message：" + result;
    }

    @GetMapping("/send/queue/{message}")
    public String sendToQueue(@PathVariable("message") String message) {
        boolean result = activeMqService.sendStringMessageToQueue(message);
        return "消息发送Queue结果：" + (result ? "成功！" : "失败！");
    }

    @GetMapping("/send/topic/{message}")
    public String sendToTopic(@PathVariable("message") String message) {
        boolean result = activeMqService.sendStringMessageToTopic(message);
        return "消息发送Topic结果：" + (result ? "成功！" : "失败！");
    }

    @GetMapping("/consumer")
    public String consumer() {
        String result = activeMqService.consumerStringMessage();
        return "队列数据：" + result;
    }

}
