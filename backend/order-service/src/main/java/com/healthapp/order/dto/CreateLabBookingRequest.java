package com.healthapp.order.dto;

import com.healthapp.order.domain.enums.BookingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Request DTO for creating lab booking.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLabBookingRequest {

    @NotBlank(message = "Patient name is required")
    private String patientName;

    private Integer patientAge;

    private String patientGender;

    @NotBlank(message = "Patient phone is required")
    private String patientPhone;

    @NotNull(message = "Booking type is required")
    private BookingType bookingType;

    @NotNull(message = "Lab partner is required")
    private UUID labPartnerId;

    // For home collection
    private UUID collectionAddressId;

    @NotNull(message = "Scheduled date is required")
    private LocalDate scheduledDate;

    private String scheduledSlot;

    private UUID slotId;

    // Tests to book
    @NotEmpty(message = "At least one test is required")
    private List<UUID> testIds;

    private UUID packageId;

    private String notes;
}
