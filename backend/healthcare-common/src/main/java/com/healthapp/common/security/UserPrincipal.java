package com.healthapp.common.security;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

/**
 * Represents the authenticated user principal.
 */
@Value
@Builder
public class UserPrincipal {
    
    String userId;
    String email;
    String phone;
    String role;
    Set<String> permissions;
    
    /**
     * Checks if the user has the specified role.
     */
    public boolean hasRole(String role) {
        return this.role != null && this.role.equalsIgnoreCase(role);
    }
    
    /**
     * Checks if the user has the specified permission.
     */
    public boolean hasPermission(String permission) {
        return permissions != null && permissions.contains(permission);
    }
    
    /**
     * Checks if the user is an admin.
     */
    public boolean isAdmin() {
        return hasRole("ADMIN");
    }
    
    /**
     * Checks if the user is a doctor.
     */
    public boolean isDoctor() {
        return hasRole("DOCTOR");
    }
    
    /**
     * Checks if the user is a patient.
     */
    public boolean isPatient() {
        return hasRole("PATIENT");
    }
}
