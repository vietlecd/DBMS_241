package com.project.shopapp.services.impl;

import com.project.shopapp.models.Claim;
import com.project.shopapp.models.Point;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.ClaimRepository;
import com.project.shopapp.repositories.PointRepository;
import com.project.shopapp.services.IClaimService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClaimService implements IClaimService {
    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
    private PointRepository pointRepository;

    @Override
    public ResponseEntity<?> claimStreakPoint(User user) {

        Claim streakClaim = getOrCreateClaim(user, "LOGIN_STREAK");

        Point claimPoints = getOrCreateUserPoint(user);

        LocalDate today = LocalDate.now();
        LocalDate lastClaimDate = (streakClaim.getClaimDate() != null) ? streakClaim.getClaimDate() : null;

        if (lastClaimDate != null && lastClaimDate.isEqual(today)) {
            return ResponseEntity.badRequest().body("Streak points already claimed today.");
        }

        int pointAward;
        if (lastClaimDate != null && lastClaimDate.plusDays(1).isEqual(today)) {

            streakClaim.setStreakCount(streakClaim.getStreakCount() + 1);
            pointAward = calculateStreakPoints(streakClaim.getStreakCount());
        } else {

            streakClaim.setStreakCount(1);
            pointAward = calculateStreakPoints(1);
        }


        streakClaim.setClaimDate(today);
        claimRepository.save(streakClaim);

        // Update user's total points
        claimPoints.setAmount(claimPoints.getAmount() + pointAward);
        pointRepository.save(claimPoints);

        return ResponseEntity.ok("Streak points claimed. You've earned " + pointAward + " points.");
    }

    @Override
    public ResponseEntity<?> claimAdPoint(User user) {
        int adPointValue = 50;
        Point claimPoints = getOrCreateUserPoint(user);
        Claim adClaim = getOrCreateClaim(user, "AD_VIEW");


        LocalDate today = LocalDate.now();
        LocalDate lastClaimDate = (adClaim.getClaimDate() != null) ? adClaim.getClaimDate() : null;

        // Reset daily ad
        if (lastClaimDate == null || !lastClaimDate.isEqual(today)) {
            claimPoints.setViewCount(0);
            adClaim.setClaimDate(today);
        }

        if (claimPoints.getViewCount() < 5) {
            claimPoints.setViewCount(claimPoints.getViewCount() + 1);
            claimPoints.setAmount(claimPoints.getAmount() + adPointValue);


            claimRepository.save(adClaim);
            pointRepository.save(claimPoints);

            return ResponseEntity.ok("Claimed 50 points for ad view " + claimPoints.getViewCount() + ".");
        } else {
            return ResponseEntity.badRequest().body("Nap do, xem chua quai ba!!!!!!!!!!!!!!!");
        }
    }

    private int calculateStreakPoints(int streakDays) {
        if (streakDays >= 1 && streakDays <= 5) {
            return 100;
        } else if (streakDays >= 6 && streakDays <= 10) {
            return 150;
        } else if (streakDays >= 11 && streakDays <= 20) {
            return 250;
        } else {
            return 350;
        }
    }

    private Claim getOrCreateClaim(User user, String claimType) {
        Claim claim = claimRepository.findByUserIdAndClaimType(user, claimType);
        Point point = getOrCreateUserPoint(user);
        if (claim == null) {
            claim = new Claim();
            claim.setClaimType(claimType);
            claim.setUserId(user);
            claim.setPointId(point);
            claim.setClaimDate(LocalDate.now());
            claim.setStreakCount(0);
            claimRepository.save(claim);
        }
        return claim;
    }


    private Point getOrCreateUserPoint(User user) {
        Point userPoint = pointRepository.findByUserIdAndType(user, "CLAIM");
        if (userPoint == null) {
            userPoint = Point.builder()
                    .userId(user)
                    .amount(0)
                    .viewCount(0)
                    .type("CLAIM")
                    .build();
            pointRepository.save(userPoint);
        }
        return userPoint;
    }

}
