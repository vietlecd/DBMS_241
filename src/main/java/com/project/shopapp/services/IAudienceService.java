package com.project.shopapp.services;

import com.project.shopapp.responses.AudienceRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IAudienceService {
    ResponseEntity<String> sendFriendRequest(String myUsername, String friendUsername);

    ResponseEntity<String> acceptedFriend(String myUsername, String friendUsername);

    ResponseEntity<String> deniedFriend(String myUsername, String friendUsername);

    ResponseEntity<String> deleteFriend(String myUsername, String friendUsername);

    List<String> getFriends(String myUsername);

    List<String> getRequest(String myUsername);
}
