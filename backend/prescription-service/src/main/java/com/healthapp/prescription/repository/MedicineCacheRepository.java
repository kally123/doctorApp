package com.healthapp.prescription.repository;

import com.healthapp.prescription.domain.MedicineCache;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface MedicineCacheRepository extends R2dbcRepository<MedicineCache, UUID> {

    Mono<MedicineCache> findByMedicineId(String medicineId);
    
    @Query("SELECT * FROM medicines_cache WHERE LOWER(brand_name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(generic_name) LIKE LOWER(CONCAT('%', :query, '%')) LIMIT :limit")
    Flux<MedicineCache> searchMedicines(String query, int limit);
    
    Flux<MedicineCache> findByGenericNameIgnoreCase(String genericName);
    
    Flux<MedicineCache> findByIsAvailable(Boolean isAvailable);
}
