-- 1. Drop the old schema if it exists
DROP SCHEMA IF EXISTS identity_service CASCADE;

-- 2. Recreate the schema
CREATE SCHEMA identity_service;

-- 3. Create app_user table
CREATE TABLE identity_service.app_user (
    user_id UUID PRIMARY KEY,
    full_name VARCHAR(256) NOT NULL,
    email VARCHAR(256) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    date_of_birth DATE,
    is_verified BOOLEAN DEFAULT FALSE,
    last_signed_in_as UUID,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    updated_by VARCHAR(256)
);

-- 4. Create contact_number table
CREATE TABLE identity_service.contact_number (
    contact_number_id UUID PRIMARY KEY,
    country_code VARCHAR(4) NOT NULL,
    area_code VARCHAR(5) NOT NULL,
    number VARCHAR(10) NOT NULL,
    extension VARCHAR(10),
    user_id UUID NOT NULL REFERENCES identity_service.app_user(user_id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    updated_by VARCHAR(256)
);
