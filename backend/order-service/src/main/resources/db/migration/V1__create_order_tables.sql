-- Order Service Database Schema
-- V1: Create all order-related tables

-- Partners (Pharmacies and Labs)
CREATE TABLE partners (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL, -- PHARMACY, LAB
    email VARCHAR(255),
    phone VARCHAR(20),
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    pincode VARCHAR(20),
    country VARCHAR(100) DEFAULT 'India',
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    license_number VARCHAR(100),
    gst_number VARCHAR(50),
    rating DECIMAL(3,2) DEFAULT 0.0,
    total_ratings INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT true,
    is_verified BOOLEAN DEFAULT false,
    opening_time TIME,
    closing_time TIME,
    delivery_radius_km DECIMAL(5,2),
    minimum_order_amount DECIMAL(10,2),
    delivery_fee DECIMAL(10,2),
    free_delivery_above DECIMAL(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_partners_type ON partners(type);
CREATE INDEX idx_partners_city ON partners(city);
CREATE INDEX idx_partners_location ON partners(latitude, longitude);
CREATE INDEX idx_partners_active ON partners(is_active);

-- Partner Inventory (for pharmacies)
CREATE TABLE partner_inventory (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    partner_id UUID NOT NULL REFERENCES partners(id),
    product_id VARCHAR(100) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    manufacturer VARCHAR(255),
    category VARCHAR(100),
    sku VARCHAR(100),
    batch_number VARCHAR(100),
    expiry_date DATE,
    mrp DECIMAL(10,2) NOT NULL,
    selling_price DECIMAL(10,2) NOT NULL,
    discount_percentage DECIMAL(5,2) DEFAULT 0,
    stock_quantity INTEGER DEFAULT 0,
    is_available BOOLEAN DEFAULT true,
    requires_prescription BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_inventory_partner ON partner_inventory(partner_id);
CREATE INDEX idx_inventory_product ON partner_inventory(product_id);
CREATE INDEX idx_inventory_available ON partner_inventory(is_available, stock_quantity);

-- Delivery Addresses
CREATE TABLE delivery_addresses (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    label VARCHAR(100),
    type VARCHAR(50) DEFAULT 'HOME', -- HOME, WORK, OTHER
    recipient_name VARCHAR(255),
    phone VARCHAR(20),
    address_line1 VARCHAR(255) NOT NULL,
    address_line2 VARCHAR(255),
    landmark VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    pincode VARCHAR(20) NOT NULL,
    country VARCHAR(100) DEFAULT 'India',
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    is_default BOOLEAN DEFAULT false,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_addresses_user ON delivery_addresses(user_id);
CREATE INDEX idx_addresses_default ON delivery_addresses(user_id, is_default);

-- Orders
CREATE TABLE orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_number VARCHAR(50) UNIQUE NOT NULL,
    user_id UUID NOT NULL,
    partner_id UUID REFERENCES partners(id),
    prescription_id UUID,
    order_type VARCHAR(50) NOT NULL, -- MEDICINE, LAB_TEST
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    payment_status VARCHAR(50) DEFAULT 'PENDING',
    payment_id VARCHAR(100),
    transaction_id VARCHAR(100),
    delivery_type VARCHAR(50) DEFAULT 'STANDARD', -- STANDARD, EXPRESS, SCHEDULED
    
    -- Pricing
    subtotal DECIMAL(12,2) NOT NULL DEFAULT 0,
    discount_amount DECIMAL(12,2) DEFAULT 0,
    coupon_code VARCHAR(50),
    coupon_discount DECIMAL(12,2) DEFAULT 0,
    delivery_fee DECIMAL(10,2) DEFAULT 0,
    packaging_fee DECIMAL(10,2) DEFAULT 0,
    tax_amount DECIMAL(12,2) DEFAULT 0,
    total_amount DECIMAL(12,2) NOT NULL DEFAULT 0,
    
    -- Delivery Address
    delivery_address_id UUID REFERENCES delivery_addresses(id),
    delivery_address_snapshot JSONB,
    
    -- Scheduling
    scheduled_delivery_date DATE,
    scheduled_delivery_slot VARCHAR(50),
    estimated_delivery_time TIMESTAMP,
    actual_delivery_time TIMESTAMP,
    
    -- Tracking
    tracking_number VARCHAR(100),
    delivery_partner VARCHAR(100),
    delivery_agent_name VARCHAR(255),
    delivery_agent_phone VARCHAR(20),
    current_location_lat DOUBLE PRECISION,
    current_location_lng DOUBLE PRECISION,
    
    -- Notes
    customer_notes TEXT,
    internal_notes TEXT,
    cancellation_reason TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    confirmed_at TIMESTAMP,
    shipped_at TIMESTAMP,
    delivered_at TIMESTAMP,
    cancelled_at TIMESTAMP
);

CREATE INDEX idx_orders_user ON orders(user_id);
CREATE INDEX idx_orders_partner ON orders(partner_id);
CREATE INDEX idx_orders_number ON orders(order_number);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_created ON orders(created_at DESC);
CREATE INDEX idx_orders_prescription ON orders(prescription_id);

-- Order Items
CREATE TABLE order_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_id VARCHAR(100) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    manufacturer VARCHAR(255),
    category VARCHAR(100),
    sku VARCHAR(100),
    batch_number VARCHAR(100),
    expiry_date DATE,
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price DECIMAL(10,2) NOT NULL,
    mrp DECIMAL(10,2),
    discount_percentage DECIMAL(5,2) DEFAULT 0,
    discount_amount DECIMAL(10,2) DEFAULT 0,
    tax_rate DECIMAL(5,2) DEFAULT 0,
    tax_amount DECIMAL(10,2) DEFAULT 0,
    total_price DECIMAL(10,2) NOT NULL,
    requires_prescription BOOLEAN DEFAULT false,
    is_substituted BOOLEAN DEFAULT false,
    substituted_product_id VARCHAR(100),
    substituted_product_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_order_items_order ON order_items(order_id);
CREATE INDEX idx_order_items_product ON order_items(product_id);

-- Order Status History
CREATE TABLE order_status_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    status VARCHAR(50) NOT NULL,
    notes TEXT,
    updated_by VARCHAR(100),
    updated_by_type VARCHAR(50), -- SYSTEM, USER, PARTNER, ADMIN
    location_lat DOUBLE PRECISION,
    location_lng DOUBLE PRECISION,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_status_history_order ON order_status_history(order_id);
CREATE INDEX idx_status_history_created ON order_status_history(created_at DESC);

-- Test Categories
CREATE TABLE test_categories (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    icon_url VARCHAR(500),
    display_order INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_test_categories_active ON test_categories(is_active);

-- Lab Tests
CREATE TABLE lab_tests (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    category_id UUID REFERENCES test_categories(id),
    name VARCHAR(255) NOT NULL,
    code VARCHAR(50),
    description TEXT,
    sample_type VARCHAR(100), -- BLOOD, URINE, STOOL, etc.
    preparation_instructions TEXT,
    fasting_required BOOLEAN DEFAULT false,
    fasting_hours INTEGER,
    report_delivery_hours INTEGER DEFAULT 24,
    mrp DECIMAL(10,2) NOT NULL,
    selling_price DECIMAL(10,2) NOT NULL,
    discount_percentage DECIMAL(5,2) DEFAULT 0,
    home_collection_available BOOLEAN DEFAULT true,
    home_collection_fee DECIMAL(10,2) DEFAULT 0,
    is_popular BOOLEAN DEFAULT false,
    is_active BOOLEAN DEFAULT true,
    parameters JSONB, -- List of test parameters
    included_tests JSONB, -- For packages, list of included test IDs
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_lab_tests_category ON lab_tests(category_id);
CREATE INDEX idx_lab_tests_name ON lab_tests(name);
CREATE INDEX idx_lab_tests_code ON lab_tests(code);
CREATE INDEX idx_lab_tests_popular ON lab_tests(is_popular);
CREATE INDEX idx_lab_tests_active ON lab_tests(is_active);

-- Test Packages
CREATE TABLE test_packages (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    short_description VARCHAR(500),
    image_url VARCHAR(500),
    included_test_count INTEGER DEFAULT 0,
    included_parameter_count INTEGER DEFAULT 0,
    sample_types JSONB, -- List of sample types required
    preparation_instructions TEXT,
    fasting_required BOOLEAN DEFAULT false,
    fasting_hours INTEGER,
    report_delivery_hours INTEGER DEFAULT 48,
    mrp DECIMAL(10,2) NOT NULL,
    selling_price DECIMAL(10,2) NOT NULL,
    discount_percentage DECIMAL(5,2) DEFAULT 0,
    home_collection_available BOOLEAN DEFAULT true,
    home_collection_fee DECIMAL(10,2) DEFAULT 0,
    is_popular BOOLEAN DEFAULT false,
    is_active BOOLEAN DEFAULT true,
    included_tests JSONB, -- List of included test IDs
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_test_packages_popular ON test_packages(is_popular);
CREATE INDEX idx_test_packages_active ON test_packages(is_active);

-- Collection Slots
CREATE TABLE collection_slots (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    partner_id UUID NOT NULL REFERENCES partners(id),
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    slot_label VARCHAR(100), -- e.g., "Morning 6AM-8AM"
    max_bookings INTEGER DEFAULT 5,
    current_bookings INTEGER DEFAULT 0,
    is_available BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_collection_slots_partner ON collection_slots(partner_id);
CREATE INDEX idx_collection_slots_date ON collection_slots(date);
CREATE INDEX idx_collection_slots_available ON collection_slots(is_available);

-- Phlebotomists
CREATE TABLE phlebotomists (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    partner_id UUID NOT NULL REFERENCES partners(id),
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(255),
    photo_url VARCHAR(500),
    certification VARCHAR(255),
    experience_years INTEGER,
    rating DECIMAL(3,2) DEFAULT 0,
    total_ratings INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT true,
    current_latitude DOUBLE PRECISION,
    current_longitude DOUBLE PRECISION,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_phlebotomists_partner ON phlebotomists(partner_id);
CREATE INDEX idx_phlebotomists_active ON phlebotomists(is_active);

-- Lab Bookings
CREATE TABLE lab_bookings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    booking_number VARCHAR(50) UNIQUE NOT NULL,
    user_id UUID NOT NULL,
    lab_partner_id UUID NOT NULL REFERENCES partners(id),
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    booking_type VARCHAR(50) NOT NULL, -- HOME_COLLECTION, WALK_IN
    payment_status VARCHAR(50) DEFAULT 'PENDING',
    payment_id VARCHAR(100),
    transaction_id VARCHAR(100),
    
    -- Tests booked
    test_ids JSONB NOT NULL, -- Array of test IDs
    package_ids JSONB, -- Array of package IDs
    
    -- Pricing
    subtotal DECIMAL(12,2) NOT NULL DEFAULT 0,
    discount_amount DECIMAL(12,2) DEFAULT 0,
    coupon_code VARCHAR(50),
    coupon_discount DECIMAL(12,2) DEFAULT 0,
    home_collection_fee DECIMAL(10,2) DEFAULT 0,
    tax_amount DECIMAL(12,2) DEFAULT 0,
    total_amount DECIMAL(12,2) NOT NULL DEFAULT 0,
    
    -- Collection Details
    collection_address_id UUID REFERENCES delivery_addresses(id),
    collection_address_snapshot JSONB,
    scheduled_date DATE,
    scheduled_slot_id UUID REFERENCES collection_slots(id),
    scheduled_slot VARCHAR(100),
    phlebotomist_id UUID REFERENCES phlebotomists(id),
    
    -- Sample & Report
    sample_collected_at TIMESTAMP,
    sample_received_at TIMESTAMP,
    report_generated_at TIMESTAMP,
    report_url VARCHAR(500),
    report_shared_with_ehr BOOLEAN DEFAULT false,
    
    -- Notes
    patient_notes TEXT,
    lab_notes TEXT,
    cancellation_reason TEXT,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    confirmed_at TIMESTAMP,
    cancelled_at TIMESTAMP
);

CREATE INDEX idx_lab_bookings_user ON lab_bookings(user_id);
CREATE INDEX idx_lab_bookings_partner ON lab_bookings(lab_partner_id);
CREATE INDEX idx_lab_bookings_number ON lab_bookings(booking_number);
CREATE INDEX idx_lab_bookings_status ON lab_bookings(status);
CREATE INDEX idx_lab_bookings_date ON lab_bookings(scheduled_date);
CREATE INDEX idx_lab_bookings_created ON lab_bookings(created_at DESC);

-- Insert sample test categories
INSERT INTO test_categories (id, name, description, display_order) VALUES
    (gen_random_uuid(), 'Full Body Checkup', 'Comprehensive health checkup packages', 1),
    (gen_random_uuid(), 'Diabetes', 'Blood sugar and diabetes related tests', 2),
    (gen_random_uuid(), 'Thyroid', 'Thyroid function tests', 3),
    (gen_random_uuid(), 'Heart Health', 'Cardiac health and lipid profile tests', 4),
    (gen_random_uuid(), 'Liver Function', 'Liver function and hepatitis tests', 5),
    (gen_random_uuid(), 'Kidney Function', 'Kidney function and urine tests', 6),
    (gen_random_uuid(), 'Vitamin Tests', 'Vitamin and mineral deficiency tests', 7),
    (gen_random_uuid(), 'Infection', 'Infection and immunity tests', 8),
    (gen_random_uuid(), 'Allergy', 'Allergy and sensitivity tests', 9),
    (gen_random_uuid(), 'Women Health', 'Women specific health tests', 10);
