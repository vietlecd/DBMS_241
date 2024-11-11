package com.project.shopapp.services.impl;

import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.models.Audience;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.AudienceRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.IAudienceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AudienceService implements IAudienceService{
    private AudienceRepository audienceRepository;
    private UserRepository userRepository;

    public ResponseEntity<String> sendFriendRequest(String myUsername, String friendUsername){
        User user;
        User friend;

        try {
            friend = userRepository.findByUsername(friendUsername)
                    .orElseThrow(() -> new DataNotFoundException("Friend not found: " + friendUsername));
            user = userRepository.findByUsername(myUsername)
                    .orElseThrow(() -> new DataNotFoundException("User not found: " + myUsername));
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

            if (user.getId() == friend.getId()) {
                return new ResponseEntity<>("Cannot create Request with each other", HttpStatus.FORBIDDEN);
            }
            Optional<Audience> existedRelationship = audienceRepository.findByUsers(user.getId(), friend.getId());
            if (existedRelationship.isPresent()) {
                return new ResponseEntity<>("Friends request already existed", HttpStatus.FORBIDDEN);
            }

            Audience audience = Audience.builder()
                    .friend1(user)
                    .friend2(friend.getId())
                    .status(0)
                    .build();

            audienceRepository.save(audience);
            return new ResponseEntity<>("Friend request sent successfully", HttpStatus.ACCEPTED);

        }


    public ResponseEntity<String> acceptedFriend(String myUsername, String friendUsername){
        User user;
        User friend;

        try {
            friend = userRepository.findByUsername(friendUsername)
                    .orElseThrow(() -> new DataNotFoundException("Friend not found: " + friendUsername));
            user = userRepository.findByUsername(myUsername)
                    .orElseThrow(() -> new DataNotFoundException("User not found: " + myUsername));
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

            Optional<Audience> request = audienceRepository.findByUsers(user.getId(), friend.getId());
            if (request.isPresent() && request.get().getStatus() == 0) {

                if (!request.get().getFriend2().equals(user.getId())) {
                    return new ResponseEntity<>("Only recipent can accept", HttpStatus.FORBIDDEN);
                }
                Audience audience = request.get();
                audience.setStatus(1);
                audienceRepository.save(audience);
                return new ResponseEntity<>("Accept friend successfully", HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>("No friend request found", HttpStatus.FORBIDDEN);
        }


    public ResponseEntity<String> deniedFriend(String myUsername, String friendUsername) {
        User user;
        User friend;

        try {
            friend = userRepository.findByUsername(friendUsername)
                    .orElseThrow(() -> new DataNotFoundException("Friend not found: " + friendUsername));
            user = userRepository.findByUsername(myUsername)
                    .orElseThrow(() -> new DataNotFoundException("User not found: " + myUsername));
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        Optional<Audience> request = audienceRepository.findByUsers(user.getId(), friend.getId());
        if (request.isPresent() && request.get().getStatus() == 0) {

            if (!request.get().getFriend2().equals(user.getId())) {
                return new ResponseEntity<>("Only recipent can deny", HttpStatus.FORBIDDEN);
            }
            Audience audience = request.get();
            audience.setStatus(-1);
            audienceRepository.save(audience);
            return new ResponseEntity<>("Deny friend successfully", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("No friend request found", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<String> deleteFriend(String myUsername, String friendUsername) {
        User user;
        User friend;

        try {
            friend = userRepository.findByUsername(friendUsername)
                    .orElseThrow(() -> new DataNotFoundException("Friend not found: " + friendUsername));
            user = userRepository.findByUsername(myUsername)
                    .orElseThrow(() -> new DataNotFoundException("User not found: " + myUsername));
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        Optional<Audience> request = audienceRepository.findByUsers(user.getId(), friend.getId());
        if (request.isPresent() && request.get().getStatus() == 1) {
            Audience audience = request.get();
            audienceRepository.delete(audience);
            return new ResponseEntity<>("Delete friend successfully", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("No friend request found", HttpStatus.FORBIDDEN);
    }

    public List<String> getFriends(String myUsername) {

        User user = userRepository.findByUsername(myUsername)
                    .orElseThrow(() -> new DataNotFoundException("User not found: " + myUsername));

        List<Integer> friendId = audienceRepository.findFriendIdsByUserId(user.getId());

        if (!friendId.isEmpty()) {
            return userRepository.findUsernamesByIds(friendId);
        }
        return Collections.emptyList();
    }

    public List<String> getRequest(String myUsername) {
        User user = userRepository.findByUsername(myUsername)
                .orElseThrow(() -> new DataNotFoundException("User not found: " + myUsername));
        List<Integer> request = audienceRepository.findRequestByUserId(user.getId());

         if(!request.isEmpty()) {
             return userRepository.findUsernamesByIds(request);
         }

        return Collections.emptyList();
    }
}

