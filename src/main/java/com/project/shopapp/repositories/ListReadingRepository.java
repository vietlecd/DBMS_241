package com.project.shopapp.repositories;

import com.project.shopapp.models.Book;
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
    Optional<ListReading> findFirstByUser(User user);
}
