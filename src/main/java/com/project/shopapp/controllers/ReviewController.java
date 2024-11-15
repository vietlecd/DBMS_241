
package com.project.shopapp.controllers;


import com.project.shopapp.DTO.ReviewDTO;
import com.project.shopapp.helpers.AuthenticationHelper;
import com.project.shopapp.models.Review;
import com.project.shopapp.models.User;
import com.project.shopapp.services.IBookService;
import com.project.shopapp.services.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
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
            @RequestBody ReviewDTO reviewDTO, Authentication authentication) {
        User user = authenticationHelper.getUser(authentication);

        return reviewService.addReviewToBook(bookID, reviewDTO, user);
    }

  // Endpoint mới để tìm tất cả các comments dựa trên bookID
  @GetMapping("/get/{bookID}")
  public List<ReviewDTO> getReviewsByBookId(@PathVariable Long bookID, Authentication authentication) {
      String username = authenticationHelper.getUsername(authentication);
    return reviewService.findReviewsByBookId(bookID, username);
  }
  @PutMapping("/update/{reviewID}")
  public ReviewDTO updateReview(
          @PathVariable Long reviewID,
          @RequestBody ReviewDTO reviewDTO) {
    return reviewService.updateReview(reviewID, reviewDTO);
  }

}

