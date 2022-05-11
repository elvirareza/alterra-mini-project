package com.alterra.miniproject.repository;

import com.alterra.miniproject.domain.model.ExpenseDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseDetailRepository extends JpaRepository<ExpenseDetail, Long> {
    
}