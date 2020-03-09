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
-- Table structure for table `scms_ex_headers`
--

DROP TABLE IF EXISTS `scms_ex_headers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scms_ex_headers` (
  `excel_idx` decimal(10,0) NOT NULL COMMENT 'excel_idx',
  `sheet_idx` decimal(10,0) NOT NULL COMMENT 'sheet_idx',
  `column_idx` decimal(10,0) NOT NULL COMMENT 'column_idx',
  `header_idx` decimal(10,0) NOT NULL,
  `header_val` varchar(4000) DEFAULT NULL COMMENT 'header_val',
  `header_cmnt` varchar(4000) DEFAULT NULL COMMENT 'header_cmnt',
  `row_merge_size` decimal(10,0) DEFAULT NULL COMMENT 'row_merge_size',
  `cell_merge_size` decimal(10,0) DEFAULT NULL COMMENT 'cell_merge_size',
  `skip_required_yn` varchar(1) DEFAULT 'N',
  PRIMARY KEY (`excel_idx`,`sheet_idx`,`column_idx`,`header_idx`),
  CONSTRAINT `FK_scms_ex_columns_TO_scms_ex_headers` FOREIGN KEY (`excel_idx`, `sheet_idx`, `column_idx`) REFERENCES `scms_ex_columns` (`excel_idx`, `sheet_idx`, `column_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='scms_ex_headers';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scms_ex_headers`
--

LOCK TABLES `scms_ex_headers` WRITE;
/*!40000 ALTER TABLE `scms_ex_headers` DISABLE KEYS */;
INSERT INTO `scms_ex_headers` VALUES (1,1,0,0,'이름',NULL,2,NULL,'N'),(1,1,0,1,'',NULL,NULL,NULL,'N'),(1,1,1,0,'직급',NULL,2,NULL,'N'),(1,1,1,1,'',NULL,NULL,NULL,'N'),(1,1,2,0,'구분1',NULL,2,NULL,'N'),(1,1,2,1,'',NULL,NULL,NULL,'N'),(1,1,3,0,'부서명',NULL,2,NULL,'N'),(1,1,3,1,'',NULL,NULL,NULL,'N'),(1,1,4,0,'구분2',NULL,2,NULL,'N'),(1,1,4,1,'',NULL,NULL,NULL,'N'),(1,1,5,0,'급여대장',NULL,NULL,15,'Y'),(1,1,5,1,'구분3',NULL,NULL,NULL,'N'),(1,1,6,0,'',NULL,NULL,NULL,'N'),(1,1,6,1,'구분4',NULL,NULL,NULL,'N'),(1,1,7,0,'',NULL,NULL,NULL,'N'),(1,1,7,1,'1월',NULL,NULL,NULL,'N'),(1,1,8,0,'',NULL,NULL,NULL,'N'),(1,1,8,1,'2월',NULL,NULL,NULL,'N'),(1,1,9,0,'',NULL,NULL,NULL,'N'),(1,1,9,1,'3월',NULL,NULL,NULL,'N'),(1,1,10,0,'',NULL,NULL,NULL,'N'),(1,1,10,1,'4월',NULL,NULL,NULL,'N'),(1,1,11,0,'',NULL,NULL,NULL,'N'),(1,1,11,1,'5월',NULL,NULL,NULL,'N'),(1,1,12,0,'',NULL,NULL,NULL,'N'),(1,1,12,1,'6월',NULL,NULL,NULL,'N'),(1,1,13,0,'',NULL,NULL,NULL,'N'),(1,1,13,1,'7월',NULL,NULL,NULL,'N'),(1,1,14,0,'',NULL,NULL,NULL,'N'),(1,1,14,1,'8월',NULL,NULL,NULL,'N'),(1,1,15,0,'',NULL,NULL,NULL,'N'),(1,1,15,1,'9월',NULL,NULL,NULL,'N'),(1,1,16,0,'',NULL,NULL,NULL,'N'),(1,1,16,1,'10월',NULL,NULL,NULL,'N'),(1,1,17,0,'',NULL,NULL,NULL,'N'),(1,1,17,1,'11월',NULL,NULL,NULL,'N'),(1,1,18,0,'',NULL,NULL,NULL,'N'),(1,1,18,1,'12월',NULL,NULL,NULL,'N'),(1,1,19,0,'',NULL,NULL,NULL,'N'),(1,1,19,1,'소계',NULL,NULL,NULL,'N'),(1,1,20,0,'비고',NULL,2,NULL,'N'),(1,1,20,1,'',NULL,NULL,NULL,'N');
/*!40000 ALTER TABLE `scms_ex_headers` ENABLE KEYS */;
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
