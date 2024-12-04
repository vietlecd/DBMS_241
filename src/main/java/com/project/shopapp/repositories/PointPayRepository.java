package com.project.shopapp.repositories;

import com.project.shopapp.models.Pay;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PointPayRepository extends JpaRepository<Pay, Integer> {
    List<Pay> findPayByUser(User user);

    Pay findPayByUserAndBook_BookID(User user, Integer bookId);

}
