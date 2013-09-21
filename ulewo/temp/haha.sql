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
  `post_time` timestamp NULL default NULL,
  `source_from` char(1) default NULL,
  `sourceid` varchar(50) default NULL,
  `source_time` datetime default NULL,
  `uid` varchar(100) default NULL,
  `username` varchar(255) default NULL,
  `avatar` varchar(255) default NULL,
  `status` varchar(1) default NULL,
  `img_url` varchar(500) default NULL,
  `video_url` varchar(500) default NULL,
  `medio_type` char(1) default NULL,
  `tag` varchar(500) default NULL,
  `up` int(11) default NULL,
  `down` int(11) default NULL,
  `source_id` varchar(255) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table article
#

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table rearticle
#

DROP TABLE IF EXISTS `rearticle`;
CREATE TABLE `rearticle` (
  `Id` int(11) NOT NULL auto_increment,
  `articleid` varchar(255) default NULL,
  `content` text,
  `uid` varchar(100) default NULL,
  `username` varchar(255) default NULL,
  `retime` datetime default NULL,
  `avatar` varchar(255) default NULL,
  PRIMARY KEY  (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table rearticle
#

LOCK TABLES `rearticle` WRITE;
/*!40000 ALTER TABLE `rearticle` DISABLE KEYS */;
/*!40000 ALTER TABLE `rearticle` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table user
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` varchar(100) default NULL,
  `username` varchar(255) default NULL,
  `password` varchar(255) default NULL,
  `avatar` varchar(255) default NULL,
  `email` varchar(255) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table user
#

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('10000','ulewo','E10ADC3949BA59ABBE56E057F20F883E','images/default.gif','123@126.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
