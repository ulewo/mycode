# MySQL-Front 5.1  (Build 4.2)

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
# Server version 5.0.51a-community-nt

#
# Source for table attached_file
#

DROP TABLE IF EXISTS `attached_file`;
CREATE TABLE `attached_file` (
  `Id` int(11) NOT NULL auto_increment,
  `articleid` int(11) default NULL,
  `gid` varchar(50) default NULL,
  `fileName` varchar(200) default NULL,
  `fileurl` varchar(200) default NULL,
  `filetype` char(1) default NULL COMMENT 'I:image R rar',
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Dumping data for table attached_file
#

LOCK TABLES `attached_file` WRITE;
/*!40000 ALTER TABLE `attached_file` DISABLE KEYS */;
INSERT INTO `attached_file` VALUES (1,1418,'10020','中文.rar','201306/中文.rar','R');
INSERT INTO `attached_file` VALUES (2,1419,'10020','中文.rar','201306/中文.rar','R');
/*!40000 ALTER TABLE `attached_file` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
