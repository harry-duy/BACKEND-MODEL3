-- Create database
CREATE DATABASE IF NOT EXISTS room_rental_management;
USE room_rental_management;

-- Drop old tables if needed (optional safety for resets)
DROP TABLE IF EXISTS rental_room;
DROP TABLE IF EXISTS payment_type;

-- Create payment_type table with extended options
CREATE TABLE IF NOT EXISTS payment_type (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

-- Create rental_room table
CREATE TABLE IF NOT EXISTS rental_room (
    id INT PRIMARY KEY AUTO_INCREMENT,
    room_code VARCHAR(10) NOT NULL UNIQUE,
    tenant_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    start_date DATE NOT NULL,
    payment_type_id INT NOT NULL,
    note VARCHAR(200),
    FOREIGN KEY (payment_type_id) REFERENCES payment_type(id)
);

-- Insert extended payment types
INSERT INTO payment_type (name) VALUES 
('Tiền mặt'),
('Chuyển khoản'),
('MoMo'),
('PayPal'),
('ZaloPay'),
('Crypto');

-- Insert sample data (room_code now handled by trigger)
-- So this should be done *after* trigger is created
-- Just example placeholders:

-- Create trigger to auto-generate room_code
DELIMITER //
CREATE TRIGGER before_insert_rental_room
BEFORE INSERT ON rental_room
FOR EACH ROW
BEGIN
    DECLARE next_id INT;
    SET next_id = (SELECT IFNULL(MAX(id), 0) + 1 FROM rental_room);
    SET NEW.room_code = CONCAT('PT-', LPAD(next_id, 3, '0'));
END //
DELIMITER ;

-- Sample data (let room_code auto-generate)
INSERT INTO rental_room (tenant_name, phone_number, start_date, payment_type_id, note) VALUES
('Nguyen Van A', '0123456789', '2025-04-01', 1, 'Phòng tầng 1'),
('Tran Thi B', '0987654321', '2025-03-15', 3, 'Phòng tầng 2'),
('Le Van C', '0369852147', '2025-01-10', 4, 'Phòng tầng 3');
