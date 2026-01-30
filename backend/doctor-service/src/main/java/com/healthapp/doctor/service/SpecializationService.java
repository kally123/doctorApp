package com.healthapp.doctor.service;

import com.healthapp.doctor.mapper.DoctorMapper;
import com.healthapp.doctor.model.dto.SpecializationDto;
import com.healthapp.doctor.repository.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Service for managing medical specializations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SpecializationService {
    
    private final SpecializationRepository specializationRepository;
    private final DoctorMapper doctorMapper;
    
    /**
     * Gets all active specializations.
     */
    @Cacheable(value = "specializations", key = "'all'")
    public Flux<SpecializationDto> getAllSpecializations() {
        return specializationRepository.findByIsActiveTrueOrderByDisplayOrderAsc()
                .map(doctorMapper::toDto);
    }
    
    /**
     * Gets specializations for a specific doctor.
     */
    public Flux<SpecializationDto> getDoctorSpecializations(UUID doctorId) {
        return specializationRepository.findByDoctorId(doctorId)
                .map(doctorMapper::toDto);
    }
    
    /**
     * Gets child specializations (subspecialties).
     */
    public Flux<SpecializationDto> getSubspecialties(UUID parentId) {
        return specializationRepository.findByParentSpecialtyIdAndIsActiveTrue(parentId)
                .map(doctorMapper::toDto);
    }
    
    /**
     * Gets a specialization by ID.
     */
    public Mono<SpecializationDto> getSpecializationById(UUID id) {
        return specializationRepository.findById(id)
                .map(doctorMapper::toDto);
    }
}
