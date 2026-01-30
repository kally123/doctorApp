package com.healthapp.doctor.repository;

import com.healthapp.doctor.model.entity.Clinic;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

/**
 * Reactive repository for Clinic entity.
 */
@Repository
public interface ClinicRepository extends R2dbcRepository<Clinic, UUID> {
    
    /**
     * Finds clinics by city.
     */
    Flux<Clinic> findByCityIgnoreCase(String city);
    
    /**
     * Finds clinics for a doctor.
     */
    @Query("""
        SELECT c.*, dc.is_primary, dc.consultation_fee as clinic_fee FROM clinics c
        JOIN doctor_clinics dc ON c.id = dc.clinic_id
        WHERE dc.doctor_id = :doctorId AND dc.is_active = true
        ORDER BY dc.is_primary DESC
        """)
    Flux<Clinic> findByDoctorId(UUID doctorId);
}
