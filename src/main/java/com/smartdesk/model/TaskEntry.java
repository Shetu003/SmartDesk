package com.smartdesk.model;

import java.time.LocalDateTime;

public class TaskEntry {
    private Long id;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;

    public TaskEntry() {
        this.createdAt = LocalDateTime.now();
        this.completed = false;
    }

    public TaskEntry(String description) {
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.completed = false;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
