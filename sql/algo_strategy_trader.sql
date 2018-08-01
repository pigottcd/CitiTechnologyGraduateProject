CREATE DATABASE  IF NOT EXISTS `algo_strategy_trader` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `algo_strategy_trader`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: algo_strategy_trader
-- ------------------------------------------------------
-- Server version	5.7.14-log

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
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `buy` bit(1) NOT NULL,
  `price` float NOT NULL,
  `size` int(11) NOT NULL,
  `stock` varchar(45) NOT NULL,
  `time` blob,
  `status` varchar(45) DEFAULT NULL,
  `strategy_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `_idx` (`strategy_id`),
  CONSTRAINT `strategy_id` FOREIGN KEY (`strategy_id`) REFERENCES `strategy` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (20,'',95.58,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚3$ö{\0x',NULL,6),(21,'\0',104.27,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚4&,>¿x',NULL,6),(22,'\0',104.15,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚8$\√Äx',NULL,8),(23,'',95.62,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚9$!é≤¿x',NULL,8),(24,'\0',104.02,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚\'\"\0x',NULL,10),(25,'',95.56,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚\'1\‘Y\0x',NULL,10),(26,'\0',104.64,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚!3≠^¿x',NULL,10),(27,'',104.83,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚#8Sé@x',NULL,10),(28,'\0',104.68,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚(#@x',NULL,10),(29,'',95.71,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚(®\0@x',NULL,11),(30,'\0',104.68,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚\"Å\0x',NULL,11),(31,'',104.86,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚$\'5Äx',NULL,11),(32,'\0',104.59,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚(\"sîÄx',NULL,11),(33,'',95.68,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚\"5Ò4@x',NULL,12),(34,'\0',104.31,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚ #	-⁄Äx',NULL,12),(35,'',95.85,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚!%üû@x',NULL,12),(36,'',95.75,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚#(\Z≥\0x',NULL,13),(37,'\0',104.28,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚.&(\r\Ë\0x',NULL,14),(38,'',95.94,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚/)\0\‰\·¿x',NULL,14),(39,'\0',104.3,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚4&\"\0x',NULL,15),(40,'',95.91,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚5(6â\ ¿x',NULL,15),(41,'\0',104.17,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚8$\Í\0x',NULL,16),(42,'',95.66,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚!$&Sf¿x',NULL,17),(43,'\0',105.25,100,'C','¨\Ì\0sr\0\rjava.time.Serï]Ñ∫\"H≤\0\0xpw\0\0\‚\"\')@x',NULL,17);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `strategy`
--

DROP TABLE IF EXISTS `strategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `strategy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `ticker` varchar(45) NOT NULL,
  `active` bit(1) NOT NULL,
  `quantity` varchar(45) NOT NULL,
  `short_period` int(11) DEFAULT NULL,
  `long_period` int(11) DEFAULT NULL,
  `p_and_l` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `strategy`
--

LOCK TABLES `strategy` WRITE;
/*!40000 ALTER TABLE `strategy` DISABLE KEYS */;
INSERT INTO `strategy` VALUES (6,'TwoMovingAverages','C','','100',2,10,0.1),(7,'TwoMovingAverages','C','','100',2,10,0.1),(8,'TwoMovingAverages','C','','100',2,10,0.1),(9,'TwoMovingAverages','C','','100',2,10,0.1),(10,'TwoMovingAverages','C','','100',2,10,0.1),(11,'TwoMovingAverages','C','','100',2,10,0.1),(12,'TwoMovingAverages','C','','100',2,10,0.1),(13,'TwoMovingAverages','C','','100',2,10,0.1),(14,'TwoMovingAverages','C','','100',2,10,0.1),(15,'TwoMovingAverages','C','','100',2,10,0.1),(16,'TwoMovingAverages','C','','100',2,10,0.1),(17,'TwoMovingAverages','C','','100',2,10,0.1),(18,'TwoMovingAverages','C','','100',2,10,0.1);
/*!40000 ALTER TABLE `strategy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'bob','test','test');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-01 13:21:32
