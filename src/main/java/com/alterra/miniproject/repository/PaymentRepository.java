package com.alterra.miniproject.repository;

import java.util.Optional;

import com.alterra.miniproject.domain.model.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query(value = "SELECT * FROM payment p WHERE p.deleted = false AND p.id = ?", nativeQuery = true)
    Optional<Payment> searchById(Long id);
}
