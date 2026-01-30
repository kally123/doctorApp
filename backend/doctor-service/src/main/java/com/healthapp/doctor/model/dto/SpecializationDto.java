package com.healthapp.doctor.model.dto;

import lombok.Builder;
import lombok.Value;

/**
 * DTO for specialization information.
 */
@Value
@Builder
public class SpecializationDto {
    String id;
    String name;
    String description;
    String iconUrl;
    Boolean isPrimary;
}
