package com.project.shopapp.services;

import com.project.shopapp.models.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.ResponseEntity;

public interface IListReadingService {


    ResponseEntity<?> addList(User user, Integer bookId);

    ResponseEntity<?> deList(User user, Integer bookId);

    ResponseEntity<?> getList(User user);
}
