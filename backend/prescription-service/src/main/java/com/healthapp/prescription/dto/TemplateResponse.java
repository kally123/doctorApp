package com.healthapp.prescription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateResponse {

    private UUID id;
    private UUID doctorId;
    private String templateName;
    private String description;
    private String diagnosis;
    private String specialization;
    private List<PrescriptionItemRequest> items;
    private String generalAdvice;
    private String dietAdvice;
    private Integer usageCount;
    private Instant lastUsedAt;
    private Boolean isActive;
    private Boolean isPublic;
    private Instant createdAt;
    private Instant updatedAt;
}
