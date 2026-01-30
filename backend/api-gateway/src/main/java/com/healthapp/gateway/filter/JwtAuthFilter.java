package com.healthapp.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * Gateway filter for JWT authentication.
 * Validates JWT tokens and adds user information to request headers.
 */
@Slf4j
@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {
    
    private final SecretKey secretKey;
    
    public JwtAuthFilter(@Value("${jwt.secret}") String secret) {
        super(Config.class);
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return unauthorized(exchange, "Missing or invalid Authorization header");
            }
            
            String token = authHeader.substring(7);
            
            try {
                Claims claims = validateToken(token);
                
                // Add user info to headers for downstream services
                ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                        .header("X-User-Id", claims.getSubject())
                        .header("X-User-Email", getClaimAsString(claims, "email"))
                        .header("X-User-Role", getClaimAsString(claims, "role"))
                        .header("X-Correlation-Id", getOrCreateCorrelationId(exchange))
                        .build();
                
                log.debug("Authenticated user: {} with role: {}", 
                        claims.getSubject(), claims.get("role"));
                
                return chain.filter(exchange.mutate().request(mutatedRequest).build());
                
            } catch (JwtException e) {
                log.warn("Token validation failed: {}", e.getMessage());
                return unauthorized(exchange, "Invalid or expired token");
            }
        };
    }
    
    private Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    private String getClaimAsString(Claims claims, String claimName) {
        Object value = claims.get(claimName);
        return value != null ? value.toString() : "";
    }
    
    private String getOrCreateCorrelationId(ServerWebExchange exchange) {
        String correlationId = exchange.getRequest().getHeaders().getFirst("X-Correlation-Id");
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = java.util.UUID.randomUUID().toString();
        }
        return correlationId;
    }
    
    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        
        String body = String.format(
                "{\"success\":false,\"error\":{\"code\":\"UNAUTHORIZED\",\"message\":\"%s\"}}",
                message
        );
        
        return response.writeWith(Mono.just(
                response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8))
        ));
    }
    
    public static class Config {
        // Configuration properties if needed
    }
}
