-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 24, 2022 at 02:35 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


--
-- Database: `jpa_ca`
--


--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `name`) VALUES
(1, 'GUEST'),
(2, 'STUDENT'),
(3, 'LECTURER'),
(4, 'ADMIN');



--
-- Dumping data for table `course`
--

INSERT INTO `course` (`course_id`, `credits`, `name`, `size`) VALUES
(1, 6, 'Software Analysis and Design', 80),
(2, 8, 'Enterprise Solutions Design and Development', 80),
(3, 6, 'Mobile Application Development', 80),
(4, 6, 'Machine Learning Application Development', 80),
(5, 6, 'Web Application Development', 80),
(6, 6, 'Application Development Project', 80);



--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `email`, `enabled`, `first_mid_name`, `last_name`, `password`, `profile_image`, `username`, `verification_code`, `role_id`) VALUES
(1, 'E0941673@u.nus.edu', b'1', 'Ko Ko Khant', 'Aung', '$2a$10$kWkAnvuBNArKm4OppqZ4HOHvhnpiGfQbcwYQ7lg088hXI4ssv7qQS', NULL, 'E0941673', NULL, 2),
(2, 'E0941666@u.nus.edu', b'1', 'Mya Phoo', 'Aye', '$2a$10$BtZT8rYDMi5pvWoZDFnM9e7eMQHHzi.M76NvfPq/dYQZlGJES3O9e', NULL, 'E0941666', NULL, 2),
(3, 'E0008064@u.nus.edu', b'1', 'Li Yuan', 'Cheong', '$2a$10$Xk9vkMNN3Z9iBpAcSP5Ui.ZDu40TFyh0X2ALk54riswo6CWZb9a9S', NULL, 'E0008064', NULL, 2),
(4, 'somik@u.nus.edu', b'1', 'Sher Mostafa Somik', 'Khan', '$2a$10$z3OA/t8o7jxQt7vnN.OCR.fwU1feRzUrEkr1EPERAV0X6tFxb4Ng2', NULL, 'somik', NULL, 2),
(5, 'E0941691@u.nus.edu', b'1', 'Htet Win Maung', 'Nyan', '$2a$10$jW.pLat95hmv/ehyiwCE.eHsyLYD/CfOTc295tQtUo34t.Hg9.TgK', NULL, 'E0941691', NULL, 2),
(6, 'E0941694@u.nus.edu', b'1', 'Tingting', 'Xie', '$2a$10$nU7TiakUsNufcg65DGeDl.O9YvsBRj3.c02lnK/A4j1GLWuClYJ6K', NULL, 'E0941694', NULL, 2),
(7, 'E0320219@u.nus.edu', b'1', 'Ziyou', 'Zhao', '$2a$10$FgoxEPtRZ2hQmUIJOXZB7O48taZJ7EpuzM2FrkG5WmdLx4v.eJJYi', NULL, 'E0320219', NULL, 2),
(8, 'guest@example.com', b'1', 'Guest', 'Guest', '$2a$10$2uTytXznLGxsNFXyyKvn5.kAosKa7oNFIGaVbViTitAZFht46Z96G', NULL, 'guest', NULL, 1),
(9, 'student@example.com', b'1', 'Student', 'Student', '$2a$10$SACrl1AhUT4C33sot/XmC.Li5.J1STz8wqmU3Gd71WVzLJRcLifBu', NULL, 'student', NULL, 2),
(10, 'lecturer@example.com', b'1', 'Lecturer', 'Lecturer', '$2a$10$gr7QEWmQM61/x/uZWfrx/.ZpCGgumlTeDZiDrlKqLet9Oka8DNz2m', NULL, 'lecturer', NULL, 3),
(11, 'yuenkwan@u.nus.edu', b'1', 'Yuenkwan', 'Chai', '$2a$10$bIQBCw5fNfUhcQvGyoYI1Ok751BCpjABThe/AdxB.vGSekizyGJiy', NULL, 'yuenkwan', NULL, 3),
(12, 'chukmunn@u.nus.edu', b'1', 'Chukmunn', 'Lee', '$2a$10$Fd9gXOi.Z5HC4T4XJ2vrAOFN4uDu4dRDW/J3i0uqBg8uEF8lcAoMW', NULL, 'chukmunn', NULL, 3),
(13, 'fan@u.nus.edu', b'1', 'Fan', 'Liu', '$2a$10$RUkFY7HmsyDWnm/9kQNi5uHwlNcaZoCoFG2XeI1kKRFi4dfc35aSS', NULL, 'fan', NULL, 3),
(14, 'tritin@u.nus.edu', b'1', 'Tritin', 'Nguyen', '$2a$10$eyhThxZn3rnQRgrO/CNbHuua2OajoauXQbIATjY6u1xi05b5SY1Qa', NULL, 'tin', NULL, 3),
(15, 'cherwah@u.nus.edu', b'1', 'Cherwah', 'Tan', '$2a$10$bbFoQmUIRdW9HALvggS.Nu5VwZRt31UuunxHZSySuTQuDtZUdLIWm', NULL, 'cherwah', NULL, 3),
(16, 'esther@u.nus.edu', b'1', 'MengyokeEsther', 'Tan', '$2a$10$Kk9RWjHCjCWQmRCuHHIqEeVF.owLLhNYbvylQYSuSPpcanR5gkwMe', NULL, 'esther', NULL, 3),
(17, 'suria@u.nus.edu', b'1', 'Suria', 'R_Asia', '$2a$10$V1f2vqhSQkvuRn2BbPmD7eOCsvsUpECzdQW3Q4L8dkW1x4PY7Yfv6', NULL, 'suria', NULL, 3),
(18, 'admin@example.com', b'1', 'Admin', 'Admin', '$2a$10$KFQGwsjIKaoiZbTf4sWgbO8Z9KMGoWFzw9Uz0W9Q4JM3rwr.9lrr2', NULL, 'admin', NULL, 4),
(19, 'hweizhong@u.nus.edu', b'1', 'Hweizhong', 'Lim', '$2a$10$OlrPLpfYRN6oGwcpHMF0XeuSru6zE4bRUV8lZu3rRDweOC0ULEPbS', NULL, 'hweizhong', NULL, 4),
(20, 'callie@u.nus.edu', b'1', 'Callie', 'Chin', '$2a$10$5P.XoZqIe70cjrBJDqFdqO59xzoHla2pYojq0LyccvfssVglBHSVW', NULL, 'callie', NULL, 4),
(21, 'elsie@u.nus.edu', b'1', 'Elsie', 'Lim', '$2a$10$6ErwkIK8dYMjizODKWIICeadnC50lqOyDfjsLdpLPfUT0QeDI4d96', NULL, 'elsie', NULL, 4),
(22, 'Pameline@u.nus.edu', b'1', 'Pameline', 'Sin', '$2a$10$rRfIcLWm43nvo5jDDO/Jt.XQQxT3Pp09dFhRUKPa7wIkOVU5DKNoa', NULL, 'pameline', NULL, 4);
COMMIT;



--
-- Dumping data for table `enrollment`
--

INSERT INTO `enrollment` (`enrollment_id`, `create_date`, `grade`, `updated_date`, `course_id`, `user_id`) VALUES
(1, '2022-06-24 08:35:12.000000', NULL, NULL, 3, 6),
(2, '2022-06-24 08:35:12.000000', NULL, NULL, 1, 6),
(3, '2022-06-24 08:35:12.000000', NULL, NULL, 3, 3),
(4, '2022-06-24 08:35:12.000000', NULL, NULL, 6, 3),
(5, '2022-06-24 08:35:12.000000', NULL, NULL, 2, 1),
(6, '2022-06-24 08:35:12.000000', NULL, NULL, 4, 1),
(7, '2022-06-24 08:35:12.000000', NULL, NULL, 6, 1),
(8, '2022-06-24 08:35:12.000000', NULL, NULL, 5, 5),
(9, '2022-06-24 08:35:12.000000', NULL, NULL, 2, 5),
(10, '2022-06-24 08:35:12.000000', NULL, NULL, 6, 5),
(11, '2022-06-24 08:35:12.000000', NULL, NULL, 1, 7),
(12, '2022-06-24 08:35:12.000000', NULL, NULL, 4, 7),
(13, '2022-06-24 08:35:12.000000', NULL, NULL, 5, 4),
(14, '2022-06-24 08:35:12.000000', NULL, NULL, 6, 4),
(15, '2022-06-24 08:35:12.000000', NULL, NULL, 1, 11),
(16, '2022-06-24 08:35:12.000000', NULL, NULL, 6, 11),
(17, '2022-06-24 08:35:12.000000', NULL, NULL, 4, 12),
(18, '2022-06-24 08:35:12.000000', NULL, NULL, 3, 12),
(19, '2022-06-24 08:35:12.000000', NULL, NULL, 2, 13),
(20, '2022-06-24 08:35:12.000000', NULL, NULL, 5, 13),
(21, '2022-06-24 08:35:12.000000', NULL, NULL, 6, 13),
(22, '2022-06-24 08:35:12.000000', NULL, NULL, 3, 15),
(23, '2022-06-24 08:35:12.000000', NULL, NULL, 4, 15),
(24, '2022-06-24 08:35:12.000000', NULL, NULL, 1, 15),
(25, '2022-06-24 08:35:12.000000', NULL, NULL, 1, 16),
(26, '2022-06-24 08:35:12.000000', NULL, NULL, 5, 17),
(27, '2022-06-24 08:35:12.000000', NULL, NULL, 4, 17),
(28, '2022-06-24 08:35:12.000000', NULL, NULL, 2, 17),
(29, '2022-06-24 08:35:12.000000', NULL, NULL, 2, 9),
(30, '2022-06-24 08:35:12.000000', NULL, NULL, 1, 9),
(31, '2022-06-24 08:35:12.000000', NULL, NULL, 6, 9),
(32, '2022-06-24 08:35:12.000000', NULL, NULL, 4, 9),
(33, '2022-06-24 08:35:12.000000', NULL, NULL, 1, 10),
(34, '2022-06-24 08:35:12.000000', NULL, NULL, 2, 10),
(35, '2022-06-24 08:35:12.000000', NULL, NULL, 5, 10);
