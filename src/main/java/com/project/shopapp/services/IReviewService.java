
package com.project.shopapp.services;


import com.project.shopapp.DTO.ReviewDTO;
import com.project.shopapp.models.Review;

public interface IReviewService {
    ReviewDTO addReviewToBook(Long bookID, ReviewDTO reviewDTO); // hoặc void nếu không cần trả về
}
