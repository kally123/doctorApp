package com.healthapp.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Health check controller for service monitoring.
 */
@RestController
public class HealthController {
    
    @GetMapping("/health")
    public Mono<Map<String, Object>> health() {
        return Mono.just(Map.of(
                "status", "UP",
                "service", "user-service"
        ));
    }
}
