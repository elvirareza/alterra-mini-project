package com.alterra.miniproject.service;

import java.util.List;

import com.alterra.miniproject.domain.common.PaymentInput;
import com.alterra.miniproject.domain.model.Payment;
import com.alterra.miniproject.repository.ExpenseRepository;
import com.alterra.miniproject.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Payment> getPayment() {
        try {
            List<Payment> payment = paymentRepository.findAll();

            if(payment == null) throw new Exception();

            log.info("Get payments success");

            return payment;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Payment getPayment(Long id) {
        try {
            Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new Exception("Payment with id " + id + " not found"));
            log.info("Get payment by id success");
            
            return payment;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Payment postPayment(PaymentInput payment) {
        try {
            Payment pay = new Payment();
            pay.setCardNumber(payment.getCardNumber());
            log.info("Payment card: {}", pay.getCardNumber());
            paymentRepository.save(pay);
            log.info("Payment saved");

            return pay; 
        } catch (Exception e) {
            log.error("Save payment error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Payment updatePayment(Long id, PaymentInput payment) {
        try {
            Payment pay = getPayment(id);

            if(payment.getCardNumber() != null) pay.setCardNumber(payment.getCardNumber());

            log.info("Payment card: {}", pay.getCardNumber());
            paymentRepository.save(pay);
            log.info("Payment updated");

            return pay; 
        } catch (Exception e) {
            log.error("Update payment error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void deletePayment(Long id) {
        try {
            paymentRepository.deleteById(id);
            expenseRepository.deleteExpenseByPayment(id);
            log.info("Payment deleted");
        } catch (Exception e) {
            log.error("Delete expense error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
