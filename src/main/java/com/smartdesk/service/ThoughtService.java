package com.smartdesk.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ThoughtService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ThoughtService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private final List<String> quotes = List.of(
            "Believe you can and you're halfway there.",
            "The harder you work for something, the greater you’ll feel when you achieve it.",
            "Dream it. Wish it. Do it.",
            "Push yourself, because no one else is going to do it for you.",
            "Sometimes we’re tested not to show our weaknesses, but to discover our strengths.",
            "Don’t stop when you’re tired. Stop when you’re done.",
            "Success doesn’t just find you. You have to go out and get it."
    );

    public String getThoughtOfTheDay() {
        int dayIndex = LocalDate.now().getDayOfYear() % quotes.size();
        String thought = quotes.get(dayIndex);
        kafkaTemplate.send("thought_logs", "Thought of the Day: " + thought);
        return thought;
    }
}
