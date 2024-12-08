package com.project.shopapp.controllers;

import com.project.shopapp.DTO.UserDTO;
import com.project.shopapp.DTO.UserLoginDTO;
import com.project.shopapp.components.CookieUtil;
import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.helpers.AuthenticationHelper;
import com.project.shopapp.services.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/users")
@AllArgsConstructor
public class AuthController {

    private IAuthService userService;
    private AuthenticationHelper authenticationHelper;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO,
                                        BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("Password not match");
            }
            userService.createUser(userDTO);//return ResponseEntity.ok("Register successfully");
            return ResponseEntity.ok("Registered successful");
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()); //rule 5
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login (
            @Valid @RequestBody UserLoginDTO userLoginDTO,
            HttpServletResponse response) {
        try {
            Map<String, String> token = userService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword());

            // Trả về token trong response
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            return userService.logout(request);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        try {
            return userService.refreshToken(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
