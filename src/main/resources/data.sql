-- Insert apartment data first
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

-- Insert tenant data with subqueries to match apartment IDs
INSERT INTO tenants_tbl (name, email, phone_number, address, father_name, flat_number, apartment_id, aadhar_card_number, criminal_history, agreement_signed) VALUES
('Aryan Singh', 'arjun.sharma@email.com', '9876543210', '123 MG Road, Mumbai', 'Rajesh Sharma', '101',
    (SELECT id FROM apartment_tbl WHERE flat_number = '101'), '123456789012', FALSE, TRUE),

('Yashpal Singh', 'priya.patel@email.com', '9876543211', '456 Park Street, Delhi', 'Suresh Patel', '102',
    (SELECT id FROM apartment_tbl WHERE flat_number = '102'), '234567890123', FALSE, TRUE),

('Aradhya Singh', 'rohit.kumar@email.com', '9876543212', '789 Brigade Road, Bangalore', 'Mahesh Kumar', '103',
    (SELECT id FROM apartment_tbl WHERE flat_number = '103'), '345678901234', FALSE, FALSE),

('Anamika Singh', 'sneha.gupta@email.com', '9876543213', '321 Commercial Street, Chennai', 'Ramesh Gupta', '104',
    (SELECT id FROM apartment_tbl WHERE flat_number = '104'), '456789012345', FALSE, TRUE),

('Manvendra Singh', 'vikram.singh@email.com', '9876543214', '654 Connaught Place, Delhi', 'Harpreet Singh', '105',
    (SELECT id FROM apartment_tbl WHERE flat_number = '105'), '567890123456', FALSE, TRUE),

('Anita Reddy', 'anita.reddy@email.com', '9876543215', '987 Jubilee Hills, Hyderabad', 'Venkat Reddy', '201',
    (SELECT id FROM apartment_tbl WHERE flat_number = '201'), '678901234567', FALSE, TRUE),

('Karthik Iyer', 'karthik.iyer@email.com', '9876543216', '111 T Nagar, Chennai', 'Subramaniam Iyer', '202',
    (SELECT id FROM apartment_tbl WHERE flat_number = '202'), '789012345678', FALSE, FALSE),

('Deepika Joshi', 'deepika.joshi@email.com', '9876543217', '222 Koregaon Park, Pune', 'Prakash Joshi', '203',
    (SELECT id FROM apartment_tbl WHERE flat_number = '203'), '890123456789', FALSE, TRUE),

('Amit Verma', 'amit.verma@email.com', '9876543218', '333 Sector 17, Chandigarh', 'Ravi Verma', '204',
    (SELECT id FROM apartment_tbl WHERE flat_number = '204'), '901234567890', FALSE, TRUE),

('Kavya Nair', 'kavya.nair@email.com', '9876543219', '444 Marine Drive, Kochi', 'Sunil Nair', '205',
    (SELECT id FROM apartment_tbl WHERE flat_number = '205'), '012345678901', FALSE, TRUE);