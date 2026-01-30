package com.healthapp.doctor.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

/**
 * Language entity for languages spoken by doctors.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("languages")
public class Language {
    
    @Id
    private UUID id;
    
    private String name;
    
    private String code;
}
