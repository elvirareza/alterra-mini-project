package com.alterra.miniproject.controller;

import com.alterra.miniproject.domain.common.TokenResponse;
import com.alterra.miniproject.domain.common.UsernamePassword;
import com.alterra.miniproject.service.AuthService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody UsernamePassword req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody UsernamePassword req) {
        TokenResponse token = authService.generateToken(req);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token.getToken());
        
        return ResponseEntity.ok().headers(responseHeaders).body(token);
    }
}
