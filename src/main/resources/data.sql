INSERT INTO apartment_tbl (flat_number, rent_amount, rent_outstanding) VALUES
                                                                           ('101', 15000.00, FALSE),
                                                                           ('102', 16000.00, FALSE),
                                                                           ('103', 14500.00, TRUE),
                                                                           ('104', 15500.00, FALSE),
                                                                           ('105', 17000.00, FALSE),
                                                                           ('201', 18000.00, FALSE),
                                                                           ('202', 19000.00, TRUE),
                                                                           ('203', 17500.00, FALSE),
                                                                           ('204', 18500.00, FALSE),
                                                                           ('205', 20000.00, FALSE);

INSERT INTO tenants_tbl (name, email, phone_number, address, father_name, flat_number, apartment_id, aadhar_card_number, criminal_history, agreement_signed) VALUES
                                                                                                                                                                 ('Aryan Singh', 'arjun.sharma@email.com', '9876543210', '123 MG Road, Mumbai', 'Rajesh Sharma', '101', (SELECT id FROM apartment_tbl WHERE flat_number = '101'), '123456789012', FALSE, TRUE),
                                                                                                                                                                 ('Yashpal Singh', 'priya.patel@email.com', '9876543211', '456 Park Street, Delhi', 'Suresh Patel', '102', (SELECT id FROM apartment_tbl WHERE flat_number = '102'), '234567890123', FALSE, TRUE),
                                                                                                                                                                 ('Aradhya Singh', 'rohit.kumar@email.com', '9876543212', '789 Brigade Road, Bangalore', 'Mahesh Kumar', '103', (SELECT id FROM apartment_tbl WHERE flat_number = '103'), '345678901234', FALSE, FALSE),
                                                                                                                                                                 ('Anamika Singh', 'sneha.gupta@email.com', '9876543213', '321 Commercial Street, Chennai', 'Ramesh Gupta', '104', (SELECT id FROM apartment_tbl WHERE flat_number = '104'), '456789012345', FALSE, TRUE),
                                                                                                                                                                 ('Manvendra Singh', 'vikram.singh@email.com', '9876543214', '654 Connaught Place, Delhi', 'Harpreet Singh', '105', (SELECT id FROM apartment_tbl WHERE flat_number = '105'), '567890123456', FALSE, TRUE),
                                                                                                                                                                 ('Anita Reddy', 'anita.reddy@email.com', '9876543215', '987 Jubilee Hills, Hyderabad', 'Venkat Reddy', '201', (SELECT id FROM apartment_tbl WHERE flat_number = '201'), '678901234567', FALSE, TRUE),
                                                                                                                                                                 ('Karthik Iyer', 'karthik.iyer@email.com', '9876543216', '111 T Nagar, Chennai', 'Subramaniam Iyer', '202', (SELECT id FROM apartment_tbl WHERE flat_number = '202'), '789012345678', FALSE, FALSE),
                                                                                                                                                                 ('Deepika Joshi', 'deepika.joshi@email.com', '9876543217', '222 Koregaon Park, Pune', 'Prakash Joshi', '203', (SELECT id FROM apartment_tbl WHERE flat_number = '203'), '890123456789', FALSE, TRUE),
                                                                                                                                                                 ('Amit Verma', 'amit.verma@email.com', '9876543218', '333 Sector 17, Chandigarh', 'Ravi Verma', '204', (SELECT id FROM apartment_tbl WHERE flat_number = '204'), '901234567890', FALSE, TRUE),
                                                                                                                                                                 ('Kavya Nair', 'kavya.nair@email.com', '9876543219', '444 Marine Drive, Kochi', 'Sunil Nair', '205', (SELECT id FROM apartment_tbl WHERE flat_number = '205'), '012345678901', FALSE, TRUE);

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

INSERT INTO maintenance_requests_tbl (tenant_id, maintenance_type, title, description, status, date_submitted, manager_id) VALUES
                                                                                                                               (1, 'PLUMBING', 'Leaky Faucet', 'The kitchen faucet is constantly dripping.', 'PENDING', NOW(), 1),
                                                                                                                               (3, 'ELECTRICAL', 'Broken Light Fixture', 'The light fixture in the living room is not working.', 'IN_PROGRESS', NOW(), 3),
                                                                                                                               (5, 'CLEANING', 'Ants in the Kitchen', 'There are a lot of ants in the kitchen and pantry.', 'COMPLETED', NOW(), 5);

INSERT INTO payments_tbl (apartment_id, amount, payment_method, payment_date) VALUES
                                                                                  (
                                                                                      (SELECT id FROM apartment_tbl WHERE flat_number = '101'),
                                                                                      15000.00,
                                                                                      'CASH',
                                                                                      NOW() - INTERVAL 1 MONTH
                                                                                  ),
                                                                                  (
                                                                                      (SELECT id FROM apartment_tbl WHERE flat_number = '101'),
                                                                                      15000.00,
                                                                                      'BANK_TRANSFER',
                                                                                      NOW()
                                                                                  ),
                                                                                  (
                                                                                      (SELECT id FROM apartment_tbl WHERE flat_number = '102'),
                                                                                      16000.00,
                                                                                      'CARD',
                                                                                      NOW()
                                                                                  );


-- Insert furniture for Flat 101 (ID 1)
INSERT INTO furniture (apartment_id, furniture_type, quantity) VALUES
                                                                   ((SELECT id FROM apartment_tbl WHERE flat_number = '101'), 'LIGHT_BULBS', 5),
                                                                   ((SELECT id FROM apartment_tbl WHERE flat_number = '101'), 'COTS', 2),
                                                                   ((SELECT id FROM apartment_tbl WHERE flat_number = '101'), 'CURTAINS', 4);

-- Insert furniture for Flat 102 (ID 2)
INSERT INTO furniture (apartment_id, furniture_type, quantity) VALUES
                                                                   ((SELECT id FROM apartment_tbl WHERE flat_number = '102'), 'FANS', 2),
                                                                   ((SELECT id FROM apartment_tbl WHERE flat_number = '102'), 'TUBELIGHTS', 2);