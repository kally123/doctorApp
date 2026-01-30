package com.healthapp.ehr.repository;

import com.healthapp.ehr.domain.Allergy;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository for allergies.
 */
public interface AllergyRepository extends ReactiveMongoRepository<Allergy, String> {

    Flux<Allergy> findByPatientIdOrderByCreatedAtDesc(UUID patientId);

    Flux<Allergy> findByPatientIdAndIsActiveTrueOrderByCreatedAtDesc(UUID patientId);

    Flux<Allergy> findByPatientIdAndAllergenType(UUID patientId, String allergenType);

    Mono<Boolean> existsByPatientIdAndAllergenIgnoreCase(UUID patientId, String allergen);

    Mono<Long> countByPatientIdAndIsActiveTrue(UUID patientId);
}
