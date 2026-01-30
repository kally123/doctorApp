package com.healthapp.doctor.model.dto;

import lombok.Builder;
import lombok.Value;

/**
 * DTO for language information.
 */
@Value
@Builder
public class LanguageDto {
    String id;
    String name;
    String code;
}
