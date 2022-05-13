package com.alterra.miniproject.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alterra.miniproject.domain.common.ExpenseDetailInput;
import com.alterra.miniproject.domain.model.Category;
import com.alterra.miniproject.domain.model.Expense;
import com.alterra.miniproject.domain.model.ExpenseDetail;
import com.alterra.miniproject.repository.CategoryRepository;
import com.alterra.miniproject.repository.ExpenseDetailRepository;
import com.alterra.miniproject.repository.ExpenseRepository;
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
public class ExpenseDetailService {
    @Autowired
    private ExpenseDetailRepository expenseDetailRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    public List<ExpenseDetail> getExpenseDetail(HttpServletRequest request) {
        try {
            String username = getUsername(request);
            List<ExpenseDetail> expenseDetails = expenseDetailRepository.getExpenseDetailsByUsername(username);

            if(expenseDetails == null) throw new Exception();

            log.info("Get expense details success");
            
            return expenseDetails;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public ExpenseDetail getExpenseDetail(HttpServletRequest request, Long id) {
        try {
            String username = getUsername(request);
            ExpenseDetail expenseDetail = expenseDetailRepository.getExpenseDetailByUsername(id, username)
                .orElseThrow(() -> new Exception("Expense detail with id " + id + " not found"));
            log.info("Get expense detail by id success");

            return expenseDetail;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public ExpenseDetail postExpenseDetail(HttpServletRequest request, ExpenseDetailInput detailInput) {
        try {
            ExpenseDetail exp = new ExpenseDetail();
            exp.setName(detailInput.getName());
            exp.setAmount(detailInput.getAmount());

            Expense expense = expenseRepository.getExpenseByUsername(detailInput.getExpenseId(), getUsername(request))
                .orElseThrow(() -> new Exception("Expense with id " + detailInput.getExpenseId() + " not found"));

            if(expense == null) throw new Exception();

            exp.setExpense(expense);

            if(detailInput.getCategoryId() != null) {
                Category category = categoryRepository.searchById(detailInput.getCategoryId())
                    .orElseThrow(() -> new Exception("Category with id " + detailInput.getCategoryId() + " not found"));

                exp.setCategory(category);
            }
            
            expenseDetailRepository.save(exp);
            log.info("Expense detail saved");

            Double totalAmount = expenseRepository.getTotalAmount(detailInput.getExpenseId());
            totalAmount += detailInput.getAmount();
            expenseRepository.updateTotalAmount(detailInput.getExpenseId(), totalAmount);

            return exp; 
        } catch (Exception e) {
            log.error("Save expense detail error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public ExpenseDetail updateExpenseDetail(HttpServletRequest request, Long id, ExpenseDetailInput detailInput) {
        try {
            ExpenseDetail exp = getExpenseDetail(request, id);

            if(!getUsername(request).equals(exp.getExpense().getUser().getUsername())) throw new Exception();

            if(detailInput.getName() != null) exp.setName(detailInput.getName());

            if(detailInput.getExpenseId() != null) {
                Expense expense = expenseRepository.getExpenseByUsername(detailInput.getExpenseId(), getUsername(request))
                    .orElseThrow(() -> new Exception("Expense with id " + detailInput.getExpenseId() + " not found"));

                if(expense == null) throw new Exception();

                exp.setExpense(expense);
            } 
            
            if(detailInput.getAmount() != null) {
                Double totalAmount = expenseRepository.getTotalAmount(exp.getExpense().getId());
                totalAmount -= exp.getAmount();
                totalAmount += detailInput.getAmount();
                expenseRepository.updateTotalAmount(exp.getExpense().getId(), totalAmount);
                exp.setAmount(detailInput.getAmount());
            }

            if(detailInput.getCategoryId() != null) {
                Category category = categoryRepository.searchById(detailInput.getCategoryId())
                    .orElseThrow(() -> new Exception("Category with id " + detailInput.getCategoryId() + " not found"));

                exp.setCategory(category);
            }else {
               detailInput.setCategoryId(exp.getCategory().getId());
            }
            
            expenseDetailRepository.save(exp);
            log.info("Expense detail updated");

            return exp; 
        } catch (Exception e) {
            log.error("Update expense detail error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void deleteExpenseDetail(HttpServletRequest request, Long id) {
        try {
            ExpenseDetail exp = getExpenseDetail(request, id);

            if(!getUsername(request).equals(exp.getExpense().getUser().getUsername())) throw new Exception();

            expenseDetailRepository.deleteById(id);
            log.info("Expense detail deleted");
        } catch (Exception e) {
            log.error("Delete expense detail error: {}", e.getMessage());
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
