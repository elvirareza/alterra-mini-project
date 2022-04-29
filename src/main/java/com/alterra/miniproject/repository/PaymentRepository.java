package com.alterra.miniproject.repository;

import com.alterra.miniproject.domain.dto.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
}
