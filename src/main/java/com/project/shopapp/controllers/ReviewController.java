/*
package com.project.shopapp.controllers;


import com.project.shopapp.models.Review;
import com.project.shopapp.services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {

    @Autowired
    private IBookService bookService;

    // Other endpoints

    @PostMapping("/api/books/{bookID}")
    public String addReviewToBook(
            @PathVariable Long bookID,
            @RequestBody Review review) {
        Review createdReview = bookService.addReviewToBook(bookID, review);
        return createdReview != null ? "Review added successfully to book with ID: " + bookID : "Failed to add review.";
    }
}
*/
