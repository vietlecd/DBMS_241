-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 104.214.171.0:3306
-- Generation Time: Nov 27, 2024 at 03:55 PM
-- Server version: 5.7.44
-- PHP Version: 8.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `book_stores`
--

-- --------------------------------------------------------

--
-- Table structure for table `audience`
--

CREATE TABLE `audience` (
  `user_id` int(11) NOT NULL,
  `friend_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `audience`
--

INSERT INTO `audience` (`user_id`, `friend_id`) VALUES
(12, 11);

-- --------------------------------------------------------

--
-- Table structure for table `author`
--

CREATE TABLE `author` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `bio` varchar(200) DEFAULT NULL,
  `id_card` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `author`
--

INSERT INTO `author` (`id`, `user_id`, `bio`, `id_card`, `status`) VALUES
(8, 12, 'tro thanh tac gia truyen traanh', '09999912202301', 1),
(9, 11, 'tro thanh tac gia truyen traanh', '09999912202302', 1),
(10, 15, NULL, NULL, NULL),
(11, 16, NULL, NULL, NULL),
(12, 17, NULL, NULL, NULL),
(13, 18, NULL, NULL, NULL),
(14, 19, NULL, NULL, NULL),
(15, 20, NULL, NULL, NULL),
(16, 22, NULL, NULL, NULL),
(18, 24, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `author_book`
--

CREATE TABLE `author_book` (
  `author_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `author_book`
--

INSERT INTO `author_book` (`author_id`, `book_id`) VALUES
(8, 30),
(8, 43),
(8, 44),
(8, 45),
(8, 57),
(8, 58),
(8, 59),
(8, 65),
(8, 66),
(8, 67),
(8, 68),
(9, 27),
(9, 28),
(9, 29),
(9, 30),
(9, 43),
(9, 44),
(9, 45),
(9, 57),
(9, 58),
(9, 59),
(9, 65),
(9, 66),
(9, 67),
(9, 68),
(9, 69);

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `bookID` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text,
  `coverimage` varchar(255) DEFAULT NULL,
  `publishyear` int(11) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `totalpage` int(11) DEFAULT NULL,
  `status` varchar(50) DEFAULT 'false',
  `pdf` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`bookID`, `title`, `description`, `coverimage`, `publishyear`, `price`, `totalpage`, `status`, `pdf`) VALUES
(6, 'C', 'Mo ta sach C', 'path/to/coverImageC.jpg', 2021, '150000', NULL, 'true', NULL),
(8, 'E', 'Mo ta sach E', 'path/to/coverImageE.jpg', 2019, '90000', NULL, 'available', NULL),
(11, 'New Book Title', NULL, 'cover.jpg', 2023, '20', NULL, 'available', NULL),
(13, 'New Book Title', NULL, 'cover.jpg', 2023, '19.99', NULL, 'available', NULL),
(14, 'New Book Title', NULL, 'cover.jpg', 2023, '19.99', NULL, 'available', NULL),
(15, 'New Book Title', NULL, 'cover.jpg', 2023, '19.99', NULL, 'available', NULL),
(16, 'New Book Title', NULL, 'cover.jpg', 2023, '19.99', NULL, 'available', NULL),
(17, 'New Book Title', NULL, 'cover.jpg', 2023, '19.99', NULL, 'available', NULL),
(23, 'Learning Java', 'An in-depth guide to learning Java for beginners and advanced users.', 'https://example.com/images/cover.jpg', 2023, '29.99', NULL, 'available', NULL),
(24, 'Learning Java', 'An in-depth guide to learning Java for beginners and advanced users.', 'https://example.com/images/cover.jpg', 2023, '29.99', NULL, 'available', NULL),
(25, 'Learning Java', 'An in-depth guide to learning Java for beginners and advanced users.', 'https://example.com/images/cover.jpg', 2023, '29.99', NULL, 'available', NULL),
(26, 'Learning Java 2', 'An in-depth guide to learning Java for beginners and advanced users.', 'https://example.com/images/cover.jpg', 2023, '29.99', NULL, 'available', NULL),
(27, 'Learning Java 2', 'An in-depth guide to learning Java for beginners and advanced users.', 'https://307a0e78.vws.vegacdn.vn/view/v2/image/img.book/0/0/0/30688.jpg?v=3', 2023, '160000', NULL, 'available', NULL),
(28, 'Learning Java 3', 'An in-depth guide to learning Java for beginners and advanced users.', 'https://307a0e78.vws.vegacdn.vn/view/v2/image/img.book/0/0/1/50248.jpg?v=1', 2023, '99000', NULL, 'available', NULL),
(29, 'Learning Java 3', 'An in-depth guide to learning Java for beginners and advanced users.', 'https://example.com/images/cover.jpg', 2023, '29.99', NULL, 'available', NULL),
(52, 'The Wonders of King', 'An insightful exploration into the world of scientific discoveries.', NULL, 0, '15.99', NULL, 'available', NULL),
(58, 'Sample Book Title', 'This is a sample description of the book.', 'https://example.com/cover_image.jpg', 2023, '19.99', NULL, 'available', NULL),
(59, 'The Wonders of King', 'An insightful exploration into the world of scientific discoveries.', 'science_cover.jpg', 2022, '15.99', NULL, 'available', NULL),
(60, 'The Art of Nature', 'A captivating journey into the beauty of nature and wildlife.', 'nature_cover.jpg', 2021, '12.50', 105, 'available', NULL),
(61, 'The Art of Nature', 'A captivating journey into the beauty of nature and wildlife.', 'nature_cover.jpg', 2021, '12.50', 105, 'available', NULL),
(62, 'The Art of Nature', 'A captivating journey into the beauty of nature and wildlife.', 'nature_cover.jpg', 2021, '12.50', 105, 'available', NULL),
(63, 'The Art of Nature', 'A captivating journey into the beauty of nature and wildlife.', 'nature_cover.jpg', 2021, '12.50', 105, NULL, NULL),
(64, 'The Art of Nature', 'A captivating journey into the beauty of nature and wildlife.', 'nature_cover.jpg', 2021, '12.50', 105, 'true', NULL),
(68, 'Learning Principal Programming Language', 'This is an interesting book.', NULL, 0, '100', NULL, 'true', NULL),
(69, 'The Art of Nature', 'A captivating journey into the beauty of nature and wildlife.', 'nature_cover.jpg', 2021, '12.50', NULL, 'false', NULL),
(70, 'The Art of Nature', 'A captivating journey into the beauty of nature and wildlife.', 'nature_cover.jpg', 2021, '12.50', 105, 'false', NULL),
(71, 'The Art of Nature', 'A captivating journey into the beauty of nature and wildlife.', 'nature_cover.jpg', 2021, '12.50', 105, 'false', NULL),
(72, 'The Art of Nature', 'A captivating journey into the beauty of nature and wildlife.', 'nature_cover.jpg', 2021, '12.50', 105, 'false', NULL),
(73, 'The Art of Nature', 'A captivating journey into the beauty of nature and wildlife.', 'nature_cover.jpg', 2021, '12.50', 105, 'false', NULL),
(74, 'The Art of Nature', 'A captivating journey into the beauty of nature and wildlife.', 'nature_cover.jpg', 2021, '12.50', 105, 'false', NULL),
(75, 'The Art of Nature', 'A captivating journey into the beauty of nature and wildlife.', 'nature_cover.jpg', 2021, '12.50', 105, 'false', NULL),
(76, 'The Art of Nature', 'A captivating journey into the beauty of nature and wildlife.', 'nature_cover.jpg', 2021, '12.50', 105, 'false', NULL),
(77, ' The Art of Nature', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1y-Q13TBbFvcFcfWb-CP09rbxdR0qlRHz'),
(78, ' The Art of Nature', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1Rl7TBlT2ZW_ghZRFH-1rS5ZAdmmNdWX4'),
(79, ' The Art of Nature', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1YWeghSure05zXB-k_aR2VPdj5oscpsFU'),
(80, ' The Art of Nature', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1m9MNga6VdtjhAKGVljveYOxR6C-qroMC'),
(81, ' The Art of Nature', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1jQ6J44vqiC0IqjP836ElOy4LTsLlk1Zb'),
(82, 'DB quiz2', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1U9EA8HZjetRJcqYvJjbu50xgvRCQAVwW'),
(83, ' The Art of Nature', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1SZBqy1ksCdELI74HCIEZYvF6SGUzMygH'),
(84, ' The Art of Nature', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1Y-m82vo8Pa60lVNpLaO63VieXrycrT1Z'),
(85, ' The Art of Nature', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1_5m4Qehdg8v7qciD6c6q05LSdbd8OK1g'),
(86, 'DB Security', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1-nHlk4EtfOpzW6MB_xpNY6mDr49Ojv0l'),
(87, ' The Art of Nature', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=12Jjwkrcu2p7C6U072aKJeGkAgMYSB50x'),
(88, ' The Art of Nature', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1wfMaLNT8bWOE0rKoVWx1jefO9fAjOoO2'),
(89, ' The Art of Nature', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1sVDHsVglhzdDIx7bwv4Vk8SM8mb2Ysym'),
(90, ' The Art of Nature', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1exf6XRX1GGRoHQ0phNEY2Y3FM3ZE-tSi'),
(91, ' The Art of Nature', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1nDi4gkxQ3W9tW5RbI4uqhMJ8M4l3ah_s'),
(92, ' The Art of Nature', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1bknLEbLFvy1hhRp9Jbi2o6qndM8vXbgk'),
(93, 'DB Security', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', NULL),
(94, 'DB Security', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', NULL),
(95, 'DB Security', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', NULL),
(96, 'DB Security', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', NULL),
(97, 'DB Security', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', NULL),
(98, 'DB Security', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', NULL),
(99, 'DB Security', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', NULL),
(100, 'DB Security', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', NULL),
(101, 'DB Security', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', NULL),
(102, 'DB Security', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', NULL),
(103, 'DB Security', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1tP5mRlsIMfHzQ0jf4Ez0Wwnlo10iHydJ'),
(104, 'DB Security', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1jYtczjHGlVaEvHSN5w8eRoUF6X65LTOR'),
(105, 'DB Security', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=1wgAGUcFLBufSFrL3ID6wjM4ENCG0VTQe'),
(106, 'DB Security', ' A captivating journey into the beauty of nature and wildlife.', ' nature_cover.jpg', 2021, '20', 105, 'false', 'https://drive.google.com/uc?export=view&id=16ixsOZXx98JYS8ndoJ7kq8t3Thgtok7v');

-- --------------------------------------------------------

--
-- Table structure for table `bookmark`
--

CREATE TABLE `bookmark` (
  `markid` bigint(20) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `bookID` int(11) DEFAULT NULL,
  `pagenumber` bigint(20) DEFAULT NULL,
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bookmark`
--

INSERT INTO `bookmark` (`markid`, `user_id`, `bookID`, `pagenumber`, `createdate`) VALUES
(5, 12, 6, 1243, '2024-11-17 00:34:31'),
(8, 12, 61, 1243, '2024-11-17 14:53:17'),
(9, 12, 6, 12, '2024-11-17 14:55:38'),
(10, 12, 6, 9, '2024-11-17 14:55:43');

-- --------------------------------------------------------

--
-- Table structure for table `cate`
--

CREATE TABLE `cate` (
  `bookID` int(11) NOT NULL,
  `cateID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cate`
--

INSERT INTO `cate` (`bookID`, `cateID`) VALUES
(68, 4),
(79, 4),
(80, 4),
(81, 4),
(83, 4),
(84, 4),
(85, 4),
(6, 6),
(59, 6),
(8, 8);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `cateID` int(11) NOT NULL,
  `namecategory` varchar(255) NOT NULL,
  `catedescription` text,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`cateID`, `namecategory`, `catedescription`, `name`) VALUES
(4, 'Fiction', 'girl', NULL),
(5, 'Non-Fiction', 'boy', NULL),
(6, 'Science', 'men', NULL),
(7, 'History', 'women', NULL),
(8, 'Fantasy', 'god', NULL),
(9, 'History', 'cat', NULL),
(10, 'science', 'fill', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `chapter`
--

CREATE TABLE `chapter` (
  `cID` int(11) NOT NULL,
  `bookID` int(11) DEFAULT NULL,
  `cTitle` varchar(255) DEFAULT NULL,
  `cContent` varchar(255) DEFAULT NULL,
  `cFee` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `claim`
--

CREATE TABLE `claim` (
  `claim_date` date DEFAULT NULL,
  `point_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `claim`
--

INSERT INTO `claim` (`claim_date`, `point_id`, `user_id`) VALUES
('2024-11-17', 2, 13),
('2024-11-27', 3, 12),
('2024-11-27', 5, 11),
('2024-11-18', 6, 14);

-- --------------------------------------------------------

--
-- Table structure for table `deposit_withdraw`
--

CREATE TABLE `deposit_withdraw` (
  `payment_id` int(11) NOT NULL,
  `point_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `deposit_withdraw`
--

INSERT INTO `deposit_withdraw` (`payment_id`, `point_id`) VALUES
(14, 2),
(18, 3),
(19, 3),
(20, 3),
(21, 4);

-- --------------------------------------------------------

--
-- Table structure for table `include`
--

CREATE TABLE `include` (
  `list_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `include`
--

INSERT INTO `include` (`list_id`, `book_id`) VALUES
(5, 26),
(6, 26),
(7, 26),
(5, 27),
(5, 28);

-- --------------------------------------------------------

--
-- Table structure for table `list_reading`
--

CREATE TABLE `list_reading` (
  `id` int(11) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `list_reading`
--

INSERT INTO `list_reading` (`id`, `created_at`, `updated_at`, `user_id`) VALUES
(5, '2024-11-16 15:13:27.323181', '2024-11-16 15:13:27.323181', 11),
(6, '2024-11-17 00:24:09.270604', '2024-11-17 00:24:09.270604', 14),
(7, '2024-11-17 00:24:40.371301', '2024-11-17 00:24:40.371301', 13);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `fullname` varchar(100) DEFAULT '',
  `email` varchar(100) DEFAULT '',
  `phone_number` varchar(20) NOT NULL,
  `note` varchar(100) DEFAULT '',
  `order_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(255) DEFAULT NULL,
  `total_money` int(11) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `order_details`
--

CREATE TABLE `order_details` (
  `id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `number_of_products` int(11) DEFAULT NULL,
  `total_money` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pay`
--

CREATE TABLE `pay` (
  `id` int(11) NOT NULL,
  `point_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `payment_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `book_price` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pay`
--

INSERT INTO `pay` (`id`, `point_id`, `book_id`, `payment_date`, `book_price`) VALUES
(1, 5, 27, '2024-11-16 15:10:46', 160000),
(3, 5, 27, '2024-11-16 16:15:57', 160000),
(4, 5, 27, '2024-11-16 16:16:23', 160000),
(5, 5, 28, '2024-11-16 16:25:04', 99000);

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `id` int(11) NOT NULL,
  `pay_amount` varchar(255) DEFAULT NULL,
  `pay_time` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `vnp_txn_ref` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`id`, `pay_amount`, `pay_time`, `user_id`, `status`, `vnp_txn_ref`) VALUES
(1, '10000', '20241114011710', 11, 'PENDING', '81528518'),
(2, '10000', '20241114012223', 11, 'PENDING', '02743402'),
(3, '10000', '20241114012417', 11, 'PENDING', '68230461'),
(4, '10000', '20241114012753', 11, 'PENDING', '45402107'),
(5, '10000', '20241114013005', 11, 'PENDING', '78524184'),
(6, '10000', '20241114013132', 11, 'PENDING', '40040868'),
(7, '10000', '20241114013421', 11, 'SUCCESS', '85842582'),
(8, '10000', '2024-11-14 01-38-19', 12, 'SUCCESS', '86611142'),
(9, '10000', '2024-11-14 01-40-14', 12, 'SUCCESS', '59838595'),
(10, '10000', '2024-11-14 10-54-10', 12, 'PENDING', '38590542'),
(12, '10000', '2024-11-14 10-59-15', 8, 'PENDING', '97792533'),
(13, '10000', '2024-11-14 10-59-22', 8, 'PENDING', '07209993'),
(14, '10000', '2024-11-14 11-02-47', 8, 'SUCCESS', '37967898'),
(15, '10000', '2024-11-14 11-25-05', 8, 'PENDING', '55211143'),
(16, '10000', '2024-11-14 11-31-04', 8, 'PENDING', '83029306'),
(17, '10000', '2024-11-14 11-40-42', 11, 'PENDING', '72342752'),
(18, '10000', '2024-11-14 11-44-37', 11, 'SUCCESS', '75744940'),
(19, '10000', '2024-11-14 12-06-07', 11, 'SUCCESS', '28163239'),
(20, '1000000', '2024-11-14 12-08-08', 11, 'SUCCESS', '86104178'),
(21, '1000000', '2024-11-14 22-12-51', 12, 'SUCCESS', '72951996');

-- --------------------------------------------------------

--
-- Table structure for table `point`
--

CREATE TABLE `point` (
  `id` int(11) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `view_count` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `point`
--

INSERT INTO `point` (`id`, `created_at`, `updated_at`, `amount`, `type`, `view_count`, `user_id`) VALUES
(2, '2024-11-14 21:23:07.947793', '2024-11-17 00:24:36.586678', 350, 'CLAIM', 5, 13),
(3, '2024-11-14 21:47:50.581077', '2024-11-27 00:05:13.838718', 1250, 'CLAIM', 5, 12),
(4, '2024-11-14 22:13:06.701751', '2024-11-14 22:13:06.702748', 1000000, 'DEPOSIT', NULL, 12),
(5, '2024-11-14 22:21:53.417680', '2024-11-27 21:35:19.745662', 365, 'CLAIM', 0, 11),
(6, '2024-11-16 23:44:36.430427', '2024-11-18 15:20:49.412483', 200, 'CLAIM', 0, 14);

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `rpID` int(11) NOT NULL,
  `bookID` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `reportdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `reportcontent` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `report`
--

INSERT INTO `report` (`rpID`, `bookID`, `user_id`, `reportdate`, `reportcontent`) VALUES
(1, 52, 12, '2024-11-18 15:18:55', 'This is a sample report content.'),
(2, 23, 12, '2024-11-18 15:19:15', 'This is a sample report content.'),
(3, 52, 12, '2024-11-18 15:19:22', 'This is a sample report content.'),
(4, 52, 12, '2024-11-18 15:19:47', 'This is not security.'),
(5, 52, 14, '2024-11-18 15:20:53', 'This is not security.'),
(6, 52, 12, '2024-11-18 15:25:19', 'This is not security.');

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `reviewid` bigint(20) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `bookID` int(11) DEFAULT NULL,
  `rating` bigint(20) DEFAULT NULL,
  `comment` text,
  `evaluate` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`reviewid`, `user_id`, `bookID`, `rating`, `comment`, `evaluate`) VALUES
(17, 10, 8, 5, 'This is an excellent book!', 'goood'),
(18, 10, 8, 5, 'hehehehe!', 'bad'),
(19, 12, 23, 5, 'hehehehe!', 'bad'),
(20, 12, 24, 5, 'fgh', 'bad'),
(21, 12, 24, 5, 'fgh', 'bad'),
(22, 12, 25, 5, 'fgh', 'bad'),
(23, 12, 25, 5, 'fghfefe', 'bad'),
(24, 12, 52, 5, 'fghfefe', 'bad'),
(25, 12, 52, 5, 'fghfdefe', 'bad'),
(26, 12, 52, 5, 'fghfdefe', 'bad'),
(27, 11, 23, 5, 'fghfdefe', 'bad'),
(28, 12, 52, NULL, NULL, NULL),
(29, 12, 23, 5, 'fghfdefe', 'bad');

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(0, 'AUTHOR'),
(1, 'ADMIN'),
(2, 'USER');

-- --------------------------------------------------------

--
-- Table structure for table `tokens`
--

CREATE TABLE `tokens` (
  `id` bigint(20) NOT NULL,
  `token` varchar(255) NOT NULL,
  `token_type` varchar(50) NOT NULL,
  `expiration_date` datetime DEFAULT NULL,
  `revoked` tinyint(1) NOT NULL,
  `expired` tinyint(1) NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `fullname` varchar(100) DEFAULT '',
  `username` varchar(10) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `phone_number` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `fullname`, `username`, `password`, `role_id`, `created_at`, `updated_at`, `is_active`, `phone_number`) VALUES
(8, 'Le Hoang Viet', 'vietlh', '$2a$10$nQeY0jqiHRkZC2ZCMDoLXeBX3bF5ZD4EmMcQeuVY.alxLDFuhEWT6', 1, '2024-11-10 17:37:03', '2024-11-12 00:19:24', 0, '0901847727'),
(10, 'Le Hoang Viet', 'vietlh1', '$2a$10$tpx3mLoUIQelHh7tQLu/jeyc5rLC0VFaLKrMaYZGTBlu6JaOj0d42', 2, '2024-11-10 17:45:13', '2024-11-10 17:45:13', 0, '0901847729'),
(11, 'Le Hoang Viet', 'vietlh2', '$2a$10$mzbJQi4dLFskGsFgJ3z6Cu0bxEh89Y65TPGlpiHkH.7XZ1Z3LgOVu', 0, '2024-11-11 22:07:21', '2024-11-12 15:56:25', 0, '0901847730'),
(12, 'Le Hoang Viet', 'vietlh3', '$2a$10$rjF.ohXIjytOUQlzXC.stuhZeHJHImsdWSkcxYrpTw9/bRPv4nJpG', 0, '2024-11-12 00:28:02', '2024-11-26 22:07:48', 0, '0901777665'),
(13, 'Le Hoang Viet', 'vietlh6', '$2a$10$TdkS9QZbWhkeIOWbpgVzUujjbWWalec2yHA2ppkOO7dfxJViIM.r.', 2, '2024-11-14 20:47:28', '2024-11-14 20:47:28', 0, '0901847737'),
(14, 'Le Hoang Viet', 'vietlh66', '$2a$10$QARuQP732rdl745NpyawBOe35MTAyhbVp9sbOkxyrchgOottcfXNy', 2, '2024-11-16 23:44:30', '2024-11-16 23:44:30', 0, '0901847738'),
(15, NULL, 'johnsmith', NULL, NULL, '2024-11-17 20:28:15', '2024-11-17 20:28:15', 0, NULL),
(16, NULL, 'annajones', NULL, NULL, '2024-11-17 20:28:15', '2024-11-17 20:28:15', 0, NULL),
(17, NULL, 'khang', NULL, 0, '2024-11-17 20:39:46', '2024-11-17 20:39:46', 0, NULL),
(18, NULL, 'toan', NULL, 0, '2024-11-17 20:39:46', '2024-11-17 20:39:46', 0, NULL),
(19, NULL, 'khsang', NULL, 0, '2024-11-17 20:43:26', '2024-11-17 20:43:26', 0, NULL),
(20, NULL, 'tosaan', NULL, 0, '2024-11-17 20:43:26', '2024-11-17 20:43:26', 0, NULL),
(21, 'Le Hoang Viet', 'vietlh80', '$2a$10$9MMViimO8TTnlax4AgLXGerLoiT8CzJI0mZvYSq0yahsFPrJTzYHi', 2, '2024-11-21 15:01:46', '2024-11-21 15:01:46', 0, '0901847732'),
(22, NULL, 'haaha', NULL, 0, '2024-11-21 23:34:12', '2024-11-21 23:34:12', 0, NULL),
(24, NULL, 'hee', NULL, 0, '2024-11-22 10:17:59', '2024-11-22 10:17:59', 0, NULL),
(25, 'Toan Le', 'vietlh30', '$2a$10$Q6nZDm16/nRBfg7cqFf8BeupyMauQrfTaPxRzxErrqvJTvGMvv3.e', 2, '2024-11-25 17:22:54', '2024-11-25 17:22:54', 0, '0901234567');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `audience`
--
ALTER TABLE `audience`
  ADD PRIMARY KEY (`user_id`,`friend_id`),
  ADD KEY `FKe7jov10ieu587mep6lh77oay1` (`friend_id`);

--
-- Indexes for table `author`
--
ALTER TABLE `author`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`user_id`);

--
-- Indexes for table `author_book`
--
ALTER TABLE `author_book`
  ADD PRIMARY KEY (`author_id`,`book_id`);

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`bookID`);

--
-- Indexes for table `bookmark`
--
ALTER TABLE `bookmark`
  ADD PRIMARY KEY (`markid`),
  ADD KEY `userID` (`user_id`),
  ADD KEY `bookID` (`bookID`);

--
-- Indexes for table `cate`
--
ALTER TABLE `cate`
  ADD PRIMARY KEY (`bookID`,`cateID`),
  ADD KEY `cateID` (`cateID`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`cateID`);

--
-- Indexes for table `chapter`
--
ALTER TABLE `chapter`
  ADD PRIMARY KEY (`cID`),
  ADD KEY `bookID` (`bookID`);

--
-- Indexes for table `claim`
--
ALTER TABLE `claim`
  ADD PRIMARY KEY (`point_id`,`user_id`),
  ADD KEY `FK518jnubg07iv569n9i61nvfmy` (`user_id`);

--
-- Indexes for table `deposit_withdraw`
--
ALTER TABLE `deposit_withdraw`
  ADD PRIMARY KEY (`payment_id`,`point_id`),
  ADD KEY `FKshj80ym0yu1cypfqjd3y2bynr` (`point_id`);

--
-- Indexes for table `include`
--
ALTER TABLE `include`
  ADD PRIMARY KEY (`list_id`,`book_id`),
  ADD KEY `FK3qx5bjthe1m6hrjpr50s1kwkj` (`book_id`);

--
-- Indexes for table `list_reading`
--
ALTER TABLE `list_reading`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKce28rbs56imba29wbbn9n4r8t` (`user_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `order_details`
--
ALTER TABLE `order_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `pay`
--
ALTER TABLE `pay`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_pay_point` (`point_id`),
  ADD KEY `fk_pay_book` (`book_id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_b6o3db1g2doj8kksdyp3m3t6s` (`vnp_txn_ref`),
  ADD KEY `FK4spfnm9si9dowsatcqs5or42i` (`user_id`);

--
-- Indexes for table `point`
--
ALTER TABLE `point`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKh4qxmn9mig6kith0ish2r67ka` (`user_id`);

--
-- Indexes for table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`rpID`),
  ADD KEY `bookID` (`bookID`),
  ADD KEY `id` (`user_id`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`reviewid`),
  ADD KEY `id` (`user_id`),
  ADD KEY `bookID` (`bookID`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tokens`
--
ALTER TABLE `tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `token` (`token`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `author`
--
ALTER TABLE `author`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `bookID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=107;

--
-- AUTO_INCREMENT for table `bookmark`
--
ALTER TABLE `bookmark`
  MODIFY `markid` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `cateID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `chapter`
--
ALTER TABLE `chapter`
  MODIFY `cID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `list_reading`
--
ALTER TABLE `list_reading`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `order_details`
--
ALTER TABLE `order_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pay`
--
ALTER TABLE `pay`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `point`
--
ALTER TABLE `point`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `report`
--
ALTER TABLE `report`
  MODIFY `rpID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `reviewid` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `tokens`
--
ALTER TABLE `tokens`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `audience`
--
ALTER TABLE `audience`
  ADD CONSTRAINT `FK4mgkxbd5qhml46cqmtr9x5l55` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKe7jov10ieu587mep6lh77oay1` FOREIGN KEY (`friend_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `author`
--
ALTER TABLE `author`
  ADD CONSTRAINT `author_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `author_book`
--
ALTER TABLE `author_book`
  ADD CONSTRAINT `FKg7j6ud9d32ll232o9mgo90s57` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`);

--
-- Constraints for table `bookmark`
--
ALTER TABLE `bookmark`
  ADD CONSTRAINT `bookmark_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `bookmark_ibfk_2` FOREIGN KEY (`bookID`) REFERENCES `book` (`bookID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `cate`
--
ALTER TABLE `cate`
  ADD CONSTRAINT `cate_ibfk_1` FOREIGN KEY (`bookID`) REFERENCES `book` (`bookID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `cate_ibfk_2` FOREIGN KEY (`cateID`) REFERENCES `category` (`cateID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `chapter`
--
ALTER TABLE `chapter`
  ADD CONSTRAINT `chapter_ibfk_1` FOREIGN KEY (`bookID`) REFERENCES `book` (`bookID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `claim`
--
ALTER TABLE `claim`
  ADD CONSTRAINT `FK518jnubg07iv569n9i61nvfmy` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKtcdeuuiy3t7kmyvo0w9672ual` FOREIGN KEY (`point_id`) REFERENCES `point` (`id`);

--
-- Constraints for table `deposit_withdraw`
--
ALTER TABLE `deposit_withdraw`
  ADD CONSTRAINT `FK5pdb4shao2qpfl84mi4tavdol` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`),
  ADD CONSTRAINT `FKshj80ym0yu1cypfqjd3y2bynr` FOREIGN KEY (`point_id`) REFERENCES `point` (`id`);

--
-- Constraints for table `include`
--
ALTER TABLE `include`
  ADD CONSTRAINT `FK3qx5bjthe1m6hrjpr50s1kwkj` FOREIGN KEY (`book_id`) REFERENCES `book` (`bookID`),
  ADD CONSTRAINT `FK7idybf9amwa0ksxfk58cn64v` FOREIGN KEY (`list_id`) REFERENCES `list_reading` (`id`);

--
-- Constraints for table `list_reading`
--
ALTER TABLE `list_reading`
  ADD CONSTRAINT `FKce28rbs56imba29wbbn9n4r8t` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `order_details`
--
ALTER TABLE `order_details`
  ADD CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `book` (`bookID`);

--
-- Constraints for table `pay`
--
ALTER TABLE `pay`
  ADD CONSTRAINT `fk_pay_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`bookID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_pay_point` FOREIGN KEY (`point_id`) REFERENCES `point` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `FK4spfnm9si9dowsatcqs5or42i` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `point`
--
ALTER TABLE `point`
  ADD CONSTRAINT `FKh4qxmn9mig6kith0ish2r67ka` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `report`
--
ALTER TABLE `report`
  ADD CONSTRAINT `report_ibfk_1` FOREIGN KEY (`bookID`) REFERENCES `book` (`bookID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `report_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `review_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `review_ibfk_2` FOREIGN KEY (`bookID`) REFERENCES `book` (`bookID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tokens`
--
ALTER TABLE `tokens`
  ADD CONSTRAINT `tokens_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
