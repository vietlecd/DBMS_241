package com.project.shopapp.services;

import com.project.shopapp.responses.AudienceRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IAudienceService {
    ResponseEntity<Map<String, Object>> addUserFriends(AudienceRequest audienceRequest);
}
