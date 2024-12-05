package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.UpdateUserDTO;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.IUserService;
import com.project.shopapp.utils.CheckExistedUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private UserRepository userRepository;
    private CheckExistedUtils checkExistedUtils;
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
            checkExistedUtils.checkObjectExisted(updateUserDTO, "Data");

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
    }

    @Override
    public ResponseEntity<?> countUserPoint(User user) {
        int res = user.getTotalPoint();
        if (res == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Point Not Found");
        }
        return ResponseEntity.ok(res);
    }
}
