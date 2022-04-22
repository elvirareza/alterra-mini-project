package com.alterra.miniproject.service;

import com.alterra.miniproject.repository.HelloWorldRepository;
import com.alterra.miniproject.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HelloWorldService {
    @Autowired
    private HelloWorldRepository helloWorldRepository;

    public ResponseEntity<Object> getAll() {
        log.info("getHello Service");
        return ResponseUtil.build("Success", helloWorldRepository.findAll(), HttpStatus.OK);
    }
}
