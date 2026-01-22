package com.github.arpit.nexus.service.controller;

import com.github.arpit.nexus.core.audit.LogExecutionTime;
import com.github.arpit.nexus.core.exception.ResourceNotFoundException;
import com.github.arpit.nexus.core.security.ratelimit.RateLimit;
import com.github.arpit.nexus.core.web.ApiResponse;
import com.github.arpit.nexus.service.dto.UserDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@Slf4j
public class TestController {

    @GetMapping("/hello")
    @LogExecutionTime
    public ApiResponse<String> hello() {
        log.info("Handling hello request");
        return ApiResponse.success("Hello from Nexus Reference Service!", "Operation successful");
    }

    @GetMapping("/limited")
    @RateLimit(limit = 5, duration = 60)
    public ApiResponse<String> limited() {
        return ApiResponse.success("You are within the rate limit!", "Success");
    }

    @GetMapping("/error")
    @LogExecutionTime
    public ApiResponse<String> error() {
        log.error("Simulating error");
        throw new RuntimeException("Simulated error for testing GlobalExceptionHandler");
    }

    @GetMapping("/not-found")
    public ApiResponse<String> notFound() {
        log.warn("Resource not found requested");
        throw new ResourceNotFoundException("This resource does not exist");
    }

    @PostMapping("/validate")
    public ApiResponse<UserDto> validate(@Valid @RequestBody UserDto userDto) {
        log.info("Validating user: {}", userDto);
        return ApiResponse.success(userDto, "Validation passed");
    }
}