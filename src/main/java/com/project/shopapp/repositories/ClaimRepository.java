package com.project.shopapp.repositories;

import com.project.shopapp.models.Claim;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {

    @Query("SELECT c FROM Claim c WHERE c.userId = :user AND c.claimType = :claimType")
    Claim findByUserIdAndClaimType(@Param("user") User user, @Param("claimType") String claimType);

}
