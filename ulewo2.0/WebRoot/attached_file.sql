# MySQL-Front 5.1  (Build 3.57)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: localhost    Database: u8china
# ------------------------------------------------------
# Server version 5.5.24

#
# Source for table attached_file
#

DROP TABLE IF EXISTS `attached_file`;
CREATE TABLE `attached_file` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `articleid` int(11) DEFAULT NULL,
  `gid` varchar(50) DEFAULT NULL,
  `filename` varchar(200) DEFAULT NULL,
  `fileurl` varchar(200) DEFAULT NULL,
  `filetype` char(1) DEFAULT NULL COMMENT 'I:image R rar',
  `mark` int(11) DEFAULT NULL,
  `dcount` int(11) DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table attached_file
#
LOCK TABLES `attached_file` WRITE;
/*!40000 ALTER TABLE `attached_file` DISABLE KEYS */;

/*!40000 ALTER TABLE `attached_file` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
