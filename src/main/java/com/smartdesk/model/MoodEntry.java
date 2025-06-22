package com.smartdesk.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class for storing mood journal entries in the database.
 */
@Entity
public class MoodEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;              // The mood content submitted by user
    private String sentiment;         // "POSITIVE" or "NEGATIVE"
    private LocalDateTime timestamp;  // Time of submission

    public MoodEntry() {}

    public MoodEntry(String text, String sentiment, LocalDateTime timestamp) {
        this.text = text;
        this.sentiment = sentiment;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getSentiment() { return sentiment; }
    public void setSentiment(String sentiment) { this.sentiment = sentiment; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
