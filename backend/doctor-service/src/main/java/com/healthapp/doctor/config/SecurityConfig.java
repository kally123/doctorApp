package com.healthapp.doctor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Security configuration for the doctor service.
 * 
 * Note: JWT validation is handled at the API Gateway level.
 * This service trusts requests that have passed through the gateway.
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // Health endpoints
                        .pathMatchers("/health/**", "/actuator/**").permitAll()
                        
                        // Public endpoints (read-only)
                        .pathMatchers(HttpMethod.GET, 
                                "/api/v1/doctors/{id}",
                                "/api/v1/doctors/top",
                                "/api/v1/doctors/city/**",
                                "/api/v1/specializations/**",
                                "/api/v1/languages/**"
                        ).permitAll()
                        
                        // All other endpoints require authentication (handled by gateway)
                        .anyExchange().permitAll()
                )
                .build();
    }
}
