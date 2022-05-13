package com.alterra.miniproject.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alterra.miniproject.domain.common.ExpenseInput;
import com.alterra.miniproject.domain.model.Expense;
import com.alterra.miniproject.service.ExpenseService;
import com.alterra.miniproject.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expense")
    public ResponseEntity<?> getExpense(HttpServletRequest request) {
        try {
            List<Expense> expense = expenseService.getExpense(request);
            return ResponseUtil.build("Get all expense", expense, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseUtil.build(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/expense/{id}")
    public ResponseEntity<?> getExpense(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            Expense expense = expenseService.getExpense(request, id);
            return ResponseUtil.build("Get expense by id", expense, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseUtil.build(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/expense")
    public ResponseEntity<?> postExpense (HttpServletRequest request, @RequestBody ExpenseInput req) {
        try {
            Expense expense = expenseService.postExpense(request, req);
            return ResponseUtil.build("Expense saved", expense, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseUtil.build(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/expense/{id}")
    public ResponseEntity<?> putExpense(HttpServletRequest request, @PathVariable("id") Long id, @RequestBody ExpenseInput req) {
        try {
            Expense expense = expenseService.updateExpense(request, id, req);
            return ResponseUtil.build("Expense updated", expense, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseUtil.build(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/expense/{id}")
    public ResponseEntity<?> deleteExpense(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            expenseService.deleteExpense(request, id);
            return ResponseUtil.build("Expense deleted", null, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseUtil.build(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
