package com.project.shopapp.repositories;

import com.project.shopapp.responses.BaseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.shopapp.models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByUsername(String username);
    //SELECT * FROM users WHERE phoneNumber=?

    @Query("SELECT u.username AS username, u.fullName AS fullName, u.phoneNumber AS phoneNumber " +
            "FROM User u JOIN u.friends f WHERE f.username = :username")
    List<BaseProjection> findFriendsByUsername(@Param("username") String username);

    Optional<User> findByPhoneNumber(String phoneNumber);
}