package com.project.shopapp.repositories;

import com.project.shopapp.models.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {

//    @Query("SELECT c FROM Claim c WHERE c.userId = :user AND c.claimType = :claimType")
//    Claim findByUserIdAndClaimType(@Param("user") User user, @Param("claimType") String claimType);

    @Query("SELECT c FROM Claim c WHERE c.userId.id = :user AND c.pointId.id = :pointId")
    Claim findByUserIdAndPointId(@Param("user") Integer userId, @Param("pointId") Integer pointId);

    @Query("SELECT MAX(c.claimDate) FROM Claim c WHERE c.userId.id = :userId")
    LocalDate findLastClaimDateByUserId(@Param("userId") Integer userId);


}
