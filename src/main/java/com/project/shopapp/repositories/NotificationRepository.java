package com.project.shopapp.repositories;

import com.project.shopapp.models.Notification;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
  List<Notification> findByUser(User user);
}
