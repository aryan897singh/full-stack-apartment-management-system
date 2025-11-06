DROP TABLE IF EXISTS furniture;
DROP TABLE IF EXISTS payments_tbl;
DROP TABLE IF EXISTS maintenance_requests_tbl;
DROP TABLE IF EXISTS tenants_tbl;
DROP TABLE IF EXISTS deposits_tbl;
DROP TABLE IF EXISTS manager_maintenance_types;
DROP TABLE IF EXISTS managers_tbl;
DROP TABLE IF EXISTS apartment_tbl;
DROP TABLE IF EXISTS config_tbl;

CREATE TABLE apartment_tbl (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               flat_number VARCHAR(255) NOT NULL UNIQUE,
                               expected_rent DECIMAL(10,2),
                               rent_amount DECIMAL(10,2),
                               maintenance_amount DECIMAL(10,2),
                               paid_maintenance DECIMAL(10,2),
                               paid_rent DECIMAL(10,2),
                               rent_outstanding BOOLEAN,
                               occupied BOOLEAN DEFAULT FALSE,
                               last_occupied DATETIME,
                               deposit_collected BOOLEAN
);

CREATE TABLE tenants_tbl (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             email VARCHAR(255),
                             phone_number VARCHAR(255),
                             address VARCHAR(500),
                             father_name VARCHAR(255),
                             apartment_id BIGINT,
                             flat_number VARCHAR(255),
                             aadhar_card_number VARCHAR(255) UNIQUE,
                             criminal_history BOOLEAN DEFAULT FALSE,
                             agreement_signed BOOLEAN DEFAULT FALSE,
                             main_owner BOOLEAN DEFAULT FALSE NOT NULL, -- <-- Updated to NOT NULL
                             join_date DATETIME,
                             leave_date DATETIME,
                             exists_flag BOOLEAN DEFAULT TRUE,

                             CONSTRAINT fk_tenant_apartment
                                 FOREIGN KEY (apartment_id)
                                     REFERENCES apartment_tbl(id)
);

CREATE TABLE managers_tbl (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(255) UNIQUE NOT NULL,
                              number BIGINT NOT NULL
);

CREATE TABLE maintenance_requests_tbl (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          apartment_id BIGINT NOT NULL,
                                          maintenance_type VARCHAR(255),
                                          title VARCHAR(255),
                                          description TEXT,
                                          status VARCHAR(255),
                                          date_submitted DATETIME,
                                          manager_id BIGINT NOT NULL,

                                          CONSTRAINT fk_maintenance_request_apartment
                                              FOREIGN KEY (apartment_id)
                                                  REFERENCES apartment_tbl(id),
                                          CONSTRAINT fk_maintenance_request_manager
                                              FOREIGN KEY (manager_id)
                                                  REFERENCES managers_tbl(id)
);

CREATE TABLE manager_maintenance_types (
                                           manager_id BIGINT NOT NULL,
                                           maintenance_type VARCHAR(255) NOT NULL,
                                           PRIMARY KEY (manager_id, maintenance_type),
                                           FOREIGN KEY (manager_id) REFERENCES managers_tbl(id)
);

CREATE TABLE payments_tbl (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              apartment_id BIGINT NOT NULL,
                              rent_amount DECIMAL(10,2),
                              maintenance_amount DECIMAL(10,2),
                              electricity_amount DECIMAL(10,2),
                              payment_method VARCHAR(255),
                              payment_date DATETIME NOT NULL,

                              CONSTRAINT fk_payment_apartment
                                  FOREIGN KEY (apartment_id)
                                      REFERENCES apartment_tbl(id)
);

CREATE TABLE furniture (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           apartment_id BIGINT NOT NULL,
                           furniture_type VARCHAR(255) NOT NULL,
                           quantity INTEGER NOT NULL,
                           FOREIGN KEY (apartment_id) REFERENCES apartment_tbl(id)
);

CREATE TABLE deposits_tbl (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              apartment_id BIGINT NOT NULL,
                              expected DECIMAL(10,2),
                              negotiated DECIMAL(10,2),
                              paid DECIMAL(10,2),

                              CONSTRAINT fk_deposit_apartment
                                  FOREIGN KEY (apartment_id)
                                      REFERENCES apartment_tbl(id)
);

-- START OF NEW TABLE
CREATE TABLE config_tbl (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            config_key VARCHAR(255) NOT NULL UNIQUE,
                            config_value VARCHAR(255) NOT NULL
);
-- END OF NEW TABLE