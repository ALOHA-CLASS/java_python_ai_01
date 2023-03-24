-- 권한
CREATE TABLE `user_auth_test` (
  `user_id` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `auth` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `auth_no` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`auth_no`)
);