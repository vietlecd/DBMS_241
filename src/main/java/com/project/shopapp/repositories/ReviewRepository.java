package com.project.shopapp.repositories;

import com.project.shopapp.DTO.ReviewDTO;
import com.project.shopapp.models.Book;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Review;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
//    List<Review> findByComment(String reviews);
    @Query("SELECT a FROM Review a WHERE a.user.username = :username")
    List<Review> findByUsername(@Param("username") String username);
    List<Review> findByBook_BookID(Long bookId);

}
