package com.project.shopapp.utils;

import org.springframework.stereotype.Component;

@Component
public class NotificationUtils {
    public String return_book(String title, String status) {
        String message = "Your book request for '" + title + "' has been " + status;
        return message;
    }

    public String return_author(String status) {
        String message = "Your author request for has been " + status;
        return message;
    }
}
