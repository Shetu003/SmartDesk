package com.smartdesk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service responsible for sending mood messages to Kafka.
 */
@Service
@Profile("!prod")
public class KafkaProducerService {

    private final String TOPIC = "mood_logs";  // Kafka topic name

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
