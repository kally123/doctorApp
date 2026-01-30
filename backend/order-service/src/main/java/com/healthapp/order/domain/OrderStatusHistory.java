package com.healthapp.order.domain;

import com.healthapp.order.domain.enums.OrderStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

/**
 * Order status history entity for tracking status changes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("order_status_history")
public class OrderStatusHistory {

    @Id
    private UUID id;

    @Column("order_id")
    private UUID orderId;

    @Column("from_status")
    private OrderStatus fromStatus;

    @Column("to_status")
    private OrderStatus toStatus;

    @Column("changed_by")
    private UUID changedBy;

    @Column("changed_by_type")
    private String changedByType; // CUSTOMER, PARTNER, SYSTEM, ADMIN

    @Column("notes")
    private String notes;

    @Column("metadata")
    private String metadata; // JSON

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;
}
