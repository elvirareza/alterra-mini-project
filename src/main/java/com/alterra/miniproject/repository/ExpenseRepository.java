package com.alterra.miniproject.repository;

import com.alterra.miniproject.domain.dto.Expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
}
