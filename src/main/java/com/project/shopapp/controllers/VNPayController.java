package com.project.shopapp.controllers;

import com.project.shopapp.DTO.PaymentDTO;
import com.project.shopapp.helpers.AuthenticationHelper;
import com.project.shopapp.models.Payment;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.PaymentRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.IVNPayService;
import com.project.shopapp.services.impl.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("${api.prefix}")
public class VNPayController {
    private AuthenticationHelper authenticationHelper;
    private UserRepository userRepository;
    private PaymentRepository paymentRepository;
    private IVNPayService vnPayService;

    @PostMapping("/submitOrder")
    public ResponseEntity<?> submidOrder(@RequestParam("amount") int orderTotal,
                              HttpServletRequest request, Authentication authentication){
        String username = authenticationHelper.getUsername(authentication);
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(orderTotal, baseUrl, request, user.get());
        return ResponseEntity.ok("redirect:" + vnpayUrl);
    }

    @GetMapping("/vnpay-payment")
    public ResponseEntity<?> getOrder(HttpServletRequest request, Authentication authentication) {
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        Optional<Payment> paymentOpt = paymentRepository.findByVnpTxnRef(vnp_TxnRef);

        if (paymentOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Transaction failed: Payment record not found");
        }

        Payment payment = paymentOpt.get();
        int paymentStatus = vnPayService.orderReturn(request);

        PaymentDTO paymentDTO = PaymentDTO.builder()
                .orderInfo(payment.getVnpTxnRef())
                .paymentTime(payment.getPayTime())
                .transactionId(request.getParameter("vnp_TransactionNo"))
                .totalPrice(payment.getPayAmount())
                .build();

        return paymentStatus == 1
                ? ResponseEntity.ok(paymentDTO)  // Trạng thái thành công
                : ResponseEntity.badRequest().body("Transaction failed");  // Trạng thái thất bại
    }

    @GetMapping("/payments")
    public ResponseEntity<?> getPaymentsByUser(Authentication authentication) {
        User user = authenticationHelper.getUser(authentication);
        return vnPayService.getPaymentsByUser(user);
    }
}
