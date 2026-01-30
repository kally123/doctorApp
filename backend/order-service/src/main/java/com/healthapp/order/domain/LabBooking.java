package com.healthapp.order.domain;

import com.healthapp.order.domain.enums.BookingType;
import com.healthapp.order.domain.enums.LabBookingStatus;
import com.healthapp.order.domain.enums.PaymentStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Lab booking entity for lab test bookings.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("lab_bookings")
public class LabBooking {

    @Id
    private UUID id;

    @Column("booking_number")
    private String bookingNumber;

    // Customer
    @Column("user_id")
    private UUID userId;

    @Column("patient_name")
    private String patientName;

    @Column("patient_age")
    private Integer patientAge;

    @Column("patient_gender")
    private String patientGender;

    @Column("patient_phone")
    private String patientPhone;

    // Booking type
    @Column("booking_type")
    private BookingType bookingType;

    // Lab partner
    @Column("lab_partner_id")
    private UUID labPartnerId;

    @Column("lab_partner_name")
    private String labPartnerName;

    // For home collection
    @Column("collection_address_id")
    private UUID collectionAddressId;

    @Column("collection_address_snapshot")
    private String collectionAddressSnapshot; // JSON

    @Column("scheduled_date")
    private LocalDate scheduledDate;

    @Column("scheduled_slot")
    private String scheduledSlot;

    // For walk-in
    @Column("lab_center_id")
    private UUID labCenterId;

    // Tests booked (JSON array)
    @Column("tests")
    private String tests;

    @Column("package_id")
    private UUID packageId;

    // Pricing
    @Column("subtotal")
    private BigDecimal subtotal;

    @Column("discount_amount")
    private BigDecimal discountAmount;

    @Column("home_collection_fee")
    private BigDecimal homeCollectionFee;

    @Column("total_amount")
    private BigDecimal totalAmount;

    // Payment
    @Column("payment_status")
    private PaymentStatus paymentStatus;

    @Column("payment_id")
    private UUID paymentId;

    @Column("paid_at")
    private Instant paidAt;

    // Status
    @Column("status")
    private LabBookingStatus status;

    // Phlebotomist (for home collection)
    @Column("phlebotomist_id")
    private UUID phlebotomistId;

    @Column("phlebotomist_name")
    private String phlebotomistName;

    @Column("phlebotomist_phone")
    private String phlebotomistPhone;

    // Sample tracking
    @Column("sample_collected_at")
    private Instant sampleCollectedAt;

    @Column("sample_received_at_lab")
    private Instant sampleReceivedAtLab;

    // Report
    @Column("report_ready_at")
    private Instant reportReadyAt;

    @Column("report_url")
    private String reportUrl;

    @Column("report_document_id")
    private UUID reportDocumentId;

    // Notes
    @Column("notes")
    private String notes;

    @Column("internal_notes")
    private String internalNotes;

    // Timestamps
    @CreatedDate
    @Column("created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt;

    @Column("cancelled_at")
    private Instant cancelledAt;

    @Column("cancellation_reason")
    private String cancellationReason;
}
