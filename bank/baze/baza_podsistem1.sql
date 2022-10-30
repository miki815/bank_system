-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: banka
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `mesto`
--

DROP TABLE IF EXISTS `mesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesto` (
  `idmesto` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  `postanskiBroj` int NOT NULL,
  PRIMARY KEY (`idmesto`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Dumping data for table `mesto`
--
LOCK TABLES `mesto` WRITE;
/*!40000 ALTER TABLE `mesto` DISABLE KEYS */;
INSERT INTO `mesto` VALUES (1,'Kostolac',12208),(2,'Beograd',11000),(3,'Monako',777),(4,'Istanbul',261024),(5,'Madrid',261025);
/*!40000 ALTER TABLE `mesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `komitent`
--

DROP TABLE IF EXISTS `komitent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `komitent` (
  `idkomitent` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  `adresa` varchar(45) NOT NULL,
  `mestoId` int DEFAULT NULL,
  PRIMARY KEY (`idkomitent`),
  KEY `mesto_idx` (`mestoId`),
  CONSTRAINT `mesto` FOREIGN KEY (`mestoId`) REFERENCES `mesto` (`idmesto`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `komitent`
--

LOCK TABLES `komitent` WRITE;
/*!40000 ALTER TABLE `komitent` DISABLE KEYS */;
INSERT INTO `komitent` VALUES (1,'Mihailo','Omladinska', 1),(2,'Milica','DjureJaksica',1),(3,'Viktor','Jadranska',2),(4,'Jovan','Rudarska',4),(5,'Ana','Miliceva',3);
/*!40000 ALTER TABLE `komitent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filijala`
--

DROP TABLE IF EXISTS `filijala`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `filijala` (
  `idfilijala` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  `adresa` varchar(45) NOT NULL,
  `mestoId` int DEFAULT NULL,
  PRIMARY KEY (`idfilijala`),
  KEY `mestoId_idx` (`mestoId`),
  CONSTRAINT `mestoId` FOREIGN KEY (`mestoId`) REFERENCES `mesto` (`idmesto`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filijala`
--

LOCK TABLES `filijala` WRITE;
/*!40000 ALTER TABLE `filijala` DISABLE KEYS */;
INSERT INTO `filijala` VALUES (1,'Intesa','Bulevar',2),(2,'NLB','Trg',2),(3,'UniCredit','Prvomajska',1),(4,'AxeCap','BB',5),(5,'Ikrik','Louv',4);
/*!40000 ALTER TABLE `filijala` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-25  3:18:27