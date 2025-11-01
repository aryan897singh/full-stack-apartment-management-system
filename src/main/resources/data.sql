INSERT INTO apartment_tbl (flat_number, expected_rent, rent_amount, maintenance_amount, paid_rent, paid_maintenance, rent_outstanding, occupied, last_occupied, deposit_collected) VALUES
                                                                                                                                                                                       ('101', 16000.00, 15000.00, 1000.00, 0.00, 0.00, FALSE, TRUE, NOW(), TRUE),
                                                                                                                                                                                       ('102', 17000.00, 16000.00, 1000.00, 0.00, 0.00, FALSE, TRUE, NOW(), FALSE),
                                                                                                                                                                                       ('103', 15500.00, 14500.00, 1000.00, 0.00, 0.00, TRUE, TRUE, NOW(), TRUE),
                                                                                                                                                                                       ('104', 16500.00, 15500.00, 1000.00, 0.00, 0.00, FALSE, TRUE, NOW(), TRUE),
                                                                                                                                                                                       ('105', 18000.00, 17000.00, 1000.00, 0.00, 0.00, FALSE, TRUE, NOW(), FALSE),
                                                                                                                                                                                       ('201', 19000.00, 18000.00, 1000.00, 0.00, 0.00, FALSE, TRUE, NOW(), TRUE),
                                                                                                                                                                                       ('202', 20000.00, 19000.00, 1000.00, 0.00, 0.00, TRUE, FALSE, NOW() - INTERVAL 10 DAY, FALSE),
                                                                                                                                                                                       ('203', 18500.00, 17500.00, 1000.00, 0.00, 0.00, FALSE, FALSE, NOW() - INTERVAL 20 DAY, FALSE),
                                                                                                                                                                                       ('204', 19500.00, 18500.00, 1000.00, 0.00, 0.00, FALSE, FALSE, NOW() - INTERVAL 5 DAY, FALSE),
                                                                                                                                                                                       ('205', 21000.00, 20000.00, 1000.00, 0.00, 0.00, FALSE, FALSE, NOW() - INTERVAL 30 DAY, FALSE);

INSERT INTO tenants_tbl (name, email, phone_number, address, father_name, apartment_id, flat_number, aadhar_card_number, criminal_history, agreement_signed, main_owner, join_date, exists_flag) VALUES
                                                                                                                                                                                                     ('Aryan Singh', 'arjun.sharma@email.com', '9876543210', '123 MG Road, Mumbai', 'Rajesh Sharma', (SELECT id FROM apartment_tbl WHERE flat_number = '101'), '101', '123456789012', FALSE, TRUE, TRUE, NOW(), TRUE),
                                                                                                                                                                                                     ('Yashpal Singh', 'priya.patel@email.com', '9876543211', '456 Park Street, Delhi', 'Suresh Patel', (SELECT id FROM apartment_tbl WHERE flat_number = '102'), '102', '234567890123', FALSE, TRUE, TRUE, NOW(), TRUE),
                                                                                                                                                                                                     ('Aradhya Singh', 'rohit.kumar@email.com', '9876543212', '789 Brigade Road, Bangalore', 'Mahesh Kumar', (SELECT id FROM apartment_tbl WHERE flat_number = '103'), '103', '345678901234', FALSE, FALSE, TRUE, NOW(), TRUE),
                                                                                                                                                                                                     ('Anamika Singh', 'sneha.gupta@email.com', '9876543213', '321 Commercial Street, Chennai', 'Ramesh Gupta', (SELECT id FROM apartment_tbl WHERE flat_number = '104'), '104', '456789012345', FALSE, TRUE, TRUE, NOW(), TRUE),
                                                                                                                                                                                                     ('Manvendra Singh', 'vikram.singh@email.com', '9876543214', '654 Connaught Place, Delhi', 'Harpreet Singh', (SELECT id FROM apartment_tbl WHERE flat_number = '105'), '105', '567890123456', FALSE, TRUE, TRUE, NOW(), TRUE),
                                                                                                                                                                                                     ('Anita Reddy', 'anita.reddy@email.com', '9876543215', '987 Jubilee Hills, Hyderabad', 'Venkat Reddy', (SELECT id FROM apartment_tbl WHERE flat_number = '201'), '201', '678901234567', FALSE, TRUE, TRUE, NOW(), TRUE);

INSERT INTO deposits_tbl (apartment_id, expected, negotiated, paid) VALUES
                                                                        ((SELECT id FROM apartment_tbl WHERE flat_number = '101'), 15000.00, 15000.00, 15000.00),
                                                                        ((SELECT id FROM apartment_tbl WHERE flat_number = '102'), 16000.00, 15500.00, 15500.00),
                                                                        ((SELECT id FROM apartment_tbl WHERE flat_number = '103'), 14500.00, 14500.00, 14500.00),
                                                                        ((SELECT id FROM apartment_tbl WHERE flat_number = '104'), 15500.00, 15500.00, 15500.00),
                                                                        ((SELECT id FROM apartment_tbl WHERE flat_number = '105'), 17000.00, 16000.00, 15000.00),
                                                                        ((SELECT id FROM apartment_tbl WHERE flat_number = '201'), 18000.00, 17500.00, 17500.00),
                                                                        ((SELECT id FROM apartment_tbl WHERE flat_number = '202'), 19000.00, 18500.00, 18000.00),
                                                                        ((SELECT id FROM apartment_tbl WHERE flat_number = '203'), 17500.00, 17500.00, 17000.00),
                                                                        ((SELECT id FROM apartment_tbl WHERE flat_number = '204'), 18500.00, 18000.00, 18000.00),
                                                                        ((SELECT id FROM apartment_tbl WHERE flat_number = '205'), 20000.00, 20000.00, 20000.00);


INSERT INTO managers_tbl (name, number) VALUES
                                            ('Kuldeep', 9876543210),
                                            ('Tiwari', 9876543211),
                                            ('Raju', 9876543212),
                                            ('Mukesh', 9876543213),
                                            ('Kamla', 9876543214),
                                            ('Keshav', 9876543215);

INSERT INTO manager_maintenance_types (manager_id, maintenance_type) VALUES
                                                                         (1, 'PLUMBING'),
                                                                         (2, 'FLOORING'),
                                                                         (2, 'APPLIANCE_REPAIR'),
                                                                         (2, 'BUILDING_INTERIOR'),
                                                                         (2, 'BUILDING_EXTERIOR'),
                                                                         (2, 'MISCELLANEOUS'),
                                                                         (3, 'ELECTRICAL'),
                                                                         (4, 'PAINTING'),
                                                                         (5, 'CLEANING'),
                                                                         (6, 'WOODWORK');

INSERT INTO maintenance_requests_tbl (apartment_id, maintenance_type, title, description, status, date_submitted, manager_id) VALUES
                                                                                                                                  ((SELECT id FROM apartment_tbl WHERE flat_number = '101'), 'PLUMBING', 'Leaky Faucet', 'The kitchen faucet is constantly dripping.', 'PENDING', NOW(), 1),
                                                                                                                                  ((SELECT id FROM apartment_tbl WHERE flat_number = '103'), 'ELECTRICAL', 'Broken Light Fixture', 'The light fixture in the living room is not working.', 'PENDING', NOW(), 3),
                                                                                                                                  ((SELECT id FROM apartment_tbl WHERE flat_number = '105'), 'APPLIANCE_REPAIR', 'Faulty Refrigerator', 'The refrigerator is making a strange humming noise.', 'IN_PROGRESS', NOW(), 2),
                                                                                                                                  ((SELECT id FROM apartment_tbl WHERE flat_number = '201'), 'PAINTING', 'Scratched Wall', 'There are a few scratches on the living room wall.', 'IN_PROGRESS', NOW(), 4),
                                                                                                                                  ((SELECT id FROM apartment_tbl WHERE flat_number = '102'), 'CLEANING', 'Ants in the Kitchen', 'There are a lot of ants in the kitchen and pantry.', 'COMPLETED', NOW(), 5),
                                                                                                                                  ((SELECT id FROM apartment_tbl WHERE flat_number = '104'), 'WOODWORK', 'Squeaky Floorboards', 'The floorboards in the bedroom are very squeaky.', 'COMPLETED', NOW(), 6),
                                                                                                                                  ((SELECT id FROM apartment_tbl WHERE flat_number = '102'), 'CLEANING', 'Ants in the Kitchen', 'There are a lot of ants in the kitchen and pantry.', 'CANCELLED', NOW(), 5),
                                                                                                                                  ((SELECT id FROM apartment_tbl WHERE flat_number = '102'), 'PLUMBING', 'Clogged Sink', 'The bathroom sink is not draining.', 'PENDING', NOW() - INTERVAL 1 DAY, 1),
                                                                                                                                  ((SELECT id FROM apartment_tbl WHERE flat_number = '104'), 'PAINTING', 'Repaint Bedroom', 'Requesting a repaint of the master bedroom walls.', 'PENDING', NOW() - INTERVAL 2 DAY, 4),
                                                                                                                                  ((SELECT id FROM apartment_tbl WHERE flat_number = '201'), 'WOODWORK', 'Broken Chair Leg', 'A dining chair leg has snapped.', 'PENDING', NOW(), 6);


INSERT INTO payments_tbl (apartment_id, rent_amount, maintenance_amount, electricity_amount, payment_method, payment_date) VALUES
                                                                                                                               ((SELECT id FROM apartment_tbl WHERE flat_number = '101'), 15000.00, 1000.00, 800.00, 'CASH', NOW() - INTERVAL 1 MONTH),
                                                                                                                               ((SELECT id FROM apartment_tbl WHERE flat_number = '101'), 15000.00, 1000.00, 950.00, 'BANK_TRANSFER', NOW()),
                                                                                                                               ((SELECT id FROM apartment_tbl WHERE flat_number = '102'), 16000.00, 1000.00, 1100.00, 'CARD', NOW());

INSERT INTO furniture (apartment_id, furniture_type, quantity) VALUES
                                                                   ((SELECT id FROM apartment_tbl WHERE flat_number = '101'), 'LIGHT_BULBS', 5),
                                                                   ((SELECT id FROM apartment_tbl WHERE flat_number = '101'), 'COTS', 2),
                                                                   ((SELECT id FROM apartment_tbl WHERE flat_number = '101'), 'CURTAINS', 4),
                                                                   ((SELECT id FROM apartment_tbl WHERE flat_number = '102'), 'FANS', 2),
                                                                   ((SELECT id FROM apartment_tbl WHERE flat_number = '102'), 'TUBELIGHTS', 2);

-- START OF NEW DATA
INSERT INTO config_tbl (config_key, config_value) VALUES
                                                      ('RENT_DUE_DAY', '5'),
                                                      ('CURRENCY_SYMBOL', '₹'),
                                                      ('LATE_FEE_GRACE_PERIOD', '3'),
                                                      ('LATE_FEE_TYPE', 'FLAT'),
                                                      ('LATE_FEE_VALUE', '500'),
                                                      ('APP_NAME', 'Apartment Manager'),
                                                      ('ADMIN_ACCESS_ENABLED', 'FALSE'),
                                                      ('MAINTENANCE_APPROVAL_REQ', 'TRUE');
-- END OF NEW DATA