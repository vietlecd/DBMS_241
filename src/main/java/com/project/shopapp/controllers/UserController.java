package com.project.shopapp.controllers;

import com.project.shopapp.DTO.UpdateUserDTO;
import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.helpers.AuthenticationHelper;
import com.project.shopapp.models.User;
import com.project.shopapp.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/users")
@AllArgsConstructor
public class UserController {

    private IUserService userService;
    private AuthenticationHelper authenticationHelper;
    @GetMapping("")
    public ResponseEntity<?> userInfo(Authentication authentication) {
        try {
            User user = authenticationHelper.getUser(authentication);
            return userService.userInfo(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDTO updateUserDTO, Authentication authentication) {
        try {
            User user = authenticationHelper.getUser(authentication);
            return userService.updateUser(updateUserDTO, user);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/countPoint")
    public ResponseEntity<?> countUserPoint(Authentication authentication) {
        try {
            User user = authenticationHelper.getUser(authentication);
            return userService.countUserPoint(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
