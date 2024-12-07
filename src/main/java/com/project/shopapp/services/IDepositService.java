package com.project.shopapp.services;

import com.project.shopapp.models.Payment;
import com.project.shopapp.models.Point;
import com.project.shopapp.models.User;

public interface IDepositService {
    Point depositPoint(int amount, User user);
    void createDeposit(Payment payment, Point point);
}
