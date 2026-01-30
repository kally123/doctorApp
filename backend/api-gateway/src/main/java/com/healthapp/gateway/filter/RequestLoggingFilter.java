package com.healthapp.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * Global filter for request logging.
 */
@Slf4j
@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String correlationId = request.getHeaders().getFirst("X-Correlation-Id");
        long startTime = Instant.now().toEpochMilli();
        
        log.info("Incoming request: {} {} [correlationId: {}]",
                request.getMethod(),
                request.getURI().getPath(),
                correlationId);
        
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    long duration = Instant.now().toEpochMilli() - startTime;
                    log.info("Completed request: {} {} - {} in {}ms [correlationId: {}]",
                            request.getMethod(),
                            request.getURI().getPath(),
                            exchange.getResponse().getStatusCode(),
                            duration,
                            correlationId);
                }));
    }
    
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
