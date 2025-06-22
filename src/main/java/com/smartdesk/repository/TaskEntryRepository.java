package com.smartdesk.repository;

import com.smartdesk.model.TaskEntry;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TaskEntryRepository {

    private final List<TaskEntry> tasks = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public TaskEntry save(TaskEntry task) {
        task.setId(idGenerator.getAndIncrement());
        tasks.add(task);
        return task;
    }

    public List<TaskEntry> findAll() {
        return tasks;
    }
}
