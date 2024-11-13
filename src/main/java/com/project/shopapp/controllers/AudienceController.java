package com.project.shopapp.controllers;

import com.project.shopapp.helpers.AuthenticationHelper;
import com.project.shopapp.responses.BaseProjection;
import com.project.shopapp.services.IAudienceService;
import com.project.shopapp.services.IAuthorService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AudienceController {
    private final IAudienceService audienceService;
    private final AuthenticationHelper authenticationHelper;

    @PostMapping("/addFollow/{author}")
    public ResponseEntity<String> addFollow(@PathVariable String author, Authentication authentication) {
        String myUsername = authenticationHelper.getUsername(authentication);
        return audienceService.sendFollow(myUsername, author);
    }

    @DeleteMapping("/deleteFollow/{author}")
    public ResponseEntity<String> deleteFollow(@PathVariable String author, Authentication authentication) {
        String myUsername = authenticationHelper.getUsername(authentication);
        return audienceService.deleteFollow(myUsername,author);
    }

    @GetMapping("/getAuthorFollow")
    public List<BaseProjection> getFollow(Authentication authentication) {
        String myUsername = authenticationHelper.getUsername(authentication);
        return audienceService.getFollows(myUsername);
    }


}
