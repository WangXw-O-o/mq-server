package com.wxw.mq.web.controller;

import com.wxw.mq.service.rabbit.RabbitMqService;
import com.wxw.mq.service.rabbit.config.RabbitMqConfig;
import com.wxw.mq.service.rabbit.config.TestQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/rabbit/mq")
public class RabbitMqController {

    @Resource
    private RabbitMqService rabbitMqService;

    @GetMapping("/send/queue/{message}")
    public String sendToQueue(@PathVariable("message") String message) {
        rabbitMqService.sendMessage(TestQueueConfig.TEST_EXCHANGE_NAME, "test.hello", message);
        return "send to queue :" ;
    }
}
