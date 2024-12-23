package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.PaymentDTO;
import com.project.shopapp.models.*;
import com.project.shopapp.repositories.*;
import com.project.shopapp.services.IPayService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class PointPayService implements IPayService {
    private BookRepository bookRepository;
    private PointPayRepository pointPayRepository;

    @Override
    public ResponseEntity<?> payForBook(User user, Integer bookId) {
        Book book = bookRepository.findByBookID(bookId);

        if (book == null || book.getStatus().equals("false")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can not found your book");
        }

        Pay payOptional = pointPayRepository.findPayByUserAndBook_BookID(user, bookId);
        if (payOptional != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Already buy it");
        }

        int bookPrice = Integer.parseInt(book.getPrice());

        int total_point = user.getTotalPoint();

        if (total_point < bookPrice) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough money");
        }

        Pay pay = Pay.builder()
                .book(book)
                .user(user)
                .book_price(bookPrice)
                .build();

        pointPayRepository.save(pay);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Payment successful!");
    }
    @Override
    public List<PaymentDTO> checkHistory(User user) {
        List<Pay> history = pointPayRepository.findPayByUserOrderByTimestampAsc(user);
        List<PaymentDTO> res = new ArrayList<>();

        if (!history.isEmpty()) {
            for (Pay pay : history) {
                PaymentDTO paymentDTO = PaymentDTO.builder()
                        .orderInfo("Point Payment")
                        .totalPrice(pay.getBook_price())
                        .transactionId(String.valueOf(pay.getId()))
                        .paymentTime(String.valueOf(pay.getTimestamp()))
                        .build();

                res.add(paymentDTO);
            }
        } else {
            return Collections.emptyList();
        }

        return res;
    }

}
