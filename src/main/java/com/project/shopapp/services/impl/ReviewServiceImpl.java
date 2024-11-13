/*
package com.project.shopapp.services.impl;


import com.project.shopapp.entities.Review;
import com.project.shopapp.models.Book;
import com.project.shopapp.repositories.BookRepository;
import com.project.shopapp.repositories.ReviewRepository;
import com.project.shopapp.services.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ReviewServiceImpl implements IReviewService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    @Transactional
    public Review addReviewToBook(Long bookID, Review review) {
        Book book = bookRepository.findById(bookID)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.addReview(review);
        reviewRepository.save(review);

        return review;
    }
}
*/
