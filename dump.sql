CREATE DATABASE  IF NOT EXISTS `podsis1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `podsis1`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: podsis1
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `grad`
--

DROP TABLE IF EXISTS `grad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grad` (
  `idG` int NOT NULL AUTO_INCREMENT,
  `ime` varchar(45) NOT NULL,
  PRIMARY KEY (`idG`),
  UNIQUE KEY `ime` (`ime`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grad`
--

LOCK TABLES `grad` WRITE;
/*!40000 ALTER TABLE `grad` DISABLE KEYS */;
INSERT INTO `grad` VALUES (1,'Beograd'),(4,'Kragujevac'),(3,'Nis'),(2,'Novi Sad'),(5,'Subotica');
/*!40000 ALTER TABLE `grad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ima`
--

DROP TABLE IF EXISTS `ima`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ima` (
  `idKor` int NOT NULL,
  `idU` int NOT NULL,
  PRIMARY KEY (`idKor`,`idU`),
  KEY `idU` (`idU`),
  CONSTRAINT `ima_ibfk_1` FOREIGN KEY (`idKor`) REFERENCES `korisnik` (`idKor`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ima_ibfk_2` FOREIGN KEY (`idU`) REFERENCES `uloga` (`idU`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ima`
--

LOCK TABLES `ima` WRITE;
/*!40000 ALTER TABLE `ima` DISABLE KEYS */;
INSERT INTO `ima` VALUES (1,1),(2,2),(3,2),(4,2),(5,2),(6,2);
/*!40000 ALTER TABLE `ima` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `idKor` int NOT NULL AUTO_INCREMENT,
  `korisnickoIme` varchar(45) NOT NULL,
  `sifra` varchar(45) NOT NULL,
  `ime` varchar(45) NOT NULL,
  `prezime` varchar(45) NOT NULL,
  `adresa` varchar(45) DEFAULT NULL,
  `stanje` decimal(10,2) DEFAULT '0.00',
  `idG` int DEFAULT NULL,
  PRIMARY KEY (`idKor`),
  UNIQUE KEY `korisnickoIme` (`korisnickoIme`),
  KEY `idG` (`idG`),
  CONSTRAINT `korisnik_ibfk_1` FOREIGN KEY (`idG`) REFERENCES `grad` (`idG`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'admin','admin123','Petar','Petrovic','Gospodar Jovanova 2',10000.00,1),(2,'mika','mika123','Mika','Mikic','Bulevar oslobođenja 24',2500.00,2),(3,'pera','pera123','Pera','Peric','Dositeja Obradovica 19',1800.00,3),(4,'ana','ana123','Ana','Anic','Bulevar Zorana Đinđića 66',3200.00,1),(5,'marko','marko123','Marko','Markovic','Kralja Aleksandra I Karađorđevića 13',500.00,4),(6,'zika','zika123','Zika','Zikic','Matije Korvina 6',7600.00,5);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uloga`
--

DROP TABLE IF EXISTS `uloga`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `uloga` (
  `idU` int NOT NULL AUTO_INCREMENT,
  `ime` varchar(45) NOT NULL,
  PRIMARY KEY (`idU`),
  UNIQUE KEY `ime` (`ime`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uloga`
--

LOCK TABLES `uloga` WRITE;
/*!40000 ALTER TABLE `uloga` DISABLE KEYS */;
INSERT INTO `uloga` VALUES (1,'administrator'),(2,'korisnik');
/*!40000 ALTER TABLE `uloga` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-18 19:08:55
CREATE DATABASE  IF NOT EXISTS `podsis3` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `podsis3`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: podsis3
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `narudzbina`
--

DROP TABLE IF EXISTS `narudzbina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `narudzbina` (
  `idN` int NOT NULL AUTO_INCREMENT,
  `idKor` int NOT NULL,
  `ukupnaCena` decimal(10,2) NOT NULL,
  `napravljeno` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `adresa` varchar(45) DEFAULT NULL,
  `idG` int DEFAULT NULL,
  PRIMARY KEY (`idN`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `narudzbina`
--

LOCK TABLES `narudzbina` WRITE;
/*!40000 ALTER TABLE `narudzbina` DISABLE KEYS */;
INSERT INTO `narudzbina` VALUES (1,2,10250.00,'2026-02-18 17:25:43','Bulevar oslobođenja 24',2),(2,3,2975.00,'2026-02-18 17:25:43','Dositeja Obradovica 19',3),(3,4,9000.00,'2026-02-18 17:25:43','Bulevar Zorana Đinđića 66',1),(4,5,4500.00,'2026-02-18 17:25:43','Kralja Aleksandra I Karađorđevića 13',4);
/*!40000 ALTER TABLE `narudzbina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transakcija`
--

DROP TABLE IF EXISTS `transakcija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transakcija` (
  `idT` int NOT NULL AUTO_INCREMENT,
  `idN` int NOT NULL,
  `placeno` decimal(10,2) NOT NULL,
  `vreme` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idT`),
  UNIQUE KEY `idN` (`idN`),
  CONSTRAINT `transakcija_ibfk_1` FOREIGN KEY (`idN`) REFERENCES `narudzbina` (`idN`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transakcija`
--

LOCK TABLES `transakcija` WRITE;
/*!40000 ALTER TABLE `transakcija` DISABLE KEYS */;
INSERT INTO `transakcija` VALUES (1,1,10250.00,'2026-02-18 17:25:43'),(2,2,2975.00,'2026-02-18 17:25:43'),(3,3,9000.00,'2026-02-18 17:25:43'),(4,4,4500.00,'2026-02-18 17:25:43');
/*!40000 ALTER TABLE `transakcija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `u_narudzbini`
--

DROP TABLE IF EXISTS `u_narudzbini`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `u_narudzbini` (
  `idN` int NOT NULL,
  `idArt` int NOT NULL,
  `kolicina` int NOT NULL,
  `jedinicnaCena` decimal(10,2) NOT NULL,
  PRIMARY KEY (`idN`,`idArt`),
  CONSTRAINT `u_narudzbini_ibfk_1` FOREIGN KEY (`idN`) REFERENCES `narudzbina` (`idN`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `u_narudzbini_chk_1` CHECK ((`kolicina` > 0)),
  CONSTRAINT `u_narudzbini_chk_2` CHECK ((`jedinicnaCena` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `u_narudzbini`
--

LOCK TABLES `u_narudzbini` WRITE;
/*!40000 ALTER TABLE `u_narudzbini` DISABLE KEYS */;
INSERT INTO `u_narudzbini` VALUES (1,1,2,2000.00),(1,5,1,6250.00),(2,3,1,2975.00),(3,6,1,9000.00),(4,2,1,2000.00),(4,4,1,2500.00);
/*!40000 ALTER TABLE `u_narudzbini` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-18 19:08:55
CREATE DATABASE  IF NOT EXISTS `podsis2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `podsis2`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: podsis2
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `artikal`
--

DROP TABLE IF EXISTS `artikal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artikal` (
  `idArt` int NOT NULL AUTO_INCREMENT,
  `ime` varchar(45) NOT NULL,
  `opis` varchar(200) DEFAULT NULL,
  `cena` decimal(10,2) NOT NULL,
  `popust` int DEFAULT '0',
  `idKor` int NOT NULL,
  PRIMARY KEY (`idArt`),
  CONSTRAINT `artikal_chk_1` CHECK ((`cena` >= 0)),
  CONSTRAINT `artikal_chk_2` CHECK ((`popust` between 0 and 100))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artikal`
--

LOCK TABLES `artikal` WRITE;
/*!40000 ALTER TABLE `artikal` DISABLE KEYS */;
INSERT INTO `artikal` VALUES (1,'plava majica','muska majica',2000.00,10,2),(2,'crvena majica','muska majica',2000.00,0,2),(3,'plavi duks','zenski duks',3500.00,15,3),(4,'crveni duks','zenski duks',3500.00,0,3),(5,'plave patike','muske patike',7000.00,5,2),(6,'crne cizme','zenske cizme',9000.00,0,3);
/*!40000 ALTER TABLE `artikal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategorija`
--

DROP TABLE IF EXISTS `kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategorija` (
  `idKat` int NOT NULL AUTO_INCREMENT,
  `ime` varchar(45) NOT NULL,
  `idKatNad` int DEFAULT NULL,
  PRIMARY KEY (`idKat`),
  UNIQUE KEY `ime` (`ime`),
  KEY `idKatNad` (`idKatNad`),
  CONSTRAINT `kategorija_ibfk_1` FOREIGN KEY (`idKatNad`) REFERENCES `kategorija` (`idKat`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategorija`
--

LOCK TABLES `kategorija` WRITE;
/*!40000 ALTER TABLE `kategorija` DISABLE KEYS */;
INSERT INTO `kategorija` VALUES (1,'musko',NULL),(2,'zensko',NULL),(3,'za_decu',NULL),(4,'odeca_musko',1),(5,'obuca_musko',1),(6,'odeca_zensko',2),(7,'obuca_zensko',2),(8,'odeca_deca',3),(9,'obuca_deca',3),(10,'majice_musko',4),(11,'duksevi_musko',4),(12,'jakne_musko',4),(13,'majice_zensko',6),(14,'duksevi_zensko',6),(15,'jakne_zensko',6),(16,'majice_deca',8),(17,'duksevi_deca',8),(18,'jakne_deca',8),(19,'patike_musko',5),(20,'cizme_musko',5),(21,'cipele_musko',5),(22,'patike_zensko',7),(23,'cizme_zensko',7),(24,'cipele_zensko',7),(25,'patike_deca',9),(26,'cizme_deca',9),(27,'cipele_deca',9);
/*!40000 ALTER TABLE `kategorija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korpa`
--

DROP TABLE IF EXISTS `korpa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korpa` (
  `idK` int NOT NULL AUTO_INCREMENT,
  `idKor` int NOT NULL,
  `ukupnaCena` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`idK`),
  UNIQUE KEY `idKor` (`idKor`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korpa`
--

LOCK TABLES `korpa` WRITE;
/*!40000 ALTER TABLE `korpa` DISABLE KEYS */;
INSERT INTO `korpa` VALUES (1,1,10250.00),(2,2,2975.00),(3,3,9000.00),(4,4,0.00),(5,5,0.00),(6,6,0.00);
/*!40000 ALTER TABLE `korpa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lista_zelja`
--

DROP TABLE IF EXISTS `lista_zelja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lista_zelja` (
  `idList` int NOT NULL AUTO_INCREMENT,
  `idKor` int NOT NULL,
  `napravljena` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idList`),
  UNIQUE KEY `idKor` (`idKor`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lista_zelja`
--

LOCK TABLES `lista_zelja` WRITE;
/*!40000 ALTER TABLE `lista_zelja` DISABLE KEYS */;
INSERT INTO `lista_zelja` VALUES (1,1,'2026-02-18 17:08:58'),(2,2,'2026-02-18 17:08:58'),(3,3,'2026-02-18 17:08:58'),(4,4,'2026-02-18 17:08:58'),(5,5,'2026-02-18 17:08:58'),(6,6,'2026-02-18 17:08:58');
/*!40000 ALTER TABLE `lista_zelja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pripada`
--

DROP TABLE IF EXISTS `pripada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pripada` (
  `idArt` int NOT NULL,
  `idKat` int NOT NULL,
  PRIMARY KEY (`idArt`,`idKat`),
  KEY `idKat` (`idKat`),
  CONSTRAINT `pripada_ibfk_1` FOREIGN KEY (`idArt`) REFERENCES `artikal` (`idArt`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pripada_ibfk_2` FOREIGN KEY (`idKat`) REFERENCES `kategorija` (`idKat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pripada`
--

LOCK TABLES `pripada` WRITE;
/*!40000 ALTER TABLE `pripada` DISABLE KEYS */;
INSERT INTO `pripada` VALUES (1,10),(2,10),(3,13),(4,13),(5,19),(6,23);
/*!40000 ALTER TABLE `pripada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `u_korpi`
--

DROP TABLE IF EXISTS `u_korpi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `u_korpi` (
  `idK` int NOT NULL,
  `idArt` int NOT NULL,
  `kolicina` int NOT NULL,
  PRIMARY KEY (`idK`,`idArt`),
  KEY `idArt` (`idArt`),
  CONSTRAINT `u_korpi_ibfk_1` FOREIGN KEY (`idK`) REFERENCES `korpa` (`idK`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `u_korpi_ibfk_2` FOREIGN KEY (`idArt`) REFERENCES `artikal` (`idArt`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `u_korpi_chk_1` CHECK ((`kolicina` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `u_korpi`
--

LOCK TABLES `u_korpi` WRITE;
/*!40000 ALTER TABLE `u_korpi` DISABLE KEYS */;
INSERT INTO `u_korpi` VALUES (1,1,2),(1,5,1),(2,3,1),(3,6,1);
/*!40000 ALTER TABLE `u_korpi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `u_listi_zelja`
--

DROP TABLE IF EXISTS `u_listi_zelja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `u_listi_zelja` (
  `idList` int NOT NULL,
  `idArt` int NOT NULL,
  `dodato` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idList`,`idArt`),
  KEY `idArt` (`idArt`),
  CONSTRAINT `u_listi_zelja_ibfk_1` FOREIGN KEY (`idList`) REFERENCES `lista_zelja` (`idList`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `u_listi_zelja_ibfk_2` FOREIGN KEY (`idArt`) REFERENCES `artikal` (`idArt`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `u_listi_zelja`
--

LOCK TABLES `u_listi_zelja` WRITE;
/*!40000 ALTER TABLE `u_listi_zelja` DISABLE KEYS */;
INSERT INTO `u_listi_zelja` VALUES (1,2,'2026-02-18 17:11:54'),(1,5,'2026-02-18 17:11:54'),(2,6,'2026-02-18 17:11:54'),(3,1,'2026-02-18 17:11:54');
/*!40000 ALTER TABLE `u_listi_zelja` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-18 19:08:55
