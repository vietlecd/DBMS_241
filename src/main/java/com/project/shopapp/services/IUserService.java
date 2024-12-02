package com.project.shopapp.services;

import com.project.shopapp.DTO.UpdateUserDTO;
import com.project.shopapp.models.User;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<?> userInfo(User user);

    ResponseEntity<?> updateUser(UpdateUserDTO updateUserDTO, User user);
}
