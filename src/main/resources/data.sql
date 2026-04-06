-- 1. Insert independent entities
INSERT INTO apartment_tbl (flat_number) VALUES
                                            ('101A'),
                                            ('102B'),
                                            ('201A');

INSERT INTO config_tbl (config_key, config_value) VALUES
                                                      ('LATE_FEE_AMOUNT', '50.00'),
                                                      ('MAINTENANCE_EMAIL', 'admin@example.com');

INSERT INTO managers_tbl (name, number) VALUES
                                            ('Bob Builder', 1234567890),
                                            ('Fixit Felix', 9876543210);

-- Insert into ElementCollection table for Managers
INSERT INTO manager_maintenance_types (manager_id, maintenance_type) VALUES
                                                                         (1, 'PLUMBING'),
                                                                         (1, 'HVAC'),
                                                                         (2, 'ELECTRICAL');

INSERT INTO tenants_tbl (name, email, phone_number, address, father_name, unique_identifier, background_check, exists_flag) VALUES
                                                                                                                                ('Alice Smith', 'alice@email.com', '555-0101', '123 Main St', 'John Smith', 'ID-12345', TRUE, TRUE),
                                                                                                                                ('Charlie Brown', 'charlie@email.com', '555-0202', '456 Oak St', 'David Brown', 'ID-67890', TRUE, TRUE);

-- 2. Insert entities with Foreign Keys
INSERT INTO furniture (apartment_id, furniture_type, quantity) VALUES
                                                                   (1, 'BED', 2),
                                                                   (1, 'SOFA', 1),
                                                                   (2, 'DINING_TABLE', 1);

INSERT INTO lease_tbl (is_active, start, end, apartment_id, rent_amount, maintenance_amount, deposit_amount, is_deposit_collected, is_deposit_returned) VALUES
                                                                                                                                                            (TRUE, '2025-01-01', '2025-12-31', 1, 1500.00, 100.00, 1500.00, TRUE, FALSE),
                                                                                                                                                            (FALSE, '2024-01-01', '2024-12-31', 2, 1400.00, 100.00, 1400.00, TRUE, TRUE);

-- Insert into Join Table for Lease <-> Tenants
INSERT INTO lease_tenants_tbl (lease_id, tenant_id) VALUES
                                                        (1, 1),
                                                        (2, 2);

INSERT INTO payments_tbl (lease_id, payment_type, payment_amount, payment_method, comment, payment_date) VALUES
                                                                                                             (1, 'RENT', 1500.00, 'CARD', 'January Rent', '2025-01-05 10:00:00'),
                                                                                                             (2, 'RENT', 1400.00, 'BANK_TRANSFER', 'December Rent', '2024-12-01 09:30:00');

INSERT INTO maintenance_requests_tbl (apartment_id, maintenance_type, title, description, status, date_submitted, manager_id) VALUES
                                                                                                                                  (1, 'PLUMBING', 'Leaky Faucet', 'The kitchen sink is leaking continuously', 'PENDING', '2025-02-15 14:00:00', 1),
                                                                                                                                  (2, 'ELECTRICAL', 'Broken Light', 'Living room ceiling light will not turn on', 'RESOLVED', '2024-10-10 08:00:00', 2);