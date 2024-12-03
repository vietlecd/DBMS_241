package com.project.shopapp.services.impl;

import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.customexceptions.InvalidParamException;
import com.project.shopapp.customexceptions.PermissionDenyException;
import com.project.shopapp.models.Author;
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
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AudienceService implements IAudienceService{
    private UserRepository userRepository;
    private AuthorRepository authorRepository;

    @Override
    public ResponseEntity<String> sendFollow(User user, String authorUsername) throws InvalidParamException {
        Author author = findAuthor(user, authorUsername);
        user.followAuthor(author);
        author.addFollower(user);
        userRepository.save(user);
        return new ResponseEntity<>("Follow request sent successfully", HttpStatus.ACCEPTED);

        }

    public ResponseEntity<String> deleteFollow(User user, String authorUsername) throws InvalidParamException {
        Author author = findAuthor(user, authorUsername);
        user.unfollowAuthor(author);
        author.deleteFollower(user);
        userRepository.save(user);
        return new ResponseEntity<>("Follow request sent successfully", HttpStatus.ACCEPTED);
    }

    private Author findAuthor(User user, String authorUsername) throws InvalidParamException {
        Author author = authorRepository.findAuthorByUser_Username(authorUsername);
        if (author == null) {
            throw new DataNotFoundException("Author not found: " + authorUsername);
        }

        if(user.getFollowedAuthor().contains(author)) {
            throw new InvalidParamException("Already follow this author: " + authorUsername);
        }
        return author;
    }

    public Set<Author> getFollows(User user) {
        Set<Author> followedAuthor = user.getFollowedAuthor();
        if (followedAuthor.isEmpty() ) {
            throw new DataNotFoundException("Author not found");
        }
        return user.getFollowedAuthor();
    }

}

