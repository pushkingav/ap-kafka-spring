package com.apushking.apkafkaspring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class KafkaSendService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topicName;

    public KafkaSendService(KafkaTemplate<String, String> kafkaTemplate,
                            @Value("${kafka.topic.name}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    public void sendMessage(String msg) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, msg);
        future.whenComplete((result, throwable) -> {
           if (throwable == null) {
               log.info("Sent message: [{}] with offset =[{}]",msg , result.getRecordMetadata().offset());
           } else {
               log.error("Unable to send message", throwable);
           }
        });

    }
}
