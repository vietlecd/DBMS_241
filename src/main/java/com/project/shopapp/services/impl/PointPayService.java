package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.PaymentDTO;
import com.project.shopapp.models.Book;
import com.project.shopapp.models.Pay;
import com.project.shopapp.models.Point;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.BookRepository;
import com.project.shopapp.repositories.PointPayRepository;
import com.project.shopapp.repositories.PointRepository;
import com.project.shopapp.services.IPayService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class PointPayService implements IPayService {
    private BookRepository bookRepository;
    private PointRepository pointRepository;
    private PointPayRepository pointPayRepository;

    @Override
    public ResponseEntity<?> payForBook(User user, Integer bookId) {
        Book book = bookRepository.findByBookID(bookId);
        Optional<Point> pointOptional = pointRepository.findByUserId(user.getId());


        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can not found your book");
        }

        Pay payOptional = pointPayRepository.findByUserIdAndId(user.getId(), bookId);
        if (payOptional != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Already buy it");
        }

        if (pointOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can not found your point set");
        }

        Point point = pointOptional.get();

        int bookPrice = Integer.parseInt(book.getPrice());
        if (point.getAmount() < bookPrice) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not enough money");
        }

        point.setAmount(point.getAmount() - bookPrice);
        pointRepository.save(point);

        Pay pay = Pay.builder()
                .book(book)
                .point(point)
                .book_price(bookPrice)
                .build();

        pointPayRepository.save(pay);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Payment successful! Remaining points: " + point.getAmount());
    }

    @Override
    public List<PaymentDTO> checkHistory(User user) {
        List<Pay> history = pointPayRepository.findByUserId(user);
        List<PaymentDTO> res = new ArrayList<>();

        if (!history.isEmpty()) {
            for (Pay pay : history) {
                PaymentDTO paymentDTO = PaymentDTO.builder()
                        .orderInfo("Point Payment")
                        .totalPrice(String.valueOf(pay.getBook_price()))
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
