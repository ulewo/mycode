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
# Source for table blog_article
#

DROP TABLE IF EXISTS `blog_article`;
CREATE TABLE `blog_article` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(50) DEFAULT NULL,
  `itemid` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `summary` longtext,
  `content` longtext,
  `readcount` int(11) DEFAULT '0',
  `posttime` datetime DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `allowreplay` int(2) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

#
# Dumping data for table blog_article
#
LOCK TABLES `blog_article` WRITE;
/*!40000 ALTER TABLE `blog_article` DISABLE KEYS */;

INSERT INTO `blog_article` VALUES (1,'10001',1,'1111111111111111','1','1',0,NULL,'1',1);
INSERT INTO `blog_article` VALUES (2,'10001',0,'111111','QWE QWER QWER','&nbsp;QWE QWER QWER',NULL,'2013-01-04 17:27:04','WE',0);
INSERT INTO `blog_article` VALUES (3,'10001',1,'爱的阿道夫','阿斯蒂芬阿斯蒂芬阿斯蒂芬阿斯蒂芬阿斯蒂芬、爱的色放阿道夫爱的方式阿斯蒂芬','&nbsp;阿斯蒂芬阿斯蒂芬阿斯蒂芬阿斯蒂芬阿斯蒂芬、<br />\r\n爱的色放阿道夫<br />\r\n爱的方式阿斯蒂芬',NULL,'2013-01-04 17:39:02','啊斯蒂芬',0);
INSERT INTO `blog_article` VALUES (4,'10001',0,'11','1111','1111',0,'2013-01-04 17:40:29','',0);
/*!40000 ALTER TABLE `blog_article` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
