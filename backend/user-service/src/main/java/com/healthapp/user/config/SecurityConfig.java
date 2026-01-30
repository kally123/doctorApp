package com.healthapp.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Security configuration for the user service.
 * Note: JWT validation is handled by the API Gateway.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // Public endpoints
                        .pathMatchers(
                                "/api/v1/users/register",
                                "/api/v1/users/login",
                                "/api/v1/users/verify-otp",
                                "/api/v1/users/forgot-password",
                                "/api/v1/users/reset-password",
                                "/api/v1/users/refresh-token",
                                "/health",
                                "/actuator/**",
                                "/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/webjars/**"
                        ).permitAll()
                        // All other endpoints require authentication (validated by X-User-Id header from gateway)
                        .anyExchange().permitAll() // Gateway handles auth, we just check header
                )
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
