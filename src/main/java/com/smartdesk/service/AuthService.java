package com.smartdesk.service;

import com.smartdesk.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private User loggedInUser = null;

    // Validate username & email (basic example: check non-empty and contains '@' for email)
    public boolean validateUser(String username, String email) {
        if(username == null || username.isEmpty()) return false;
        if(email == null || email.isEmpty() || !email.contains("@")) return false;

        // You can add more validation or dummy user data here
        // Example: accept any username with an '@' in email for demo
        return true;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void clearLoggedInUser() {
        this.loggedInUser = null;
    }
}
