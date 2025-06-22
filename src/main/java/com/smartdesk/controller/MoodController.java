package com.smartdesk.controller;

import com.smartdesk.model.MoodEntry;
import com.smartdesk.service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for exposing REST endpoints to submit and view mood entries.
 */
@RestController
@RequestMapping("/mood")
public class MoodController {

    @Autowired
    private MoodService moodService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Submits a new mood entry.
     * Accepts mood via query parameter ?text=Feeling great
     * Stores it and sends to Kafka topic "mood_logs"
     */
    @GetMapping("/submit")  // Changed from POST to GET to match dashboard.html
    public String submitMood(@RequestParam String text) {
        moodService.saveMood(text);  // Save to in-memory DB
        kafkaTemplate.send("mood_logs", "Mood submitted: " + text);  // Send to Kafka
        return "Mood submitted successfully!";
    }

    /**
     * Returns all saved mood entries (used for mood history feature)
     */
    @GetMapping("/history")
    public List<MoodEntry> getMoodHistory() {
        return moodService.getAll();
    }
}
