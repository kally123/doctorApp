package com.healthapp.gateway.config;

import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GatewayFilter;

/**
 * Custom filter factory for adding correlation ID (used in route configuration).
 */
@Component
public class AddCorrelationId extends AbstractGatewayFilterFactory<AddCorrelationId.Config> {
    
    public AddCorrelationId() {
        super(Config.class);
    }
    
    @Override
    public GatewayFilter apply(Config config) {
        // Actual correlation ID handling is done by CorrelationIdFilter global filter
        return (exchange, chain) -> chain.filter(exchange);
    }
    
    public static class Config {
        // Configuration properties if needed
    }
}
