package com.alterra.miniproject.service;

import com.alterra.miniproject.domain.common.ExpenseDetailInput;
import com.alterra.miniproject.domain.model.Category;
import com.alterra.miniproject.domain.model.Expense;
import com.alterra.miniproject.domain.model.ExpenseDetail;
import com.alterra.miniproject.repository.CategoryRepository;
import com.alterra.miniproject.repository.ExpenseDetailRepository;
import com.alterra.miniproject.repository.ExpenseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDetailService {
    @Autowired
    private ExpenseDetailRepository expenseDetailRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ExpenseDetailInput saveExpenseDetail(ExpenseDetailInput detailInput) {
        try {
            ExpenseDetail exp = new ExpenseDetail();
            exp.setName(detailInput.getName());
            exp.setAmount(detailInput.getAmount());

            Expense expense = expenseRepository.getById(detailInput.getExpenseId());

            if(expense == null) throw new Exception();

            exp.setExpense(expense);

            if(detailInput.getCategoryId() != null) {
                Category category = categoryRepository.getById(detailInput.getCategoryId());

                if(category == null) throw new Exception();

                exp.setCategory(category);
            }
            
            expenseDetailRepository.save(exp);
            log.info("Expense detail saved");

            return detailInput; 
        } catch (Exception e) {
            log.error("Save expense detail error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
