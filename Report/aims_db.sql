-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 07, 2024 at 10:30 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `aims_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `id` int(11) NOT NULL,
  `author` varchar(45) NOT NULL,
  `publisher` varchar(45) NOT NULL,
  `language` varchar(45) NOT NULL,
  `bookCategory` varchar(45) NOT NULL,
  `coverType` varchar(45) NOT NULL,
  `numberOfPages` int(11) NOT NULL,
  `publishDate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `id` int(11) NOT NULL,
  `totalPrice` int(11) NOT NULL,
  `createAt` timestamp NOT NULL DEFAULT current_timestamp(),
  `updateAt` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cart_media`
--

CREATE TABLE `cart_media` (
  `cartId` int(11) NOT NULL,
  `mediaId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cd`
--

CREATE TABLE `cd` (
  `id` int(11) NOT NULL,
  `type` varchar(45) NOT NULL,
  `artist` varchar(45) NOT NULL,
  `dateRelease` datetime NOT NULL,
  `recordLabel` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `deleveryinfo`
--

CREATE TABLE `deleveryinfo` (
  `id` int(11) NOT NULL,
  `receiverName` varchar(45) NOT NULL,
  `number` varchar(10) NOT NULL,
  `province` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `instruction` varchar(100) NOT NULL,
  `createAt` timestamp NOT NULL DEFAULT current_timestamp(),
  `updateAt` timestamp NOT NULL DEFAULT current_timestamp(),
  `isRushOrder` tinyint(1) NOT NULL,
  `rushDeliveryTime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `dvd`
--

CREATE TABLE `dvd` (
  `id` int(11) NOT NULL,
  `discType` varchar(45) NOT NULL,
  `director` varchar(45) NOT NULL,
  `runtime` int(11) NOT NULL,
  `subtitles` varchar(45) NOT NULL,
  `studio` varchar(45) NOT NULL,
  `realeaseDate` varchar(45) NOT NULL,
  `filmType` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE `invoice` (
  `id` int(11) NOT NULL,
  `totalAmount` int(11) NOT NULL,
  `orderId` int(11) NOT NULL,
  `createAt` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `lp`
--

CREATE TABLE `lp` (
  `id` int(11) NOT NULL,
  `musicType` varchar(45) NOT NULL,
  `artist` varchar(45) NOT NULL,
  `dateRelease` datetime NOT NULL,
  `recordLabel` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `media`
--

CREATE TABLE `media` (
  `id` int(11) NOT NULL,
  `title` varchar(45) NOT NULL,
  `category` varchar(45) NOT NULL,
  `value` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `type` varchar(45) NOT NULL,
  `imageURL` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_media`
--

CREATE TABLE `order_media` (
  `orderId` int(11) NOT NULL,
  `mediaId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `paymenttransaction`
--

CREATE TABLE `paymenttransaction` (
  `id` int(11) NOT NULL,
  `createAt` timestamp NOT NULL DEFAULT current_timestamp(),
  `content` varchar(45) NOT NULL,
  `method` varchar(45) NOT NULL,
  `cardId` int(11) NOT NULL,
  `invoiceId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `_order`
--

CREATE TABLE `_order` (
  `id` int(11) NOT NULL,
  `shippingFee` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `totalPrice` int(11) NOT NULL,
  `deliveryId` int(11) NOT NULL,
  `createAt` timestamp NOT NULL DEFAULT current_timestamp(),
  `updateAt` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cart_media`
--
ALTER TABLE `cart_media`
  ADD PRIMARY KEY (`cartId`,`mediaId`),
  ADD KEY `mediaId` (`mediaId`);

--
-- Indexes for table `cd`
--
ALTER TABLE `cd`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `deleveryinfo`
--
ALTER TABLE `deleveryinfo`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `dvd`
--
ALTER TABLE `dvd`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `invoice`
--
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`id`),
  ADD KEY `orderId` (`orderId`);

--
-- Indexes for table `lp`
--
ALTER TABLE `lp`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `media`
--
ALTER TABLE `media`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_media`
--
ALTER TABLE `order_media`
  ADD PRIMARY KEY (`orderId`,`mediaId`),
  ADD KEY `mediaId` (`mediaId`);

--
-- Indexes for table `paymenttransaction`
--
ALTER TABLE `paymenttransaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cardId` (`cardId`),
  ADD KEY `invoiceId` (`invoiceId`);

--
-- Indexes for table `_order`
--
ALTER TABLE `_order`
  ADD PRIMARY KEY (`id`),
  ADD KEY `deliveryId` (`deliveryId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `media`
--
ALTER TABLE `media`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `book`
--
ALTER TABLE `book`
  ADD CONSTRAINT `book_ibfk_1` FOREIGN KEY (`id`) REFERENCES `media` (`id`);

--
-- Constraints for table `cart_media`
--
ALTER TABLE `cart_media`
  ADD CONSTRAINT `cart_media_ibfk_1` FOREIGN KEY (`cartId`) REFERENCES `cart` (`id`),
  ADD CONSTRAINT `cart_media_ibfk_2` FOREIGN KEY (`mediaId`) REFERENCES `media` (`id`);

--
-- Constraints for table `cd`
--
ALTER TABLE `cd`
  ADD CONSTRAINT `cd_ibfk_1` FOREIGN KEY (`id`) REFERENCES `media` (`id`);

--
-- Constraints for table `dvd`
--
ALTER TABLE `dvd`
  ADD CONSTRAINT `dvd_ibfk_1` FOREIGN KEY (`id`) REFERENCES `media` (`id`);

--
-- Constraints for table `invoice`
--
ALTER TABLE `invoice`
  ADD CONSTRAINT `invoice_ibfk_1` FOREIGN KEY (`orderId`) REFERENCES `_order` (`id`);

--
-- Constraints for table `lp`
--
ALTER TABLE `lp`
  ADD CONSTRAINT `lp_ibfk_1` FOREIGN KEY (`id`) REFERENCES `media` (`id`);

--
-- Constraints for table `order_media`
--
ALTER TABLE `order_media`
  ADD CONSTRAINT `order_media_ibfk_1` FOREIGN KEY (`orderId`) REFERENCES `_order` (`id`),
  ADD CONSTRAINT `order_media_ibfk_2` FOREIGN KEY (`mediaId`) REFERENCES `media` (`id`);

--
-- Constraints for table `paymenttransaction`
--
ALTER TABLE `paymenttransaction`
  ADD CONSTRAINT `paymenttransaction_ibfk_1` FOREIGN KEY (`invoiceId`) REFERENCES `invoice` (`id`),
  ADD CONSTRAINT `paymenttransaction_ibfk_2` FOREIGN KEY (`cardId`) REFERENCES `card` (`id`);

--
-- Constraints for table `_order`
--
ALTER TABLE `_order`
  ADD CONSTRAINT `_order_ibfk_1` FOREIGN KEY (`deliveryId`) REFERENCES `deleveryinfo` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
