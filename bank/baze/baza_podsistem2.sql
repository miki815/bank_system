-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: banka3
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
-- Table structure for table `komitent`
--

DROP TABLE IF EXISTS `komitent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `komitent` (
  `idKom` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  `adresa` varchar(45) NOT NULL,
  `idM` int NOT NULL,
  PRIMARY KEY (`idKom`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
-- Table structure for table `racun`
--

DROP TABLE IF EXISTS `racun`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `racun` (
  `idRac` int NOT NULL AUTO_INCREMENT,
  `stanje` double DEFAULT NULL,
  `status` varchar(45) NOT NULL,
  `dozvoljeniMinus` double NOT NULL,
  `mestoId` int NOT NULL,
  `datumOtvaranja` datetime NOT NULL,
  `idKom` int NOT NULL,
  `brojTransakcija` int DEFAULT NULL,
  PRIMARY KEY (`idRac`),
  KEY `idK_idx` (`idKom`),
  CONSTRAINT `idK` FOREIGN KEY (`idKom`) REFERENCES `komitent` (`idKom`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `racun`
--

LOCK TABLES `racun` WRITE;
/*!40000 ALTER TABLE `racun` DISABLE KEYS */;
INSERT INTO `racun` VALUES (1,0,'Aktivan',100000,3,'2022-02-25 15:11:17',3,0),(2,0,'Aktivan',30000,3,'2022-02-25 15:12:56',2,0),(3,0,'Aktivan',80000,3,'2022-02-25 15:18:38',3,0),(4,0,'Aktivan',70000,3,'2022-02-25 15:29:45',2,0);
/*!40000 ALTER TABLE `racun` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transakcija`
--

DROP TABLE IF EXISTS `transakcija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transakcija` (
  `idTr` int NOT NULL AUTO_INCREMENT,
  `datumObavljanja` datetime NOT NULL,
  `iznos` double NOT NULL,
  `redniBroj` int NOT NULL,
  `svrha` varchar(45) NOT NULL,
  `idRac` int NOT NULL,
  `idRacPrim` int DEFAULT NULL,
  `idFil` int DEFAULT NULL,
  `tip` int DEFAULT NULL,
  PRIMARY KEY (`idTr`),
  KEY `idR_idx` (`idRac`),
  CONSTRAINT `idR` FOREIGN KEY (`idRac`) REFERENCES `racun` (`idRac`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-25 17:43:29