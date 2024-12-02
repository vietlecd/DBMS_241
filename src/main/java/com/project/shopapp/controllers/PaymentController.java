package com.project.shopapp.controllers;

import com.project.shopapp.DTO.PaymentDTO;
import com.project.shopapp.helpers.AuthenticationHelper;
import com.project.shopapp.models.User;
import com.project.shopapp.services.IPayService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/payment")
@AllArgsConstructor
public class PaymentController {
    private IPayService payService;
    private AuthenticationHelper authenticationHelper;

    @PostMapping("/create")
    public ResponseEntity<?> payForBook(@RequestParam Integer bookId, Authentication authentication) {
        User user = authenticationHelper.getUser(authentication);

        return payService.payForBook(user, bookId);
    }

    @GetMapping("/get")
    public List<PaymentDTO> getHistory(Authentication authentication) {
        User user = authenticationHelper.getUser(authentication);
        return payService.checkHistory(user);
    }
}
