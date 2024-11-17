package com.project.shopapp.repositories;

import com.project.shopapp.models.Point;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point, Integer> {
    Point findByUserIdAndType(User user, String type);

    @Query("SELECT p FROM Point p WHERE p.userId.id = :userId")
    Optional<Point> findByUserId(@Param("userId") Integer userId);

}
