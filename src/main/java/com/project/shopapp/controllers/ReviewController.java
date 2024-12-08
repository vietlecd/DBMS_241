
package com.project.shopapp.controllers;


import com.project.shopapp.DTO.ReviewDTO;
import com.project.shopapp.helpers.AuthenticationHelper;
import com.project.shopapp.models.User;
import com.project.shopapp.services.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/review")
public class ReviewController {

  @Autowired
    IReviewService reviewService;
  @Autowired
  private AuthenticationHelper authenticationHelper;

    // Other endpoints

  @PostMapping("/add/{bookID}")
  public ResponseEntity<?> addReviewToBook(
          @PathVariable Long bookID,
          @RequestBody ReviewDTO reviewDTO,
          Authentication authentication) {
    try {
      User user = authenticationHelper.getUser(authentication);
      return reviewService.addReviewToBook(bookID, reviewDTO, user);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
    }
  }


  // Endpoint mới để tìm tất cả các comments dựa trên bookID
  @GetMapping("/get/{bookID}")
  public ResponseEntity<?> getReviewsByBookId(@PathVariable Long bookID, Authentication authentication) {
    try {
      String username = authenticationHelper.getUsername(authentication);
      return reviewService.findReviewsByBookId(bookID, username);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
    }
  }

  @PutMapping("/update/{reviewID}")
  public ResponseEntity<?> updateReview(
          @PathVariable Long reviewID,
          @RequestBody ReviewDTO reviewDTO,
           Authentication authentication) {
    try {
      User user = authenticationHelper.getUser(authentication);
      return reviewService.updateReview(reviewID, reviewDTO,user);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
    }
  }
}

