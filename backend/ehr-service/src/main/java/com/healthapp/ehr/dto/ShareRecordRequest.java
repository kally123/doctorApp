package com.healthapp.ehr.dto;

import com.healthapp.ehr.domain.enums.AccessLevel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Request to share records with a healthcare provider.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareRecordRequest {

    @NotNull(message = "Doctor ID is required")
    private UUID sharedWithId;

    private String sharedWithName;
    private String sharedWithRole;

    @NotNull(message = "Access level is required")
    private AccessLevel accessLevel;

    private List<String> recordTypes;
    private List<String> specificRecordIds;

    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
}
