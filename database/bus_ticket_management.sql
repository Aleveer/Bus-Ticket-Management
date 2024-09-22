-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 22, 2024 at 05:25 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bus_ticket_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `number_of_tickets` int(11) NOT NULL,
  `total_price` double NOT NULL,
  `status` enum('paid','unpaid','canceled') NOT NULL DEFAULT 'unpaid',
  `ticket_type` enum('round_trip_ticket','one_way_ticket') NOT NULL DEFAULT 'one_way_ticket'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `booking_details`
--

CREATE TABLE `booking_details` (
  `id` int(11) NOT NULL,
  `booking_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `seat_id` int(11) NOT NULL,
  `price_per_ticket` int(11) NOT NULL,
  `booking_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `buses`
--

CREATE TABLE `buses` (
  `id` int(11) NOT NULL,
  `plate_number` varchar(50) NOT NULL,
  `number_of_seats` tinyint(4) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `bus_type` varchar(50) NOT NULL,
  `status` enum('working','maintenance') NOT NULL DEFAULT 'working'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `buses`
--

INSERT INTO `buses` (`id`, `plate_number`, `number_of_seats`, `driver_id`, `bus_type`, `status`) VALUES
(1, 'ABC123', 40, 1, 'Luxury', 'working'),
(2, 'XYZ789', 30, 2, 'Standard', 'maintenance'),
(3, 'DEF456', 50, 3, 'Double Decker', 'working');

-- --------------------------------------------------------

--
-- Table structure for table `discounts`
--

CREATE TABLE `discounts` (
  `id` int(11) NOT NULL,
  `code` varchar(50) NOT NULL,
  `discount_value` float NOT NULL,
  `expiry_date` datetime NOT NULL,
  `number_of_uses_left` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `drivers`
--

CREATE TABLE `drivers` (
  `id` int(11) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `email` varchar(255) NOT NULL,
  `status` enum('working','resting') NOT NULL DEFAULT 'working'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `drivers`
--

INSERT INTO `drivers` (`id`, `full_name`, `phone`, `email`, `status`) VALUES
(1, 'John Doe', '1234567890', 'john.doe@example.com', 'working'),
(2, 'Jane Smith', '0987654321', 'jane.smith@example.com', 'resting'),
(3, 'Robert Brown', '1122334455', 'robert.brown@example.com', 'working');

-- --------------------------------------------------------

--
-- Table structure for table `driver_bus_assignments`
--

CREATE TABLE `driver_bus_assignments` (
  `id` int(11) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `bus_id` int(11) NOT NULL,
  `assignment_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `trip_id` int(11) NOT NULL,
  `rating` tinyint(4) NOT NULL,
  `comment` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `message` text NOT NULL,
  `is_read` tinyint(1) NOT NULL DEFAULT 0,
  `created_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `permission`
--

CREATE TABLE `permission` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL DEFAULT 'customer'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'customer');

-- --------------------------------------------------------

--
-- Table structure for table `roles_permissions`
--

CREATE TABLE `roles_permissions` (
  `id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `routes`
--

CREATE TABLE `routes` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `start_point` varchar(255) NOT NULL,
  `end_point` varchar(255) NOT NULL,
  `distance` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `schedules`
--

CREATE TABLE `schedules` (
  `id` int(11) NOT NULL,
  `bus_id` int(11) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `route_id` int(11) NOT NULL,
  `start_destination` varchar(255) NOT NULL,
  `end_destination` varchar(255) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `description` text NOT NULL,
  `status` enum('finished','unfinished','canceled','arriving') NOT NULL DEFAULT 'unfinished'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `seats`
--

CREATE TABLE `seats` (
  `id` int(11) NOT NULL,
  `bus_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `status` enum('assigned','unassigned') NOT NULL DEFAULT 'unassigned',
  `price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `spring_session`
--

CREATE TABLE `spring_session` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint(20) NOT NULL,
  `LAST_ACCESS_TIME` bigint(20) NOT NULL,
  `MAX_INACTIVE_INTERVAL` int(11) NOT NULL,
  `EXPIRY_TIME` bigint(20) NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `transaction_history`
--

CREATE TABLE `transaction_history` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `method` enum('cash','credit') NOT NULL DEFAULT 'cash',
  `payment_date` datetime NOT NULL,
  `total_price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `trip_history`
--

CREATE TABLE `trip_history` (
  `id` int(11) NOT NULL,
  `bus_id` int(11) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `route_id` int(11) NOT NULL,
  `departure` varchar(255) NOT NULL,
  `arrival` varchar(255) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `description` text NOT NULL,
  `passenger_count` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `status` enum('unverified','verified','banned') NOT NULL DEFAULT 'unverified',
  `verification_code` varchar(255) DEFAULT NULL,
  `verification_expiration` datetime DEFAULT NULL,
  `login_token` text DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `password`, `full_name`, `phone`, `role_id`, `status`, `verification_code`, `verification_expiration`, `login_token`, `enabled`) VALUES
(10, 'theprogamingofdeath123@gmail.com', '$2a$10$8qEOe5vvHuigklNYAaNJdej/WVrph3mBC0qVAxEKrJKfECwCRImhG', 'Nguyen Van A', '01234567890', 1, 'verified', NULL, NULL, NULL, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `booking_details`
--
ALTER TABLE `booking_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `booking_id` (`booking_id`,`user_id`,`seat_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `seat_id` (`seat_id`);

--
-- Indexes for table `buses`
--
ALTER TABLE `buses`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `plate_number` (`plate_number`),
  ADD KEY `driver_id` (`driver_id`);

--
-- Indexes for table `discounts`
--
ALTER TABLE `discounts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `drivers`
--
ALTER TABLE `drivers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `driver_bus_assignments`
--
ALTER TABLE `driver_bus_assignments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `driver_id` (`driver_id`),
  ADD KEY `bus_id` (`bus_id`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`,`trip_id`),
  ADD KEY `trip_id` (`trip_id`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `permission`
--
ALTER TABLE `permission`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `roles_permissions`
--
ALTER TABLE `roles_permissions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role_id` (`role_id`,`permission_id`),
  ADD KEY `permission_id` (`permission_id`);

--
-- Indexes for table `routes`
--
ALTER TABLE `routes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `schedules`
--
ALTER TABLE `schedules`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bus_id` (`bus_id`,`driver_id`),
  ADD KEY `driver_id` (`driver_id`),
  ADD KEY `route_id` (`route_id`);

--
-- Indexes for table `seats`
--
ALTER TABLE `seats`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bus_id` (`bus_id`);

--
-- Indexes for table `spring_session`
--
ALTER TABLE `spring_session`
  ADD PRIMARY KEY (`PRIMARY_ID`),
  ADD UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  ADD KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  ADD KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`);

--
-- Indexes for table `transaction_history`
--
ALTER TABLE `transaction_history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `trip_history`
--
ALTER TABLE `trip_history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bus_id` (`bus_id`,`driver_id`),
  ADD KEY `driver_id` (`driver_id`),
  ADD KEY `route_id` (`route_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookings`
--
ALTER TABLE `bookings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `booking_details`
--
ALTER TABLE `booking_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `buses`
--
ALTER TABLE `buses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `discounts`
--
ALTER TABLE `discounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `drivers`
--
ALTER TABLE `drivers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `driver_bus_assignments`
--
ALTER TABLE `driver_bus_assignments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `permission`
--
ALTER TABLE `permission`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `roles_permissions`
--
ALTER TABLE `roles_permissions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `routes`
--
ALTER TABLE `routes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `schedules`
--
ALTER TABLE `schedules`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `seats`
--
ALTER TABLE `seats`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `transaction_history`
--
ALTER TABLE `transaction_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `trip_history`
--
ALTER TABLE `trip_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `booking_details`
--
ALTER TABLE `booking_details`
  ADD CONSTRAINT `booking_details_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `booking_details_ibfk_2` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`),
  ADD CONSTRAINT `booking_details_ibfk_3` FOREIGN KEY (`seat_id`) REFERENCES `seats` (`id`);

--
-- Constraints for table `buses`
--
ALTER TABLE `buses`
  ADD CONSTRAINT `buses_ibfk_1` FOREIGN KEY (`driver_id`) REFERENCES `drivers` (`id`);

--
-- Constraints for table `driver_bus_assignments`
--
ALTER TABLE `driver_bus_assignments`
  ADD CONSTRAINT `driver_bus_assignments_ibfk_1` FOREIGN KEY (`driver_id`) REFERENCES `drivers` (`id`),
  ADD CONSTRAINT `driver_bus_assignments_ibfk_2` FOREIGN KEY (`bus_id`) REFERENCES `buses` (`id`);

--
-- Constraints for table `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`trip_id`) REFERENCES `routes` (`id`);

--
-- Constraints for table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `roles_permissions`
--
ALTER TABLE `roles_permissions`
  ADD CONSTRAINT `roles_permissions_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `roles_permissions_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`);

--
-- Constraints for table `schedules`
--
ALTER TABLE `schedules`
  ADD CONSTRAINT `schedules_ibfk_1` FOREIGN KEY (`bus_id`) REFERENCES `buses` (`id`),
  ADD CONSTRAINT `schedules_ibfk_2` FOREIGN KEY (`driver_id`) REFERENCES `drivers` (`id`),
  ADD CONSTRAINT `schedules_ibfk_3` FOREIGN KEY (`route_id`) REFERENCES `routes` (`id`);

--
-- Constraints for table `seats`
--
ALTER TABLE `seats`
  ADD CONSTRAINT `seats_ibfk_1` FOREIGN KEY (`bus_id`) REFERENCES `buses` (`id`);

--
-- Constraints for table `transaction_history`
--
ALTER TABLE `transaction_history`
  ADD CONSTRAINT `transaction_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `trip_history`
--
ALTER TABLE `trip_history`
  ADD CONSTRAINT `trip_history_ibfk_1` FOREIGN KEY (`bus_id`) REFERENCES `buses` (`id`),
  ADD CONSTRAINT `trip_history_ibfk_2` FOREIGN KEY (`driver_id`) REFERENCES `drivers` (`id`),
  ADD CONSTRAINT `trip_history_ibfk_3` FOREIGN KEY (`route_id`) REFERENCES `routes` (`id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
