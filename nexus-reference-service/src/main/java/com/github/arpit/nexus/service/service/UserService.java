package com.github.arpit.nexus.service.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final Map<String, String> userStore = new HashMap<>();

    @PostConstruct
    public void init() {
        // Default users
        // password is "password"
        userStore.put("arpit", passwordEncoder.encode("password"));
        userStore.put("admin", passwordEncoder.encode("admin123"));
    }

    public boolean verifyUser(String username, String rawPassword) {
        String storedHash = userStore.get(username);
        if (storedHash == null) {
            return false;
        }
        return passwordEncoder.matches(rawPassword, storedHash);
    }
}