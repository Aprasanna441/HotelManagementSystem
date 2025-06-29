CREATE DATABASE hotel_db;

USE hotel_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(50)
);

INSERT INTO users(username, password) VALUES ('admin', 'admin123');

CREATE TABLE rooms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    room_number VARCHAR(10),
    type VARCHAR(20),
    is_booked BOOLEAN DEFAULT FALSE
);

CREATE TABLE bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    guest_name VARCHAR(100),
    room_id INT,
    checkin_date DATE,
    checkout_date DATE,
    FOREIGN KEY (room_id) REFERENCES rooms(id)
);
