package com.smartdesk.controller;

import com.smartdesk.service.KafkaConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActivityController {

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    @GetMapping("/activity/logs")
    public List<String> getActivityLogs() {
        return kafkaConsumerService.getActivityLog();
    }

    @DeleteMapping("/activity/clear")
    public void clearActivityLogs() {
        kafkaConsumerService.clearActivityLog(); // You'll create this method in the service
    }

}
