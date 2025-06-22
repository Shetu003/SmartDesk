package com.smartdesk.controller;

import com.smartdesk.service.ThoughtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/thought")
public class ThoughtController {

    @Autowired
    private ThoughtService thoughtService;

    @GetMapping("/today")
    public String getThoughtOfTheDay() {
        return thoughtService.getThoughtOfTheDay();
    }
}
