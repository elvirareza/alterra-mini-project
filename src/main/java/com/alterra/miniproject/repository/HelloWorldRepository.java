package com.alterra.miniproject.repository;

import com.alterra.miniproject.domain.model.HelloWorld;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelloWorldRepository extends JpaRepository<HelloWorld, Long> {
    
}
