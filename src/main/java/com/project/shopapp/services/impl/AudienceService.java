package com.project.shopapp.services.impl;

import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.AuthorRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.responses.BaseProjection;
import com.project.shopapp.services.IAudienceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AudienceService implements IAudienceService{
    private UserRepository userRepository;
    private AuthorRepository authorRepository;

    public ResponseEntity<String> sendFollow(String myUsername, String authorUsername){
        User user;
        User author;

        try {
            author = userRepository.findByUsername(authorUsername)
                    .orElseThrow(() -> new DataNotFoundException("Author not found: " + authorUsername));
            if (authorRepository.findByUserIdAndStatus(author.getId()).isEmpty()) {
                return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
            }
            user = userRepository.findByUsername(myUsername)
                    .orElseThrow(() -> new DataNotFoundException("User not found: " + myUsername));
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }


        if (user.equals(author)) {
            return new ResponseEntity<>("Cannot send a follow request to yourself", HttpStatus.FORBIDDEN);
        }

        if (user.getFriends().contains(author)) {
            return new ResponseEntity<>("Follow request already exists", HttpStatus.FORBIDDEN);
        }

        user.getFriends().add(author);
        userRepository.save(user);

        return new ResponseEntity<>("Follow request sent successfully", HttpStatus.ACCEPTED);

        }

    public ResponseEntity<String> deleteFollow(String myUsername, String authorUsername) {
        User user;
        User author;

        try {
            author = userRepository.findByUsername(authorUsername)
                    .orElseThrow(() -> new DataNotFoundException("Author not found: " + authorUsername));
            if (authorRepository.findByUserIdAndStatus(author.getId()).isEmpty()) {
                return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
            }
            user = userRepository.findByUsername(myUsername)
                    .orElseThrow(() -> new DataNotFoundException("User not found: " + myUsername));
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        if (!user.getFriends().contains(author)) {
            return new ResponseEntity<>("No author exists to delete", HttpStatus.FORBIDDEN);
        }

        user.getFriends().remove(author);
        author.getFriends().remove(user);
        userRepository.save(user);
        userRepository.save(author);
        return new ResponseEntity<>("Author follow deleted successfully", HttpStatus.ACCEPTED);
    }

    public List<BaseProjection> getFollows(String myUsername) {
        return userRepository.findFriendsByUsername(myUsername);
    }

}

