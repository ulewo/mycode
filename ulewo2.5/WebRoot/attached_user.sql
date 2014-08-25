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
# Source for table attached_user
#

DROP TABLE IF EXISTS `attached_user`;
CREATE TABLE `attached_user` (
  `attachedid` int(11) NOT NULL DEFAULT '0',
  `userid` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`attachedid`,`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table attached_user
#
LOCK TABLES `attached_user` WRITE;
/*!40000 ALTER TABLE `attached_user` DISABLE KEYS */;

/*!40000 ALTER TABLE `attached_user` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
