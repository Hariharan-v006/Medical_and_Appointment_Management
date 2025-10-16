-- --------------------------------------------------------
-- Create database
-- --------------------------------------------------------
CREATE DATABASE IF NOT EXISTS medical_management;
USE medical_management;

-- --------------------------------------------------------
-- Table structure for table `mm_company`
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS mm_company (
  Name VARCHAR(50) NOT NULL,
  Address VARCHAR(100) NOT NULL,
  `Phone No.` VARCHAR(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Sample artificial data
INSERT INTO mm_company (Name, Address, `Phone No.`) VALUES
('HealthCorp', '123 Main Street, Mumbai', '9876543210'),
('Medico Pvt Ltd', '45 Park Avenue, Delhi', '9123456780'),
('Wellness Inc', '78 Lakeview Road, Pune', '9988776655'),
('CureAll Pharma', '22 Green Street, Bangalore', '9012345678');

-- --------------------------------------------------------
-- Table structure for table `mm_drugs`
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS mm_drugs (
  SN INT(11) NOT NULL AUTO_INCREMENT,
  Name VARCHAR(100) NOT NULL,
  Type VARCHAR(50) NOT NULL,
  Price INT(10) NOT NULL,
  `Expiry days` INT(10) NOT NULL,
  Company VARCHAR(100) NOT NULL,
  `Shelf No.` INT(10) NOT NULL,
  Quantity INT(10) NOT NULL,
  PRIMARY KEY (SN)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Sample artificial data
INSERT INTO mm_drugs (Name, Type, Price, `Expiry days`, Company, `Shelf No.`, Quantity) VALUES
('Paracetamol', 'Tablet', 50, 365, 'HealthCorp', 1, 100),
('Amoxicillin', 'Capsule', 80, 180, 'Medico Pvt Ltd', 2, 50),
('Cough Syrup', 'Syrup', 120, 90, 'Wellness Inc', 3, 30),
('Vitamin D', 'Tablet', 40, 730, 'CureAll Pharma', 4, 150),
('Ibuprofen', 'Tablet', 60, 540, 'HealthCorp', 5, 80);

-- --------------------------------------------------------
-- Table structure for table `mm_sales`
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS mm_sales (
  SN INT(11) NOT NULL AUTO_INCREMENT,
  Name VARCHAR(100) NOT NULL,
  Type VARCHAR(50) NOT NULL,
  Price INT(10) NOT NULL,
  Quantity INT(10) NOT NULL,
  `Total Price` INT(10) NOT NULL,
  `Date` DATE NOT NULL,
  PRIMARY KEY (SN)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Sample artificial data
INSERT INTO mm_sales (Name, Type, Price, Quantity, `Total Price`, `Date`) VALUES
('Paracetamol', 'Tablet', 50, 2, 100, '2025-10-01'),
('Amoxicillin', 'Capsule', 80, 1, 80, '2025-10-02'),
('Cough Syrup', 'Syrup', 120, 3, 360, '2025-10-03'),
('Vitamin D', 'Tablet', 40, 5, 200, '2025-10-04'),
('Ibuprofen', 'Tablet', 60, 4, 240, '2025-10-05');

-- --------------------------------------------------------
-- Table structure for table `mm_warning`
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS mm_warning (
  Name VARCHAR(100) NOT NULL,
  Type VARCHAR(50) NOT NULL,
  `Expiry days` INT(10) NOT NULL,
  Quantity INT(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Sample artificial data
INSERT INTO mm_warning (Name, Type, `Expiry days`, Quantity) VALUES
('Paracetamol', 'Tablet', 10, 5),
('Amoxicillin', 'Capsule', 15, 2),
('Cough Syrup', 'Syrup', 5, 1),
('Vitamin D', 'Tablet', 30, 10),
('Ibuprofen', 'Tablet', 20, 8);

-- --------------------------------------------------------
-- Table structure for table `mm_appointments`
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS mm_appointments (
  AppointmentID INT(11) NOT NULL AUTO_INCREMENT,
  PatientName VARCHAR(100) NOT NULL,
  DoctorName VARCHAR(100) NOT NULL,
  AppointmentDate DATE NOT NULL,
  AppointmentTime TIME NOT NULL,
  Status VARCHAR(50) NOT NULL DEFAULT 'Scheduled',
  PRIMARY KEY (AppointmentID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Sample artificial data
INSERT INTO mm_appointments (PatientName, DoctorName, AppointmentDate, AppointmentTime, Status) VALUES
('John Doe', 'Dr. Smith', '2025-10-10', '10:00:00', 'Scheduled'),
('Jane Roe', 'Dr. Brown', '2025-10-11', '11:30:00', 'Scheduled'),
('Alice Green', 'Dr. White', '2025-10-12', '14:00:00', 'Completed'),
('Bob Black', 'Dr. Smith', '2025-10-13', '09:30:00', 'Cancelled'),
('Charlie Blue', 'Dr. Brown', '2025-10-14', '13:00:00', 'Scheduled');
