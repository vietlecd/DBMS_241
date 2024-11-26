package com.project.shopapp.controllers;

import com.project.shopapp.components.CookieUtil;
import com.project.shopapp.components.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private JwtTokenUtil jwtTokenUtil;
    @GetMapping
    public ResponseEntity<?> userInfo(HttpServletRequest request) {
        String token = CookieUtil.getTokenCookieName(request);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
        }

        String username = jwtTokenUtil.extractUsername(token);
        return ResponseEntity.ok("User: " + username);
    }
}
