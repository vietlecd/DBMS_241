package com.project.shopapp.services.impl;

import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.models.Claim;
import com.project.shopapp.models.Point;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.ClaimRepository;
import com.project.shopapp.repositories.PointRepository;
import com.project.shopapp.services.IClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ClaimService implements IClaimService {
    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
    private PointRepository pointRepository;

    @Override
    public void claimStreakPoint(User user) {
        if (user == null || user.getRole().getName().equals("ADMIN")) {
            return;
        }

        Claim streakClaim = getOrCreateClaim(user);


        Point claimPoints = getOrCreateUserPoint(user);

        LocalDate today = LocalDate.now();
        LocalDate lastClaimDate = (streakClaim.getClaimDate() != null) ? streakClaim.getClaimDate() : null;


        if (lastClaimDate != null && lastClaimDate.isEqual(today)) {
            return;
        }

        int streakCount;
        int pointAward;

        if (lastClaimDate != null && lastClaimDate.plusDays(1).isEqual(today)) {

            streakCount = calculateStreakCount(user);
            pointAward = calculateStreakPoints(streakCount);

            //Ssau 29 ngay quay lai moc 1
            if (streakCount == 29) {
                streakCount = 1;
                pointAward = calculateStreakPoints(streakCount);
            }
        } else {

            streakCount = 1;
            pointAward = calculateStreakPoints(streakCount);
        }

        // Cập nhật ngày claim
        streakClaim.setClaimDate(today);
        claimRepository.save(streakClaim);

        // Cập nhật điểm cho người dùng
        claimPoints.setAmount(claimPoints.getAmount() + pointAward);
        pointRepository.save(claimPoints);
    }

    @Override
    public ResponseEntity<?> claimAdPoint(User user) {
        int adPointValue = 50;
        Point claimPoints = getOrCreateUserPoint(user);
        Claim adClaim = getOrCreateClaim(user);

        LocalDate today = LocalDate.now();
        LocalDate lastClaimDate = (adClaim.getClaimDate() != null) ? adClaim.getClaimDate() : null;

        // Reset
        if (lastClaimDate == null || !lastClaimDate.isEqual(today)) {
            claimPoints.setViewCount(0);
            adClaim.setClaimDate(today);
        }

        if (claimPoints.getViewCount() < 5) {
            claimPoints.setViewCount(claimPoints.getViewCount() + 1);
            claimPoints.setAmount(claimPoints.getAmount() + adPointValue);

            claimRepository.save(adClaim);
            pointRepository.save(claimPoints);

            return ResponseEntity.ok("Claimed 50 points for qc view " + claimPoints.getViewCount() + ".");
        } else {
            return ResponseEntity.badRequest().body("Nap tien di, 5 lan duoc roi");
        }
    }

    private int calculateStreakCount(User user) {
        int streakCount = 1;
        LocalDate currentDate = LocalDate.now();
        LocalDate lastClaimDate = claimRepository.findLastClaimDateByUserId(user.getId());

        while (lastClaimDate != null && lastClaimDate.plusDays(1).isEqual(currentDate)) {
            streakCount++;
            currentDate = lastClaimDate;
            lastClaimDate = claimRepository.findLastClaimDateByUserId(user.getId());
        }

        return streakCount;
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

    private Claim getOrCreateClaim(User user) {
        Claim claim = claimRepository.findByUserIdAndPointId(user.getId(), getOrCreateUserPoint(user).getId());
        if (claim == null) {
            claim = new Claim();
            claim.setUserId(user);
            claim.setPointId(getOrCreateUserPoint(user));
            claim.setClaimDate(LocalDate.now());
            claimRepository.save(claim);
        }
        return claim;
    }



    private Point getOrCreateUserPoint(User user) {
        Point userPoint = pointRepository.findByUserAndType(user, "CLAIM");
        if (userPoint == null) {
            userPoint = Point.builder()
                    .user(user)
                    .amount(0)
                    .viewCount(0)
                    .type("CLAIM")
                    .build();
            pointRepository.save(userPoint);
        }
        return userPoint;
    }

}
