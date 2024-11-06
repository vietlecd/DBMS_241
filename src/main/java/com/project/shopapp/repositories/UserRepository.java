package com.project.shopapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.shopapp.models.*;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    //SELECT * FROM users WHERE phoneNumber=?
}