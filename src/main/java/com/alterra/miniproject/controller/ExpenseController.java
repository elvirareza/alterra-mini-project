package com.alterra.miniproject.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import com.alterra.miniproject.domain.common.ExpenseInput;
import com.alterra.miniproject.service.ExpenseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/expense")
    public ResponseEntity<?> postExpense (HttpServletRequest request, @RequestBody ExpenseInput req) {
        req.setExpenseDate(LocalDateTime.now());
        return ResponseEntity.ok(expenseService.saveExpense(request, req));
    }
}
