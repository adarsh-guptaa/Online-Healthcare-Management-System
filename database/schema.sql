-- CarePlus / Online Healthcare Management System
-- Run in MariaDB/MySQL. This creates a separate database and does not alter frontend files.

CREATE DATABASE IF NOT EXISTS careplus_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE careplus_db;

CREATE TABLE IF NOT EXISTS doctors (
  id INT AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(120) NOT NULL,
  specialization VARCHAR(120) NOT NULL,
  email VARCHAR(190) UNIQUE,
  phone VARCHAR(30),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS patients (
  id INT AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(120) NOT NULL,
  email VARCHAR(190) NOT NULL UNIQUE,
  phone VARCHAR(30),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS appointments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  patient_name VARCHAR(120) NOT NULL,
  patient_email VARCHAR(190) NOT NULL,
  doctor_id INT NOT NULL,
  appointment_datetime DATETIME NOT NULL,
  reason TEXT,
  status ENUM('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED') NOT NULL DEFAULT 'PENDING',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_appointments_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  INDEX idx_appointments_patient_email (patient_email),
  INDEX idx_appointments_datetime (appointment_datetime),
  INDEX idx_appointments_status (status)
);

INSERT IGNORE INTO doctors (full_name, specialization, email, phone) VALUES
('Dr. Asha Sharma', 'General Physician', 'asha.sharma@example.test', '9000000001'),
('Dr. Rohan Verma', 'Cardiology', 'rohan.verma@example.test', '9000000002');
