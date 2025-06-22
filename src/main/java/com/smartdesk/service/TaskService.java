package com.smartdesk.service;

import com.smartdesk.model.TaskEntry;
import com.smartdesk.repository.TaskEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskEntryRepository repository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final String TOPIC = "task_logs";

    public TaskEntry addTask(String description) {
        TaskEntry task = new TaskEntry(description);
        TaskEntry saved = repository.save(task);
        kafkaTemplate.send(TOPIC, "Task added: " + description);
        return saved;
    }

    public void markTaskCompleted(Long id) {
        for (TaskEntry task : repository.findAll()) {
            if (task.getId().equals(id)) {
                task.setCompleted(true);
                kafkaTemplate.send("task_logs", "Task marked as completed: " + task.getDescription());
                break;
            }
        }
    }


    public List<TaskEntry> getAllTasks() {
        return repository.findAll();
    }
}
