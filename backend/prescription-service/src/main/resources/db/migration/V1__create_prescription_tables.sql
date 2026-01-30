-- V1__create_prescription_tables.sql
-- Prescription Service Database Schema

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Main prescriptions table
CREATE TABLE prescriptions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    
    -- References
    consultation_id UUID,
    appointment_id UUID,
    patient_id UUID NOT NULL,
    doctor_id UUID NOT NULL,
    
    -- Prescription details
    prescription_number VARCHAR(50) UNIQUE NOT NULL,
    prescription_date DATE NOT NULL DEFAULT CURRENT_DATE,
    valid_until DATE,
    
    -- Clinical information
    diagnosis TEXT,
    chief_complaints TEXT,
    clinical_notes TEXT,
    
    -- Advice and follow-up
    general_advice TEXT,
    diet_advice TEXT,
    follow_up_date DATE,
    follow_up_notes TEXT,
    
    -- Lab tests recommended (stored as JSON array)
    lab_tests_recommended TEXT,
    
    -- Digital signature
    status VARCHAR(30) NOT NULL DEFAULT 'DRAFT',
    signed_at TIMESTAMP WITH TIME ZONE,
    signature_hash VARCHAR(500),
    certificate_serial VARCHAR(100),
    
    -- PDF storage
    pdf_url VARCHAR(500),
    pdf_s3_key VARCHAR(500),
    pdf_generated_at TIMESTAMP WITH TIME ZONE,
    
    -- Template used
    template_id UUID,
    
    -- Metadata
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    created_by UUID NOT NULL
);

-- Indexes for prescriptions
CREATE INDEX idx_prescriptions_patient ON prescriptions(patient_id);
CREATE INDEX idx_prescriptions_doctor ON prescriptions(doctor_id);
CREATE INDEX idx_prescriptions_consultation ON prescriptions(consultation_id);
CREATE INDEX idx_prescriptions_date ON prescriptions(prescription_date);
CREATE INDEX idx_prescriptions_status ON prescriptions(status);
CREATE INDEX idx_prescriptions_number ON prescriptions(prescription_number);

-- Prescription items (medicines)
CREATE TABLE prescription_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    prescription_id UUID NOT NULL REFERENCES prescriptions(id) ON DELETE CASCADE,
    
    -- Medicine reference
    medicine_id VARCHAR(50),
    medicine_name VARCHAR(255) NOT NULL,
    generic_name VARCHAR(255),
    manufacturer VARCHAR(255),
    
    -- Dosage information
    strength VARCHAR(50),
    formulation VARCHAR(50),
    
    -- Prescription details
    dosage VARCHAR(100),
    frequency VARCHAR(100),
    duration VARCHAR(100),
    timing VARCHAR(100),
    route VARCHAR(50),
    
    quantity INT,
    quantity_unit VARCHAR(20),
    
    -- Additional instructions
    special_instructions TEXT,
    
    -- Ordering
    sequence_order INT DEFAULT 0,
    
    -- Dispensing tracking
    is_dispensed BOOLEAN DEFAULT FALSE,
    dispensed_quantity INT DEFAULT 0,
    
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_prescription_items_prescription ON prescription_items(prescription_id);

-- Prescription templates
CREATE TABLE prescription_templates (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    doctor_id UUID NOT NULL,
    
    template_name VARCHAR(255) NOT NULL,
    description TEXT,
    
    -- Template conditions
    diagnosis VARCHAR(255),
    specialization VARCHAR(100),
    
    -- Template content (stored as JSON)
    template_items TEXT NOT NULL,
    general_advice TEXT,
    diet_advice TEXT,
    
    -- Usage tracking
    usage_count INT DEFAULT 0,
    last_used_at TIMESTAMP WITH TIME ZONE,
    
    -- Status
    is_active BOOLEAN DEFAULT TRUE,
    is_public BOOLEAN DEFAULT FALSE,
    
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    
    CONSTRAINT unique_template_name UNIQUE (doctor_id, template_name)
);

CREATE INDEX idx_templates_doctor ON prescription_templates(doctor_id);
CREATE INDEX idx_templates_diagnosis ON prescription_templates(diagnosis);

-- Prescription audit trail
CREATE TABLE prescription_audit (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    prescription_id UUID NOT NULL REFERENCES prescriptions(id),
    
    action VARCHAR(50) NOT NULL,
    actor_id UUID NOT NULL,
    actor_type VARCHAR(20),
    
    previous_status VARCHAR(30),
    new_status VARCHAR(30),
    
    change_details TEXT,
    ip_address VARCHAR(45),
    user_agent TEXT,
    
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_prescription_audit_prescription ON prescription_audit(prescription_id);
CREATE INDEX idx_prescription_audit_created ON prescription_audit(created_at);

-- Medicine cache table (local cache from Elasticsearch)
CREATE TABLE medicines_cache (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    medicine_id VARCHAR(50) UNIQUE NOT NULL,
    brand_name VARCHAR(255) NOT NULL,
    generic_name VARCHAR(255),
    manufacturer VARCHAR(255),
    category VARCHAR(100),
    formulation VARCHAR(50),
    strength VARCHAR(50),
    pack_size INT,
    price DECIMAL(10, 2),
    requires_prescription BOOLEAN DEFAULT TRUE,
    is_available BOOLEAN DEFAULT TRUE,
    last_synced_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_medicines_brand ON medicines_cache(brand_name);
CREATE INDEX idx_medicines_generic ON medicines_cache(generic_name);
