package com.project.shopapp.utils;

import org.springframework.stereotype.Component;

@Component
public class NotificationUtils {
    public String return_book(String title, String status) {
        return "Your book request for '" + title + "' has been " + status;
    }

    public String return_author(String status) {
        return "Your author request for has been " + status;
    }

    public String return_payment(String status) {
        return "Your payment request for has been " + status;
    }
}
