package com.healthapp.order.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Response DTO for available collection slot.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableSlotResponse {

    private UUID slotId;

    private UUID labPartnerId;

    private String labPartnerName;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String slotLabel;

    private int availableCapacity;

    private BigDecimal homeCollectionFee;
}
