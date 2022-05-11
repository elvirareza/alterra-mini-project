package com.alterra.miniproject.controller;

import com.alterra.miniproject.domain.common.ExpenseDetailInput;
import com.alterra.miniproject.service.ExpenseDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ExpenseDetailController {
    @Autowired
    private ExpenseDetailService expenseDetailService;

    @PostMapping("/expense-detail")
    public ResponseEntity<?> postCategory (@RequestBody ExpenseDetailInput req) {
        return ResponseEntity.ok(expenseDetailService.saveExpenseDetail(req));
    }
}
