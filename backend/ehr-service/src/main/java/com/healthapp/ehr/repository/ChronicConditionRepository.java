package com.healthapp.ehr.repository;

import com.healthapp.ehr.domain.ChronicCondition;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Repository for chronic conditions.
 */
public interface ChronicConditionRepository extends ReactiveMongoRepository<ChronicCondition, String> {

    Flux<ChronicCondition> findByPatientIdOrderByDiagnosedDateDesc(UUID patientId);

    Flux<ChronicCondition> findByPatientIdAndIsActiveTrueOrderByDiagnosedDateDesc(UUID patientId);

    Flux<ChronicCondition> findByPatientIdAndStatus(UUID patientId, String status);

    Mono<Boolean> existsByPatientIdAndIcdCode(UUID patientId, String icdCode);

    Mono<Long> countByPatientIdAndIsActiveTrue(UUID patientId);
}
