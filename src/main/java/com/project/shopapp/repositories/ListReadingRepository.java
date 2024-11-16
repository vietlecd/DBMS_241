package com.project.shopapp.repositories;

import com.project.shopapp.models.ListReading;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ListReadingRepository extends JpaRepository<ListReading, Integer> {
    @Query("SELECT lr FROM ListReading lr JOIN lr.bookSet b WHERE lr.userId.id = :userId AND b.bookID = :bookId")
    Set<ListReading> findAllByUserIdAndBookId(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

    Optional<ListReading> findFirstByUserId(User user);

    @Query("SELECT lr FROM ListReading lr JOIN lr.bookSet WHERE lr.userId.id = :userId")
    Optional<ListReading> findFirstByUserIdAndBooks(@Param("userId") Integer userId);
}
