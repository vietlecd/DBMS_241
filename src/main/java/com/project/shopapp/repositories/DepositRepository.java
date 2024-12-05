package com.project.shopapp.repositories;

import com.project.shopapp.models.Point;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends JpaRepository<Point, Integer> {
    Point findByUserAndType(User user, String type);
}
