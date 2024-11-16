package com.project.shopapp.services;

import com.project.shopapp.models.User;
import org.springframework.http.ResponseEntity;

public interface IListReadingService {


    ResponseEntity<?> addList(User user, Long bookId);

    ResponseEntity<?> deList(User user, Long bookId);

    ResponseEntity<?> getList(User user);
}
