package com.project.shopapp.controllers;

import com.project.shopapp.helpers.AuthenticationHelper;
import com.project.shopapp.models.User;
import com.project.shopapp.services.IClaimService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}")
@AllArgsConstructor
public class ClaimController {
    private IClaimService claimService;
    private AuthenticationHelper authenticationHelper;
    @GetMapping("/view_advertisement")
    public ResponseEntity<?> viewAds (Authentication authentication) {
        User user = authenticationHelper.getUser(authentication);
        return claimService.claimAdPoint(user);
    }

}
