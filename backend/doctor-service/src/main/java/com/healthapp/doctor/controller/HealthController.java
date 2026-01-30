package com.healthapp.doctor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Health check controller for Kubernetes probes.
 */
@RestController
@RequestMapping("/health")
public class HealthController {
    
    @GetMapping
    public Mono<Map<String, String>> health() {
        return Mono.just(Map.of(
                "status", "UP",
                "service", "doctor-service"
        ));
    }
    
    @GetMapping("/liveness")
    public Mono<Map<String, String>> liveness() {
        return Mono.just(Map.of("status", "UP"));
    }
    
    @GetMapping("/readiness")
    public Mono<Map<String, String>> readiness() {
        return Mono.just(Map.of("status", "UP"));
    }
}
