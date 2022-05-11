package com.alterra.miniproject.service;

import javax.servlet.http.HttpServletRequest;

import com.alterra.miniproject.domain.common.ExpenseInput;
import com.alterra.miniproject.domain.model.Expense;
import com.alterra.miniproject.domain.model.Payment;
import com.alterra.miniproject.domain.model.User;
import com.alterra.miniproject.repository.ExpenseRepository;
import com.alterra.miniproject.repository.PaymentRepository;
import com.alterra.miniproject.repository.UserRepository;
import com.alterra.miniproject.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ExpenseInput saveExpense(HttpServletRequest request, ExpenseInput expense) {
        try {
            Expense exp = new Expense();

            exp.setName(expense.getName());
            exp.setExpenseDate(expense.getExpenseDate());  

            String bearerToken = request.getHeader("Authorization");
            String token = bearerToken.substring(7);
            String username = jwtTokenProvider.getUsername(token);
            User user = userRepository.findByUsername(username);

            exp.setUser(user);
            expense.setUsername(user.getUsername());

            if(expense.getPaymentId() != null) {
                Payment payment = paymentRepository.getById(expense.getPaymentId());

                if(payment == null) throw new Exception();

                exp.setPayment(payment);
            }         

            expenseRepository.save(exp);
            log.info("Expense saved");

            return expense; 
        } catch (Exception e) {
            log.error("Save expense error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
