package com.github.arpit.nexus.service.controller;

import com.github.arpit.nexus.core.redis.RedisService;
import com.github.arpit.nexus.core.web.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/redis")
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @PostMapping("/set")
    public ApiResponse<String> setValue(@RequestParam String key, @RequestParam String value, @RequestParam(defaultValue = "60") long timeout) {
        redisService.set(key, value, timeout, TimeUnit.SECONDS);
        return ApiResponse.success("Value set successfully", "Success");
    }

    @GetMapping("/get")
    public ApiResponse<Object> getValue(@RequestParam String key) {
        Object value = redisService.get(key);
        if (value == null) {
            return ApiResponse.error("Key not found", 404);
        }
        return ApiResponse.success(value, "Success");
    }

    @DeleteMapping("/delete")
    public ApiResponse<String> deleteValue(@RequestParam String key) {
        redisService.delete(key);
        return ApiResponse.success("Key deleted", "Success");
    }
}