package com.smartdesk.controller;

import com.smartdesk.model.TaskEntry;
import com.smartdesk.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/task/add")
    public String addTask(@RequestParam String description) {
        taskService.addTask(description);
        return "Task added successfully!";
    }

    @GetMapping("/task/all")
    public List<TaskEntry> getTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/task/complete/{id}")
    public String completeTask(@PathVariable Long id) {
        taskService.markTaskCompleted(id);
        return "Task marked as completed!";
    }

}
