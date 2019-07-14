-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: cometa
-- ------------------------------------------------------
-- Server version	5.7.25-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(200) NOT NULL,
  `owner_user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_application_user_idx` (`owner_user_id`),
  CONSTRAINT `fk_application_user` FOREIGN KEY (`owner_user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (1,'test','ss',1),(8,'qqq','test_q1q',1),(9,'test2','s',1);
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `stereotype_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `glossary` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_area_application1_idx` (`application_id`),
  KEY `fk_area_version1_idx` (`version_id`),
  KEY `fk_area_stereotype1_idx` (`stereotype_id`),
  CONSTRAINT `fk_area_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_area_stereotype1` FOREIGN KEY (`stereotype_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_area_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
INSERT INTO `area` VALUES (1,1,2,7,'ttb','ttt',NULL,NULL),(3,1,2,7,'qqq','qqq',NULL,NULL),(5,1,2,7,'q','q',NULL,NULL),(6,1,2,7,'q','q',NULL,NULL),(7,1,2,7,'qq','qq',NULL,NULL),(9,1,2,7,'www','ww2_',NULL,NULL),(11,9,8,21,'Area1','Area1',NULL,NULL),(12,8,9,33,'Area','area',NULL,NULL);
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assembly`
--

DROP TABLE IF EXISTS `assembly`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assembly` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_assembly_application1_idx` (`application_id`),
  CONSTRAINT `fk_assembly_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assembly`
--

LOCK TABLES `assembly` WRITE;
/*!40000 ALTER TABLE `assembly` DISABLE KEYS */;
INSERT INTO `assembly` VALUES (1,8,'ss','ss',NULL),(2,8,'www','wwww',NULL);
/*!40000 ALTER TABLE `assembly` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assembly_has_version`
--

DROP TABLE IF EXISTS `assembly_has_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assembly_has_version` (
  `assembly_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  PRIMARY KEY (`assembly_id`,`version_id`),
  KEY `fk_assembly_has_version_version1_idx` (`version_id`),
  KEY `fk_assembly_has_version_assembly1_idx` (`assembly_id`),
  CONSTRAINT `fk_assembly_has_version_assembly1` FOREIGN KEY (`assembly_id`) REFERENCES `assembly` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_assembly_has_version_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assembly_has_version`
--

LOCK TABLES `assembly_has_version` WRITE;
/*!40000 ALTER TABLE `assembly_has_version` DISABLE KEYS */;
INSERT INTO `assembly_has_version` VALUES (1,7),(1,9),(2,9);
/*!40000 ALTER TABLE `assembly_has_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute`
--

DROP TABLE IF EXISTS `attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `stereotype_id` int(11) NOT NULL,
  `structure_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `glossary` varchar(2000) DEFAULT NULL,
  `element_id` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  `is_nullable` int(11) NOT NULL,
  `is_reference` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_attribute_element1_idx` (`element_id`),
  KEY `fk_attribute_structure_idx` (`structure_id`),
  KEY `fk_attribute_application1_idx` (`application_id`),
  KEY `fk_attribute_version1_idx` (`version_id`),
  KEY `fk_attribute_stereotype1_idx` (`stereotype_id`),
  KEY `fk_attribute_attribute1_idx` (`parent_id`),
  CONSTRAINT `fk_attribute_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_attribute_attribute1` FOREIGN KEY (`parent_id`) REFERENCES `attribute` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_attribute_element1` FOREIGN KEY (`element_id`) REFERENCES `element` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_attribute_stereotype1` FOREIGN KEY (`stereotype_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_attribute_structure1` FOREIGN KEY (`structure_id`) REFERENCES `structure` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_attribute_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute`
--

LOCK TABLES `attribute` WRITE;
/*!40000 ALTER TABLE `attribute` DISABLE KEYS */;
INSERT INTO `attribute` VALUES (2,1,2,9,2,'sss','sss',NULL,NULL,2,1,0,0,NULL),(3,1,2,9,3,'sss','ss',NULL,NULL,2,1,0,0,NULL),(4,1,2,9,3,'w','w',NULL,NULL,2,2,0,0,NULL),(5,1,2,9,3,'f','f',NULL,NULL,2,0,0,0,NULL),(6,1,2,9,4,'qqqpk','qqqpk',NULL,NULL,2,0,1,0,NULL),(10,1,2,9,7,'wwwww','wwwwww',NULL,NULL,3,0,0,1,NULL),(11,1,2,9,8,'rrr','rrr',NULL,NULL,6,0,1,0,NULL),(12,1,2,9,8,'ddd','dd',NULL,NULL,3,1,0,1,NULL),(13,1,2,9,9,'ccc','ccc',NULL,NULL,3,0,0,1,NULL),(14,1,2,9,13,'q','q',NULL,NULL,2,0,0,0,NULL),(140,9,8,22,90,'DealID','DealID',NULL,NULL,16,0,1,0,NULL),(144,9,8,22,94,'DealID','DealID',NULL,NULL,16,0,0,0,NULL),(146,9,8,22,96,'id','id',NULL,NULL,17,0,0,0,NULL),(147,9,8,22,96,'name','name',NULL,NULL,18,1,0,0,NULL),(148,9,8,22,96,'ref','ref',NULL,NULL,19,2,0,0,NULL),(149,9,8,22,97,'id','id',NULL,NULL,17,0,0,0,146),(150,9,8,22,98,'name','name',NULL,NULL,18,1,0,0,147),(151,9,8,22,98,'ref','ref',NULL,NULL,19,2,0,0,148),(152,9,8,22,99,'DealID','DealID',NULL,NULL,16,0,0,0,144),(153,8,9,34,100,'идентификатор','id',NULL,NULL,20,0,0,0,NULL),(154,8,9,34,100,'Имя','name',NULL,NULL,21,1,0,0,NULL),(155,8,9,34,100,'Фамилия','lastname',NULL,NULL,22,2,0,0,NULL),(156,8,9,34,101,'идентификатор','id',NULL,NULL,20,0,0,0,153),(157,8,9,34,102,'Имя','name',NULL,NULL,21,1,0,0,154),(158,8,9,34,102,'Фамилия','lastname',NULL,NULL,22,2,0,0,155),(159,8,9,34,104,'идентификатор машины','carid',NULL,NULL,23,0,0,0,NULL),(160,8,9,34,104,'владелец','homoid',NULL,NULL,24,1,0,0,NULL),(161,8,9,34,105,'идентификатор машины','carid',NULL,NULL,23,1,0,0,159);
/*!40000 ALTER TABLE `attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute_has_tag`
--

DROP TABLE IF EXISTS `attribute_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attribute_has_tag` (
  `attribute_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`attribute_id`,`tag_id`),
  KEY `fk_attribute_has_tag_tag1_idx` (`tag_id`),
  KEY `fk_attribute_has_tag_attribute1_idx` (`attribute_id`),
  CONSTRAINT `fk_attribute_has_tag_attribute1` FOREIGN KEY (`attribute_id`) REFERENCES `attribute` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_attribute_has_tag_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute_has_tag`
--

LOCK TABLES `attribute_has_tag` WRITE;
/*!40000 ALTER TABLE `attribute_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `attribute_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `build`
--

DROP TABLE IF EXISTS `build`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `build` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `assembly_id` int(11) NOT NULL,
  `on_time` datetime(6) NOT NULL,
  `number` int(11) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `path` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_build_assembly1_idx` (`assembly_id`),
  KEY `fk_build_application1_idx` (`application_id`),
  CONSTRAINT `fk_build_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_build_assembly1` FOREIGN KEY (`assembly_id`) REFERENCES `assembly` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `build`
--

LOCK TABLES `build` WRITE;
/*!40000 ALTER TABLE `build` DISABLE KEYS */;
INSERT INTO `build` VALUES (19,8,1,'2019-07-13 21:39:54.264000',13,NULL,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_13'),(20,8,1,'2019-07-14 07:20:35.166000',14,NULL,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_14'),(21,8,1,'2019-07-14 08:13:21.384000',15,NULL,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_15'),(24,8,1,'2019-07-14 12:46:55.058000',3,NULL,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_3');
/*!40000 ALTER TABLE `build` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `build_log`
--

DROP TABLE IF EXISTS `build_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `build_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `build_id` int(11) NOT NULL,
  `generator_id` int(11) DEFAULT NULL,
  `path` varchar(2000) NOT NULL,
  `file` varchar(200) NOT NULL,
  `is_directory` int(11) NOT NULL,
  `metaobject` varchar(45) NOT NULL,
  `object_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_build_log_application1_idx` (`application_id`),
  KEY `fk_build_log_build1_idx` (`build_id`),
  KEY `fk_build_log_generator1_idx` (`generator_id`),
  CONSTRAINT `fk_build_log_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_build_log_build1` FOREIGN KEY (`build_id`) REFERENCES `build` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_build_log_generator1` FOREIGN KEY (`generator_id`) REFERENCES `generator` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `build_log`
--

LOCK TABLES `build_log` WRITE;
/*!40000 ALTER TABLE `build_log` DISABLE KEYS */;
INSERT INTO `build_log` VALUES (1,8,20,7,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_14\\core\\dbtesthome','dbtesthome',0,'AppEntity',21),(2,8,20,7,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_14\\core\\dbtestcar','dbtestcar',0,'AppEntity',22),(3,8,20,NULL,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_14\\core','core',1,'Package',10),(4,8,20,NULL,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_14\\test','test',1,'Package',9),(5,8,21,7,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_15\\core\\dbtesthome','dbtesthome',0,'AppEntity',21),(6,8,21,7,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_15\\core\\dbtestcar','dbtestcar',0,'AppEntity',22),(7,8,21,NULL,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_15\\core','core',1,'Package',10),(8,8,21,NULL,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_15\\test','test',1,'Package',9),(9,8,24,7,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_3\\core\\home_21.txt','home_21.txt',0,'AppEntity',21),(10,8,24,7,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_3\\core\\car_22.txt','car_22.txt',0,'AppEntity',22),(11,8,24,8,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_3\\core\\home_21.sql','home_21.sql',0,'AppEntity',21),(12,8,24,8,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_3\\core\\car_22.sql','car_22.sql',0,'AppEntity',22),(13,8,24,NULL,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_3\\core\\package_child','package_child',1,'Package',11),(14,8,24,NULL,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_3\\core','core',1,'Package',10),(15,8,24,NULL,'C:\\Projects\\workspace\\co-meta\\out\\app_test_q1q\\assembly_ss\\build_3\\test','test',1,'Package',9);
/*!40000 ALTER TABLE `build_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `component`
--

DROP TABLE IF EXISTS `component`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `component` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `platform_id` int(11) NOT NULL,
  `package_id` int(11) NOT NULL,
  `metatype_code` varchar(45) NOT NULL,
  `file_name_template` varchar(2000) NOT NULL DEFAULT 'object.sysname',
  `filter_method` varchar(15000) NOT NULL DEFAULT 'true',
  PRIMARY KEY (`id`),
  KEY `fk_component_application1_idx` (`application_id`),
  KEY `fk_component_version1_idx` (`version_id`),
  KEY `fk_component_platform1_idx` (`platform_id`),
  KEY `fk_component_package1_idx` (`package_id`),
  KEY `fk_component_metatype1_idx` (`metatype_code`),
  CONSTRAINT `fk_component_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_component_metatype1` FOREIGN KEY (`metatype_code`) REFERENCES `metatype` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_component_package1` FOREIGN KEY (`package_id`) REFERENCES `package` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_component_platform1` FOREIGN KEY (`platform_id`) REFERENCES `platform` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_conponent_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `component`
--

LOCK TABLES `component` WRITE;
/*!40000 ALTER TABLE `component` DISABLE KEYS */;
INSERT INTO `component` VALUES (3,1,3,'sqsq','sqsq',NULL,2,1,'','entity.sysname','true'),(4,9,8,'table','table',NULL,3,8,'','entity.sysname','true'),(5,8,7,'qwer','qwer',NULL,5,9,'entity','entity.sysname','true'),(6,8,9,'dbtest','dbtest',NULL,4,10,'entity','entity.sysname + \"_\" + entity.id','true');
/*!40000 ALTER TABLE `component` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consumer`
--

DROP TABLE IF EXISTS `consumer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consumer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `stereotype_id` int(11) NOT NULL,
  `stream_id` int(11) NOT NULL,
  `process_function_id` int(11) NOT NULL,
  `output_structure_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_consumer_data_stream1_idx` (`stream_id`),
  KEY `fk_consumer_application1_idx` (`application_id`),
  KEY `fk_consumer_function1_idx` (`process_function_id`),
  KEY `fk_consumer_structure1_idx` (`output_structure_id`),
  KEY `fk_consumer_version1_idx` (`version_id`),
  KEY `fk_consumer_stereotype1_idx` (`stereotype_id`),
  CONSTRAINT `fk_consumer_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_consumer_function1` FOREIGN KEY (`process_function_id`) REFERENCES `function` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_consumer_stereotype1` FOREIGN KEY (`stereotype_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_consumer_stream1` FOREIGN KEY (`stream_id`) REFERENCES `stream` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_consumer_structure1` FOREIGN KEY (`output_structure_id`) REFERENCES `structure` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_consumer_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consumer`
--

LOCK TABLES `consumer` WRITE;
/*!40000 ALTER TABLE `consumer` DISABLE KEYS */;
/*!40000 ALTER TABLE `consumer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dependency`
--

DROP TABLE IF EXISTS `dependency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dependency` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `influencer_version_id` int(11) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `dependent_version_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_dependency_application1_idx` (`application_id`),
  KEY `fk_dependency_version1_idx` (`influencer_version_id`),
  KEY `fk_dependency_version2_idx` (`dependent_version_id`),
  CONSTRAINT `fk_dependency_application1100` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_dependency_version1` FOREIGN KEY (`influencer_version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_dependency_version2` FOREIGN KEY (`dependent_version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dependency`
--

LOCK TABLES `dependency` WRITE;
/*!40000 ALTER TABLE `dependency` DISABLE KEYS */;
INSERT INTO `dependency` VALUES (1,1,3,NULL,2),(3,1,5,NULL,3),(4,8,9,NULL,7);
/*!40000 ALTER TABLE `dependency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `element`
--

DROP TABLE IF EXISTS `element`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `element` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `stereotype_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `glossary` varchar(2000) DEFAULT NULL,
  `type_id` int(11) NOT NULL,
  `size` int(11) DEFAULT NULL,
  `accuracy` int(11) DEFAULT NULL,
  `reference_entity_id` int(11) DEFAULT NULL,
  `structure_id` int(11) DEFAULT NULL,
  `key_element_id` int(11) DEFAULT NULL,
  `value_element_id` int(11) DEFAULT NULL,
  `area_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_element_type1_idx` (`type_id`),
  KEY `fk_element_application1_idx` (`application_id`),
  KEY `fk_element_entity1_idx` (`reference_entity_id`),
  KEY `fk_element_structure1_idx` (`structure_id`),
  KEY `fk_element_element1_idx` (`value_element_id`),
  KEY `fk_element_version1_idx` (`version_id`),
  KEY `fk_element_stereotype1_idx` (`stereotype_id`),
  KEY `fk_element_element2_idx` (`key_element_id`),
  KEY `fk_element_area1_idx` (`area_id`),
  CONSTRAINT `fk_element_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_element_area1` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_element_element1` FOREIGN KEY (`value_element_id`) REFERENCES `element` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_element_element2` FOREIGN KEY (`key_element_id`) REFERENCES `element` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_element_entity1` FOREIGN KEY (`reference_entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_element_stereotype1` FOREIGN KEY (`stereotype_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_element_structure1` FOREIGN KEY (`structure_id`) REFERENCES `structure` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_element_type1` FOREIGN KEY (`type_id`) REFERENCES `element_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_element_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `element`
--

LOCK TABLES `element` WRITE;
/*!40000 ALTER TABLE `element` DISABLE KEYS */;
INSERT INTO `element` VALUES (2,1,2,3,'hhh','hhh','hhh',NULL,8,NULL,NULL,NULL,6,NULL,NULL,1),(3,1,2,6,'reference','reference',NULL,NULL,7,NULL,NULL,1,NULL,NULL,NULL,9),(4,1,2,6,'dnjkm','dnkmf',NULL,NULL,8,NULL,NULL,NULL,4,NULL,NULL,9),(5,1,2,6,'vvvv','vvvvv',NULL,NULL,9,NULL,NULL,NULL,NULL,NULL,4,5),(6,1,2,6,'bbbb','bbbb',NULL,NULL,10,NULL,NULL,NULL,NULL,2,3,5),(16,9,8,23,'DealID','DealID',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,11),(17,9,8,23,'id','id',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,11),(18,9,8,23,'name','name',NULL,NULL,4,45,NULL,NULL,NULL,NULL,NULL,11),(19,9,8,23,'ref','ref',NULL,NULL,7,NULL,NULL,19,NULL,NULL,NULL,11),(20,8,9,35,'идентификатор','id',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,12),(21,8,9,35,'Имя','name',NULL,NULL,4,50,NULL,NULL,NULL,NULL,NULL,12),(22,8,9,35,'Фамилия','lastname',NULL,NULL,4,50,NULL,NULL,NULL,NULL,NULL,12),(23,8,9,35,'идентификатор машины','carid',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,12),(24,8,9,35,'владелец','homoid',NULL,NULL,7,NULL,NULL,21,NULL,NULL,NULL,12);
/*!40000 ALTER TABLE `element` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `element_has_tag`
--

DROP TABLE IF EXISTS `element_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `element_has_tag` (
  `element_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`element_id`,`tag_id`),
  KEY `fk_element_has_tag_tag1_idx` (`tag_id`),
  KEY `fk_element_has_tag_element1_idx` (`element_id`),
  CONSTRAINT `fk_element_has_tag_element1` FOREIGN KEY (`element_id`) REFERENCES `element` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_element_has_tag_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `element_has_tag`
--

LOCK TABLES `element_has_tag` WRITE;
/*!40000 ALTER TABLE `element_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `element_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `element_type`
--

DROP TABLE IF EXISTS `element_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `element_type` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `element_type`
--

LOCK TABLES `element_type` WRITE;
/*!40000 ALTER TABLE `element_type` DISABLE KEYS */;
INSERT INTO `element_type` VALUES (1,'integer'),(2,'float'),(3,'boolean'),(4,'string'),(5,'date'),(6,'datetime'),(7,'reference'),(8,'structure'),(9,'collection'),(10,'map'),(11,'numeric');
/*!40000 ALTER TABLE `element_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entity`
--

DROP TABLE IF EXISTS `entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `stereotype_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `glossary` varchar(2000) DEFAULT NULL,
  `structure_id` int(11) NOT NULL,
  `pk_attribute_id` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `area_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_entity_attribute1_idx` (`pk_attribute_id`),
  KEY `fk_entity_structure1_idx` (`structure_id`),
  KEY `fk_entity_application1_idx` (`application_id`),
  KEY `fk_entity_version1_idx` (`version_id`),
  KEY `fk_entity_entity1_idx` (`parent_id`),
  KEY `fk_entity_stereotype1_idx` (`stereotype_id`),
  KEY `fk_entity_area1_idx` (`area_id`),
  CONSTRAINT `fk_entity_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_entity_area1` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_entity_attribute1` FOREIGN KEY (`pk_attribute_id`) REFERENCES `attribute` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_entity_entity1` FOREIGN KEY (`parent_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_entity_stereotype1` FOREIGN KEY (`stereotype_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_entity_structure1` FOREIGN KEY (`structure_id`) REFERENCES `structure` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_entity_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entity`
--

LOCK TABLES `entity` WRITE;
/*!40000 ALTER TABLE `entity` DISABLE KEYS */;
INSERT INTO `entity` VALUES (1,1,2,4,'primary','primary',NULL,NULL,5,NULL,NULL,1),(2,1,2,4,'222','rrr2',NULL,NULL,6,NULL,NULL,9),(3,1,2,4,'qewr','qwer',NULL,NULL,8,NULL,NULL,9),(4,1,2,4,'cc','cc',NULL,NULL,9,NULL,NULL,7),(19,9,8,24,'d1','d1',NULL,NULL,94,NULL,NULL,11),(20,9,8,24,'d2','d2',NULL,NULL,96,NULL,NULL,11),(21,8,9,36,'Человек','home',NULL,NULL,100,NULL,NULL,12),(22,8,9,36,'Машина','car',NULL,NULL,104,NULL,NULL,12);
/*!40000 ALTER TABLE `entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entity_has_tag`
--

DROP TABLE IF EXISTS `entity_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entity_has_tag` (
  `entity_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`entity_id`,`tag_id`),
  KEY `fk_entity_has_tag_tag1_idx` (`tag_id`),
  KEY `fk_entity_has_tag_entity1_idx` (`entity_id`),
  CONSTRAINT `fk_entity_has_tag_entity1` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_entity_has_tag_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entity_has_tag`
--

LOCK TABLES `entity_has_tag` WRITE;
/*!40000 ALTER TABLE `entity_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `entity_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entity_key`
--

DROP TABLE IF EXISTS `entity_key`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entity_key` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `stereotype_id` int(11) NOT NULL,
  `entity_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `glossary` varchar(2000) DEFAULT NULL,
  `structure_id` int(11) NOT NULL,
  `metatype_code` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_key_application1_idx` (`application_id`),
  KEY `fk_key_version1_idx` (`version_id`),
  KEY `fk_key_stereotype1_idx` (`stereotype_id`),
  KEY `fk_key_entity1_idx` (`entity_id`),
  KEY `fk_key_structure1_idx` (`structure_id`),
  KEY `fk_key_metatype1_idx` (`metatype_code`),
  CONSTRAINT `fk_key_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_key_entity1` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_key_metatype1` FOREIGN KEY (`metatype_code`) REFERENCES `metatype` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_key_stereotype1` FOREIGN KEY (`stereotype_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_key_structure1` FOREIGN KEY (`structure_id`) REFERENCES `structure` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_key_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entity_key`
--

LOCK TABLES `entity_key` WRITE;
/*!40000 ALTER TABLE `entity_key` DISABLE KEYS */;
INSERT INTO `entity_key` VALUES (30,9,8,29,20,'d2 PK','pk_d2_1',NULL,NULL,97,'pk'),(31,9,8,31,20,'d2 UQ','uq_d2_2',NULL,NULL,98,'uq'),(32,9,8,29,19,'d1 PK','pk_d1_1',NULL,NULL,99,'pk'),(33,8,9,41,21,'Человек PK','pk_home_1',NULL,NULL,101,'pk'),(34,8,9,43,21,'Человек UQ','uq_home_2',NULL,NULL,102,'uq'),(35,8,9,41,22,'Машина PK','pk_car_1',NULL,NULL,105,'pk');
/*!40000 ALTER TABLE `entity_key` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `environment`
--

DROP TABLE IF EXISTS `environment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `environment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `environment_type_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_language_application1_idx` (`application_id`),
  KEY `fk_language_version1_idx` (`version_id`),
  KEY `fk_environment_environment_type1_idx` (`environment_type_id`),
  CONSTRAINT `fk_environment_environment_type1` FOREIGN KEY (`environment_type_id`) REFERENCES `environment_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_language_application103` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_language_version101` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `environment`
--

LOCK TABLES `environment` WRITE;
/*!40000 ALTER TABLE `environment` DISABLE KEYS */;
/*!40000 ALTER TABLE `environment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `environment_type`
--

DROP TABLE IF EXISTS `environment_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `environment_type` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `environment_type`
--

LOCK TABLES `environment_type` WRITE;
/*!40000 ALTER TABLE `environment_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `environment_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expression`
--

DROP TABLE IF EXISTS `expression`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expression` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `is_super` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `text` varchar(45) NOT NULL,
  `platform_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_expression_application1_idx` (`application_id`),
  KEY `fk_expression_version1_idx` (`version_id`),
  KEY `fk_expression_platform1_idx` (`platform_id`),
  KEY `fk_expression_category1_idx` (`category_id`),
  CONSTRAINT `fk_expression_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_expression_category1` FOREIGN KEY (`category_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_expression_platform1` FOREIGN KEY (`platform_id`) REFERENCES `platform` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_expression_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expression`
--

LOCK TABLES `expression` WRITE;
/*!40000 ALTER TABLE `expression` DISABLE KEYS */;
/*!40000 ALTER TABLE `expression` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expression_has_tag`
--

DROP TABLE IF EXISTS `expression_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expression_has_tag` (
  `expression_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`expression_id`,`tag_id`),
  KEY `fk_expression_has_tag_tag1_idx` (`tag_id`),
  KEY `fk_expression_has_tag_expression1_idx` (`expression_id`),
  CONSTRAINT `fk_expression_has_tag_expression1` FOREIGN KEY (`expression_id`) REFERENCES `expression` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_expression_has_tag_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expression_has_tag`
--

LOCK TABLES `expression_has_tag` WRITE;
/*!40000 ALTER TABLE `expression_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `expression_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `function`
--

DROP TABLE IF EXISTS `function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `function` (
  `id` int(11) NOT NULL,
  `application_id` int(11) NOT NULL,
  `is_super` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `expression_id` int(11) NOT NULL,
  `result_element_id` int(11) NOT NULL,
  `parameter_structure_id` int(11) NOT NULL,
  `function_type_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_condition_application1_idx` (`application_id`),
  KEY `fk_condition_expression1_idx` (`expression_id`),
  KEY `fk_function_element1_idx` (`result_element_id`),
  KEY `fk_function_structure1_idx` (`parameter_structure_id`),
  KEY `fk_function_function_type1_idx` (`function_type_id`),
  KEY `fk_function_version1_idx` (`version_id`),
  KEY `fk_function_category1_idx` (`category_id`),
  CONSTRAINT `fk_condition_application10` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_condition_expression10` FOREIGN KEY (`expression_id`) REFERENCES `expression` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_function_category1` FOREIGN KEY (`category_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_function_element1` FOREIGN KEY (`result_element_id`) REFERENCES `element` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_function_function_type1` FOREIGN KEY (`function_type_id`) REFERENCES `function_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_function_structure1` FOREIGN KEY (`parameter_structure_id`) REFERENCES `structure` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_function_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `function`
--

LOCK TABLES `function` WRITE;
/*!40000 ALTER TABLE `function` DISABLE KEYS */;
/*!40000 ALTER TABLE `function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `function_has_tag`
--

DROP TABLE IF EXISTS `function_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `function_has_tag` (
  `function_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`function_id`,`tag_id`),
  KEY `fk_function_has_tag_tag1_idx` (`tag_id`),
  KEY `fk_function_has_tag_function1_idx` (`function_id`),
  CONSTRAINT `fk_function_has_tag_function1` FOREIGN KEY (`function_id`) REFERENCES `function` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_function_has_tag_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `function_has_tag`
--

LOCK TABLES `function_has_tag` WRITE;
/*!40000 ALTER TABLE `function_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `function_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `function_type`
--

DROP TABLE IF EXISTS `function_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `function_type` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `function_type`
--

LOCK TABLES `function_type` WRITE;
/*!40000 ALTER TABLE `function_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `function_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `generator`
--

DROP TABLE IF EXISTS `generator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `generator` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `stereotype_id` int(11) NOT NULL,
  `platform_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  `extension` varchar(45) NOT NULL DEFAULT 'txt',
  `encoding` varchar(45) NOT NULL DEFAULT 'UTF-8',
  PRIMARY KEY (`id`),
  KEY `fk_generator_application1_idx` (`application_id`),
  KEY `fk_generator_version1_idx` (`version_id`),
  KEY `fk_generator_stereotype1_idx` (`stereotype_id`),
  KEY `fk_generator_platform1_idx` (`platform_id`),
  KEY `fk_generator_resource1_idx` (`resource_id`),
  CONSTRAINT `fk_generator_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_generator_platform1` FOREIGN KEY (`platform_id`) REFERENCES `platform` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_generator_resource1` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_generator_stereotype1` FOREIGN KEY (`stereotype_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_generator_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `generator`
--

LOCK TABLES `generator` WRITE;
/*!40000 ALTER TABLE `generator` DISABLE KEYS */;
INSERT INTO `generator` VALUES (7,8,9,'asdf','asdf',NULL,36,4,20,'txt','UTF-8'),(8,8,9,'db2','db2',NULL,36,4,21,'sql','UTF-8');
/*!40000 ALTER TABLE `generator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instance`
--

DROP TABLE IF EXISTS `instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `instance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `environment_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `server_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_language_application1_idx` (`application_id`),
  KEY `fk_language_version1_idx` (`version_id`),
  KEY `fk_instance_environment1_idx` (`environment_id`),
  KEY `fk_instance_server1_idx` (`server_id`),
  CONSTRAINT `fk_instance_environment1` FOREIGN KEY (`environment_id`) REFERENCES `environment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_instance_server1` FOREIGN KEY (`server_id`) REFERENCES `component` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_language_application104` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_language_version102` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instance`
--

LOCK TABLES `instance` WRITE;
/*!40000 ALTER TABLE `instance` DISABLE KEYS */;
/*!40000 ALTER TABLE `instance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `stereotype_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `parameter_structure_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_job_structure1_idx` (`parameter_structure_id`),
  KEY `fk_job_version1_idx` (`version_id`),
  KEY `fk_job_application1_idx` (`application_id`),
  KEY `fk_job_stereotype1_idx` (`stereotype_id`),
  CONSTRAINT `fk_job_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_job_stereotype1` FOREIGN KEY (`stereotype_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_job_structure1` FOREIGN KEY (`parameter_structure_id`) REFERENCES `structure` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_job_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_has_tag`
--

DROP TABLE IF EXISTS `job_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job_has_tag` (
  `job_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`job_id`,`tag_id`),
  KEY `fk_job_has_tag_tag1_idx` (`tag_id`),
  KEY `fk_job_has_tag_job1_idx` (`job_id`),
  CONSTRAINT `fk_job_has_tag_job1` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_job_has_tag_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_has_tag`
--

LOCK TABLES `job_has_tag` WRITE;
/*!40000 ALTER TABLE `job_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `job_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listener`
--

DROP TABLE IF EXISTS `listener`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `listener` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `stereotype_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `process_function_id` int(11) NOT NULL,
  `condition_structure_id` int(11) NOT NULL,
  `parameter_structure_id` int(11) NOT NULL,
  `job_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_listener_application1_idx` (`application_id`),
  KEY `fk_listener_function1_idx` (`process_function_id`),
  KEY `fk_listener_structure1_idx` (`condition_structure_id`),
  KEY `fk_listener_structure2_idx` (`parameter_structure_id`),
  KEY `fk_listener_job1_idx` (`job_id`),
  KEY `fk_listener_version1_idx` (`version_id`),
  KEY `fk_listener_stereotype1_idx` (`stereotype_id`),
  CONSTRAINT `fk_listener_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_listener_function1` FOREIGN KEY (`process_function_id`) REFERENCES `function` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_listener_job1` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_listener_stereotype1` FOREIGN KEY (`stereotype_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_listener_structure1` FOREIGN KEY (`condition_structure_id`) REFERENCES `structure` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_listener_structure2` FOREIGN KEY (`parameter_structure_id`) REFERENCES `structure` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_listener_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listener`
--

LOCK TABLES `listener` WRITE;
/*!40000 ALTER TABLE `listener` DISABLE KEYS */;
/*!40000 ALTER TABLE `listener` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listener_has_consumer`
--

DROP TABLE IF EXISTS `listener_has_consumer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `listener_has_consumer` (
  `listener_id` int(11) NOT NULL,
  `consumer_id` int(11) NOT NULL,
  PRIMARY KEY (`listener_id`,`consumer_id`),
  KEY `fk_listener_has_consumer_consumer1_idx` (`consumer_id`),
  KEY `fk_listener_has_consumer_listener1_idx` (`listener_id`),
  CONSTRAINT `fk_listener_has_consumer_consumer1` FOREIGN KEY (`consumer_id`) REFERENCES `consumer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_listener_has_consumer_listener1` FOREIGN KEY (`listener_id`) REFERENCES `listener` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listener_has_consumer`
--

LOCK TABLES `listener_has_consumer` WRITE;
/*!40000 ALTER TABLE `listener_has_consumer` DISABLE KEYS */;
/*!40000 ALTER TABLE `listener_has_consumer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listener_has_tag`
--

DROP TABLE IF EXISTS `listener_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `listener_has_tag` (
  `listener_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`listener_id`,`tag_id`),
  KEY `fk_listener_has_tag_tag1_idx` (`tag_id`),
  KEY `fk_listener_has_tag_listener1_idx` (`listener_id`),
  CONSTRAINT `fk_listener_has_tag_listener1` FOREIGN KEY (`listener_id`) REFERENCES `listener` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_listener_has_tag_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listener_has_tag`
--

LOCK TABLES `listener_has_tag` WRITE;
/*!40000 ALTER TABLE `listener_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `listener_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `major_version`
--

DROP TABLE IF EXISTS `major_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `major_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `module_id` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_major_version_application1_idx` (`application_id`),
  KEY `fk_major_version_module1_idx` (`module_id`),
  CONSTRAINT `fk_language_application1101` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_version_module10` FOREIGN KEY (`module_id`) REFERENCES `module` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major_version`
--

LOCK TABLES `major_version` WRITE;
/*!40000 ALTER TABLE `major_version` DISABLE KEYS */;
INSERT INTO `major_version` VALUES (1,1,1,1,'','OPENED'),(2,1,2,1,'','OPENED'),(4,1,4,1,'','OPENED'),(6,8,6,0,'Default version','OPENED'),(7,9,7,0,'Default version','OPENED'),(8,8,8,0,'Default version','OPENED');
/*!40000 ALTER TABLE `major_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `metatype`
--

DROP TABLE IF EXISTS `metatype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `metatype` (
  `code` varchar(45) NOT NULL,
  `metaobject` varchar(45) NOT NULL,
  `metaobject_name` varchar(45) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `metatype`
--

LOCK TABLES `metatype` WRITE;
/*!40000 ALTER TABLE `metatype` DISABLE KEYS */;
INSERT INTO `metatype` VALUES ('area','Area','Area'),('attribute','Attribute','Attribute'),('element','Element','Element'),('entity','AppEntity','Entity'),('entityStructure','Structure','Structure'),('generator','Generator','Generator'),('index','Key','Key'),('keyStructure','Structure','Structure'),('pack','Package','Package'),('pk','Key','Key'),('simpleStructure','Structure','Structure'),('uq','Key','Key');
/*!40000 ALTER TABLE `metatype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `method`
--

DROP TABLE IF EXISTS `method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `method` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `is_super` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `entity_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `function_id` int(11) NOT NULL,
  `method_type_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_language_application1_idx` (`application_id`),
  KEY `fk_reader_function1_idx` (`function_id`),
  KEY `fk_reader_entity1_idx` (`entity_id`),
  KEY `fk_method_method_type1_idx` (`method_type_id`),
  KEY `fk_method_version1_idx` (`version_id`),
  KEY `fk_method_category1_idx` (`category_id`),
  CONSTRAINT `fk_language_application1012` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_method_category1` FOREIGN KEY (`category_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_method_method_type1` FOREIGN KEY (`method_type_id`) REFERENCES `method_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_method_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_reader_entity11` FOREIGN KEY (`entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_reader_function12` FOREIGN KEY (`function_id`) REFERENCES `function` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `method`
--

LOCK TABLES `method` WRITE;
/*!40000 ALTER TABLE `method` DISABLE KEYS */;
/*!40000 ALTER TABLE `method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `method_has_tag`
--

DROP TABLE IF EXISTS `method_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `method_has_tag` (
  `method_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`method_id`,`tag_id`),
  KEY `fk_method_has_tag_tag1_idx` (`tag_id`),
  KEY `fk_method_has_tag_method1_idx` (`method_id`),
  CONSTRAINT `fk_method_has_tag_method1` FOREIGN KEY (`method_id`) REFERENCES `method` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_method_has_tag_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `method_has_tag`
--

LOCK TABLES `method_has_tag` WRITE;
/*!40000 ALTER TABLE `method_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `method_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `method_type`
--

DROP TABLE IF EXISTS `method_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `method_type` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `method_type`
--

LOCK TABLES `method_type` WRITE;
/*!40000 ALTER TABLE `method_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `method_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `parent_application_id` int(11) DEFAULT NULL,
  `parent_module_id` int(11) DEFAULT NULL,
  `parent_version_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_module_application1_idx` (`application_id`),
  KEY `fk_module_application2_idx` (`parent_application_id`),
  KEY `fk_module_module1_idx` (`parent_module_id`),
  KEY `fk_module_version1_idx` (`parent_version_id`),
  CONSTRAINT `fk_module_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_module_application2` FOREIGN KEY (`parent_application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_module_module1` FOREIGN KEY (`parent_module_id`) REFERENCES `module` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_module_version1` FOREIGN KEY (`parent_version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module`
--

LOCK TABLES `module` WRITE;
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
INSERT INTO `module` VALUES (1,1,'Module1','module1',NULL,NULL,NULL,NULL),(2,1,'Module2','module2',NULL,NULL,NULL,NULL),(4,1,'Module3','module3','wwqq',NULL,NULL,NULL),(6,8,'Module1','Module1',NULL,NULL,NULL,NULL),(7,9,'m1','m1',NULL,NULL,NULL,NULL),(8,8,'t2','t2',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation`
--

DROP TABLE IF EXISTS `operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `stereotype_id` int(11) NOT NULL,
  `job_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `is_starter` int(11) NOT NULL,
  `process_function_id` int(11) NOT NULL,
  `operation_type_id` int(11) NOT NULL,
  `cache_entity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_operation_job1_idx` (`job_id`),
  KEY `fk_operation_function1_idx` (`process_function_id`),
  KEY `fk_operation_operatio_type1_idx` (`operation_type_id`),
  KEY `fk_operation_entity1_idx` (`cache_entity_id`),
  KEY `fk_operation_version1_idx` (`version_id`),
  KEY `fk_operation_application1_idx` (`application_id`),
  KEY `fk_operation_stereotype1_idx` (`stereotype_id`),
  CONSTRAINT `fk_operation_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_operation_entity1` FOREIGN KEY (`cache_entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_operation_function1` FOREIGN KEY (`process_function_id`) REFERENCES `function` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_operation_job1` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_operation_operatio_type1` FOREIGN KEY (`operation_type_id`) REFERENCES `operation_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_operation_stereotype1` FOREIGN KEY (`stereotype_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_operation_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation`
--

LOCK TABLES `operation` WRITE;
/*!40000 ALTER TABLE `operation` DISABLE KEYS */;
/*!40000 ALTER TABLE `operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation_has_tag`
--

DROP TABLE IF EXISTS `operation_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operation_has_tag` (
  `operation_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`operation_id`,`tag_id`),
  KEY `fk_operation_has_tag_tag1_idx` (`tag_id`),
  KEY `fk_operation_has_tag_operation1_idx` (`operation_id`),
  CONSTRAINT `fk_operation_has_tag_operation1` FOREIGN KEY (`operation_id`) REFERENCES `operation` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_operation_has_tag_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation_has_tag`
--

LOCK TABLES `operation_has_tag` WRITE;
/*!40000 ALTER TABLE `operation_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `operation_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation_type`
--

DROP TABLE IF EXISTS `operation_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operation_type` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `description` varchar(20000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation_type`
--

LOCK TABLES `operation_type` WRITE;
/*!40000 ALTER TABLE `operation_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `operation_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `package` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `parent_package_id` int(11) DEFAULT NULL,
  `file_name_template` varchar(2000) NOT NULL DEFAULT 'object.sysname',
  PRIMARY KEY (`id`),
  KEY `fk_package_application1_idx` (`application_id`),
  KEY `fk_package_version1_idx` (`version_id`),
  KEY `fk_package_package1_idx` (`parent_package_id`),
  CONSTRAINT `fk_package_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_package_package1` FOREIGN KEY (`parent_package_id`) REFERENCES `package` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_package_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package`
--

LOCK TABLES `package` WRITE;
/*!40000 ALTER TABLE `package` DISABLE KEYS */;
INSERT INTO `package` VALUES (1,1,3,'p1','p1',NULL,NULL,'pack.sysname'),(2,1,3,'p2','p2',NULL,NULL,'pack.sysname'),(3,1,3,'p11','p11',NULL,1,'pack.sysname'),(4,1,3,'p111','p111',NULL,3,'pack.sysname'),(6,1,3,'p3','p3',NULL,1,'pack.sysname'),(7,1,3,'ww','ww',NULL,6,'pack.sysname'),(8,9,8,'DB','db',NULL,NULL,'pack.sysname'),(9,8,7,'test','test',NULL,NULL,'pack.sysname'),(10,8,9,'Core','core',NULL,NULL,'pack.sysname'),(11,8,9,'child','child',NULL,10,'\"package_\" + pack.sysname');
/*!40000 ALTER TABLE `package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `platform`
--

DROP TABLE IF EXISTS `platform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `platform` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_platform_application1_idx` (`application_id`),
  KEY `fk_platform_version1_idx` (`version_id`),
  CONSTRAINT `fk_platform_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_platform_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `platform`
--

LOCK TABLES `platform` WRITE;
/*!40000 ALTER TABLE `platform` DISABLE KEYS */;
INSERT INTO `platform` VALUES (2,1,3,'ss','ss',NULL),(3,9,8,'MySQL 5.7','mssql_v5_7',NULL),(4,8,9,'DB','db',NULL),(5,8,7,'DB','DB',NULL);
/*!40000 ALTER TABLE `platform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producer`
--

DROP TABLE IF EXISTS `producer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `producer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `stream_id` int(11) NOT NULL,
  `stereotype_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_producer_application1_idx` (`application_id`),
  KEY `fk_producer_stream1_idx` (`stream_id`),
  KEY `fk_producer_version1_idx` (`version_id`),
  KEY `fk_producer_stereotype1_idx` (`stereotype_id`),
  CONSTRAINT `fk_producer_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producer_stereotype1` FOREIGN KEY (`stereotype_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producer_stream1` FOREIGN KEY (`stream_id`) REFERENCES `stream` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_producer_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producer`
--

LOCK TABLES `producer` WRITE;
/*!40000 ALTER TABLE `producer` DISABLE KEYS */;
/*!40000 ALTER TABLE `producer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `format` varchar(200) NOT NULL DEFAULT 'freemarker',
  `encoding` varchar(45) NOT NULL DEFAULT 'UTF-8',
  `owner` varchar(45) NOT NULL DEFAULT 'Generator',
  `text` mediumtext NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_resource_application1_idx` (`application_id`),
  CONSTRAINT `fk_resource_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
INSERT INTO `resource` VALUES (1,1,'freemarker','UTF-8','Generator','sfssdgsfdgs'),(2,8,'freemarker','UTF-8','Generator','select * from ${tableName}!'),(3,8,'freemarker','UTF-8','Generator','select * from ${tableName}!'),(4,8,'freemarker','UTF-8','Generator','select * from ${tableName}!'),(5,8,'freemarker','UTF-8','Generator','select * from ${tableName}!'),(6,8,'freemarker','UTF-8','Generator','select * from ${tableName}!'),(7,8,'freemarker','UTF-8','Generator','select * from ${tableName}!'),(8,8,'freemarker','UTF-8','Generator','select * from ${tableName}!'),(9,8,'freemarker','UTF-8','Generator','select * from ${tableName}!'),(10,8,'freemarker','UTF-8','Generator','select * from ${tableName}!'),(11,8,'freemarker','UTF-8','Generator','select * from ${tableName}!'),(12,8,'freemarker','UTF-8','Generator','select * from ${tableName}!'),(13,8,'freemarker','UTF-8','Generator','select * from ${tableName}!+++'),(14,8,'freemarker','UTF-8','Generator','select * from ${tableName}!+++'),(15,8,'freemarker','UTF-8','Generator','create table ${AppElement.sysname}'),(16,8,'freemarker','UTF-8','Generator','create table ${AppEntity.sysname}'),(17,8,'freemarker','UTF-8','Generator','create table if not exists ${object.sysname}\n(\nid integer \n<#list object.structure.attributes as attribute>\n<#if attribute.element.type.name == \'reference\'>\n, ${attribute.sysname}_id integer\n<#else>\n, ${attribute.sysname}  ${attribute.element.type.name} \n</#if>\n</#list>\n, primary key (id)\n);\n-- проверка кодировки'),(18,8,'freemarker','UTF-8','Generator','create table if not exists ${object.sysname}\r\n(\r\nid integer \r\n<#list object.structure.attributes as attribute>\r\n<#if attribute.element.type.name == \'reference\'>\r\n, ${attribute.sysname}_id integer\r\n<#else>\r\n, ${attribute.sysname}  ${attribute.element.type.name} \r\n</#if>\r\n</#list>\r\n, primary key (id)\r\n);\r\n-- проверка кодировки'),(19,8,'freemarker','cp1251','Generator','create table if not exists ${object.sysname}\r\n(\r\nid integer \r\n<#list object.structure.attributes as attribute>\r\n<#if attribute.element.type.name == \'reference\'>\r\n, ${attribute.sysname}_id integer\r\n<#else>\r\n, ${attribute.sysname}  ${attribute.element.type.name} \r\n</#if>\r\n</#list>\r\n, primary key (id)\r\n);\r\n-- проверка кодировки'),(20,8,'freemarker','UTF-8','Generator','create table if not exists ${entity.sysname}\n(\nid integer \n<#list entity.structure.attributes as attribute>\n<#if attribute.element.type.name == \'reference\'>\n, ${attribute.sysname}_id integer\n<#else>\n, ${attribute.sysname}  ${attribute.element.type.name} \n</#if>\n</#list>\n, primary key (id)\n);\n-- проверка кодировки'),(21,8,'freemarker','UTF-8','Generator','create table if not exists ${entity.sysname}\n(\nid integer \n<#list entity.structure.attributes as attribute>\n<#if attribute.element.type.name == \'reference\'>\n, ${attribute.sysname}_id integer\n<#else>\n, ${attribute.sysname}  ${attribute.element.type.name} \n</#if>\n</#list>\n, primary key (id)\n);\n-- проверка кодировки');
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sender_has_tag`
--

DROP TABLE IF EXISTS `sender_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sender_has_tag` (
  `sender_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`sender_id`,`tag_id`),
  KEY `fk_sender_has_tag_tag1_idx` (`tag_id`),
  KEY `fk_sender_has_tag_sender1_idx` (`sender_id`),
  CONSTRAINT `fk_sender_has_tag_sender1` FOREIGN KEY (`sender_id`) REFERENCES `producer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sender_has_tag_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sender_has_tag`
--

LOCK TABLES `sender_has_tag` WRITE;
/*!40000 ALTER TABLE `sender_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `sender_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sequence`
--

DROP TABLE IF EXISTS `sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sequence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `to_operation_id` int(11) NOT NULL,
  `from_operation_id` int(11) NOT NULL,
  `condition_function_id` int(11) NOT NULL,
  `stereotype_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sequence_operation1_idx` (`to_operation_id`),
  KEY `fk_sequence_function1_idx` (`condition_function_id`),
  KEY `fk_sequence_application1_idx` (`application_id`),
  KEY `fk_sequence_operation2_idx` (`from_operation_id`),
  KEY `fk_sequence_version1_idx` (`version_id`),
  KEY `fk_sequence_stereotype1_idx` (`stereotype_id`),
  CONSTRAINT `fk_sequence_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sequence_function1` FOREIGN KEY (`condition_function_id`) REFERENCES `function` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sequence_operation1` FOREIGN KEY (`to_operation_id`) REFERENCES `operation` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sequence_operation2` FOREIGN KEY (`from_operation_id`) REFERENCES `operation` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sequence_stereotype1` FOREIGN KEY (`stereotype_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sequence_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequence`
--

LOCK TABLES `sequence` WRITE;
/*!40000 ALTER TABLE `sequence` DISABLE KEYS */;
/*!40000 ALTER TABLE `sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sequence_has_tag`
--

DROP TABLE IF EXISTS `sequence_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sequence_has_tag` (
  `sequence_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`sequence_id`,`tag_id`),
  KEY `fk_sequence_has_tag_tag1_idx` (`tag_id`),
  KEY `fk_sequence_has_tag_sequence1_idx` (`sequence_id`),
  CONSTRAINT `fk_sequence_has_tag_sequence1` FOREIGN KEY (`sequence_id`) REFERENCES `sequence` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sequence_has_tag_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sequence_has_tag`
--

LOCK TABLES `sequence_has_tag` WRITE;
/*!40000 ALTER TABLE `sequence_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `sequence_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `source`
--

DROP TABLE IF EXISTS `source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `source` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `is_super` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `operation_id` int(11) NOT NULL,
  `method_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_language_application1_idx` (`application_id`),
  KEY `fk_reader_operation1_idx` (`operation_id`),
  KEY `fk_reader_method1_idx` (`method_id`),
  KEY `fk_reader_version1_idx` (`version_id`),
  KEY `fk_source_category1_idx` (`category_id`),
  CONSTRAINT `fk_language_application101` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_reader_method1` FOREIGN KEY (`method_id`) REFERENCES `method` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_reader_operation1` FOREIGN KEY (`operation_id`) REFERENCES `operation` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_reader_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_source_category1` FOREIGN KEY (`category_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `source`
--

LOCK TABLES `source` WRITE;
/*!40000 ALTER TABLE `source` DISABLE KEYS */;
/*!40000 ALTER TABLE `source` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `source_has_tag`
--

DROP TABLE IF EXISTS `source_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `source_has_tag` (
  `source_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`source_id`,`tag_id`),
  KEY `fk_source_has_tag_tag1_idx` (`tag_id`),
  KEY `fk_source_has_tag_source1_idx` (`source_id`),
  CONSTRAINT `fk_source_has_tag_source1` FOREIGN KEY (`source_id`) REFERENCES `source` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_source_has_tag_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `source_has_tag`
--

LOCK TABLES `source_has_tag` WRITE;
/*!40000 ALTER TABLE `source_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `source_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stereotype`
--

DROP TABLE IF EXISTS `stereotype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stereotype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `is_default` int(11) NOT NULL,
  `metatype_code` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stereotype_application1_idx` (`application_id`),
  KEY `fk_stereotype_version1_idx` (`version_id`),
  KEY `fk_stereotype_metatype1_idx` (`metatype_code`),
  CONSTRAINT `fk_stereotype_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_stereotype_metatype1` FOREIGN KEY (`metatype_code`) REFERENCES `metatype` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_stereotype_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stereotype`
--

LOCK TABLES `stereotype` WRITE;
/*!40000 ALTER TABLE `stereotype` DISABLE KEYS */;
INSERT INTO `stereotype` VALUES (1,1,0,'Field','field',NULL,0,''),(3,1,3,'111','111',NULL,0,''),(4,1,2,'qqq','qqq',NULL,0,''),(5,1,5,'333','333',NULL,0,''),(6,1,2,'222','222',NULL,1,''),(7,1,2,'area default','area_default',NULL,0,''),(8,1,2,'str','str',NULL,0,''),(9,1,2,'attr','attr',NULL,0,''),(10,1,2,'a2','a2',NULL,0,''),(21,9,8,'Default area','default_area',NULL,1,'area'),(22,9,8,'Default attribute','default_attribute',NULL,1,'attribute'),(23,9,8,'Default element','default_element',NULL,1,'element'),(24,9,8,'Default entity','default_entity',NULL,1,'entity'),(25,9,8,'Default entity structure','default_entity structure',NULL,1,'entityStructure'),(26,9,8,'Default generator','default_generator',NULL,1,'generator'),(27,9,8,'Default index','default_index',NULL,1,'index'),(28,9,8,'Default key structure','default_key structure',NULL,1,'keyStructure'),(29,9,8,'Default pk','default_pk',NULL,1,'pk'),(30,9,8,'Default simple structure','default_simple structure',NULL,1,'simpleStructure'),(31,9,8,'Default uq','default_uq',NULL,1,'uq'),(33,8,9,'Default area','default_area',NULL,1,'area'),(34,8,9,'Default attribute','default_attribute',NULL,1,'attribute'),(35,8,9,'Default element','default_element',NULL,1,'element'),(36,8,9,'Default entity','default_entity',NULL,1,'entity'),(37,8,9,'Default entity structure','default_entity structure',NULL,1,'entityStructure'),(38,8,9,'Default generator','default_generator',NULL,1,'generator'),(39,8,9,'Default index','default_index',NULL,1,'index'),(40,8,9,'Default key structure','default_key structure',NULL,1,'keyStructure'),(41,8,9,'Default pk','default_pk',NULL,1,'pk'),(42,8,9,'Default simple structure','default_simple structure',NULL,1,'simpleStructure'),(43,8,9,'Default uq','default_uq',NULL,1,'uq'),(44,8,7,'Entity default stereotype','entity',NULL,1,'entity');
/*!40000 ALTER TABLE `stereotype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stream`
--

DROP TABLE IF EXISTS `stream`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stream` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `stereotype_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `glossary` varchar(2000) DEFAULT NULL,
  `message_structure_id` int(11) NOT NULL,
  `catche_entity_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stream_structure1_idx` (`message_structure_id`),
  KEY `fk_stream_application1_idx` (`application_id`),
  KEY `fk_stream_entity1_idx` (`catche_entity_id`),
  KEY `fk_stream_version1_idx` (`version_id`),
  KEY `fk_stream_stereotype1_idx` (`stereotype_id`),
  CONSTRAINT `fk_stream_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_stream_entity1` FOREIGN KEY (`catche_entity_id`) REFERENCES `entity` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_stream_stereotype1` FOREIGN KEY (`stereotype_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_stream_structure1` FOREIGN KEY (`message_structure_id`) REFERENCES `structure` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_stream_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stream`
--

LOCK TABLES `stream` WRITE;
/*!40000 ALTER TABLE `stream` DISABLE KEYS */;
/*!40000 ALTER TABLE `stream` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stream_has_tag`
--

DROP TABLE IF EXISTS `stream_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stream_has_tag` (
  `stream_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`stream_id`,`tag_id`),
  KEY `fk_stream_has_tag_tag1_idx` (`tag_id`),
  KEY `fk_stream_has_tag_stream1_idx` (`stream_id`),
  CONSTRAINT `fk_stream_has_tag_stream1` FOREIGN KEY (`stream_id`) REFERENCES `stream` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_stream_has_tag_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stream_has_tag`
--

LOCK TABLES `stream_has_tag` WRITE;
/*!40000 ALTER TABLE `stream_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `stream_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `structure`
--

DROP TABLE IF EXISTS `structure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `structure` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `stereotype_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `glossary` varchar(2000) DEFAULT NULL,
  `area_id` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_structure_application1_idx` (`application_id`),
  KEY `fk_structure_version1_idx` (`version_id`),
  KEY `fk_structure_stereotype1_idx` (`stereotype_id`),
  KEY `fk_structure_area1_idx` (`area_id`),
  KEY `fk_structure_structure1_idx` (`parent_id`),
  CONSTRAINT `fk_structure_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_structure_area1` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_structure_stereotype1` FOREIGN KEY (`stereotype_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_structure_structure1` FOREIGN KEY (`parent_id`) REFERENCES `structure` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_structure_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `structure`
--

LOCK TABLES `structure` WRITE;
/*!40000 ALTER TABLE `structure` DISABLE KEYS */;
INSERT INTO `structure` VALUES (1,1,2,8,'s1','s1',NULL,NULL,1,NULL),(2,1,2,8,'xx','dd',NULL,NULL,1,NULL),(3,1,2,8,'xx','ss',NULL,NULL,1,NULL),(4,1,2,8,'qqq','qqq',NULL,NULL,1,NULL),(5,1,2,8,'primary','primary',NULL,NULL,1,NULL),(6,1,2,8,'222','rrr2',NULL,NULL,9,NULL),(7,1,2,8,'wwww','wwww','www',NULL,9,NULL),(8,1,2,8,'qewr','qwer',NULL,NULL,9,NULL),(9,1,2,8,'cc','cc',NULL,NULL,7,NULL),(10,1,2,8,'zz','zz',NULL,NULL,6,NULL),(11,1,2,8,'zz','zz',NULL,NULL,6,NULL),(12,1,2,8,'zz','zz',NULL,NULL,6,NULL),(13,1,2,8,'qwre','qwer',NULL,NULL,9,NULL),(90,9,8,30,'test','test',NULL,NULL,11,NULL),(94,9,8,25,'d1','d1',NULL,NULL,11,NULL),(96,9,8,25,'d2','d2',NULL,NULL,11,NULL),(97,9,8,28,'d2 PK','pk_d2_1',NULL,NULL,11,96),(98,9,8,28,'d2 UQ','uq_d2_2',NULL,NULL,11,96),(99,9,8,28,'d1 PK','pk_d1_1',NULL,NULL,11,94),(100,8,9,37,'Человек','home',NULL,NULL,12,NULL),(101,8,9,40,'Человек PK','pk_home_1',NULL,NULL,12,100),(102,8,9,40,'Человек UQ','uq_home_2',NULL,NULL,12,100),(104,8,9,37,'Машина','car',NULL,NULL,12,NULL),(105,8,9,40,'Машина PK','pk_car_1',NULL,NULL,12,104);
/*!40000 ALTER TABLE `structure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `structure_has_tag`
--

DROP TABLE IF EXISTS `structure_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `structure_has_tag` (
  `structure_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`structure_id`,`tag_id`),
  KEY `fk_structure_has_tag_tag1_idx` (`tag_id`),
  KEY `fk_structure_has_tag_structure1_idx` (`structure_id`),
  CONSTRAINT `fk_structure_has_tag_structure1` FOREIGN KEY (`structure_id`) REFERENCES `structure` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_structure_has_tag_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `structure_has_tag`
--

LOCK TABLES `structure_has_tag` WRITE;
/*!40000 ALTER TABLE `structure_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `structure_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscribtion_has_tag`
--

DROP TABLE IF EXISTS `subscribtion_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscribtion_has_tag` (
  `subscribtion_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`subscribtion_id`,`tag_id`),
  KEY `fk_subscribtion_has_tag_tag1_idx` (`tag_id`),
  KEY `fk_subscribtion_has_tag_subscribtion1_idx` (`subscribtion_id`),
  CONSTRAINT `fk_subscribtion_has_tag_subscribtion1` FOREIGN KEY (`subscribtion_id`) REFERENCES `consumer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_subscribtion_has_tag_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscribtion_has_tag`
--

LOCK TABLES `subscribtion_has_tag` WRITE;
/*!40000 ALTER TABLE `subscribtion_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscribtion_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `sysname` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tag_application1_idx` (`application_id`),
  KEY `fk_tag_version1_idx` (`version_id`),
  CONSTRAINT `fk_tag_application1` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tag_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `target`
--

DROP TABLE IF EXISTS `target`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `target` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `is_super` int(11) NOT NULL,
  `version_id` int(11) NOT NULL,
  `operation_id` int(11) NOT NULL,
  `method_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_language_application1_idx` (`application_id`),
  KEY `fk_reader_operation1_idx` (`operation_id`),
  KEY `fk_writer_method1_idx` (`method_id`),
  KEY `fk_writer_version1_idx` (`version_id`),
  KEY `fk_target_category1_idx` (`category_id`),
  CONSTRAINT `fk_language_application1010` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_reader_operation10` FOREIGN KEY (`operation_id`) REFERENCES `operation` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_target_category1` FOREIGN KEY (`category_id`) REFERENCES `stereotype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_writer_method1` FOREIGN KEY (`method_id`) REFERENCES `method` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_writer_version1` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `target`
--

LOCK TABLES `target` WRITE;
/*!40000 ALTER TABLE `target` DISABLE KEYS */;
/*!40000 ALTER TABLE `target` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `target_has_tag`
--

DROP TABLE IF EXISTS `target_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `target_has_tag` (
  `target_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`target_id`,`tag_id`),
  KEY `fk_target_has_tag_tag1_idx` (`tag_id`),
  KEY `fk_target_has_tag_target1_idx` (`target_id`),
  CONSTRAINT `fk_target_has_tag_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_target_has_tag_target1` FOREIGN KEY (`target_id`) REFERENCES `target` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `target_has_tag`
--

LOCK TABLES `target_has_tag` WRITE;
/*!40000 ALTER TABLE `target_has_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `target_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `current_application_id` int(11) DEFAULT NULL,
  `current_version_id` int(11) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_version1_idx` (`current_version_id`),
  KEY `fk_user_application1_idx` (`current_application_id`),
  CONSTRAINT `fk_user_application1` FOREIGN KEY (`current_application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_version1` FOREIGN KEY (`current_version_id`) REFERENCES `version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Admin','admin','admin',NULL,9,NULL),(2,'dddddd','dddddd','ddddd',NULL,NULL,'vasiliy.kuzmin@gmail.com'),(3,'cretatus','cretatus','load1979',NULL,NULL,'vasiliy.kuzmin@gmail.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `version`
--

DROP TABLE IF EXISTS `version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_id` int(11) NOT NULL,
  `module_id` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `major_version_id` int(11) NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_version_application1_idx` (`application_id`),
  KEY `fk_version_module1_idx` (`module_id`),
  KEY `fk_version_major_version1_idx` (`major_version_id`),
  CONSTRAINT `fk_version_application110` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_version_major_version1` FOREIGN KEY (`major_version_id`) REFERENCES `major_version` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_version_module1` FOREIGN KEY (`module_id`) REFERENCES `module` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `version`
--

LOCK TABLES `version` WRITE;
/*!40000 ALTER TABLE `version` DISABLE KEYS */;
INSERT INTO `version` VALUES (1,1,1,0,'1.0',NULL,1,'CLOSED'),(2,1,2,0,'1.0',NULL,2,'OPENED'),(3,1,1,1,'1.1',NULL,1,'OPENED'),(5,1,4,0,'1.0',NULL,4,'OPENED'),(7,8,6,0,'0.0',NULL,6,'OPENED'),(8,9,7,0,'0.0',NULL,7,'OPENED'),(9,8,8,0,'0.0',NULL,8,'OPENED');
/*!40000 ALTER TABLE `version` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-14 15:45:00
