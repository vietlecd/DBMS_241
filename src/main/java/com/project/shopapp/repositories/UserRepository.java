package com.project.shopapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.shopapp.models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    //SELECT * FROM users WHERE phoneNumber=?

    @Query("SELECT u.username FROM User u WHERE u.id IN :friendIds")
    List<String> findUsernamesByIds(@Param("friendIds") List<Integer> friendIds);
}