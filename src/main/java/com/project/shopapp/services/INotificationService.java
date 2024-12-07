package com.project.shopapp.services;

import com.project.shopapp.DTO.NotificationDTO;
import com.project.shopapp.models.Notification;
import com.project.shopapp.models.User;

import java.util.List;

public interface INotificationService {
    void createNotification(String message, User user);

    List<NotificationDTO> getNotificationsByUser(User user);
}
