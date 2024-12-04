package com.project.shopapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shopapp.DTO.PaymentDTO;
import com.project.shopapp.helpers.AuthenticationHelper;
import com.project.shopapp.models.Payment;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.PaymentRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.IVNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
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
        return ResponseEntity.ok(vnpayUrl);
    }

    @GetMapping("/payment_return")
    public ResponseEntity<?> getOrder(HttpServletRequest request, HttpServletResponse response) {
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        Optional<Payment> paymentOpt = paymentRepository.findByVnpTxnRef(vnp_TxnRef);

        if (paymentOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Transaction failed: Payment record not found");
        }

        Payment payment = paymentOpt.get();
        int paymentStatus = vnPayService.orderReturn(request);

        PaymentDTO paymentDTO = PaymentDTO.builder()
                .paymentTime(payment.getPayTime())
                .transactionId(request.getParameter("vnp_TransactionNo"))
                .totalPrice(payment.getPayAmount())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String paymentJson = "";
        try {
            paymentJson = objectMapper.writeValueAsString(paymentDTO);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error serializing PaymentDTO");
        }

        String redirectUrl = paymentStatus == 1 ? "http://localhost:5173/success" : "http://localhost:5173/fail";

        try {
            String encode = URLEncoder.encode(paymentJson, "UTF-8");

            if (paymentStatus == 1) {
                response.sendRedirect( redirectUrl + "?paymentInfo=" + encode);
                return null;
            } else {
                response.sendRedirect(redirectUrl);
                return null;
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error redirecting");

        }
    }

    @GetMapping("/payments")
    public ResponseEntity<?> getPaymentsByUser(Authentication authentication) {
        User user = authenticationHelper.getUser(authentication);
        return vnPayService.getPaymentsByUser(user);
    }
}
