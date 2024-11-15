package com.project.shopapp.services;

import com.project.shopapp.models.Point;
import com.project.shopapp.models.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface IClaimService {
    ResponseEntity<?> claimStreakPoint(User user);
    ResponseEntity<?> claimAdPoint(User user);

}
