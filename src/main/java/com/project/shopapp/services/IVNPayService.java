package com.project.shopapp.services;

import com.project.shopapp.models.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface IVNPayService {
    String createOrder(int orderTotal, String baseUrl, HttpServletRequest request, User user);

    int orderReturn(HttpServletRequest request);

    ResponseEntity<?> getPaymentsByUser(User user);
}
