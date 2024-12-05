package com.project.shopapp.services.impl;

import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.customexceptions.InvalidParamException;
import com.project.shopapp.helpers.AuthorHelper;
import com.project.shopapp.models.Author;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.AuthorRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.IAudienceService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AudienceService implements IAudienceService{
    private UserRepository userRepository;
    private AuthorRepository authorRepository;
    private AuthorHelper authorHelper;

    @Transactional
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
        Optional<Author> author = authorHelper.getAuthorByUsernameAndStatus(authorUsername, 1);
        if (author.isEmpty()) {
            throw new DataNotFoundException("Author not found: " + authorUsername);
        }
        Author author1 = author.get();

        if(user.getFollowedAuthor().contains(author1)) {
            throw new InvalidParamException("Already follow this author: " + authorUsername);
        }
        return author1;
    }

    public Set<Author> getFollows(User user) {
        Set<Author> followedAuthor = user.getFollowedAuthor();
        if (followedAuthor.isEmpty() ) {
            throw new DataNotFoundException("Author not found");
        }
        return user.getFollowedAuthor();
    }

}

