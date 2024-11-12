package com.project.shopapp.services;

import com.project.shopapp.responses.BaseProjection;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IAudienceService {
    ResponseEntity<String> sendFollow(String myUsername, String friendUsername);

    ResponseEntity<String> deleteFollow(String myUsername, String friendUsername);

    List<BaseProjection> getFollows(String myUsername);


}
