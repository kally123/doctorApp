package com.healthapp.prescription.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

/**
 * Prescription audit log for tracking all actions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("prescription_audit")
public class PrescriptionAudit {

    @Id
    private UUID id;
    
    @Column("prescription_id")
    private UUID prescriptionId;
    
    private String action;
    
    @Column("actor_id")
    private UUID actorId;
    
    @Column("actor_type")
    private String actorType;
    
    @Column("previous_status")
    private String previousStatus;
    
    @Column("new_status")
    private String newStatus;
    
    @Column("change_details")
    private String changeDetails;
    
    @Column("ip_address")
    private String ipAddress;
    
    @Column("user_agent")
    private String userAgent;
    
    @CreatedDate
    @Column("created_at")
    private Instant createdAt;
}
