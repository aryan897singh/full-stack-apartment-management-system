-- Drop tables in reverse order of dependencies to avoid foreign key constraint errors
DROP TABLE IF EXISTS lease_tenants_tbl;
DROP TABLE IF EXISTS payments_tbl;
DROP TABLE IF EXISTS lease_tbl;
DROP TABLE IF EXISTS furniture;
DROP TABLE IF EXISTS maintenance_requests_tbl;
DROP TABLE IF EXISTS tenants_tbl;
DROP TABLE IF EXISTS deposits_tbl;
DROP TABLE IF EXISTS manager_maintenance_types;
DROP TABLE IF EXISTS managers_tbl;
DROP TABLE IF EXISTS apartment_tbl;
DROP TABLE IF EXISTS config_tbl;

-- 1. Create Independent Tables
CREATE TABLE apartment_tbl (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               flat_number VARCHAR(255)
);

CREATE TABLE config_tbl (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            config_key VARCHAR(255) NOT NULL UNIQUE,
                            config_value VARCHAR(255)
);

CREATE TABLE managers_tbl (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(255) UNIQUE,
                              number BIGINT
);

CREATE TABLE tenants_tbl (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             email VARCHAR(255),
                             phone_number VARCHAR(255),
                             address VARCHAR(255),
                             father_name VARCHAR(255),
                             unique_identifier VARCHAR(255) UNIQUE,
                             background_check BOOLEAN,
                             exists_flag BOOLEAN DEFAULT TRUE
);

-- 2. Create Dependent Tables (Foreign Keys)
CREATE TABLE furniture (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           apartment_id BIGINT NOT NULL,
                           furniture_type VARCHAR(255),
                           quantity INT,
                           FOREIGN KEY (apartment_id) REFERENCES apartment_tbl(id)
);

CREATE TABLE manager_maintenance_types (
                                           manager_id BIGINT NOT NULL,
                                           maintenance_type VARCHAR(255) NOT NULL,
                                           FOREIGN KEY (manager_id) REFERENCES managers_tbl(id)
);

CREATE TABLE lease_tbl (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           is_active BOOLEAN,
                           start DATETIME,
                           end DATETIME,
                           apartment_id BIGINT NOT NULL,
                           rent_amount DECIMAL(10, 2),
                           maintenance_amount DECIMAL(10, 2),
                           deposit_amount DECIMAL(10, 2),
                           is_deposit_collected BOOLEAN,
                           is_deposit_returned BOOLEAN,
                           FOREIGN KEY (apartment_id) REFERENCES apartment_tbl(id)
);

CREATE TABLE lease_tenants_tbl (
                                   lease_id BIGINT NOT NULL,
                                   tenant_id BIGINT NOT NULL,
                                   PRIMARY KEY (lease_id, tenant_id),
                                   FOREIGN KEY (lease_id) REFERENCES lease_tbl(id),
                                   FOREIGN KEY (tenant_id) REFERENCES tenants_tbl(id)
);

CREATE TABLE payments_tbl (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              lease_id BIGINT,
                              payment_type VARCHAR(255),
                              payment_amount DECIMAL(10, 2),
                              payment_method VARCHAR(255),
                              comment VARCHAR(255),
                              payment_date DATETIME,
                              FOREIGN KEY (lease_id) REFERENCES lease_tbl(id)
);

CREATE TABLE maintenance_requests_tbl (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          apartment_id BIGINT NOT NULL,
                                          maintenance_type VARCHAR(255),
                                          title VARCHAR(255),
                                          description VARCHAR(255),
                                          status VARCHAR(255),
                                          date_submitted DATETIME,
                                          manager_id BIGINT NOT NULL,
                                          FOREIGN KEY (apartment_id) REFERENCES apartment_tbl(id),
                                          FOREIGN KEY (manager_id) REFERENCES managers_tbl(id)
);