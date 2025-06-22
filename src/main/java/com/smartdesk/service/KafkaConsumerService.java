package com.smartdesk.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Kafka Consumer to listen for messages from different SmartDesk modules.
 * Also stores a combined activity log for the Activity Feed.
 */
@Profile("!prod")
@Service
public class KafkaConsumerService {

    // Stores recent activity messages (newest first)
    private final List<String> activityLog = new LinkedList<>();

    /**
     * Returns the stored activity logs (used by ActivityController).
     */
    public List<String> getActivityLog() {
        return activityLog;
    }

    /**
     * Listens to the 'mood_logs' topic and logs mood messages.
     *
     * @param record the message received from Kafka
     */
    @KafkaListener(topics = "mood_logs", groupId = "smartdesk-group")
    public void listenMoodLogs(ConsumerRecord<String, String> record) {
        String message = "🔔 Mood Log: " + record.value();
        System.out.println(message);
        activityLog.add(0, message); // Add at beginning for most recent first
    }

    /**
     * Listens to the 'task_logs' topic and logs task messages.
     *
     * @param record the message received from Kafka
     */
    @KafkaListener(topics = "task_logs", groupId = "smartdesk-group")
    public void listenTaskLogs(ConsumerRecord<String, String> record) {
        String message = "📝 Task Log: " + record.value();
        System.out.println(message);
        activityLog.add(0, message);
    }

    public void clearActivityLog() {
        activityLog.clear();
    }

}
