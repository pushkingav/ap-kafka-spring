package com.apushking.apkafkaspring.service;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class SendMessageService {

    private final KafkaSendService kafkaSendService;

    public SendMessageService(KafkaSendService kafkaSendService) {
        this.kafkaSendService = kafkaSendService;
    }

    @EventListener(value = ContextRefreshedEvent.class)
    public void onRefresh() {
        kafkaSendService.sendMessage("Application Started! And it's the event to store in Kafka.");
    }
}
