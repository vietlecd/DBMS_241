package com.project.shopapp.controllers;

import com.project.shopapp.helpers.AuthenticationHelper;
import com.project.shopapp.models.User;
import com.project.shopapp.services.IListReadingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/list")
@AllArgsConstructor
public class ListReadingController {
    private AuthenticationHelper authenticationHelper;
    private IListReadingService listReadingService;
    @PostMapping("/add")
    public ResponseEntity<?> addList(@RequestParam(name = "bookId") Long bookId, Authentication authentication){
        User user = authenticationHelper.getUser(authentication);
        return listReadingService.addList(user, bookId);
    }

    @DeleteMapping("/delist")
    public ResponseEntity<?> deList(@RequestParam(name = "bookId") Long bookId, Authentication authentication) {
        User user = authenticationHelper.getUser(authentication);
        return listReadingService.deList(user, bookId);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getList(Authentication authentication) {
        User user = authenticationHelper.getUser(authentication);
        return listReadingService.getList(user);
    }
}
