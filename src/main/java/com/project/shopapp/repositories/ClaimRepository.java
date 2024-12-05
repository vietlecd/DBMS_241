package com.project.shopapp.repositories;

import com.project.shopapp.models.Claim;
import com.project.shopapp.models.Point;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {

    Claim findByUserIdAndPointId(User user, Point point);

    @Procedure(procedureName = "findLastClaimDateByUserId")
    LocalDate findLastClaimDateByUserId(@Param("p_user_id") Integer userId);


}
