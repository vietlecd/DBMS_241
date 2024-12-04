package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.UpdateUserDTO;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private UserRepository userRepository;
    @Override
    public ResponseEntity<?> userInfo(User user) {
        UpdateUserDTO res = UpdateUserDTO.builder()
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .username(user.getUsername())
                .build();

        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<?> updateUser(UpdateUserDTO updateUserDTO, User user) {
        try {
            if (updateUserDTO == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Du lieu kh duoc de trong");
            }
            String username= updateUserDTO.getUsername();
            String phoneNumber = updateUserDTO.getPhoneNumber();

            if (username!= null) {
                if (userRepository.existsByUsername(username)) {
                    return ResponseEntity.badRequest().body("username has been set yet.");
                }
                user.setUsername(updateUserDTO.getUsername());
            }
            if (phoneNumber != null) {
                if (userRepository.existsByPhoneNumber(phoneNumber)) {
                    return ResponseEntity.badRequest().body("phoneNumber has been set yet.");
                }
                user.setPhoneNumber(updateUserDTO.getPhoneNumber());
            }
            if (updateUserDTO.getFullName() != null) {
                user.setFullName(updateUserDTO.getFullName());
            }
            userRepository.save(user);

            return ResponseEntity.ok("Da cap nhat thanh cong");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Da xay ra loi khi cap nhat du lieu");
        }
    }
}
