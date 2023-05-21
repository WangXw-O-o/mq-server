package com.wxw.mq.web.controller;

import com.wxw.mq.service.kafka.KafkaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Resource
    private KafkaService kafkaService;


    @GetMapping("/send/{message}")
    public String send(@PathVariable("message") String message) {
        kafkaService.send(message);
        return "kafka send ....";
    }

}
