package com.alterra.miniproject.service;

import com.alterra.miniproject.domain.model.User;
import com.alterra.miniproject.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.getDistinctTopByUsername(username);
            log.info("User '{}' found", username);
            return user;
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Username not found");
        }
    }
    
}
