package com.project.shopapp.repositories;

import com.project.shopapp.models.Pay;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PointPayRepository extends JpaRepository<Pay, Integer> {
    @Query("SELECT p FROM Pay p WHERE p.point.userId = :user")
    List<Pay> findByUserId(User user);

    @Query("SELECT p FROM Pay p JOIN p.point c WHERE c.userId.id = :userId AND p.book.bookID = :bookId")
    Pay findByUserIdAndId(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

}
