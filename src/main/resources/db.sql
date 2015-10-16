# ************************************************************
# Sequel Pro SQL dump
# Version 4499
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.6.24)
# Database: meli_hackathon_db
# Generation Time: 2015-10-16 21:00:39 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table order
# ------------------------------------------------------------

CREATE TABLE `order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL,
  `status` varchar(40) NOT NULL DEFAULT '',
  `seller_id` bigint(11) NOT NULL,
  `seller_name` varchar(100) NOT NULL DEFAULT '',
  `seller_address` varchar(100) NOT NULL DEFAULT '',
  `seller_nickname` varchar(100) NOT NULL DEFAULT '',
  `seller_email` varchar(100) NOT NULL DEFAULT '',
  `seller_phone` varchar(40) NOT NULL DEFAULT '',
  `receiver_id` bigint(11) NOT NULL,
  `receiver_name` varchar(100) NOT NULL DEFAULT '',
  `receiver_address` varchar(100) NOT NULL DEFAULT '',
  `receiver_nickname` varchar(100) NOT NULL DEFAULT '',
  `receiver_email` varchar(100) NOT NULL,
  `receiver_phone` varchar(40) NOT NULL DEFAULT '',
  `item_title` varchar(100) NOT NULL,
  `item_latitude` double NOT NULL,
  `item_longitude` double NOT NULL,
  `item_quantity` bigint(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table shipper
# ------------------------------------------------------------

CREATE TABLE `shipper` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL,
  `name` varchar(100) NOT NULL DEFAULT '',
  `shipper_type` varchar(50) DEFAULT '',
  `vehicle` varchar(100) NOT NULL DEFAULT '',
  `email` varchar(100) NOT NULL,
  `reputation` int(4) NOT NULL,
  `last_latitude` double DEFAULT NULL,
  `last_longitude` double DEFAULT NULL,
  `token` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(15) NOT NULL DEFAULT '',
  `user_pass` varchar(15) NOT NULL DEFAULT '',
  `name` varchar(30) DEFAULT NULL,
  `surname` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_user_name` (`user_name`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `user_name`, `user_pass`, `name`, `surname`)
VALUES
	(1,'admin','admin','Administrador','Shippers');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(15) NOT NULL DEFAULT '',
  `user_name` varchar(15) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `fk_user_role_user` (`user_name`),
  CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_name`) REFERENCES `user` (`user_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;

INSERT INTO `user_role` (`id`, `role_name`, `user_name`)
VALUES
	(1,'admin','admin');

/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
