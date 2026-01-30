package com.healthapp.order.dto;

import com.healthapp.order.domain.enums.BookingType;
import com.healthapp.order.domain.enums.LabBookingStatus;
import com.healthapp.order.domain.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Response DTO for lab booking.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabBookingResponse {

    private UUID id;

    private String bookingNumber;

    private UUID userId;

    private String patientName;

    private Integer patientAge;

    private String patientGender;

    private String patientPhone;

    private BookingType bookingType;

    private UUID labPartnerId;

    private String labPartnerName;

    private AddressResponse collectionAddress;

    private LocalDate scheduledDate;

    private String scheduledSlot;

    private List<LabTestResponse> tests;

    private TestPackageResponse package_;

    private BigDecimal subtotal;

    private BigDecimal discountAmount;

    private BigDecimal homeCollectionFee;

    private BigDecimal totalAmount;

    private PaymentStatus paymentStatus;

    private UUID paymentId;

    private Instant paidAt;

    private LabBookingStatus status;

    private String phlebotomistName;

    private String phlebotomistPhone;

    private Instant sampleCollectedAt;

    private Instant reportReadyAt;

    private String reportUrl;

    private String notes;

    private Instant createdAt;

    private Instant updatedAt;

    private boolean canCancel;

    private boolean canDownloadReport;
}
