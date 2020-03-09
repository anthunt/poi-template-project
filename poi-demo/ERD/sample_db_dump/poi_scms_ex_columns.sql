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
-- Table structure for table `scms_ex_columns`
--

DROP TABLE IF EXISTS `scms_ex_columns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scms_ex_columns` (
  `excel_idx` decimal(10,0) NOT NULL COMMENT 'excel_idx',
  `sheet_idx` decimal(10,0) NOT NULL COMMENT 'sheet_idx',
  `column_idx` decimal(10,0) NOT NULL COMMENT 'column_idx',
  `required_yn` varchar(1) DEFAULT 'N' COMMENT 'required_yn',
  `sample_value` varchar(4000) DEFAULT NULL COMMENT 'sample_value',
  `data_cell_tp` varchar(10) DEFAULT NULL COMMENT 'data_cell_tp',
  `numeric_format_tp` varchar(10) DEFAULT NULL COMMENT 'numeric_format_tp',
  `column_formula` varchar(100) DEFAULT NULL,
  `explicits_sql` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`excel_idx`,`sheet_idx`,`column_idx`),
  CONSTRAINT `FK_scms_ex_sheets_TO_scms_ex_columns` FOREIGN KEY (`excel_idx`, `sheet_idx`) REFERENCES `scms_ex_sheets` (`excel_idx`, `sheet_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='scms_ex_columns';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scms_ex_columns`
--

LOCK TABLES `scms_ex_columns` WRITE;
/*!40000 ALTER TABLE `scms_ex_columns` DISABLE KEYS */;
INSERT INTO `scms_ex_columns` VALUES (1,1,0,'Y','홍길동','STRING',NULL,NULL,NULL),(1,1,1,'Y','대리','STRING',NULL,NULL,NULL),(1,1,2,'Y','직접','STRING',NULL,NULL,'select val from code_tb where pcd = \'CD1\''),(1,1,3,'Y','제조팀','STRING',NULL,NULL,NULL),(1,1,4,'Y','정규직','STRING',NULL,NULL,'select val from code_tb where pcd = \'CD2\''),(1,1,5,'Y','급여','STRING',NULL,NULL,'select val from code_tb where pcd = \'CD3\''),(1,1,6,'Y','기본급','STRING',NULL,NULL,'select val from code_tb where pcd = \'CD4\''),(1,1,7,'N','1800000','NUMERIC','INTEGER',NULL,NULL),(1,1,8,'N','1800000','NUMERIC','INTEGER',NULL,NULL),(1,1,9,'N','1800000','NUMERIC','INTEGER',NULL,NULL),(1,1,10,'N','1800000','NUMERIC','INTEGER',NULL,NULL),(1,1,11,'N','1800000','NUMERIC','INTEGER',NULL,NULL),(1,1,12,'N','1800000','NUMERIC','INTEGER',NULL,NULL),(1,1,13,'N','1800000','NUMERIC','INTEGER',NULL,NULL),(1,1,14,'N','1800000','NUMERIC','INTEGER',NULL,NULL),(1,1,15,'N','1800000','NUMERIC','INTEGER',NULL,NULL),(1,1,16,'N','1800000','NUMERIC','INTEGER',NULL,NULL),(1,1,17,'N','1800000','NUMERIC','INTEGER',NULL,NULL),(1,1,18,'N','1800000','NUMERIC','INTEGER',NULL,NULL),(1,1,19,'N',NULL,'FORMULA','INTEGER','SUM({COL8}{ROW}:{COL19}{ROW})',NULL),(1,1,20,'N','비고','STRING',NULL,NULL,NULL);
/*!40000 ALTER TABLE `scms_ex_columns` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-19 15:27:49
