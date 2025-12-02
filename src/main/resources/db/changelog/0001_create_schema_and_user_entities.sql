CREATE SCHEMA IF NOT EXISTS identity_schema;

CREATE TABLE IF NOT EXISTS identity_schema.app_user (
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

CREATE TABLE IF NOT EXISTS identity_schema.contact_number (
    contact_number_id UUID PRIMARY KEY,
    country_code VARCHAR(4) NOT NULL,
    area_code VARCHAR(5) NOT NULL,
    number VARCHAR(10) NOT NULL,
    extension VARCHAR(10),
    user_id UUID NOT NULL REFERENCES identity_schema.app_user(user_id),
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now(),
    updated_by VARCHAR(256)
)
