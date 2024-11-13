package com.project.shopapp.controllers;

import com.project.shopapp.DTO.PaymentDTO;
import com.project.shopapp.services.impl.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}")
public class VNPayController {
    @Autowired
    private VNPayService vnPayService;

    @GetMapping("")
    public String home(){
        return "index";
    }

    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") int orderTotal,
                              HttpServletRequest request){
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(orderTotal, baseUrl, request);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public ResponseEntity<?> getOrder(HttpServletRequest request) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTimeStr = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPriceStr = request.getParameter("vnp_Amount");

        // Tạo một đối tượng PaymentDTO với các thông tin cần trả về
        PaymentDTO paymentDTO1 = PaymentDTO.builder()
                .orderInfo(orderInfo)
                .paymentTime(paymentTimeStr)
                .transactionId(transactionId)
                .totalPrice(totalPriceStr)
                .build();

        // Trả về JSON và xác định trạng thái HTTP
        return paymentStatus == 1
                ? ResponseEntity.ok(paymentDTO1)  // Trạng thái thành công
                : ResponseEntity.badRequest().body("Transaction failed");  // Trạng thái thất bại
    }
}
