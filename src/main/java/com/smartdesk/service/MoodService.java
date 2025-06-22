package com.smartdesk.service;

import com.smartdesk.model.MoodEntry;
import com.smartdesk.repository.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Business logic for mood submission and analysis.
 */
@Service
public class MoodService {

    @Autowired
    private MoodRepository moodRepository;

    @Autowired
    private KafkaProducerService kafkaProducer;

    public void saveMood(String text) {
        String sentiment = text.toLowerCase().contains("sad") ? "NEGATIVE" : "POSITIVE";
        MoodEntry entry = new MoodEntry(text, sentiment, LocalDateTime.now());
        moodRepository.save(entry);

        // Send log to Kafka
        kafkaProducer.sendMessage("Mood submitted: " + text + " | Sentiment: " + sentiment);
    }

    public List<MoodEntry> getAll() {
        return moodRepository.findAll();
    }


}
