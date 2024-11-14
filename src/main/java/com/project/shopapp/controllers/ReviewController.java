
package com.project.shopapp.controllers;


import com.project.shopapp.DTO.ReviewDTO;
import com.project.shopapp.models.Review;
import com.project.shopapp.services.IBookService;
import com.project.shopapp.services.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {

  @Autowired
    IReviewService reviewService;

    // Other endpoints

    @PostMapping("/api/addreview/{bookID}")
    public ReviewDTO addReviewToBook(
            @PathVariable Long bookID,
            @RequestBody ReviewDTO reviewDTO) {

        return reviewService.addReviewToBook(bookID, reviewDTO);
    }
}

