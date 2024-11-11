package com.project.shopapp.repositories;

import com.project.shopapp.models.Audience;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AudienceRepository extends JpaRepository<Audience, Long> {
    @Query("SELECT a FROM Audience a WHERE " +
            "(a.friend1.id = :myUserId AND a.friend2 = :friendUserId) OR " +
            "(a.friend1.id = :friendUserId AND a.friend2 = :myUserId)")
    Optional<Audience> findByUsers(@Param("myUserId") Integer myUserId, @Param("friendUserId") Integer friendUserId);

    @Query("SELECT CASE WHEN a.friend1.id = :userId THEN a.friend2 ELSE a.friend1.id END " +
            "FROM Audience a WHERE (a.friend1.id = :userId OR a.friend2 = :userId) AND a.status = 1")
    List<Integer> findFriendIdsByUserId(@Param("userId") Integer userId);

    @Query("SELECT CASE WHEN a.friend1.id = :userId THEN a.friend2 ELSE a.friend1.id END " +
            "FROM Audience a WHERE (a.friend1.id = :userId OR a.friend2 = :userId) AND a.status = 0")
    List<Integer> findRequestByUserId(@Param("userId") Integer userId);

}
