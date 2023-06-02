/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.33-log : Database - chemical_lab_item_ms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`chemical_lab_item_ms` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `chemical_lab_item_ms`;

/*Table structure for table `borrow_in_warehouse` */

DROP TABLE IF EXISTS `borrow_in_warehouse`;

CREATE TABLE `borrow_in_warehouse` (
  `borrow_in_warehouse_id` varchar(64) NOT NULL,
  `borrow_in_order_id` varchar(64) DEFAULT NULL,
  `warehouse_id` varchar(64) DEFAULT NULL,
  `get_or_borrow_order_id` varchar(64) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `notes` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`borrow_in_warehouse_id`),
  UNIQUE KEY `borrow_in_order_id` (`borrow_in_order_id`),
  KEY `warehouse_id` (`warehouse_id`),
  KEY `get_or_borrow_order_id` (`get_or_borrow_order_id`),
  CONSTRAINT `borrow_in_warehouse_ibfk_1` FOREIGN KEY (`borrow_in_order_id`) REFERENCES `object_entry` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `borrow_in_warehouse_ibfk_2` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`warehouse_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `borrow_in_warehouse_ibfk_3` FOREIGN KEY (`get_or_borrow_order_id`) REFERENCES `get_or_borrow_requisition` (`get_or_borrow_order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `borrow_in_warehouse` */

LOCK TABLES `borrow_in_warehouse` WRITE;

UNLOCK TABLES;

/*Table structure for table `ex_warehouse` */

DROP TABLE IF EXISTS `ex_warehouse`;

CREATE TABLE `ex_warehouse` (
  `ex_warehouse_id` varchar(64) NOT NULL,
  `ex_order_id` varchar(64) DEFAULT NULL,
  `warehouse_id` varchar(64) DEFAULT NULL,
  `requisition_id` varchar(64) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `notes` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`ex_warehouse_id`),
  UNIQUE KEY `ex_order_id` (`ex_order_id`),
  KEY `warehouse_id` (`warehouse_id`),
  KEY `requisition_id` (`requisition_id`),
  CONSTRAINT `ex_warehouse_ibfk_1` FOREIGN KEY (`ex_order_id`) REFERENCES `object_entry` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ex_warehouse_ibfk_2` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`warehouse_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ex_warehouse_ibfk_3` FOREIGN KEY (`requisition_id`) REFERENCES `get_or_borrow_requisition` (`get_or_borrow_order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ex_warehouse_ibfk_4` FOREIGN KEY (`requisition_id`) REFERENCES `purchase_requisition` (`purchase_order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ex_warehouse` */

LOCK TABLES `ex_warehouse` WRITE;

UNLOCK TABLES;

/*Table structure for table `get_or_borrow_requisition` */

DROP TABLE IF EXISTS `get_or_borrow_requisition`;

CREATE TABLE `get_or_borrow_requisition` (
  `get_or_borrow_requisition_id` varchar(64) NOT NULL,
  `get_or_borrow_order_id` varchar(64) DEFAULT NULL,
  `approval_user_id` varchar(64) DEFAULT NULL,
  `applicant_user_id` varchar(64) DEFAULT NULL,
  `purpose` varchar(300) DEFAULT NULL,
  `requisition_date` datetime DEFAULT NULL,
  `borrow_date` date DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  `approval_opinions` varchar(300) DEFAULT NULL,
  `approval_date` datetime DEFAULT NULL,
  PRIMARY KEY (`get_or_borrow_requisition_id`),
  UNIQUE KEY `get_or_borrow_order_id` (`get_or_borrow_order_id`),
  KEY `approval_user_id` (`approval_user_id`),
  KEY `applicant_user_id` (`applicant_user_id`),
  CONSTRAINT `get_or_borrow_requisition_ibfk_1` FOREIGN KEY (`get_or_borrow_order_id`) REFERENCES `object_entry` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `get_or_borrow_requisition_ibfk_2` FOREIGN KEY (`approval_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `get_or_borrow_requisition_ibfk_3` FOREIGN KEY (`applicant_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `get_or_borrow_requisition` */

LOCK TABLES `get_or_borrow_requisition` WRITE;

UNLOCK TABLES;

/*Table structure for table `identity` */

DROP TABLE IF EXISTS `identity`;

CREATE TABLE `identity` (
  `identity_id` varchar(64) NOT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `identity` int(5) DEFAULT NULL,
  PRIMARY KEY (`identity_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `identity` */

LOCK TABLES `identity` WRITE;

UNLOCK TABLES;

/*Table structure for table `object` */

DROP TABLE IF EXISTS `object`;

CREATE TABLE `object` (
  `object_id` varchar(64) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `specification` varchar(20) DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `classfication` varchar(20) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `experation_time` date DEFAULT NULL,
  `upper_limit` float DEFAULT NULL,
  `lower_limit` float DEFAULT NULL,
  `notes` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `object` */

LOCK TABLES `object` WRITE;

UNLOCK TABLES;

/*Table structure for table `object_entry` */

DROP TABLE IF EXISTS `object_entry`;

CREATE TABLE `object_entry` (
  `object_entry_id` varchar(64) NOT NULL,
  `order_id` varchar(64) DEFAULT NULL,
  `object_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`object_entry_id`),
  UNIQUE KEY `order_id` (`order_id`),
  KEY `object_id` (`object_id`),
  CONSTRAINT `object_id` FOREIGN KEY (`object_id`) REFERENCES `object` (`object_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `object_entry` */

LOCK TABLES `object_entry` WRITE;

UNLOCK TABLES;

/*Table structure for table `other_in_warehouse` */

DROP TABLE IF EXISTS `other_in_warehouse`;

CREATE TABLE `other_in_warehouse` (
  `purchase_in_warehouse_id` varchar(64) NOT NULL,
  `other_in_order_id` varchar(64) DEFAULT NULL,
  `warehouse_id` varchar(64) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `notes` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`purchase_in_warehouse_id`),
  UNIQUE KEY `other_in_order_id` (`other_in_order_id`),
  KEY `warehouse_id` (`warehouse_id`),
  CONSTRAINT `other_in_warehouse_ibfk_1` FOREIGN KEY (`other_in_order_id`) REFERENCES `object_entry` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `other_in_warehouse_ibfk_2` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`warehouse_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `other_in_warehouse` */

LOCK TABLES `other_in_warehouse` WRITE;

UNLOCK TABLES;

/*Table structure for table `purchase_in_warehouse` */

DROP TABLE IF EXISTS `purchase_in_warehouse`;

CREATE TABLE `purchase_in_warehouse` (
  `purchase_in_warehouse_id` varchar(64) NOT NULL,
  `warehouse_id` varchar(64) DEFAULT NULL,
  `purchase_in_order_id` varchar(64) DEFAULT NULL,
  `purchase_order_id` varchar(64) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `notes` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`purchase_in_warehouse_id`),
  UNIQUE KEY `purchase_in_order_id` (`purchase_in_order_id`),
  KEY `warehouse_id` (`warehouse_id`),
  KEY `purchase_order_id` (`purchase_order_id`),
  CONSTRAINT `purchase_in_warehouse_ibfk_1` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`warehouse_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchase_in_warehouse_ibfk_2` FOREIGN KEY (`purchase_in_order_id`) REFERENCES `object_entry` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchase_in_warehouse_ibfk_3` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchase_requisition` (`purchase_order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `purchase_in_warehouse` */

LOCK TABLES `purchase_in_warehouse` WRITE;

UNLOCK TABLES;

/*Table structure for table `purchase_requisition` */

DROP TABLE IF EXISTS `purchase_requisition`;

CREATE TABLE `purchase_requisition` (
  `purchase_requisition_id` varchar(64) NOT NULL,
  `purchase_order_id` varchar(64) NOT NULL,
  `approval_user_id` varchar(64) DEFAULT NULL,
  `requisition_user_id` varchar(64) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `purpose` varchar(300) DEFAULT NULL,
  `requisition_date` datetime DEFAULT NULL,
  `approval_opinions` varchar(300) DEFAULT NULL,
  `approval_date` datetime DEFAULT NULL,
  PRIMARY KEY (`purchase_requisition_id`),
  UNIQUE KEY `order_id` (`purchase_order_id`),
  KEY `approval_user_id` (`approval_user_id`),
  KEY `requisition_user_id` (`requisition_user_id`),
  CONSTRAINT `purchase_requisition_ibfk_1` FOREIGN KEY (`approval_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchase_requisition_ibfk_2` FOREIGN KEY (`purchase_order_id`) REFERENCES `object_entry` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchase_requisition_ibfk_3` FOREIGN KEY (`requisition_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `purchase_requisition` */

LOCK TABLES `purchase_requisition` WRITE;

UNLOCK TABLES;

/*Table structure for table `reminder` */

DROP TABLE IF EXISTS `reminder`;

CREATE TABLE `reminder` (
  `id` varchar(64) NOT NULL,
  `get_or_borrow_requisition_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `get_or_borrow_requisition_id` (`get_or_borrow_requisition_id`),
  CONSTRAINT `reminder_ibfk_1` FOREIGN KEY (`get_or_borrow_requisition_id`) REFERENCES `get_or_borrow_requisition` (`get_or_borrow_requisition_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `reminder` */

LOCK TABLES `reminder` WRITE;

UNLOCK TABLES;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` varchar(64) NOT NULL,
  `account` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `pohnenumber` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `user` */

LOCK TABLES `user` WRITE;

UNLOCK TABLES;

/*Table structure for table `warehouse` */

DROP TABLE IF EXISTS `warehouse`;

CREATE TABLE `warehouse` (
  `warehouse_id` varchar(64) NOT NULL,
  `warehouse_keeper_user_id` varchar(64) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  PRIMARY KEY (`warehouse_id`),
  KEY `warehouse_keeper_user_id` (`warehouse_keeper_user_id`),
  CONSTRAINT `warehouse_keeper_user_id` FOREIGN KEY (`warehouse_keeper_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `warehouse` */

LOCK TABLES `warehouse` WRITE;

UNLOCK TABLES;

/*Table structure for table `waste_requisition` */

DROP TABLE IF EXISTS `waste_requisition`;

CREATE TABLE `waste_requisition` (
  `waste_requisition_id` varchar(64) NOT NULL,
  `waste_order_id` varchar(64) DEFAULT NULL,
  `requisition_user_id` varchar(64) DEFAULT NULL,
  `approval_user_id` varchar(64) DEFAULT NULL,
  `waste_user_id` varchar(64) DEFAULT NULL,
  `waste_reason` varchar(300) DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `requisition_date` datetime DEFAULT NULL,
  `approval_opinions` varchar(300) DEFAULT NULL,
  `approval_date` datetime DEFAULT NULL,
  PRIMARY KEY (`waste_requisition_id`),
  UNIQUE KEY `waste_order_id` (`waste_order_id`),
  KEY `requisition_user_id` (`requisition_user_id`),
  KEY `approval_user_id` (`approval_user_id`),
  KEY `waste_user_id` (`waste_user_id`),
  CONSTRAINT `waste_requisition_ibfk_1` FOREIGN KEY (`waste_order_id`) REFERENCES `object_entry` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `waste_requisition_ibfk_2` FOREIGN KEY (`requisition_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `waste_requisition_ibfk_3` FOREIGN KEY (`approval_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `waste_requisition_ibfk_4` FOREIGN KEY (`waste_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `waste_requisition` */

LOCK TABLES `waste_requisition` WRITE;

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
