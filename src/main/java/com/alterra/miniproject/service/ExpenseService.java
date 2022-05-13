package com.alterra.miniproject.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alterra.miniproject.domain.common.ExpenseInput;
import com.alterra.miniproject.domain.model.Expense;
import com.alterra.miniproject.domain.model.Payment;
import com.alterra.miniproject.domain.model.User;
import com.alterra.miniproject.repository.ExpenseDetailRepository;
import com.alterra.miniproject.repository.ExpenseRepository;
import com.alterra.miniproject.repository.PaymentRepository;
import com.alterra.miniproject.repository.UserRepository;
import com.alterra.miniproject.security.JwtTokenProvider;

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
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ExpenseDetailRepository expenseDetailRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public List<Expense> getExpense(HttpServletRequest request) {
        try {
            String username = getUsername(request);
            List<Expense> expense = expenseRepository.getExpensesByUsername(username);

            log.info("Get expenses success");

            return expense;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Expense getExpense(HttpServletRequest request, Long id) {
        try {
            String username = getUsername(request);

            Expense expense = expenseRepository.getExpenseByUsername(id, username)
                .orElseThrow(() -> new Exception("Expense with id " + id + " not found"));
            log.info("Get expense by id success");            

            return expense;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Expense postExpense(HttpServletRequest request, ExpenseInput expense) {
        try {
            Expense exp = new Expense();

            exp.setName(expense.getName());
            exp.setExpenseDate(LocalDateTime.now());  

            User user = userRepository.findByUsername(getUsername(request));

            exp.setUser(user);

            if(expense.getPaymentId() != null) {
                Payment payment = paymentRepository.searchById(expense.getPaymentId())
                    .orElseThrow(() -> new Exception("Payment with id " + expense.getPaymentId() + " not found"));

                exp.setPayment(payment);
            }         

            expenseRepository.save(exp);
            log.info("Expense saved");

            return exp; 
        } catch (Exception e) {
            log.error("Save expense error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Expense updateExpense(HttpServletRequest request, Long id, ExpenseInput expense) {
        try {
            Expense exp = getExpense(request, id);

            if(!getUsername(request).equals(exp.getUser().getUsername())) throw new Exception();

            if(expense.getName() != null) exp.setName(expense.getName());

            if(expense.getPaymentId() != null) {
                Payment payment = paymentRepository.searchById(expense.getPaymentId())
                    .orElseThrow(() -> new Exception("Payment with id " + expense.getPaymentId() + " not found"));

                exp.setPayment(payment);
            } 

            expenseRepository.save(exp);
            log.info("Expense updated");

            return exp; 
        } catch (Exception e) {
            log.error("Update expense error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void deleteExpense(HttpServletRequest request, Long id) {
        try {
            Expense exp = getExpense(request, id);

            if(!getUsername(request).equals(exp.getUser().getUsername())) throw new Exception();

            expenseRepository.deleteById(id);
            expenseDetailRepository.deleteExpenseDetailByExpense(id);
            log.info("Expense deleted");
        } catch (Exception e) {
            log.error("Delete expense error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String getUsername(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        String token = bearerToken.substring(7);
        String username = jwtTokenProvider.getUsername(token);

        return username;
    }
}
