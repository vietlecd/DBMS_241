package com.project.shopapp.controllers;

import com.project.shopapp.helpers.AuthenticationHelper;
import com.project.shopapp.models.Audience;
import com.project.shopapp.models.User;
import com.project.shopapp.services.impl.AudienceService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class AudienceController {
    private final AudienceService audienceService;
    private final AuthenticationHelper authenticationHelper;

    @PostMapping("/addFriend/{friendUsername}")
    public ResponseEntity<String> addFriend(@PathVariable String friendUsername, Authentication authentication) throws Exception {
        String myUsername = authenticationHelper.getUsername(authentication);
        return audienceService.sendFriendRequest(myUsername, friendUsername);
    }

    @PostMapping("/acceptFriend/{friendUsername}")
    public ResponseEntity<String> acceptFriend(@PathVariable String friendUsername, Authentication authentication) throws Exception {
        String myUsername = authenticationHelper.getUsername(authentication);
        return audienceService.acceptedFriend(myUsername, friendUsername);
    }

    @PostMapping("/denyFriend/{friendUsername}")
    public ResponseEntity<String> denyFriend(@PathVariable String friendUsername, Authentication authentication) throws Exception {
        String myUsername = authenticationHelper.getUsername(authentication);
        return audienceService.deniedFriend(myUsername,friendUsername);
    }

    @DeleteMapping("/deleteFriend/{friendUsername}")
    public ResponseEntity<String> deleteFriend(@PathVariable String friendUsername, Authentication authentication) throws Exception {
        String myUsername = authenticationHelper.getUsername(authentication);
        return audienceService.deleteFriend(myUsername,friendUsername);
    }

    @GetMapping("/getFriend")
    public List<String> getFriends(Authentication authentication) throws Exception {
        User currentUser = (User) authentication.getPrincipal();
        String myUsername = currentUser.getUsername();

        return audienceService.getFriends(myUsername);
    }

    @GetMapping("/getFriendRequest")
    public List<String> getRequests(Authentication authentication) throws Exception {
        User currentUser = (User) authentication.getPrincipal();
        String myUsername = currentUser.getUsername();

        return audienceService.getRequest(myUsername);
    }
}
