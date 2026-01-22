package com.github.arpit.nexus.core.security.ratelimit;

import com.github.arpit.nexus.core.exception.ResourceNotFoundException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimit)")
    public Object enforceRateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String key = getClientKey();
        Bucket bucket = buckets.computeIfAbsent(key, k -> createNewBucket(rateLimit));

        if (bucket.tryConsume(1)) {
            return joinPoint.proceed();
        } else {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Rate limit exceeded. Try again later.");
        }
    }

    private Bucket createNewBucket(RateLimit rateLimit) {
        long capacity = rateLimit.limit();
        long durationInSeconds = rateLimit.unit().toSeconds(rateLimit.duration());
        
        Bandwidth limit = Bandwidth.classic(capacity, Refill.greedy(capacity, Duration.ofSeconds(durationInSeconds)));
        return Bucket4j.builder().addLimit(limit).build();
    }

    private String getClientKey() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            return authentication.getName(); // User ID
        }
        
        // Fallback to IP address for anonymous users
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return request.getRemoteAddr();
        }
        
        return "unknown";
    }
}