package com.project.shopapp.repositories;

import com.project.shopapp.models.Point;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Integer> {
    Point findByUserIdAndType(User user, String type);
}
