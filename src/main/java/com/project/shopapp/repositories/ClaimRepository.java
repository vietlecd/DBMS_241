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
//    @Query("SELECT c FROM Claim c WHERE c.userId.id = :userId")
    @Query("SELECT c FROM Claim c JOIN c.userId u WHERE u.id = :userId")
    List<Claim> findByUserId(@Param("userId") Integer userId);


    Claim findByUserIdAndClaimType(User user, String claimType);
}
