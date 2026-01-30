package com.healthapp.order.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Partner inventory entity for real-time availability.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("partner_inventory")
public class PartnerInventory {

    @Id
    private UUID id;

    @Column("partner_id")
    private UUID partnerId;

    @Column("product_id")
    private String productId;

    @Column("quantity_available")
    private Integer quantityAvailable;

    @Column("unit_price")
    private BigDecimal unitPrice;

    @Column("mrp")
    private BigDecimal mrp;

    @Column("discount_percent")
    private BigDecimal discountPercent;

    @Column("is_available")
    private Boolean isAvailable;

    @LastModifiedDate
    @Column("last_synced_at")
    private Instant lastSyncedAt;
}
