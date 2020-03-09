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
-- Table structure for table `scms_ex_db_columns`
--

DROP TABLE IF EXISTS `scms_ex_db_columns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scms_ex_db_columns` (
  `excel_idx` decimal(10,0) NOT NULL COMMENT 'excel_idx',
  `sheet_idx` decimal(10,0) NOT NULL COMMENT 'sheet_idx',
  `column_idx` decimal(10,0) NOT NULL COMMENT 'column_idx',
  `table_nm` varchar(30) NOT NULL COMMENT 'table_nm',
  `column_nm` varchar(30) NOT NULL COMMENT 'column_nm',
  `data_tp` varchar(10) NOT NULL COMMENT 'data_tp',
  `pivot_col_yn` varchar(1) DEFAULT NULL COMMENT 'pivot_col_yn',
  `pivot_column_nm` varchar(30) DEFAULT NULL COMMENT 'pivot_column_nm',
  `pivot_column_val` varchar(100) DEFAULT NULL COMMENT 'pivot_column_val',
  `pivot_column_primary_yn` varchar(1) DEFAULT 'N',
  `primary_key_yn` varchar(1) DEFAULT 'N',
  PRIMARY KEY (`excel_idx`,`sheet_idx`,`column_idx`,`table_nm`,`column_nm`),
  CONSTRAINT `FK_scms_ex_columns_TO_scms_ex_db_columns` FOREIGN KEY (`excel_idx`, `sheet_idx`, `column_idx`) REFERENCES `scms_ex_columns` (`excel_idx`, `sheet_idx`, `column_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='scms_ex_db_columns';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scms_ex_db_columns`
--

LOCK TABLES `scms_ex_db_columns` WRITE;
/*!40000 ALTER TABLE `scms_ex_db_columns` DISABLE KEYS */;
INSERT INTO `scms_ex_db_columns` VALUES (1,1,0,'tbtest','name','STRING',NULL,NULL,NULL,'N','Y'),(1,1,1,'tbtest','col1','STRING',NULL,NULL,NULL,'N','N'),(1,1,2,'tbtest','col2','STRING',NULL,NULL,NULL,'N','N'),(1,1,3,'tbtest','col3','STRING',NULL,NULL,NULL,'N','N'),(1,1,4,'tbtest','col4','STRING',NULL,NULL,NULL,'N','N'),(1,1,5,'tbtest','col5','STRING',NULL,NULL,NULL,'N','N'),(1,1,6,'tbtest','col6','STRING',NULL,NULL,NULL,'N','N'),(1,1,7,'tbtest','col8','NUMERIC','Y','col7','1','Y','N'),(1,1,8,'tbtest','col8','NUMERIC','Y','col7','2','Y','N'),(1,1,9,'tbtest','col8','NUMERIC','Y','col7','3','Y','N'),(1,1,10,'tbtest','col8','NUMERIC','Y','col7','4','Y','N'),(1,1,11,'tbtest','col8','NUMERIC','Y','col7','5','Y','N'),(1,1,12,'tbtest','col8','NUMERIC','Y','col7','6','Y','N'),(1,1,13,'tbtest','col8','NUMERIC','Y','col7','7','Y','N'),(1,1,14,'tbtest','col8','NUMERIC','Y','col7','8','Y','N'),(1,1,15,'tbtest','col8','NUMERIC','Y','col7','9','Y','N'),(1,1,16,'tbtest','col8','NUMERIC','Y','col7','10','Y','N'),(1,1,17,'tbtest','col8','NUMERIC','Y','col7','11','Y','N'),(1,1,18,'tbtest','col8','NUMERIC','Y','col7','12','Y','N'),(1,1,20,'tbtest','col9','STRING',NULL,NULL,NULL,'N','N');
/*!40000 ALTER TABLE `scms_ex_db_columns` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-19 15:27:52
