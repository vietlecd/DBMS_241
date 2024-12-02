package com.project.shopapp.controllers;

import com.project.shopapp.DTO.UpdateUserDTO;
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
        User user = authenticationHelper.getUser(authentication);
        return userService.userInfo(user);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDTO updateUserDTO, Authentication authentication) {
        User user = authenticationHelper.getUser(authentication);
        return userService.updateUser(updateUserDTO, user);
    }
}
