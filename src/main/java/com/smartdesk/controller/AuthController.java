package com.smartdesk.controller;

import com.smartdesk.model.User;
import com.smartdesk.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String email) {
        boolean valid = authService.validateUser(username, email);
        if (valid) {
            authService.setLoggedInUser(new User(username, email));  // save logged in user in memory/session
            return "success";
        } else {
            return "Invalid username or email";
        }
    }

    @GetMapping("/user")
    public User getUser() {
        return authService.getLoggedInUser();
    }

    @PostMapping("/logout")
    public String logout() {
        authService.clearLoggedInUser();
        return "logged out";
    }
}
