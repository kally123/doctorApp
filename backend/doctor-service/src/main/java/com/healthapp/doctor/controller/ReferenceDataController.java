package com.healthapp.doctor.controller;

import com.healthapp.common.dto.ApiResponse;
import com.healthapp.doctor.model.dto.LanguageDto;
import com.healthapp.doctor.model.dto.SpecializationDto;
import com.healthapp.doctor.service.LanguageService;
import com.healthapp.doctor.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for reference data (specializations, languages).
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReferenceDataController {
    
    private final SpecializationService specializationService;
    private final LanguageService languageService;
    
    /**
     * Gets all active specializations.
     */
    @GetMapping("/specializations")
    public Mono<ApiResponse<List<SpecializationDto>>> getAllSpecializations() {
        return specializationService.getAllSpecializations()
                .collectList()
                .map(ApiResponse::success);
    }
    
    /**
     * Gets a specialization by ID.
     */
    @GetMapping("/specializations/{id}")
    public Mono<ApiResponse<SpecializationDto>> getSpecializationById(@PathVariable UUID id) {
        return specializationService.getSpecializationById(id)
                .map(ApiResponse::success);
    }
    
    /**
     * Gets subspecialties for a parent specialization.
     */
    @GetMapping("/specializations/{id}/subspecialties")
    public Mono<ApiResponse<List<SpecializationDto>>> getSubspecialties(@PathVariable UUID id) {
        return specializationService.getSubspecialties(id)
                .collectList()
                .map(ApiResponse::success);
    }
    
    /**
     * Gets all available languages.
     */
    @GetMapping("/languages")
    public Mono<ApiResponse<List<LanguageDto>>> getAllLanguages() {
        return languageService.getAllLanguages()
                .collectList()
                .map(ApiResponse::success);
    }
}
