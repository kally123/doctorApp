package com.healthapp.ehr.repository;

import com.healthapp.ehr.domain.VitalReading;
import com.healthapp.ehr.domain.enums.VitalType;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository for vital readings with time-series queries.
 */
public interface VitalReadingRepository extends ReactiveMongoRepository<VitalReading, String> {

    Flux<VitalReading> findByPatientIdOrderByRecordedAtDesc(UUID patientId);

    Flux<VitalReading> findByPatientIdAndVitalTypeOrderByRecordedAtDesc(UUID patientId, VitalType vitalType);

    Flux<VitalReading> findByPatientIdAndVitalTypeAndRecordedAtBetweenOrderByRecordedAtDesc(
            UUID patientId, VitalType vitalType, LocalDateTime start, LocalDateTime end);

    Flux<VitalReading> findByPatientIdAndRecordedAtBetweenOrderByRecordedAtDesc(
            UUID patientId, LocalDateTime start, LocalDateTime end);

    Flux<VitalReading> findByConsultationId(UUID consultationId);

    Mono<VitalReading> findFirstByPatientIdAndVitalTypeOrderByRecordedAtDesc(UUID patientId, VitalType vitalType);

    Flux<VitalReading> findByPatientIdAndIsAbnormalTrueOrderByRecordedAtDesc(UUID patientId);

    @Query("{'patientId': ?0, 'vitalType': ?1}")
    @Aggregation(pipeline = {
            "{ $match: { 'patientId': ?0, 'vitalType': ?1 } }",
            "{ $group: { '_id': null, 'avg': { $avg: '$value' }, 'min': { $min: '$value' }, 'max': { $max: '$value' }, 'count': { $sum: 1 } } }"
    })
    Mono<VitalStatistics> getVitalStatistics(UUID patientId, VitalType vitalType);

    @Query("{'patientId': ?0, 'vitalType': ?1, 'recordedAt': { $gte: ?2, $lte: ?3 }}")
    @Aggregation(pipeline = {
            "{ $match: { 'patientId': ?0, 'vitalType': ?1, 'recordedAt': { $gte: ?2, $lte: ?3 } } }",
            "{ $group: { '_id': null, 'avg': { $avg: '$value' }, 'min': { $min: '$value' }, 'max': { $max: '$value' }, 'count': { $sum: 1 } } }"
    })
    Mono<VitalStatistics> getVitalStatisticsInPeriod(
            UUID patientId, VitalType vitalType, LocalDateTime start, LocalDateTime end);

    interface VitalStatistics {
        Double getAvg();
        Double getMin();
        Double getMax();
        Long getCount();
    }
}
