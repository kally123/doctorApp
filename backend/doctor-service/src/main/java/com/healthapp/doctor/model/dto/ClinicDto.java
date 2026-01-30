package com.healthapp.doctor.model.dto;

import lombok.Builder;
import lombok.Value;

/**
 * DTO for clinic information.
 */
@Value
@Builder
public class ClinicDto {
    String id;
    String name;
    String addressLine1;
    String addressLine2;
    String city;
    String state;
    String country;
    String postalCode;
    Double latitude;
    Double longitude;
    String phone;
    String email;
    String fullAddress;
    Boolean isPrimary;
}
