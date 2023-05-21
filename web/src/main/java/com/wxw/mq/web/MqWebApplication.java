package com.wxw.mq.web;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@EnableRabbit
@SpringBootApplication(scanBasePackages = "com.wxw.mq.*")
public class MqWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqWebApplication.class, args);
    }

}
