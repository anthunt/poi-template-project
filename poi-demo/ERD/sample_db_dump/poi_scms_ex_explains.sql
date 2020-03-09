-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: poi
-- ------------------------------------------------------
-- Server version	5.7.19-enterprise-commercial-advanced-log

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
-- Table structure for table `scms_ex_explains`
--

DROP TABLE IF EXISTS `scms_ex_explains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scms_ex_explains` (
  `excel_idx` decimal(10,0) NOT NULL COMMENT 'excel_idx',
  `sheet_idx` decimal(10,0) NOT NULL COMMENT 'sheet_idx',
  `column_idx` decimal(10,0) NOT NULL COMMENT 'column_idx',
  `explain_idx` decimal(10,0) NOT NULL COMMENT 'explain_idx',
  `explain_val` varchar(4000) DEFAULT NULL COMMENT 'explain_val',
  `explain_cmnt` varchar(4000) DEFAULT NULL COMMENT 'explain_cmnt',
  `row_merge_size` decimal(10,0) DEFAULT NULL COMMENT 'row_merge_size',
  `cell_merge_size` decimal(10,0) DEFAULT NULL COMMENT 'cell_merge_size',
  PRIMARY KEY (`excel_idx`,`sheet_idx`,`column_idx`,`explain_idx`),
  CONSTRAINT `FK_scms_ex_columns_TO_scms_ex_explains` FOREIGN KEY (`excel_idx`, `sheet_idx`, `column_idx`) REFERENCES `scms_ex_columns` (`excel_idx`, `sheet_idx`, `column_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='scms_ex_explains';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scms_ex_explains`
--

LOCK TABLES `scms_ex_explains` WRITE;
/*!40000 ALTER TABLE `scms_ex_explains` DISABLE KEYS */;
INSERT INTO `scms_ex_explains` VALUES (1,1,0,0,'샘플 데이터','샘플 메모',NULL,NULL);
/*!40000 ALTER TABLE `scms_ex_explains` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-19 15:27:51
