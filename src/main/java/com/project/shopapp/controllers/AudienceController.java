package com.project.shopapp.controllers;

import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.customexceptions.InvalidParamException;
import com.project.shopapp.helpers.AuthenticationHelper;
import com.project.shopapp.models.Author;
import com.project.shopapp.models.User;
import com.project.shopapp.services.IAudienceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/audience")
@AllArgsConstructor
public class AudienceController {
    private final IAudienceService audienceService;
    private final AuthenticationHelper authenticationHelper;

    @PostMapping("/addFollow")
    public ResponseEntity<String> addFollow(@RequestParam String authorUsername, Authentication authentication) {
        try {
            User user = authenticationHelper.getUser(authentication);
            return audienceService.sendFollow(user, authorUsername);
        } catch (InvalidParamException | DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteFollow")
    public ResponseEntity<String> deleteFollow(@RequestParam String author, Authentication authentication) {
        try {
            User user = authenticationHelper.getUser(authentication);
            return audienceService.deleteFollow(user,author);
        } catch (InvalidParamException | DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getFollow")
    public ResponseEntity<?> getFollow(Authentication authentication) {
        try {
            User user = authenticationHelper.getUser(authentication);
            Set<Author> authorFollowed = audienceService.getFollows(user);
            return ResponseEntity.ok(authorFollowed);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
