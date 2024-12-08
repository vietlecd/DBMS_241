package com.project.shopapp.services;

import com.project.shopapp.DTO.UserDTO;
import com.project.shopapp.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IAuthService {
    User createUser(UserDTO userDTO) throws Exception;
    Map<String, String> login(String phoneNumber, String password) throws Exception;

    ResponseEntity<?> refreshToken(HttpServletRequest request) throws Exception;

    ResponseEntity<?> logout(HttpServletRequest request);
}

