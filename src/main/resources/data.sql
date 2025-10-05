INSERT INTO apartment_tbl (flat_number, rent_amount, rent_outstanding, occupied) VALUES
                                                                                     ('101', 15000.00, FALSE, TRUE),
                                                                                     ('102', 16000.00, FALSE, TRUE),
                                                                                     ('103', 14500.00, TRUE, TRUE),
                                                                                     ('104', 15500.00, FALSE, TRUE),
                                                                                     ('105', 17000.00, FALSE, TRUE),
                                                                                     ('201', 18000.00, FALSE, TRUE),
                                                                                     ('202', 19000.00, TRUE, FALSE),
                                                                                     ('203', 17500.00, FALSE, FALSE),
                                                                                     ('204', 18500.00, FALSE, FALSE),
                                                                                     ('205', 20000.00, FALSE, FALSE);

INSERT INTO tenants_tbl (name, email, phone_number, address, father_name, flat_number, apartment_id, aadhar_card_number, criminal_history, agreement_signed) VALUES
                                                                                                                                                                 ('Aryan Singh', 'arjun.sharma@email.com', '9876543210', '123 MG Road, Mumbai', 'Rajesh Sharma', '101', (SELECT id FROM apartment_tbl WHERE flat_number = '101'), '123456789012', FALSE, TRUE),
                                                                                                                                                                 ('Yashpal Singh', 'priya.patel@email.com', '9876543211', '456 Park Street, Delhi', 'Suresh Patel', '102', (SELECT id FROM apartment_tbl WHERE flat_number = '102'), '234567890123', FALSE, TRUE),
                                                                                                                                                                 ('Aradhya Singh', 'rohit.kumar@email.com', '9876543212', '789 Brigade Road, Bangalore', 'Mahesh Kumar', '103', (SELECT id FROM apartment_tbl WHERE flat_number = '103'), '345678901234', FALSE, FALSE),
                                                                                                                                                                 ('Anamika Singh', 'sneha.gupta@email.com', '9876543213', '321 Commercial Street, Chennai', 'Ramesh Gupta', '104', (SELECT id FROM apartment_tbl WHERE flat_number = '104'), '456789012345', FALSE, TRUE),
                                                                                                                                                                 ('Manvendra Singh', 'vikram.singh@email.com', '9876543214', '654 Connaught Place, Delhi', 'Harpreet Singh', '105', (SELECT id FROM apartment_tbl WHERE flat_number = '105'), '567890123456', FALSE, TRUE),
                                                                                                                                                                 ('Anita Reddy', 'anita.reddy@email.com', '9876543215', '987 Jubilee Hills, Hyderabad', 'Venkat Reddy', '201', (SELECT id FROM apartment_tbl WHERE flat_number = '201'), '678901234567', FALSE, TRUE);

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
                                                                                                                                  ((SELECT id FROM apartment_tbl WHERE flat_number = '102'), 'CLEANING', 'Ants in the Kitchen', 'There are a lot of ants in the kitchen and pantry.', 'CANCELLED', NOW(), 5);


INSERT INTO payments_tbl (apartment_id, amount, payment_method, payment_date) VALUES
                                                                                  ((SELECT id FROM apartment_tbl WHERE flat_number = '101'), 15000.00, 'CASH', NOW() - INTERVAL 1 MONTH),
                                                                                  ((SELECT id FROM apartment_tbl WHERE flat_number = '101'), 15000.00, 'BANK_TRANSFER', NOW()),
                                                                                  ((SELECT id FROM apartment_tbl WHERE flat_number = '102'), 16000.00, 'CARD', NOW());

INSERT INTO furniture (apartment_id, furniture_type, quantity) VALUES
                                                                   ((SELECT id FROM apartment_tbl WHERE flat_number = '101'), 'LIGHT_BULBS', 5),
                                                                   ((SELECT id FROM apartment_tbl WHERE flat_number = '101'), 'COTS', 2),
                                                                   ((SELECT id FROM apartment_tbl WHERE flat_number = '101'), 'CURTAINS', 4),
                                                                   ((SELECT id FROM apartment_tbl WHERE flat_number = '102'), 'FANS', 2),
                                                                   ((SELECT id FROM apartment_tbl WHERE flat_number = '102'), 'TUBELIGHTS', 2);

DELETE FROM maintenance_requests_tbl WHERE apartment_id = 0;
