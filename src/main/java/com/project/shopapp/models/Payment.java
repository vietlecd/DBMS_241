package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "payment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "pay_amount")
    private Integer payAmount;

    @Column(name = "pay_time")
    private String payTime;

    @Column(name = "vnp_txn_ref", unique = true)
    private String vnpTxnRef;  // Unique transaction reference from VNPay

    @Column(name = "status")
    private String status;  // Status of the payment (e.g., "PENDING", "SUCCESS", "FAILED")


}