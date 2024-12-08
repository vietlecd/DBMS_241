
package com.project.shopapp.services;


import com.project.shopapp.DTO.ReviewDTO;
import com.project.shopapp.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IReviewService {
    ResponseEntity<?> addReviewToBook(Long bookID, ReviewDTO reviewDTO, User user); // hoặc void nếu không cần trả về
    ResponseEntity<?> findReviewsByBookId(Long bookID, String username);
    ResponseEntity<?> updateReview(Long reviewID, ReviewDTO reviewDTO,User user);
}
