package com.alterra.miniproject.controller;

import javax.servlet.http.HttpServletRequest;

import com.alterra.miniproject.domain.common.UsernamePassword;
import com.alterra.miniproject.domain.model.User;
import com.alterra.miniproject.repository.UserRepository;
import com.alterra.miniproject.security.JwtTokenProvider;
import com.alterra.miniproject.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody UsernamePassword req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody UsernamePassword req) {
        return ResponseEntity.ok(authService.generateToken(req));
    }

    @GetMapping("/hello")
    public User helloWorld(HttpServletRequest request) {
            String bearerToken = request.getHeader("Authorization");
            String token = bearerToken.substring(7);
            String username = jwtTokenProvider.getUsername(token);
            User user = userRepository.findByUsername(username);
            return user;
    }
}
