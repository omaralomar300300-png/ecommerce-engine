-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 16 مايو 2026 الساعة 15:28
-- إصدار الخادم: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ecommerce_engine`
--

-- --------------------------------------------------------

--
-- بنية الجدول `invoice_task`
--

CREATE TABLE `invoice_task` (
  `id` bigint(20) NOT NULL,
  `attempts` int(11) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `error_message` varchar(255) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `processed_at` datetime(6) DEFAULT NULL,
  `status` enum('DONE','FAILED','PENDING','PROCESSING') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- إرجاع أو استيراد بيانات الجدول `invoice_task`
--

INSERT INTO `invoice_task` (`id`, `attempts`, `created_at`, `error_message`, `order_id`, `processed_at`, `status`) VALUES
(1, 1, '2026-05-11 20:07:33.000000', NULL, 4, '2026-05-11 20:07:36.000000', 'DONE'),
(2, 1, '2026-05-11 20:10:45.000000', NULL, 5, '2026-05-11 20:10:50.000000', 'DONE'),
(3, 1, '2026-05-16 15:43:25.000000', NULL, 6, '2026-05-16 15:43:30.000000', 'DONE');

-- --------------------------------------------------------

--
-- بنية الجدول `product`
--

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `stock_quantity` int(11) NOT NULL,
  `version` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- إرجاع أو استيراد بيانات الجدول `product`
--

INSERT INTO `product` (`id`, `description`, `name`, `price`, `stock_quantity`, `version`) VALUES
(1, 'Gaming laptop', 'Laptop', 800, 9999, 6),
(2, 'Product for optimistic locking test', 'Lock Test Product', 100, 1, 0);

-- --------------------------------------------------------

--
-- بنية الجدول `purchase_order`
--

CREATE TABLE `purchase_order` (
  `id` bigint(20) NOT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `total_price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- إرجاع أو استيراد بيانات الجدول `purchase_order`
--

INSERT INTO `purchase_order` (`id`, `product_id`, `product_name`, `quantity`, `total_price`) VALUES
(1, 1, 'Laptop', 1, 800),
(2, 1, 'Laptop', 2, 1600),
(3, 1, 'Laptop', 1, 800),
(4, 1, 'Laptop', 1, 800),
(5, 1, 'Laptop', 1, 800),
(6, 1, 'Laptop', 1, 800);

-- --------------------------------------------------------

--
-- بنية الجدول `wallet`
--

CREATE TABLE `wallet` (
  `id` bigint(20) NOT NULL,
  `balance` double NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- إرجاع أو استيراد بيانات الجدول `wallet`
--

INSERT INTO `wallet` (`id`, `balance`, `user_name`, `version`) VALUES
(1, 999200, 'Ahmad', 2),
(2, 400, 'Ahmad', 2),
(3, 9200, 'Test User', 1),
(4, 10000, 'Queue Test User', 0),
(5, 1000, 'LockUser', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `invoice_task`
--
ALTER TABLE `invoice_task`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `purchase_order`
--
ALTER TABLE `purchase_order`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `wallet`
--
ALTER TABLE `wallet`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `invoice_task`
--
ALTER TABLE `invoice_task`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `purchase_order`
--
ALTER TABLE `purchase_order`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `wallet`
--
ALTER TABLE `wallet`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
