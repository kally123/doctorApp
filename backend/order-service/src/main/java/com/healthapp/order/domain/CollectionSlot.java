package com.healthapp.order.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Collection slot entity for home collection scheduling.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("collection_slots")
public class CollectionSlot {

    @Id
    private UUID id;

    @Column("lab_partner_id")
    private UUID labPartnerId;

    @Column("slot_date")
    private LocalDate slotDate;

    @Column("start_time")
    private LocalTime startTime;

    @Column("end_time")
    private LocalTime endTime;

    @Column("slot_label")
    private String slotLabel;

    @Column("max_bookings")
    private Integer maxBookings;

    @Column("current_bookings")
    private Integer currentBookings;

    @Column("serviceable_pincodes")
    private String[] serviceablePincodes;

    @Column("is_available")
    private Boolean isAvailable;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;
}
