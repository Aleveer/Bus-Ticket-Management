-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307:3307
-- Generation Time: Oct 17, 2024 at 02:41 AM
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
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `account_id` varchar(10) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `role_id` varchar(10) DEFAULT NULL,
  `name` varchar(70) DEFAULT NULL,
  `phone` varchar(12) DEFAULT NULL,
  `verification_code` varchar(255) DEFAULT NULL,
  `verification_expiration` datetime DEFAULT NULL,
  `login_token` mediumtext DEFAULT NULL,
  `password_reset_token` mediumtext DEFAULT NULL,
  `password_reset_expiration` datetime DEFAULT NULL,
  `enable` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`account_id`, `email`, `password`, `role_id`, `name`, `phone`, `verification_code`, `verification_expiration`, `login_token`, `password_reset_token`, `password_reset_expiration`, `enable`) VALUES
('A1B2C3D4E5', 'a1b2c3@example.com', 'pass123', 'R01', 'Nguyen Van A', '+84901234561', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('B6C7D8E9F0', 'b6c7d8@example.com', 'pass123', 'R03', 'Hoang Thi V', '+84901234582', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('C1D2E3F4G5', 'c1d2e3@example.com', 'pass123', 'R02', 'Pham Van Q', '+84901234577', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('D6E7F8G9H0', 'd6e7f8@example.com', 'pass123', 'R02', 'Do Thi L', '+84901234572', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('E1F2G3H4I5', 'e1f2g3@example.com', 'pass123', 'R01', 'Nguyen Van G', '+84901234567', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('F6G7H8I9J0', 'f6g7h8@example.com', 'pass123', 'R01', 'Tran Thi B', '+84901234562', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('G1H2I3J4K5', 'g1h2i3@example.com', 'pass123', 'R03', 'Pham Van W', '+84901234583', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('H6I7J8K9L0', 'h6i7j8@example.com', 'pass123', 'R02', 'Do Thi R', '+84901234578', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('I1J2K3L4M5', 'i1j2k3@example.com', 'pass123', 'R02', 'Nguyen Van M', '+84901234573', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('J6K7L8M9N0', 'j6k7l8@example.com', 'pass123', 'R01', 'Tran Thi H', '+84901234568', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('K1L2M3N4O5', 'k1l2m3@example.com', 'pass123', 'R01', 'Le Van C', '+84901234563', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('L6M7N8O9P0', 'l6m7n8@example.com', 'pass123', 'R03', 'Do Thi X', '+84901234584', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('M1N2O3P4Q5', 'm1n2o3@example.com', 'pass123', 'R02', 'Nguyen Van S', '+84901234579', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('N6O7P8Q9R0', 'n6o7p8@example.com', 'pass123', 'R02', 'Tran Thi N', '+84901234574', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('O1P2Q3R4S5', 'o1p2q3@example.com', 'pass123', 'R01', 'Le Van I', '+84901234569', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('P6Q7R8S9T0', 'p6q7r8@example.com', 'pass123', 'R01', 'Hoang Thi D', '+84901234564', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('Q1R2S3T4U5', 'q1r2s3@example.com', 'pass123', 'R03', 'Nguyen Van Y', '+84901234585', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('R6S7T8U9V0', 'r6s7t8@example.com', 'pass123', 'R02', 'Tran Thi T', '+84901234580', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('S1T2U3V4W5', 's1t2u3@example.com', 'pass123', 'R02', 'Le Van O', '+84901234575', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('T6U7V8W9X0', 't6u7v8@example.com', 'pass123', 'R01', 'Hoang Thi J', '+84901234570', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('U1V2W3X4Y5', 'u1v2w3@example.com', 'pass123', 'R01', 'Pham Van E', '+84901234565', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('V6W7X8Y9Z0', 'v6w7x8@example.com', 'pass123', 'R03', 'Tran Thi Z', '+84901234586', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('W1X2Y3Z4A5', 'w1x2y3@example.com', 'pass123', 'R03', 'Le Van U', '+84901234581', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('X6Y7Z8A9B0', 'x6y7z8@example.com', 'pass123', 'R02', 'Hoang Thi P', '+84901234576', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('Y1Z2A3B4C5', 'y1z2a3@example.com', 'pass123', 'R02', 'Pham Van K', '+84901234571', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0),
('Z6A7B8C9D0', 'z6a7b8@example.com', 'pass123', 'R01', 'Do Thi F', '+84901234566', '', '0000-00-00 00:00:00', '', '', '0000-00-00 00:00:00', 0);

-- --------------------------------------------------------

--
-- Table structure for table `account_notification`
--

CREATE TABLE `account_notification` (
  `account_id` varchar(10) NOT NULL,
  `notification_id` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

CREATE TABLE `bill` (
  `bill_id` varchar(25) NOT NULL,
  `customer_id` varchar(10) DEFAULT NULL,
  `payment_method` varchar(10) DEFAULT NULL,
  `payment_day` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bill_detail`
--

CREATE TABLE `bill_detail` (
  `bill_id` varchar(25) NOT NULL,
  `trip_id` varchar(30) DEFAULT NULL,
  `number_of_ticket` smallint(6) DEFAULT NULL,
  `fee` int(11) DEFAULT NULL,
  `type_ticket` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `booking_Id` varchar(10) NOT NULL,
  `trip_id` varchar(30) NOT NULL,
  `customer_id` varchar(10) NOT NULL,
  `number_of_seat` smallint(6) DEFAULT NULL,
  `is_round_trip` tinyint(1) DEFAULT NULL,
  `round_trip_id` varchar(50) DEFAULT NULL,
  `pickup_point` varchar(100) DEFAULT NULL,
  `drop_off_point` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bus`
--

CREATE TABLE `bus` (
  `bus_id` varchar(12) NOT NULL,
  `type` varchar(10) DEFAULT NULL,
  `number_of_seat` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bus`
--

INSERT INTO `bus` (`bus_id`, `type`, `number_of_seat`) VALUES
('14F.555-016', 'bunk', 36),
('14F.555-034', 'bunk', 36),
('14F.555-052', 'bunk', 36),
('14F.555-070', 'bunk', 36),
('14F.666-007', 'bunk', 36),
('14F.666-025', 'bunk', 36),
('14F.666-043', 'bunk', 36),
('14F.666-061', 'bunk', 36),
('14F.666-079', 'bunk', 36),
('29F.555-002', 'limousine', 34),
('29F.555-014', 'bunk', 36),
('29F.555-032', 'bunk', 36),
('29F.555-050', 'bunk', 36),
('29F.555-068', 'bunk', 36),
('29F.666-023', 'bunk', 36),
('29F.666-041', 'bunk', 36),
('29F.666-059', 'bunk', 36),
('29F.666-077', 'bunk', 36),
('30F.555-004', 'limousine', 34),
('30F.555-028', 'seat', 45),
('30F.555-046', 'seat', 45),
('30F.555-064', 'seat', 45),
('30F.666-019', 'limousine', 34),
('30F.666-037', 'limousine', 34),
('30F.666-055', 'limousine', 34),
('30F.666-073', 'limousine', 34),
('36F.555-018', 'bunk', 36),
('36F.555-036', 'bunk', 36),
('36F.555-054', 'bunk', 36),
('36F.555-072', 'bunk', 36),
('36F.666-009', 'limousine', 34),
('36F.666-027', 'bunk', 36),
('36F.666-045', 'bunk', 36),
('36F.666-063', 'bunk', 36),
('43F.555-022', 'limousine', 34),
('43F.555-040', 'limousine', 34),
('43F.555-058', 'limousine', 34),
('43F.555-076', 'limousine', 34),
('43F.666-003', 'bunk', 36),
('43F.666-013', 'limousine', 34),
('43F.666-031', 'limousine', 34),
('43F.666-049', 'limousine', 34),
('43F.666-067', 'limousine', 34),
('51F.555-020', 'seat', 45),
('51F.555-038', 'seat', 45),
('51F.555-056', 'seat', 45),
('51F.555-074', 'seat', 45),
('51F.666-001', 'bunk', 36),
('51F.666-005', 'bunk', 36),
('51F.666-011', 'limousine', 34),
('51F.666-029', 'limousine', 34),
('51F.666-047', 'limousine', 34),
('51F.666-065', 'limousine', 34),
('60F.555-010', 'bunk', 36),
('60F.555-026', 'limousine', 34),
('60F.555-044', 'limousine', 34),
('60F.555-062', 'limousine', 34),
('60F.555-080', 'limousine', 34),
('60F.666-017', 'limousine', 34),
('60F.666-035', 'limousine', 34),
('60F.666-053', 'limousine', 34),
('60F.666-071', 'limousine', 34),
('75F.555-008', 'seat', 45),
('75F.555-024', 'limousine', 34),
('75F.555-042', 'limousine', 34),
('75F.555-060', 'limousine', 34),
('75F.555-078', 'limousine', 34),
('75F.666-015', 'limousine', 34),
('75F.666-033', 'limousine', 34),
('75F.666-051', 'limousine', 34),
('75F.666-069', 'limousine', 34),
('92F.555-006', 'limousine', 34),
('92F.555-012', 'bunk', 36),
('92F.555-030', 'bunk', 36),
('92F.555-048', 'bunk', 36),
('92F.555-066', 'bunk', 36),
('92F.666-021', 'bunk', 36),
('92F.666-039', 'bunk', 36),
('92F.666-057', 'bunk', 36),
('92F.666-075', 'bunk', 36);

-- --------------------------------------------------------

--
-- Table structure for table `checkpoint`
--

CREATE TABLE `checkpoint` (
  `checkpoint_id` smallint(6) NOT NULL,
  `name` varchar(70) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `region` varchar(15) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `checkpoint`
--

INSERT INTO `checkpoint` (`checkpoint_id`, `name`, `phone`, `region`, `address`) VALUES
(1, '202 LÊ HỒNG PHONG', '202 - 204 Lê Hồ', '19006067', 'Miền Nam'),
(2, '205 PHẠM NGŨ LÃO', '205 Phạm Ngũ Lã', '19006067', 'Miền Nam'),
(3, '231 LÊ HỒNG PHONG', '231 - 233 Lê Hồ', '19006067', 'Miền Nam'),
(4, '27 NGUYỄN ĐÁNG', 'Số 27, đường Ng', '02943 747 474', 'Miền Nam'),
(5, '43 NGUYỄN CƯ TRINH', '43 Nguyễn Cư Tr', '19006067', 'Miền Nam'),
(6, 'AN TRẠCH', '720 QL1A, ấp An', '02993 872 872', 'Miền Nam'),
(7, 'BẾN XE BÀ RỊA', 'Km 65, Quốc lộ ', '02543 826 768', 'Miền Nam'),
(8, 'BẾN XE BẾN CÁT', '213 Đường D11, ', '19006915', 'Miền Nam'),
(9, 'BẾN XE CÀ MAU', 'Quốc lộ 1A, Lý ', '02903 651 651', 'Miền Nam'),
(10, 'BẾN XE CHÂU ĐỐC', 'QL 91,K Hòa Bìn', '02963 989 999', 'Miền Nam'),
(11, 'BẾN XE NĂM CĂN', 'Thị Trấn Năm Că', '02903 715 715', 'Miền Nam'),
(12, 'BẾN XE NGÃ 4 GA', '720 QL1A, Khu P', '19006067', 'Miền Nam'),
(13, 'BẾN XE Ô MÔN', 'Khu vực 5- P.Ch', '02923 667 997', 'Miền Nam'),
(14, 'BẾN XE TRUNG TÂM TP CẦN THƠ', 'Đối diện nhà Ga', '19006919', 'Miền Nam'),
(15, 'BẾN XE VŨNG TÀU', '192 Đường Nam K', '19006917', 'Miền Nam'),
(16, 'BỆNH VIỆN NHI ĐỒNG 3 (BV NHI TPHCM)', '15 đường Võ Trầ', '19006067', 'Miền Nam'),
(17, 'BÌNH MINH', 'Ấp Thuận Tiến B', '02703 742 999', 'Miền Nam'),
(18, 'BX AN SƯƠNG', 'QL22, Ấp Đông L', '19006067', 'Miền Nam'),
(19, 'BX BÌNH DƯƠNG', 'Đường 30/04, Ph', '19006915', 'Miền Nam'),
(20, 'BX CAO LÃNH', 'QL30, Ấp An Địn', '02773 636 636', 'Miền Nam'),
(21, 'BX CHÂU THÀNH', 'Đường Hoàng Lê ', '19006913', 'Miền Nam'),
(22, 'BX ĐỒNG TÂM', '168 Quản lộ Phụ', '02903 658 888', 'Miền Nam'),
(23, 'BX HỒNG NGỰ', 'Tổ 3, Ấp Bình H', '02773 898 777', 'Miền Nam'),
(24, 'BX LONG XUYÊN', '392 Phạm Cự Lượ', '02963 989 999', 'Miền Nam'),
(25, 'BX MIỀN ĐÔNG CŨ', '292 Đinh Bộ Lĩn', '19006067', 'Miền Nam'),
(26, 'BX MIỀN ĐÔNG MỚI', '501 Hoàng Hữu N', '19006067', 'Miền Nam'),
(27, 'BX MIỀN TÂY', '395 Kinh Dương ', '19006067', 'Miền Nam'),
(28, 'BX NGÃ 5', '109 Nguyễn Trãi', '02993 523 523', 'Miền Nam'),
(29, 'BX NGÃ 7', 'BX Ngã 7, P.Ngã', '02933 868 866', 'Miền Nam'),
(30, 'BX SA ĐÉC', '149/8 khóm Hoà ', '02773 774 993', 'Miền Nam'),
(31, 'BX SÓC TRĂNG', '38 Lê Duẩn, P3,', '02993 868 868', 'Miền Nam'),
(32, 'BX TÂN BIÊN', 'Bến xe khách Tâ', '19006913', 'Miền Nam'),
(33, 'BX TÂY NINH', 'Bến xe khách Tâ', '19006913', 'Miền Nam'),
(34, 'BX THANH BÌNH', 'QL30, Khóm Tân ', '02773 833 068', 'Miền Nam'),
(35, 'BX TRÀ VINH', 'Số 554, QL 54, ', '02943 747 474', 'Miền Nam'),
(36, 'BX VỊ THANH', 'Ấp 7, Xã Vị Tru', '02933 583 583', 'Miền Nam'),
(37, 'BX VĨNH CHÂU', 'Đường Phan Than', '02993 615 615', 'Miền Nam'),
(38, 'BX VĨNH LONG', '1E, Đinh Tiên H', '02703 879 777', 'Miền Nam'),
(39, 'CÁI NƯỚC', 'Quốc lộ 1A, Khó', '02903 748 748', 'Miền Nam'),
(40, 'CÁI TÀU HẠ', 'QL 80, Khóm Phú', '02773 611 333', 'Miền Nam'),
(41, 'CHỢ RẪY', '20 Phạm Hữu Chí', '19006067', 'Miền Nam'),
(42, 'ĐẦM DƠI', 'Đường 30 tháng ', '02903 956 956', 'Miền Nam'),
(43, 'ĐỒNG ĐEN', '288 Đồng Đen, P', '19006067', 'Miền Nam'),
(44, 'LAI VUNG', '137A -QL 80, Xã', '02773 849 443', 'Miền Nam'),
(45, 'LẤP VÒ', '135 Đường 3/2, ', '02773 688 988', 'Miền Nam'),
(46, 'LỘ TẺ TRI TÔN', 'Bến xe Châu Thà', '02963 989 999', 'Miền Nam'),
(47, 'LONG PHÚ', 'Ấp 1, TT Long P', '02993 712 713', 'Miền Nam'),
(48, 'LŨY BÁN BÍCH', '973 Lũy Bán Bíc', '19006067', 'Miền Nam'),
(49, 'MA LÂM', '474 đường 8 thá', '02523 733 939', 'Miền Nam'),
(50, 'MŨI NÉ', '20 Huỳnh Thúc K', '02523 743 113', 'Miền Nam'),
(51, 'MỸ THỚI', '26/32 Quốc Lộ 9', '02963 989 999', 'Miền Nam'),
(52, 'NGÃ TƯ ĐỒNG TÂM', 'Quốc Lộ 1A, Ấp ', '02733 618 666', 'Miền Nam'),
(53, 'PHÚ HÒA', '455 Trần Phú, Ấ', '02963 989 999', 'Miền Nam'),
(54, 'PHƯỚC LONG', 'Ấp Long Thành, ', '02913 859 859', 'Miền Nam'),
(55, 'SATRA TRẠM DỪNG', 'Ấp Hòa Phúc, xã', '02733 756 238', 'Miền Nam'),
(56, 'SÔNG ĐỐC', 'Tổ 5, Khóm 10, ', '02903 890 890', 'Miền Nam'),
(57, 'SONG PHÚ', 'Lô 27-28-29 Khu', '02703 959 999', 'Miền Nam'),
(58, 'SUỐI LINH', 'D9, Xa Lộ Hà Nộ', '02518 890 638', 'Miền Nam'),
(59, 'TÂN PHÚ', '782, Quốc Lộ 20', '02513 698 002', 'Miền Nam'),
(60, 'TRẦN ĐỀ', 'Ấp Đầu Giồng, T', '02993 717 718', 'Miền Nam'),
(61, 'UNG BƯỚU 1', '68 Nơ Trang Lon', '19006067', 'Miền Nam'),
(62, 'VĂN PHÒNG DĨ AN', '269 Lê Hồng Pho', '19006915', 'Miền Nam'),
(63, 'VĂN PHÒNG LÁI THIÊU', '70 Đường CMT8, ', '19006915', 'Miền Nam'),
(64, 'VP BẠC LIÊU', 'Bến xe Bạc Liêu', '02913 932 345', 'Miền Nam'),
(65, 'VP BÌNH PHÚ', 'Ấp Nguyệt Lãng ', '02943 888 474', 'Miền Nam'),
(66, 'VP CÁI DẦU', 'Tổ 5, Ấp Bình C', '02963 989 999', 'Miền Nam'),
(67, 'VP CÁI TẮC', 'Bến xe Cái Tắc,', '02933 947 947', 'Miền Nam'),
(68, 'VP CHỢ CHÂU ĐỐC', 'Đ.Nguyễn Hữu Cả', '02963 989 999', 'Miền Nam'),
(69, 'VP HÀ TIÊN', 'Bến xe Hà Tiên,', '02973 668 866', 'Miền Nam'),
(70, 'VP HÀNG XANH', '486H-486J Điện ', '19006067', 'Miền Nam'),
(71, 'VP HỘ PHÒNG', 'Bến xe Hộ phòng', '02913 942 942', 'Miền Nam'),
(72, 'VP HÒA BÌNH', 'Số 617 Quốc Lộ ', '02913 897 897', 'Miền Nam'),
(73, 'VP KINH CÙNG', 'Bến xe Kinh Cùn', '02933 983 983', 'Miền Nam'),
(74, 'VP MỸ LONG', 'QL30, Ấp 2, xã ', '02773 853 999', 'Miền Nam'),
(75, 'VP MỸ THỌ', 'QL30, Khóm Mỹ T', '02773 986 939', 'Miền Nam'),
(76, 'VP NGÃ 3 LỘ TẺ', 'KV Thới Hòa 1 -', '02923 642 668', 'Miền Nam'),
(77, 'VP NÚI SAM', 'Tân Lộ Kiều Lươ', '02963 989 999', 'Miền Nam'),
(78, 'VP PHONG ĐIỀN', '353B Ấp Thị Tứ ', '19006767', 'Miền Nam'),
(79, 'VP RẠCH GIÁ', 'Bến xe Rạch Giá', '02973 963 322', 'Miền Nam'),
(80, 'VP RẠCH SỎI', 'Bến xe tỉnh Kiê', '19006916', 'Miền Nam'),
(81, 'VP TÂN HIỆP', '81 Khu phố Đông', '02973 733 668', 'Miền Nam'),
(82, 'VP THỐT NỐT', 'KV Qui Thạnh 1 ', '02923 851 060', 'Miền Nam'),
(83, 'VP THỨ 3', 'Số 14, Khu phố ', '02973 822 828', 'Miền Nam'),
(84, 'VP TRÀ NÓC', '19/8 Lê Hồng Ph', '02923 730 779', 'Miền Nam'),
(85, 'VP TRẠM DỪNG BẾN TRE', 'Đường Võ Nguyên', '02753 646 464', 'Miền Nam'),
(86, 'VP TRI TÔN', 'Đường 3 tháng 2', '02963 989 999', 'Miền Nam'),
(87, 'VP VĨNH THUẬN', 'Bến xe Vĩnh Thu', '02973 636 366', 'Miền Nam'),
(88, 'VP VỊNH TRE', 'Số 1A, Đinh Tiê', '02963 989 999', 'Miền Nam'),
(89, 'VP VĨNH TƯỜNG', '242 QL 61A, KV ', '02933 993 993', 'Miền Nam'),
(90, 'VP VÕ THỊ SÁU', '17-19, Võ Thị S', '02773 876 851', 'Miền Nam'),
(91, 'VP XA LỘ HÀ NỘI', '798 +798A ,KP3,', '19006067', 'Miền Nam'),
(92, 'VP Y DƯỢC', '03 Mạc Thiên Tí', '02838 555 175', 'Miền Nam'),
(93, 'VP. BẾN XE BIÊN HÒA', '04, Nguyễn Ái Q', '02518 890 638', 'Miền Nam'),
(94, 'VP 172 LÊ DUẨN', 'Số 172 Lê Duẩn,', '02623 936 868', 'Miền Nam'),
(95, 'VP BẢO LÂM', '95 Trần Phú,TT ', '02633 734 734', 'Miền Nam'),
(96, 'VP BẢO LỘC', '399 Trần Phú, P', '02633 731 731', 'Miền Nam'),
(97, 'VP BX ĐÀ LẠT', 'Số 01 - Tô Hiến', '19006070', 'Miền Nam'),
(98, 'VP DI LINH', '735 Hùng Vương,', '02633 788 799', 'Miền Nam'),
(99, 'VP DRAN', 'TDP Đường mới,T', '02633 642 742', 'Miền Nam'),
(100, 'VP ĐỨC TRỌNG', '795 Quốc lộ 20,', '02633 651 651', 'Miền Nam'),
(101, 'VP FI NÔM', '14 Tổ 13 - Ngã ', '02633 658 678', 'Miền Nam'),
(102, 'VP GIA NGHĨA', '226 Hai Bà Trưn', '02613 676 767', 'Miền Nam'),
(103, 'VP HOÀ NINH', 'Ngã 3 Hoà Ninh ', '02633 799 789', 'Miền Nam'),
(104, 'VP KIẾN ĐỨC', 'Ki-ot số 08 , N', '02613 595 959', 'Miền Nam'),
(105, 'VP LÊ QUÝ ĐÔN', '11A/2 Lê Quý Đô', '02633 582 582', 'Miền Nam'),
(106, 'VP TÀ HINE', 'Ngã 3 Ninh Gia-', '02633 628 638', 'Miền Nam'),
(107, 'VP THẠNH MỸ', 'Khu Phố Nghĩa Đ', '02633 646 646', 'Miền Nam'),
(108, 'VP TRẠI MÁT', '07 Huỳnh Tấn Ph', '02633 535 558', 'Miền Nam'),
(109, '207 TÂY SƠN', '207 Tây Sơn, Ph', '02563 947 947', 'Miền Trung'),
(110, '210 NGÔ GIA TỰ', '210 Ngô Gia Tự,', '02593 828 223', 'Miền Trung'),
(111, 'AN NHƠN', '02 Nguyễn Văn L', '02563 789 889', 'Miền Trung'),
(112, 'BẾN XE PHAN RANG', '52 Lê Duẩn, QL1', '02593 828 222', 'Miền Trung'),
(113, 'BẾN XE PHÍA BẮC HUẾ', '132 Lý Thái Tổ,', '02343 766 868', 'Miền Trung'),
(114, 'BẾN XE PHÍA NAM', '97 An dương vươ', '02343 870 870', 'Miền Trung'),
(115, 'BẾN XE QUẢNG NGÃI', '02 Trần Khánh D', '02553 839 839', 'Miền Trung'),
(116, 'BẾN XE QUY NHƠN', 'TT Quy Nhơn, Ph', '02563 946 166', 'Miền Trung'),
(117, 'BÌNH SƠN', 'Số 01 Lê Thị Hà', '02553 525 252', 'Miền Trung'),
(118, 'BX BẮC PHAN THIẾT', '01 Từ Văn Tư, P', '02523 833 179', 'Miền Trung'),
(119, 'BX NAM PHAN THIẾT', '64 Trần Quý Cáp', '02523 730 252', 'Miền Trung'),
(120, 'PHÚ QUÝ', '144 Quốc Lộ 1A,', '02593 841 222', 'Miền Trung'),
(121, 'SÔNG VỆ', 'QL1A ,Thôn 3, X', '02553 719 549', 'Miền Trung'),
(122, 'VĂN PHÒNG 176 TRẦN QUÝ CÁP', '176 Trần Quý Cá', '02583 560 093', 'Miền Trung'),
(123, 'VĂN PHÒNG DIÊN KHÁNH', '62 Lạc Long Quâ', '02583 851 851', 'Miền Trung'),
(124, 'VĂN PHÒNG MỸ CA', '240 Đại Lộ Hùng', '02583 977 968', 'Miền Trung'),
(125, 'VĂN PHÒNG TAM KỲ', '252 Nguyễn Hoàn', '02353 819 819', 'Miền Trung'),
(126, 'VP 77 LÊ LỢI', '77 Lê Lợi, Phườ', '02573 812 812', 'Miền Trung'),
(127, 'VP BẾN XE CAM RANH', 'Số 01 Lê Duẩn, ', '02583 953 953', 'Miền Trung'),
(128, 'VP BẾN XE ĐÀ NẴNG', 'Số 97-99 Cao Sơ', '02363 786 786', 'Miền Trung'),
(129, 'VP BẾN XE ĐẠI LỘC', '279 Hùng Vương,', '02353 747 747', 'Miền Trung'),
(130, 'VP BẾN XE NAM TUY HOÀ', '507 Nguyễn Văn ', '02573 793 793', 'Miền Trung'),
(131, 'VP BẾN XE NINH HOÀ', 'QL1A, Km1421, T', '02583 645 666', 'Miền Trung'),
(132, 'VP BẾN XE PHÍA BẮC', 'Số 01 đường 2/4', '02583 540 068', 'Miền Trung'),
(133, 'VP BẾN XE PHÍA NAM', 'Km6 đường 23/10', '02583 891 955', 'Miền Trung'),
(134, 'VP CẦU BẾN LỘI', '326 đường 19 th', '02523 739 984', 'Miền Trung'),
(135, 'VP NAM PHƯỚC', '284 Điện Biên P', '02353 776 688', 'Miền Trung'),
(136, 'VP NGUYỄN ĐỨC CẢNH', '14 Nguyễn Đức C', '02583 880 079', 'Miền Trung'),
(137, 'VP NGUYỄN THỊ MINH KHAI', '151 Nguyễn Thị ', '02583 876 079', 'Miền Trung'),
(138, 'VP PHAN THIẾT (NỘI Ô)', 'B1 Võ Văn Kiệt,', '2523636636', 'Miền Trung'),
(139, 'VP TRẦN PHÚ', 'Số 16, Đ. Trần ', '02363 786 786', 'Miền Trung'),
(140, 'BẾN XE GIÁP BÁT', 'KM Số 6, Đường ', '02438 641 919', 'Miền Bắc'),
(141, 'BẾN XE NAM ĐỊNH', 'KM Số 2, Đường ', '02283 863 863', 'Miền Bắc');

-- --------------------------------------------------------

--
-- Table structure for table `controller`
--

CREATE TABLE `controller` (
  `controller_id` varchar(15) NOT NULL,
  `account_id` varchar(30) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `controller`
--

INSERT INTO `controller` (`controller_id`, `account_id`, `status`) VALUES
('C000000001', 'Y1Z2A3B4C5', 'active'),
('C000000002', 'D6E7F8G9H0', 'inactive'),
('C000000003', 'I1J2K3L4M5', 'active'),
('C000000004', 'N6O7P8Q9R0', 'inactive'),
('C000000005', 'S1T2U3V4W5', 'active'),
('C000000006', 'X6Y7Z8A9B0', 'active'),
('C000000007', 'C1D2E3F4G5', 'inactive'),
('C000000008', 'H6I7J8K9L0', 'active'),
('C000000009', 'M1N2O3P4Q5', 'active'),
('C000000010', 'R6S7T8U9V0', 'inactive');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customer_id` varchar(10) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `name` varchar(70) DEFAULT NULL,
  `phone` varchar(12) DEFAULT NULL,
  `account_id` varchar(10) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `driver`
--

CREATE TABLE `driver` (
  `driver_id` varchar(10) NOT NULL,
  `account_id` varchar(10) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `driver`
--

INSERT INTO `driver` (`driver_id`, `account_id`, `status`) VALUES
('D000000001', 'A1B2C3D4E5', 'active'),
('D000000002', 'F6G7H8I9J0', 'inactive'),
('D000000003', 'K1L2M3N4O5', 'active'),
('D000000004', 'P6Q7R8S9T0', 'inactive'),
('D000000005', 'U1V2W3X4Y5', 'active'),
('D000000006', 'Z6A7B8C9D0', 'active'),
('D000000007', 'E1F2G3H4I5', 'inactive'),
('D000000008', 'J6K7L8M9N0', 'active'),
('D000000009', 'O1P2Q3R4S5', 'active'),
('D000000010', 'T6U7V8W9X0', 'inactive');

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `feedback_idid` varchar(30) NOT NULL,
  `trip_id` varchar(10) DEFAULT NULL,
  `account_id` varchar(10) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `rating` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE `notification` (
  `notification_idid` varchar(20) NOT NULL,
  `type` varchar(10) DEFAULT NULL,
  `subject` varchar(50) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `permission`
--

CREATE TABLE `permission` (
  `permission_id` varchar(10) NOT NULL,
  `permission_name` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `permission`
--

INSERT INTO `permission` (`permission_id`, `permission_name`) VALUES
('P01', 'read'),
('P02', 'write'),
('P03', 'full acces'),
('P04', 'booking');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `role_id` varchar(10) NOT NULL,
  `role_name` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `role_name`) VALUES
('R01', 'driver'),
('R02', 'controller'),
('R03', 'staff'),
('R04', 'customer'),
('R05', 'admin'),
('role_id', 'role_name');

-- --------------------------------------------------------

--
-- Table structure for table `role_notification`
--

CREATE TABLE `role_notification` (
  `role_id` varchar(10) NOT NULL,
  `notification_id` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `role_permission`
--

CREATE TABLE `role_permission` (
  `role_id` varchar(10) NOT NULL,
  `permission_id` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role_permission`
--

INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES
('R01', 'P01'),
('R02', 'P01'),
('R03', 'P03'),
('R04', 'P04'),
('R05', 'P03');

-- --------------------------------------------------------

--
-- Table structure for table `route`
--

CREATE TABLE `route` (
  `route_id` varchar(10) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `time` varchar(15) DEFAULT NULL,
  `distance` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `route`
--

INSERT INTO `route` (`route_id`, `name`, `time`, `distance`) VALUES
('BLHCM', 'VP Bảo Lộc - BX Miền Đông Mới', '5 giờ 21 phút', 150),
('BR-VTHCM', 'VP BX Vũng Tàu - BX Miền Tây', '5 giờ 30 phút', 140),
('CDHCM', 'BX Châu Đốc - BX Miền Tây', '6 giờ 15 phút', 240),
('CMCT', 'BX Cà Mau - BX Cần Thơ', '4 giờ', 138),
('CTCM', 'BX Cần Thơ - BX Cà Mau', '4 giờ', 138),
('CTHCM', 'BX Cần Thơ - BX Miền Tây', '4 giờ', 170),
('CTRG', 'BX Cần Thơ - VP BX Rạch Giá', '4 giờ 30 phút', 120),
('DLHCM', 'BX Đà Lạt - BX Miền Tây', '8 giờ', 320),
('DNHCM', 'BX Trung Tâm Đà Nẵng - BX Miền Tây', '20 giờ', 950),
('DONAHCM', 'VP Suối Linh - BX Miền Đông Mới', '4 giờ', 140),
('DTHCM', 'BX Sa Đéc - BX Miền Tây', '5 giờ', 140),
('HCMBL', 'BX Miền Đông Mới - VP Bảo Lộc', '5 giờ 21 phút', 150),
('HCMBR-VT', 'BX Miền Tây - VP BX Vũng Tàu', '5 giờ 30 phút', 120),
('HCMCD', 'BX Miền Tây - BX Châu Đốc', '6 giờ 15 phút', 240),
('HCMCT', 'BX Miền Tây - BX Cần Thơ', '4 giờ', 170),
('HCMDL', 'BX Miền Tây - BX Đà Lạt', '8 giờ', 320),
('HCMDN', 'BX Miền Tây - BX Trung Tâm Đà Nẵng', '20 giờ', 950),
('HCMDONA', 'BX Miền Đông Mới - VP Suối Linh', '4 giờ', 140),
('HCMDT', 'BX Miền Tây - BX Sa Đéc', '5 giờ', 140),
('HCMLX ', 'BX Miền Tây - BX Long Xuyên', '5 giờ', 190),
('HCMNT ', 'BX Miền Đông Mới - VP Nguyễn Thị Minh Khai (Nha Trang)', '9 giờ 40 phút', 388),
('HCMPT', 'BX Miền Đông Mới - VP Mũi Né', '6 giờ', 200),
('HCMQNG', 'BX Miền Tây - Bến Xe Quãng Ngãi', '18 giờ', 780),
('HCMQNH', 'BX Miền Tây - Bến Xe Quy Nhơn', '13 giờ 46 phút', 650),
('HCMRG', 'BX Miền Tây - VP BX Rạch Giá', '6 giờ', 270),
('HCMTH', 'BX An Sương - BX Nam Tuy Hòa', '12 giờ', 400),
('HCMTN', 'BX An Sương - BX Tây Ninh', '3 giờ 30 phút', 100),
('HCMTTH', 'BX Miền Tây - Bến xe Phía Nam Huế', '22 giờ', 1043),
('HCMTV', 'BX Miền Tây - BX Trà vinh', '4 giờ 30 phút', 130),
('LXHCM', 'BX Long Xuyên - BX Miền Tây', '5 giờ', 190),
('NTHCM', 'VP Nguyễn Thị Minh Khai (Nha Trang)- BX Miền Đông Mới', '9 giờ 40 phút', 388),
('PTHCM', 'VP Mũi Né - BX Miền Đông Mới', '6 giờ', 200),
('QNGHCM', 'Bến Xe Quãng Ngãi - BX Miền Tây', '18 giờ', 780),
('QNHHCM', 'Bến Xe Quy Nhơn - BX Miền Tây', '13 giờ 46 phút', 650),
('RGCT', 'VP BX Rạch Giá - BX Cần Thơ', '4 giờ 30 phút', 120),
('RGHCM', 'VP BX Rạch Giá - BX Miền Tây', '6 giờ', 270),
('THHCM', 'BX An Sương - BX Nam Tuy Hòa ', '12 giờ', 400),
('TNHCM', 'BX Tây Ninh - BX An Sương', '3 giờ 30 phút', 100),
('TTHHCM', 'Bến xe Phía Nam Huế - BX Miền Tây', '22 giờ', 850),
('TVHCM', 'BX Trà Vinh - BX Miền Tây', '4 giờ 30 phút', 130);

-- --------------------------------------------------------

--
-- Table structure for table `routecheckpoint`
--

CREATE TABLE `routecheckpoint` (
  `route_id` varchar(10) NOT NULL,
  `checkpoint_id` smallint(6) NOT NULL,
  `checkpoint_order` varchar(15) DEFAULT NULL,
  `checkpoint_city` varchar(100) DEFAULT NULL,
  `checkpoint_province` varchar(100) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `routecheckpoint`
--

INSERT INTO `routecheckpoint` (`route_id`, `checkpoint_id`, `checkpoint_order`, `checkpoint_city`, `checkpoint_province`, `type`) VALUES
('BLHCM', 26, 'last', '', 'TP.Hồ Chí Minh', 'dropoff'),
('BLHCM', 96, 'first', 'Bảo Lộc', 'Lâm Đồng', 'departure'),
('BR-VTHCM', 15, 'first', '', 'BRVT', 'departure'),
('BR-VTHCM', 27, 'last', '', 'TP.Hồ Chí Minh', 'dropoff'),
('CDHCM', 10, 'first', 'Châu Đốc', 'An Giang', 'departure'),
('CDHCM', 27, 'last', '', 'TP.Hồ Chí Minh', 'dropoff'),
('CMCT', 9, 'first', '', 'Cà Mau', 'departure'),
('CMCT', 14, 'last', '', 'Cần Thơ', 'dropoff'),
('CTCM', 9, 'last', '', 'Cà Mau', 'dropoff'),
('CTCM', 14, 'first', '', 'Cần Thơ', 'departure'),
('CTHCM', 14, 'first', '', 'Cần Thơ', 'departure'),
('CTHCM', 27, 'last', '', 'TP.Hồ Chí Minh', 'dropoff'),
('CTHCM', 52, 'second', '', 'Tiền Giang', 'rest'),
('CTRG', 14, 'first', '', 'Cần Thơ', 'departure'),
('CTRG', 79, 'last', 'Rạch Giá', 'Kiên Giang', 'dropoff'),
('DLHCM', 27, 'last', '', 'TP.Hồ Chí Minh', 'dropoff'),
('DLHCM', 59, 'second', '', 'Đồng Nai', 'rest'),
('DLHCM', 97, 'first', 'Đà Lạt', 'Lâm Đồng', 'departure'),
('DNHCM', 27, 'last', '', 'TP.Hồ Chí Minh', 'dropoff'),
('DNHCM', 128, 'first', '', 'Đà Nẵng', 'departure'),
('DONAHCM', 26, 'last', 'Thủ Đức', 'TP.Hồ Chí Minh', 'dropoff'),
('DONAHCM', 58, 'first', 'Biên Hoà ', 'Đồng Nai', 'departure'),
('DTHCM', 27, 'last', '', 'TP.Hồ Chí Minh', 'dropoff'),
('DTHCM', 30, 'first', 'Sa Đéc', 'Đồng Tháp', 'departure'),
('DTHCM', 52, 'second', '', 'Tiền Giang', 'rest'),
('HCMBL', 26, 'first', '', 'TP.Hồ Chí Minh', 'departure'),
('HCMBL', 96, 'last', 'Bảo Lộc', 'Lâm Đồng', 'dropoff'),
('HCMBR-VT', 15, 'last', '', 'BRVT', 'dropoff'),
('HCMBR-VT', 27, 'first', '', 'TP.Hồ Chí Minh', 'departure'),
('HCMCD', 10, 'last', 'Châu Đốc', 'An Giang', 'dropoff'),
('HCMCD', 27, 'first', '', 'TP.Hồ Chí Minh', 'departure'),
('HCMCT', 14, 'last', '', 'Cần Thơ', 'dropoff'),
('HCMCT', 27, 'first', '', 'TP.Hồ Chí Minh', 'departure'),
('HCMCT', 52, 'second', '', 'Tiền Giang', 'rest'),
('HCMDL', 27, 'first', '', 'TP.Hồ Chí Minh', 'departure'),
('HCMDL', 59, 'second', '', 'Đồng Nai', 'rest'),
('HCMDL', 97, 'last', 'Đà Lạt', 'Lâm Đồng', 'dropoff'),
('HCMDN', 27, 'first', '', 'TP.Hồ Chí Minh', 'departure'),
('HCMDN', 128, 'last', '', 'Đà Nẵng', 'dropoff'),
('HCMDONA', 26, 'first', 'Thủ Đức', 'TP.Hồ Chí Minh', 'departure'),
('HCMDONA', 58, 'last', 'Biên Hoà ', 'Đồng Nai', 'dropoff'),
('HCMDT', 27, 'first', '', 'TP.Hồ Chí Minh', 'departure'),
('HCMDT', 30, 'last', 'Sa Đéc', 'Đồng Tháp', 'dropoff'),
('HCMDT', 52, 'second', '', 'Tiền Giang', 'rest'),
('HCMLX ', 24, 'last', 'Long Xuyên', 'An Giang', 'dropoff'),
('HCMLX ', 27, 'first', '', 'TP.Hồ Chí Minh', 'departure'),
('HCMNT ', 26, 'first', 'Thủ Đức', 'TP.Hồ Chí Minh', 'departure'),
('HCMNT ', 137, 'last', 'Nha Trang', 'Khánh Hoà', 'dropoff'),
('HCMPT', 26, 'first', 'Thủ Đức', 'TP.Hồ Chí Minh', 'departure'),
('HCMPT', 50, 'last', 'Phan Thiết ', 'Bình Thuận', 'dropoff'),
('HCMQNG', 27, 'first', '', 'TP.Hồ Chí Minh', 'departure'),
('HCMQNG', 115, 'last', '', 'Quảng Ngãi', 'dropoff'),
('HCMQNH', 27, 'first', '', 'TP.Hồ Chí Minh', 'departure'),
('HCMQNH', 116, 'last', 'Quy Nhơn', 'Bình Định', 'dropoff'),
('HCMRG', 27, 'first', '', 'TP.Hồ Chí Minh', 'departure'),
('HCMRG', 79, 'last', 'Rạch Giá', 'Kiên Giang', 'dropoff'),
('HCMTH', 18, 'first', '', 'TP.Hồ Chí Minh', 'departure'),
('HCMTH', 130, 'last', 'Tuy Hoà ', 'Phú Yên', 'dropoff'),
('HCMTN', 18, 'first', '', 'TP.Hồ Chí Minh', 'departure'),
('HCMTN', 33, 'last', '', 'Tây Ninh', 'dropoff'),
('HCMTTH', 27, 'first', '', 'TP.Hồ Chí Minh', 'departure'),
('HCMTTH', 114, 'last', '', 'Huế ', 'dropoff'),
('HCMTV', 27, 'first', '', 'TP.Hồ Chí Minh', 'departure'),
('HCMTV', 35, 'last', '', 'Trà Vinh', 'dropoff'),
('HCMTV', 52, 'second', '', 'Tiền Giang', 'rest'),
('HCMTV', 85, 'third', '', 'Bến Tre', 'en-route'),
('LXHCM', 24, 'first', 'Long Xuyên', 'An Giang', 'departure'),
('LXHCM', 27, 'last', '', 'TP.Hồ Chí Minh', 'dropoff'),
('NTHCM', 26, 'last', 'Thủ Đức', 'TP.Hồ Chí Minh', 'dropoff'),
('NTHCM', 137, 'first', 'Nha Trang', 'Khánh Hoà', 'departure'),
('PTHCM', 26, 'last', 'Thủ Đức', 'TP.Hồ Chí Minh', 'dropoff'),
('PTHCM', 50, 'first', 'Phan Thiết ', 'Bình Thuận', 'departure'),
('QNGHCM', 27, 'last', '', 'TP.Hồ Chí Minh', 'dropoff'),
('QNGHCM', 115, 'first', '', 'Quảng Ngãi', 'departure'),
('QNHHCM', 27, 'last', '', 'TP.Hồ Chí Minh', 'dropoff'),
('QNHHCM', 116, 'first', 'Quy Nhơn', 'Bình Định', 'departure'),
('RGCT', 14, 'last', '', 'Cần Thơ', 'dropoff'),
('RGCT', 79, 'first', 'Rạch Giá', 'Kiên Giang', 'departure'),
('RGHCM', 27, 'last', '', 'TP.Hồ Chí Minh', 'dropoff'),
('RGHCM', 79, 'first', 'Rạch Giá', 'Kiên Giang', 'departure'),
('THHCM', 18, 'last', '', 'TP.Hồ Chí Minh', 'dropoff'),
('THHCM', 130, 'first', 'Tuy Hoà ', 'Phú Yên', 'departure'),
('TNHCM', 18, 'last', '', 'TP.Hồ Chí Minh', 'dropoff'),
('TNHCM', 33, 'first', '', 'Tây Ninh', 'departure'),
('TTHHCM', 27, 'last', '', 'TP.Hồ Chí Minh', 'dropoff'),
('TTHHCM', 114, 'first', '', 'Huế ', 'departure'),
('TVHCM', 27, 'last', '', 'TP.Hồ Chí Minh', 'dropoff'),
('TVHCM', 35, 'first', '', 'Trà Vinh', 'departure'),
('TVHCM', 52, 'third', '', 'Tiền Giang', 'rest'),
('TVHCM', 85, 'second', '', 'Bến Tre', 'en-route');

-- --------------------------------------------------------

--
-- Table structure for table `seatbooking`
--

CREATE TABLE `seatbooking` (
  `trip_id` varchar(30) NOT NULL,
  `customer_id` varchar(10) NOT NULL,
  `seat` varchar(5) NOT NULL
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
  `PRINCIPAL_NAME` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `staff_id` varchar(10) NOT NULL,
  `account_id` varchar(10) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`staff_id`, `account_id`, `status`) VALUES
('S000000001', 'W1X2Y3Z4A5', 'active'),
('S000000002', 'B6C7D8E9F0', 'inactive'),
('S000000003', 'G1H2I3J4K5', 'active'),
('S000000004', 'L6M7N8O9P0', 'inactive'),
('S000000005', 'Q1R2S3T4U5', 'active'),
('S000000006', 'V6W7X8Y9Z0', 'active');

-- --------------------------------------------------------

--
-- Table structure for table `trip`
--

CREATE TABLE `trip` (
  `trip_id` varchar(30) NOT NULL,
  `departure_time` datetime DEFAULT NULL,
  `arrival_time` datetime DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `bus_id` varchar(9) DEFAULT NULL,
  `customer_id` varchar(10) DEFAULT NULL,
  `driver_id` varchar(10) DEFAULT NULL,
  `controller_id` varchar(30) DEFAULT NULL,
  `staff_id` varchar(10) DEFAULT NULL,
  `route_id` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`account_id`),
  ADD UNIQUE KEY `account_id` (`account_id`),
  ADD KEY `role_id` (`role_id`);

--
-- Indexes for table `account_notification`
--
ALTER TABLE `account_notification`
  ADD PRIMARY KEY (`account_id`,`notification_id`),
  ADD UNIQUE KEY `account_id` (`account_id`),
  ADD KEY `notification_id` (`notification_id`);

--
-- Indexes for table `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`bill_id`),
  ADD UNIQUE KEY `bill_id` (`bill_id`);

--
-- Indexes for table `bill_detail`
--
ALTER TABLE `bill_detail`
  ADD PRIMARY KEY (`bill_id`),
  ADD UNIQUE KEY `bill_id` (`bill_id`),
  ADD KEY `trip_id` (`trip_id`);

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`booking_Id`),
  ADD UNIQUE KEY `trip_id` (`trip_id`),
  ADD KEY `customer_id` (`customer_id`);

--
-- Indexes for table `bus`
--
ALTER TABLE `bus`
  ADD PRIMARY KEY (`bus_id`),
  ADD UNIQUE KEY `bus_id` (`bus_id`);

--
-- Indexes for table `checkpoint`
--
ALTER TABLE `checkpoint`
  ADD PRIMARY KEY (`checkpoint_id`),
  ADD UNIQUE KEY `checkpoint_id` (`checkpoint_id`);

--
-- Indexes for table `controller`
--
ALTER TABLE `controller`
  ADD PRIMARY KEY (`controller_id`),
  ADD UNIQUE KEY `controller_id` (`controller_id`),
  ADD KEY `account_id` (`account_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_id`),
  ADD UNIQUE KEY `customer_id` (`customer_id`),
  ADD KEY `account_id` (`account_id`);

--
-- Indexes for table `driver`
--
ALTER TABLE `driver`
  ADD PRIMARY KEY (`driver_id`),
  ADD UNIQUE KEY `driver_id` (`driver_id`),
  ADD KEY `account_id` (`account_id`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`feedback_idid`),
  ADD UNIQUE KEY `feedback_idid` (`feedback_idid`),
  ADD KEY `trip_id` (`trip_id`),
  ADD KEY `account_id` (`account_id`);

--
-- Indexes for table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`notification_idid`),
  ADD UNIQUE KEY `notification_idid` (`notification_idid`);

--
-- Indexes for table `permission`
--
ALTER TABLE `permission`
  ADD PRIMARY KEY (`permission_id`),
  ADD UNIQUE KEY `permission_id` (`permission_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`,`role_name`),
  ADD UNIQUE KEY `role_id` (`role_id`);

--
-- Indexes for table `role_notification`
--
ALTER TABLE `role_notification`
  ADD PRIMARY KEY (`role_id`,`notification_id`),
  ADD UNIQUE KEY `role_id` (`role_id`),
  ADD KEY `notification_id` (`notification_id`);

--
-- Indexes for table `role_permission`
--
ALTER TABLE `role_permission`
  ADD PRIMARY KEY (`role_id`,`permission_id`);

--
-- Indexes for table `route`
--
ALTER TABLE `route`
  ADD PRIMARY KEY (`route_id`),
  ADD UNIQUE KEY `route_id` (`route_id`);

--
-- Indexes for table `routecheckpoint`
--
ALTER TABLE `routecheckpoint`
  ADD PRIMARY KEY (`route_id`,`checkpoint_id`);

--
-- Indexes for table `seatbooking`
--
ALTER TABLE `seatbooking`
  ADD PRIMARY KEY (`trip_id`,`customer_id`,`seat`),
  ADD UNIQUE KEY `trip_id` (`trip_id`),
  ADD KEY `customer_id` (`customer_id`);

--
-- Indexes for table `spring_session`
--
ALTER TABLE `spring_session`
  ADD PRIMARY KEY (`PRIMARY_ID`,`SESSION_ID`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`staff_id`),
  ADD UNIQUE KEY `staff_id` (`staff_id`),
  ADD KEY `account_id` (`account_id`);

--
-- Indexes for table `trip`
--
ALTER TABLE `trip`
  ADD PRIMARY KEY (`trip_id`),
  ADD UNIQUE KEY `trip_id` (`trip_id`),
  ADD KEY `route_id` (`route_id`),
  ADD KEY `driver_id` (`driver_id`),
  ADD KEY `controller_id` (`controller_id`),
  ADD KEY `staff_id` (`staff_id`),
  ADD KEY `trip_ibfk_2` (`bus_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `account_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `account_notification`
--
ALTER TABLE `account_notification`
  ADD CONSTRAINT `account_notification_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `account_notification_ibfk_2` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`notification_idid`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `bill_detail`
--
ALTER TABLE `bill_detail`
  ADD CONSTRAINT `bill_detail_ibfk_1` FOREIGN KEY (`trip_id`) REFERENCES `booking` (`trip_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `bill_detail_ibfk_2` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`bill_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`trip_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `controller`
--
ALTER TABLE `controller`
  ADD CONSTRAINT `controller_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `driver`
--
ALTER TABLE `driver`
  ADD CONSTRAINT `driver_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`trip_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `role_notification`
--
ALTER TABLE `role_notification`
  ADD CONSTRAINT `role_notification_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `role_notification_ibfk_2` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`notification_idid`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `role_permission`
--
ALTER TABLE `role_permission`
  ADD CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `routecheckpoint`
--
ALTER TABLE `routecheckpoint`
  ADD CONSTRAINT `routecheckpoint_ibfk_1` FOREIGN KEY (`route_id`) REFERENCES `route` (`route_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `routecheckpoint_ibfk_2` FOREIGN KEY (`checkpoint_id`) REFERENCES `checkpoint` (`checkpoint_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `seatbooking`
--
ALTER TABLE `seatbooking`
  ADD CONSTRAINT `seatbooking_ibfk_1` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`trip_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `seatbooking_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `trip`
--
ALTER TABLE `trip`
  ADD CONSTRAINT `trip_ibfk_1` FOREIGN KEY (`route_id`) REFERENCES `route` (`route_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `trip_ibfk_2` FOREIGN KEY (`bus_id`) REFERENCES `bus` (`bus_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `trip_ibfk_3` FOREIGN KEY (`driver_id`) REFERENCES `driver` (`driver_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `trip_ibfk_4` FOREIGN KEY (`controller_id`) REFERENCES `controller` (`controller_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `trip_ibfk_5` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
