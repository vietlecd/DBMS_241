package com.project.shopapp.repositories;

import com.project.shopapp.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT a FROM Review a WHERE a.userId.username = :username")
    List<Review> findReviewByFullname(@Param("username") String username);
}
