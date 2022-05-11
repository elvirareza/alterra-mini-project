package com.alterra.miniproject.service;

import com.alterra.miniproject.domain.common.PaymentInput;
import com.alterra.miniproject.domain.model.Payment;
import com.alterra.miniproject.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentInput savePayment(PaymentInput payment) {
        try {
            Payment pay = new Payment();
            pay.setCardNumber(payment.getCardNumber());
            log.info("Payment card: {}", pay.getCardNumber());
            paymentRepository.save(pay);
            log.info("Payment saved");

            return payment; 
        } catch (Exception e) {
            log.error("Save payment error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
