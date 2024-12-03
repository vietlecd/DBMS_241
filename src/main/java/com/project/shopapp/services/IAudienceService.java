package com.project.shopapp.services;

import com.project.shopapp.customexceptions.InvalidParamException;
import com.project.shopapp.models.Author;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.BaseProjection;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface IAudienceService {
    ResponseEntity<String> sendFollow(User user, String authorUsername) throws InvalidParamException;

    ResponseEntity<String> deleteFollow(User user, String authorUsername) throws InvalidParamException;

    Set<Author> getFollows(User user);


}
