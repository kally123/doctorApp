package com.healthapp.order.dto;

import com.healthapp.order.domain.enums.OrderStatus;
import lombok.*;

import java.time.Instant;

/**
 * Individual tracking step for order tracking.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackingStep {

    private OrderStatus status;

    private String title;

    private String description;

    private Instant timestamp;

    private boolean isComplete;

    private boolean isCurrent;
}
