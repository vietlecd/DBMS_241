package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.PaymentDTO;
import com.project.shopapp.configurations.VNPayConfig;
import com.project.shopapp.models.Payment;
import com.project.shopapp.models.Point;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.DepositRepository;
import com.project.shopapp.repositories.PaymentRepository;
import com.project.shopapp.services.IDepositService;
import com.project.shopapp.services.IVNPayService;
import com.project.shopapp.utils.CheckExistedUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VNPayService implements IVNPayService {

    private final PaymentRepository paymentRepository;
    private final IDepositService pointService;
    private final CheckExistedUtils checkExistedUtils;

    public String createOrder(int total, String urlReturn, HttpServletRequest request, User user) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VNPayConfig.getIpAddress(request);
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        String orderType = "order-type";

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(total * 100)); // Amount in VND
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "ncb");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Payment for order: " + VNPayConfig.getRandomNumber(4));
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");

        urlReturn += VNPayConfig.vnp_Returnurl;
        vnp_Params.put("vnp_ReturnUrl", urlReturn);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        // Create Payment record with PENDING status
        Payment payment = new Payment();
        payment.setUserId(user);
        payment.setPayTime(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
        payment.setPayAmount(total);
        payment.setVnpTxnRef(vnp_TxnRef);
        payment.setStatus("PENDING");
        paymentRepository.save(payment);
        // *************************************** //

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 50);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=');
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append('&');
                hashData.append('&');
            }
        }
        // Remove last '&' character
        query.setLength(query.length() - 1);
        hashData.setLength(hashData.length() - 1);

        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
        return VNPayConfig.vnp_PayUrl + "?" + query.toString();
    }

    public int orderReturn(HttpServletRequest request) {
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            fields.put(fieldName, fieldValue);
        }

//            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");

//            String signValue = VNPayConfig.hashAllFields(fields);
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        Optional<Payment> optionalPayment = paymentRepository.findPaymentByVnpTxnRef(vnp_TxnRef);

        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            User user = payment.getUserId();
            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                payment.setStatus("SUCCESS");
                //Set Point
                int total = payment.getPayAmount();
                Point point = pointService.depositPoint(total, user);
                pointService.createDeposit(payment, point);
                //***********************//
            } else {
                payment.setStatus("FAILED");
            }
            paymentRepository.save(payment);
            return "SUCCESS".equals(payment.getStatus()) ? 1 : 0;
        } else {
            return -1;  // Validation failed
        }
    }

    public ResponseEntity<?> getPaymentsByUser(User user) {
        List<Payment> payment = paymentRepository.findByUserIdAndStatus(user, "SUCCESS");
        checkExistedUtils.checkObjectExisted(payment, "Payment List");
        List<PaymentDTO> res = new ArrayList<>();

        for (Payment payment1 : payment) {
            PaymentDTO paymentDTO = PaymentDTO.builder()
                    .orderInfo("DEPOSIT")
                    .paymentTime(payment1.getPayTime())
                    .transactionId(payment1.getVnpTxnRef())
                    .totalPrice(payment1.getPayAmount())
                    .build();

            res.add(paymentDTO);
        }

        return ResponseEntity.ok(res);

    }
}

