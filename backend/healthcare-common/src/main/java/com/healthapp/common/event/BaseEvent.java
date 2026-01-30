package com.healthapp.common.event;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * Base event structure for all domain events in the system.
 * Follows CloudEvents specification for event metadata.
 */
@Value
@Builder
public class BaseEvent<T> {
    
    /**
     * Unique identifier for this event.
     */
    @Builder.Default
    String eventId = UUID.randomUUID().toString();
    
    /**
     * Event type identifier (e.g., "user.registered.v1").
     */
    String eventType;
    
    /**
     * Timestamp when the event occurred.
     */
    @Builder.Default
    Instant eventTime = Instant.now();
    
    /**
     * Source service that produced this event.
     */
    String source;
    
    /**
     * Event data payload.
     */
    T data;
    
    /**
     * Correlation ID for tracing across services.
     */
    String correlationId;
    
    /**
     * Schema version for the event data.
     */
    @Builder.Default
    String dataVersion = "1.0";
    
    /**
     * Optional metadata.
     */
    Map<String, String> metadata;
    
    /**
     * Creates a new event with the specified type and data.
     */
    public static <T> BaseEvent<T> of(String eventType, String source, T data) {
        return BaseEvent.<T>builder()
                .eventType(eventType)
                .source(source)
                .data(data)
                .build();
    }
    
    /**
     * Creates a new event with correlation ID for tracing.
     */
    public static <T> BaseEvent<T> of(String eventType, String source, T data, String correlationId) {
        return BaseEvent.<T>builder()
                .eventType(eventType)
                .source(source)
                .data(data)
                .correlationId(correlationId)
                .build();
    }
}
