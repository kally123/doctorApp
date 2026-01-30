package com.healthapp.doctor.repository;

import com.healthapp.doctor.model.entity.Doctor;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Reactive repository for Doctor entity.
 */
@Repository
public interface DoctorRepository extends R2dbcRepository<Doctor, UUID> {
    
    /**
     * Finds a doctor by user ID.
     */
    Mono<Doctor> findByUserId(UUID userId);
    
    /**
     * Finds a doctor by registration number.
     */
    Mono<Doctor> findByRegistrationNumber(String registrationNumber);
    
    /**
     * Checks if a doctor exists by user ID.
     */
    Mono<Boolean> existsByUserId(UUID userId);
    
    /**
     * Checks if a registration number exists.
     */
    Mono<Boolean> existsByRegistrationNumber(String registrationNumber);
    
    /**
     * Finds verified doctors accepting patients.
     */
    @Query("SELECT * FROM doctors WHERE is_verified = true AND is_accepting_patients = true ORDER BY rating DESC LIMIT :limit")
    Flux<Doctor> findTopDoctors(int limit);
    
    /**
     * Finds doctors by city through their clinics.
     */
    @Query("""
        SELECT DISTINCT d.* FROM doctors d
        JOIN doctor_clinics dc ON d.id = dc.doctor_id
        JOIN clinics c ON dc.clinic_id = c.id
        WHERE c.city = :city AND d.is_verified = true AND d.is_accepting_patients = true
        ORDER BY d.rating DESC
        """)
    Flux<Doctor> findByCity(String city);
}
