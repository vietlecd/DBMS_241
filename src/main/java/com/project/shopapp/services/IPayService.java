package com.project.shopapp.services;

import com.project.shopapp.DTO.PaymentDTO;
import com.project.shopapp.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPayService {

    ResponseEntity<?> payForBook(User user, Integer bookId);

    List<PaymentDTO> checkHistory(User user);


}
