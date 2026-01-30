package com.healthapp.gateway.config;

import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GatewayFilter;

/**
 * Custom filter factory for request logging (used in route configuration).
 */
@Component
public class RequestLogging extends AbstractGatewayFilterFactory<RequestLogging.Config> {
    
    public RequestLogging() {
        super(Config.class);
    }
    
    @Override
    public GatewayFilter apply(Config config) {
        // Actual logging is handled by RequestLoggingFilter global filter
        return (exchange, chain) -> chain.filter(exchange);
    }
    
    public static class Config {
        // Configuration properties if needed
    }
}
