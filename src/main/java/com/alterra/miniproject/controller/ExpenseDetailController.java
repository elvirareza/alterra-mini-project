package com.alterra.miniproject.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alterra.miniproject.domain.common.ExpenseDetailInput;
import com.alterra.miniproject.domain.model.ExpenseDetail;
import com.alterra.miniproject.service.ExpenseDetailService;
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
public class ExpenseDetailController {
    @Autowired
    private ExpenseDetailService expenseDetailService;

    @GetMapping("/expense-detail")
    public ResponseEntity<?> getExpenseDetail(HttpServletRequest request) {
        try {
            List<ExpenseDetail> expenseDetail = expenseDetailService.getExpenseDetail(request);
            return ResponseUtil.build("Get all expense detail", expenseDetail, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseUtil.build(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/expense-detail/{id}")
    public ResponseEntity<?> getExpenseDetail(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            ExpenseDetail expenseDetail = expenseDetailService.getExpenseDetail(request, id);
            return ResponseUtil.build("Get expense detail by id", expenseDetail, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseUtil.build(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/expense-detail")
    public ResponseEntity<?> postExpenseDetail (HttpServletRequest request, @RequestBody ExpenseDetailInput req) {
        try {
            ExpenseDetail expenseDetail = expenseDetailService.postExpenseDetail(request, req);
            return ResponseUtil.build("Expense detail saved", expenseDetail, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseUtil.build(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/expense-detail/{id}")
    public ResponseEntity<?> putExpenseDetail (HttpServletRequest request, @PathVariable("id") Long id, @RequestBody ExpenseDetailInput req) {
        try {
            ExpenseDetail expenseDetail = expenseDetailService.updateExpenseDetail(request, id, req);
            return ResponseUtil.build("Expense detail saved", expenseDetail, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseUtil.build(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/expense-detail/{id}")
    public ResponseEntity<?> deleteExpenseDetail(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            expenseDetailService.deleteExpenseDetail(request, id);
            return ResponseUtil.build("Expense detail deleted", null, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseUtil.build(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
