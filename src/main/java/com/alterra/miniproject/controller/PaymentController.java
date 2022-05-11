package com.alterra.miniproject.controller;

import com.alterra.miniproject.domain.common.PaymentInput;
import com.alterra.miniproject.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<?> postCategory (@RequestBody PaymentInput req) {
        return ResponseEntity.ok(paymentService.savePayment(req));
    }
}
