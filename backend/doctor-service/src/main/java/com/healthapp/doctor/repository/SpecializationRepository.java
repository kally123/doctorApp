package com.healthapp.doctor.repository;

import com.healthapp.doctor.model.entity.Specialization;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Reactive repository for Specialization entity.
 */
@Repository
public interface SpecializationRepository extends R2dbcRepository<Specialization, UUID> {
    
    /**
     * Finds a specialization by name.
     */
    Mono<Specialization> findByName(String name);
    
    /**
     * Finds all active specializations.
     */
    Flux<Specialization> findByIsActiveTrueOrderByDisplayOrderAsc();
    
    /**
     * Finds specializations for a doctor.
     */
    @Query("""
        SELECT s.*, ds.is_primary FROM specializations s
        JOIN doctor_specializations ds ON s.id = ds.specialization_id
        WHERE ds.doctor_id = :doctorId
        ORDER BY ds.is_primary DESC, s.display_order ASC
        """)
    Flux<Specialization> findByDoctorId(UUID doctorId);
    
    /**
     * Finds child specializations.
     */
    Flux<Specialization> findByParentSpecialtyIdAndIsActiveTrue(UUID parentId);
}
