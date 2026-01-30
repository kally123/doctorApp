package com.healthapp.doctor.service;

import com.healthapp.doctor.mapper.DoctorMapper;
import com.healthapp.doctor.model.dto.LanguageDto;
import com.healthapp.doctor.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

/**
 * Service for managing languages.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LanguageService {
    
    private final LanguageRepository languageRepository;
    private final DoctorMapper doctorMapper;
    
    /**
     * Gets all available languages.
     */
    @Cacheable(value = "languages", key = "'all'")
    public Flux<LanguageDto> getAllLanguages() {
        return languageRepository.findAllByOrderByNameAsc()
                .map(doctorMapper::toDto);
    }
    
    /**
     * Gets languages spoken by a doctor.
     */
    public Flux<LanguageDto> getDoctorLanguages(UUID doctorId) {
        return languageRepository.findByDoctorId(doctorId)
                .map(doctorMapper::toDto);
    }
}
