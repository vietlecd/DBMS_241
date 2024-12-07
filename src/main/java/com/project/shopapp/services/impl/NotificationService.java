package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.NotificationDTO;
import com.project.shopapp.models.Notification;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.NotificationRepository;
import com.project.shopapp.services.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void createNotification(String message, User user) {
        Notification notification = new Notification(message,user);
        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationDTO> getNotificationsByUser(User user) {
        List<Notification> notifications = notificationRepository.findByUser(user);

        List<NotificationDTO> notificationDTOs = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = NotificationDTO.builder()
                    .nid(notification.getNid())
                    .messagedate(notification.getMessagedate().toString())
                    .messagecontent(notification.getMessagecontent())
                    .build();
            notificationDTOs.add(notificationDTO);
        }

        return notificationDTOs;
    }
}
