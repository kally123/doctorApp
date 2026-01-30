package com.healthapp.doctor.repository;

import com.healthapp.doctor.model.entity.DoctorQualification;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Reactive repository for DoctorQualification entity.
 */
@Repository
public interface QualificationRepository extends R2dbcRepository<DoctorQualification, UUID> {
    
    /**
     * Finds all qualifications for a doctor.
     */
    Flux<DoctorQualification> findByDoctorIdOrderByYearOfCompletionDesc(UUID doctorId);
    
    /**
     * Deletes all qualifications for a doctor.
     */
    Mono<Void> deleteByDoctorId(UUID doctorId);
}
