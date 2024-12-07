package com.project.shopapp.services;

import com.project.shopapp.models.User;
import org.springframework.http.ResponseEntity;

public interface IClaimService {
    void claimStreakPoint(User user);

}
