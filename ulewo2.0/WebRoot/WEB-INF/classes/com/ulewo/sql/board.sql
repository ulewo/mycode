# MySQL-Front 5.1  (Build 3.57)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: localhost    Database: board
# ------------------------------------------------------
# Server version 5.5.24

#
# Source for table board
#

DROP TABLE IF EXISTS `board`;
CREATE TABLE `board` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(50) DEFAULT NULL,
  `message` varchar(500) DEFAULT NULL,
  `posttime` date DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

#
# Dumping data for table board
#
LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;

INSERT INTO `board` VALUES (6,'阿萨德','阿斯蒂芬','2013-03-15');
INSERT INTO `board` VALUES (7,'adf','asdf','2013-03-15');
INSERT INTO `board` VALUES (8,'adf','asdf','2013-03-15');
INSERT INTO `board` VALUES (9,'dd','dd','2013-03-15');
INSERT INTO `board` VALUES (10,'dd','dd','2013-03-15');
INSERT INTO `board` VALUES (11,'sdf','sdf','2013-03-15');
INSERT INTO `board` VALUES (12,'2323','23','2013-03-15');
INSERT INTO `board` VALUES (13,'斯蒂芬','23阿斯蒂芬','2013-03-15');
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table member
#

DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `usericon` varchar(50) DEFAULT NULL COMMENT '用户头像',
  `username` varchar(50) DEFAULT NULL,
  `age` int(2) DEFAULT NULL,
  `sex` varchar(1) DEFAULT NULL COMMENT 'M:男  F:女',
  `characters` varchar(250) DEFAULT NULL COMMENT '个性签名',
  `registertime` datetime DEFAULT NULL COMMENT '注册时间',
  `address` varchar(150) DEFAULT NULL COMMENT '住址',
  `work` varchar(50) DEFAULT NULL COMMENT '工作',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table member
#
LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;

/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
