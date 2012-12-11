# MySQL-Front 5.1  (Build 4.2)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: localhost    Database: haha
# ------------------------------------------------------
# Server version 5.0.51a-community-nt

#
# Source for table article
#

DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `Id` int(11) NOT NULL auto_increment,
  `content` text,
  `source_from` varchar(1) default NULL,
  `source_time` varchar(11) default NULL,
  `uid` varchar(50) default NULL,
  `user_name` varchar(50) default NULL,
  `status` varchar(1) default NULL,
  `img_url` varchar(100) default NULL,
  `up` int(11) default NULL,
  `down` int(11) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Dumping data for table article
#

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'123456',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL);
INSERT INTO `article` VALUES (2,'一个老头慢慢地沿街边走着，看到一个小孩踮着脚想按一个门铃，但还是差了一点。于是老头走过去和蔼地说：“小朋友，我来帮你按吧。”说着，老头就按响了门铃，直到确信里面的人能听见了才放开了手。这时，小孩急切地对老头说：“咱们赶快逃吧，快！”?',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
