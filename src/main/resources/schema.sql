DROP TABLE IF EXISTS tenants_tbl;
DROP TABLE IF EXISTS apartment_tbl;

CREATE TABLE apartment_tbl (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    flat_number VARCHAR(255) NOT NULL UNIQUE,
    rent_amount DECIMAL(10,2),
    rent_outstanding BOOLEAN
);

CREATE TABLE tenants_tbl (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone_number VARCHAR(255),
    address VARCHAR(500),
    father_name VARCHAR(255),
    apartment_id BIGINT NOT NULL,
    flat_number VARCHAR(255),
    aadhar_card_number VARCHAR(255) UNIQUE,
    criminal_history BOOLEAN DEFAULT FALSE,
    agreement_signed BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_tenant_apartment
        FOREIGN KEY (apartment_id)
        REFERENCES apartment_tbl(id)
);