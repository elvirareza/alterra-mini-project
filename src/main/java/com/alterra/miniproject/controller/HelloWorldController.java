package com.alterra.miniproject.controller;

import com.alterra.miniproject.util.ResponseUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/hello-world")
public class HelloWorldController {
    @GetMapping
    public ResponseEntity<Object> getHello() {
        log.info("getHello running");
        return ResponseUtil.build("Hello World!", null, HttpStatus.OK);
    }
}
