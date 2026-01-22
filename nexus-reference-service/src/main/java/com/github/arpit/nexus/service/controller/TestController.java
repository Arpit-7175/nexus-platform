package com.github.arpit.nexus.service.controller;

import com.github.arpit.nexus.core.audit.LogExecutionTime;
import com.github.arpit.nexus.core.exception.ResourceNotFoundException;
import com.github.arpit.nexus.core.web.ApiResponse;
import com.github.arpit.nexus.service.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hello")
    @LogExecutionTime
    public ApiResponse<String> hello() {
        return ApiResponse.success("Hello from Nexus Reference Service!", "Operation successful");
    }

    @GetMapping("/error")
    @LogExecutionTime
    public ApiResponse<String> error() {
        throw new RuntimeException("Simulated error for testing GlobalExceptionHandler");
    }

    @GetMapping("/not-found")
    public ApiResponse<String> notFound() {
        throw new ResourceNotFoundException("This resource does not exist");
    }

    @PostMapping("/validate")
    public ApiResponse<UserDto> validate(@Valid @RequestBody UserDto userDto) {
        return ApiResponse.success(userDto, "Validation passed");
    }
}