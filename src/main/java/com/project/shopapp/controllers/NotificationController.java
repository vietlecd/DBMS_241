package com.project.shopapp.controllers;

import com.project.shopapp.models.Notification;
import com.project.shopapp.models.User;
import com.project.shopapp.services.IBookService;

import com.project.shopapp.helpers.AuthenticationHelper;

import com.project.shopapp.services.impl.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}")
public class NotificationController {

    @Autowired
    private IBookService bookService;
    @Autowired
    private AuthenticationHelper authenticationHelper;
    @Autowired
    private NotificationService notificationService;

    // API tạo thông báo


    // API lấy tất cả thông báo của người dùng
    @GetMapping("/getNotifications")
    public ResponseEntity<?> getNotifications(Authentication authentication) {
        User user  = authenticationHelper.getUser(authentication);
        try {
            return ResponseEntity.ok(notificationService.getNotificationsByUser(user));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }


        // API đánh dấu thông báo là đã đọc
  /*  @PostMapping("/markNotificationAsRead")
    public ResponseEntity<?> markNotificationAsRead(@RequestParam Long notificationId) {
        try {
            notificationService.markAsRead(notificationId);
            return ResponseEntity.ok("Notification marked as read");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }*/
    }
}