package com.github.arpit.nexus.service.controller;

import com.github.arpit.nexus.core.security.JwtUtils;
import com.github.arpit.nexus.core.web.ApiResponse;
import com.github.arpit.nexus.service.dto.LoginRequest;
import com.github.arpit.nexus.service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtils jwtUtils;
    private final UserService userService;

    @PostMapping("/login")
    public ApiResponse<String> login(@Valid @RequestBody LoginRequest request) {
        if (userService.verifyUser(request.getUsername(), request.getPassword())) {
            String token = jwtUtils.generateToken(request.getUsername(), new HashMap<>());
            return ApiResponse.success(token, "Login successful");
        }
        return ApiResponse.error("Invalid username or password", 401);
    }

    @GetMapping("/verify")
    public ApiResponse<String> verify() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            return ApiResponse.success("Token is valid for user: " + authentication.getName(), "Success");
        }
        return ApiResponse.error("Invalid or missing Token", 401);
    }
}