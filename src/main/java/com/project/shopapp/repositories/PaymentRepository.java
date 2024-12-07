package com.project.shopapp.repositories;

import com.project.shopapp.models.Payment;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findPaymentByVnpTxnRef(String vnp_TxnRef);
    List<Payment> findByUserIdAndStatus(User user, String status);
}
