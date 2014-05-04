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
# Source for table user_friend
#

DROP TABLE IF EXISTS `user_friend`;
CREATE TABLE `user_friend` (
  `userid` varchar(50) NOT NULL default '',
  `friendid` varchar(59) NOT NULL default '',
  `type` varchar(1) default NULL,
  `createtime` datetime default NULL,
  PRIMARY KEY  (`userid`,`friendid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table user_friend
#

LOCK TABLES `user_friend` WRITE;
/*!40000 ALTER TABLE `user_friend` DISABLE KEYS */;
INSERT INTO `user_friend` VALUES ('10001','10002',NULL,NULL);
INSERT INTO `user_friend` VALUES ('10001','10003',NULL,NULL);
INSERT INTO `user_friend` VALUES ('10001','10004',NULL,NULL);
INSERT INTO `user_friend` VALUES ('10002','10001',NULL,NULL);
INSERT INTO `user_friend` VALUES ('10002','10003',NULL,NULL);
INSERT INTO `user_friend` VALUES ('10005','10001',NULL,NULL);
/*!40000 ALTER TABLE `user_friend` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
