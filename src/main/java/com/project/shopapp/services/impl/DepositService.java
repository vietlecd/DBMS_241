package com.project.shopapp.services.impl;

import com.project.shopapp.models.Payment;
import com.project.shopapp.models.Point;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.PaymentRepository;
import com.project.shopapp.repositories.DepositRepository;
import com.project.shopapp.services.IDepositService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DepositService implements IDepositService {
    private DepositRepository depositRepository;
    private PaymentRepository paymentRepository;

    @Override
    public Point depositPoint(int amount, User user) {
        Point exPoint = depositRepository.findByUserIdAndType(user, "DEPOSIT");

        int newPoint;
        if (exPoint != null) {
            newPoint = exPoint.getAmount() + amount;
            exPoint.setAmount(newPoint);
        } else {
            newPoint = amount;
            exPoint = Point.builder()
                    .userId(user)
                    .amount(newPoint)
                    .type("DEPOSIT")
                    .build();
        }

        return depositRepository.save(exPoint);

    }

    @Override
    public void createDeposit(Payment payment, Point point) {
        payment.getPointSet().add(point);
        paymentRepository.save(payment);
    }


}
