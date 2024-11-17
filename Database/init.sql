-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: avto4
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `catalogauto`
--

DROP TABLE IF EXISTS `catalogauto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalogauto` (
  `idCatalogAuto` int NOT NULL AUTO_INCREMENT,
  `Date_proizvod` text,
  `Price` decimal(19,2) DEFAULT NULL,
  `Status_nal` varchar(45) DEFAULT 'в наличии',
  `descr` varchar(500) DEFAULT NULL,
  `Manufactur_idManufactur` int NOT NULL,
  `Marka_idMarka` int NOT NULL,
  `catalogautoPhoto` text,
  `model_idModel` int NOT NULL,
  PRIMARY KEY (`idCatalogAuto`,`Manufactur_idManufactur`,`Marka_idMarka`,`model_idModel`),
  KEY `fk_CatalogAuto_Manufactur_idx` (`Manufactur_idManufactur`),
  KEY `fk_CatalogAuto_Marka1_idx` (`Marka_idMarka`),
  KEY `fk_catalogauto_model1_idx` (`model_idModel`),
  CONSTRAINT `fk_CatalogAuto_Manufactur` FOREIGN KEY (`Manufactur_idManufactur`) REFERENCES `manufactur` (`idManufactur`),
  CONSTRAINT `fk_CatalogAuto_Marka1` FOREIGN KEY (`Marka_idMarka`) REFERENCES `marka` (`idMarka`),
  CONSTRAINT `fk_catalogauto_model1` FOREIGN KEY (`model_idModel`) REFERENCES `model` (`idModel`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogauto`
--

LOCK TABLES `catalogauto` WRITE;
/*!40000 ALTER TABLE `catalogauto` DISABLE KEYS */;
INSERT INTO `catalogauto` VALUES (3,'2023',700000000.00,'в наличии','Уникальность',3,1,'car1.jpg',3),(4,'2023',56000000.00,'в наличи','Доступность ',4,1,'car4.jpg',4),(5,'2023',56000000.00,'нет в наличии','Красота',5,1,'car5.jpg',5),(7,'2023',596000000.00,'нет в наличии','Уникальность',7,1,'car7.jpg',7),(8,'2023',56000000.00,'нет в наличии','Стиль',8,2,'car8.jpg',8);
/*!40000 ALTER TABLE `catalogauto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalogauto_has_clients`
--

DROP TABLE IF EXISTS `catalogauto_has_clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalogauto_has_clients` (
  `catalogauto_idCatalogAuto` int NOT NULL AUTO_INCREMENT,
  `clients_idClients` int NOT NULL,
  `Date_order` datetime NOT NULL,
  `price_order` decimal(19,2) DEFAULT NULL,
  `status_order` varchar(45) DEFAULT 'В работе',
  `dop_color_idDop_color` int NOT NULL,
  `dop_diski_idDop_diski` int NOT NULL,
  PRIMARY KEY (`catalogauto_idCatalogAuto`,`clients_idClients`,`dop_color_idDop_color`,`dop_diski_idDop_diski`,`Date_order`),
  KEY `fk_catalogauto_has_clients_clients1_idx` (`clients_idClients`),
  KEY `fk_catalogauto_has_clients_catalogauto1_idx` (`catalogauto_idCatalogAuto`),
  KEY `fk_catalogauto_has_clients_dop_color1_idx` (`dop_color_idDop_color`),
  KEY `fk_catalogauto_has_clients_dop_diski1_idx` (`dop_diski_idDop_diski`),
  CONSTRAINT `fk_catalogauto_has_clients_catalogauto1` FOREIGN KEY (`catalogauto_idCatalogAuto`) REFERENCES `catalogauto` (`idCatalogAuto`),
  CONSTRAINT `fk_catalogauto_has_clients_clients1` FOREIGN KEY (`clients_idClients`) REFERENCES `clients` (`idClients`),
  CONSTRAINT `fk_catalogauto_has_clients_dop_color1` FOREIGN KEY (`dop_color_idDop_color`) REFERENCES `dop_color` (`idDop_color`),
  CONSTRAINT `fk_catalogauto_has_clients_dop_diski1` FOREIGN KEY (`dop_diski_idDop_diski`) REFERENCES `dop_diski` (`idDop_diski`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogauto_has_clients`
--

LOCK TABLES `catalogauto_has_clients` WRITE;
/*!40000 ALTER TABLE `catalogauto_has_clients` DISABLE KEYS */;
INSERT INTO `catalogauto_has_clients` VALUES (4,18,'2023-11-20 02:38:18',56000000.00,'В работе',9,7),(5,18,'2023-11-20 15:13:35',56225000.00,'В работе',4,6),(5,18,'2023-11-20 00:51:00',56000000.00,'В работе',9,7),(5,18,'2023-11-20 20:40:07',56000000.00,'В работе',9,7),(7,18,'2023-11-20 20:11:11',596000000.00,'В работе',9,7),(8,18,'2023-11-09 00:00:00',56165000.00,'В работе',2,2);
/*!40000 ALTER TABLE `catalogauto_has_clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `idClients` int NOT NULL AUTO_INCREMENT,
  `ClientsName` varchar(45) DEFAULT NULL,
  `ClientsSurname` varchar(45) DEFAULT NULL,
  `ClientsPatronymic` varchar(45) DEFAULT NULL,
  `ClientsLogin` text,
  `ClientsPassword` text,
  `role_idrole` int NOT NULL,
  PRIMARY KEY (`idClients`,`role_idrole`),
  KEY `fk_clients_role1_idx` (`role_idrole`),
  CONSTRAINT `fk_clients_role1` FOREIGN KEY (`role_idrole`) REFERENCES `role` (`idrole`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (18,'Дмитрий','Орлов','Алексеевич','123','123',3),(19,'Николай','Приженцов','','323','232',3),(20,'143','124','789','login','432',3),(23,'1','2','3   ','4','5',3),(24,'Victoria','Soloveva','','Viksa','123456',3),(25,'Viksa','Tvixa','','Tvixi','123456789',3),(26,'hgh','ghfgh','fgfhg','fhghfg','fghfg',3),(27,'Алина','Травкина','Витальевна','travka','11052006',3);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dop_color`
--

DROP TABLE IF EXISTS `dop_color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dop_color` (
  `idDop_color` int NOT NULL AUTO_INCREMENT,
  `Dop_color_name` varchar(45) DEFAULT NULL,
  `Dop_color_Price` decimal(19,2) DEFAULT NULL,
  `Dop_colorPhoto` text,
  PRIMARY KEY (`idDop_color`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dop_color`
--

LOCK TABLES `dop_color` WRITE;
/*!40000 ALTER TABLE `dop_color` DISABLE KEYS */;
INSERT INTO `dop_color` VALUES (1,'Белоснежный',50000.00,'color1.jpg'),(2,'M Черный Карбон металлик',70000.00,'color2.jpg'),(3,'Черный Сапфир металлик',60000.00,'color3.jpg'),(4,'Белый Минерал металлик',55000.00,'color4.jpg'),(5,'Красный Авантюрин металлик',78000.00,'color5.jpg'),(6,'Individual Синий Танзанит металлик',50000.00,'color6.jpg'),(7,'Individual Аметрин металлик',50000.00,'color7.jpg'),(8,'Individual Морозный Чистый Серый металлик',50000.00,'color8.jpg'),(9,'Не требуются доп. цвет',0.00,'icons96.png');
/*!40000 ALTER TABLE `dop_color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dop_diski`
--

DROP TABLE IF EXISTS `dop_diski`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dop_diski` (
  `idDop_diski` int NOT NULL AUTO_INCREMENT,
  `Dop_diskiName` varchar(200) DEFAULT NULL,
  `Dop_diskiPrice` decimal(19,2) DEFAULT NULL,
  `Dop_diski_photo` text,
  PRIMARY KEY (`idDop_diski`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dop_diski`
--

LOCK TABLES `dop_diski` WRITE;
/*!40000 ALTER TABLE `dop_diski` DISABLE KEYS */;
INSERT INTO `dop_diski` VALUES (1,'20\" M легкосплавные диски Star-spoke 740 M Bicolour',80000.00,'disk1.jpg'),(2,'21\" M легкосплавные диски V-spoke style 915 M Bicolour',95000.00,'disk2.jpg'),(3,'21\" M легкосплавные диски Y-spoke 741 M Bicolour',100000.00,'disk3.jpg'),(4,'22\" M легкосплавные диски Double-spoke 742 M Bicolour',150000.00,'disk4.jpg'),(5,'22\" M легкосплавные диски Double-spoke 742 M',160000.00,'disk5.jpg'),(6,'22\" M легкосплавные диски V-spoke style 747 M Bicolour',170000.00,'disk6.jpg'),(7,'не требуются доп. диски',0.00,'icons96.png');
/*!40000 ALTER TABLE `dop_diski` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manufactur`
--

DROP TABLE IF EXISTS `manufactur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manufactur` (
  `idManufactur` int NOT NULL AUTO_INCREMENT,
  `Name_compani` varchar(45) DEFAULT NULL,
  `Contact_person` varchar(45) DEFAULT NULL,
  `telephone` varchar(45) DEFAULT NULL,
  `adress` varchar(45) DEFAULT NULL,
  `mail` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idManufactur`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manufactur`
--

LOCK TABLES `manufactur` WRITE;
/*!40000 ALTER TABLE `manufactur` DISABLE KEYS */;
INSERT INTO `manufactur` VALUES (1,'OOO Прайм','Кузенкова А.П','89544230123','г.Нижний Новгород','kuz@yandex.ru'),(2,'OOO Автомир','Кузенкова А.П','89544230123','г.Нижний Новгород','kuz@yandex.ru'),(3,'OOO Автоторг','Кузенкова А.П','89544230123','г.Нижний Новгород','kuz@yandex.ru'),(4,'OOO ПраймАвто','Кузенкова А.П','89544230123','г.Нижний Новгород','kuz@yandex.ru'),(5,'OOO АВТО','Кузенкова А.П','89544230123','г.Нижний Новгород','kuz@yandex.ru'),(6,'OOO АВТ','Кузенкова А.П','89544230123','г.Нижний Новгород','kuz@yandex.ru'),(7,'OOO Купи','Кузенкова А.П','89544230123','г.Нижний Новгород','kuz@yandex.ru'),(8,'OOO АВТОМечта','Кузенкова А.П','89544230123','г.Нижний Новгород','kuz@yandex.ru'),(9,'OOO Причина','Кузенкова А.П','89544230123','г.Нижний Новгород','kuz@yandex.ru'),(10,'OOO Классика','Кузенкова А.П','89544230123','г.Нижний Новгород','kuz@yandex.ru');
/*!40000 ALTER TABLE `manufactur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marka`
--

DROP TABLE IF EXISTS `marka`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `marka` (
  `idMarka` int NOT NULL AUTO_INCREMENT,
  `Marka_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idMarka`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marka`
--

LOCK TABLES `marka` WRITE;
/*!40000 ALTER TABLE `marka` DISABLE KEYS */;
INSERT INTO `marka` VALUES (1,'BMW'),(2,'AUDI');
/*!40000 ALTER TABLE `marka` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `model`
--

DROP TABLE IF EXISTS `model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `model` (
  `idModel` int NOT NULL AUTO_INCREMENT,
  `Modelname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idModel`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `model`
--

LOCK TABLES `model` WRITE;
/*!40000 ALTER TABLE `model` DISABLE KEYS */;
INSERT INTO `model` VALUES (1,'X1'),(2,'X2'),(3,'X3'),(4,'X4'),(5,'X5'),(6,'X6'),(7,'X7'),(8,'Q5'),(9,'Q6'),(10,'Q7'),(11,'Q8');
/*!40000 ALTER TABLE `model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `idrole` int NOT NULL,
  `namerole` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idrole`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Директор'),(2,'Менеджер'),(3,'Клиент');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `idStaff` int NOT NULL AUTO_INCREMENT,
  `StaffName` varchar(45) DEFAULT NULL,
  `StaffSurname` varchar(45) DEFAULT NULL,
  `StaffPost` varchar(45) DEFAULT NULL,
  `StaffSalary` decimal(19,2) DEFAULT NULL,
  `staffPatronymic` varchar(45) DEFAULT NULL,
  `role_idrole` int NOT NULL,
  `staffPas` text,
  `staffLog` text,
  PRIMARY KEY (`idStaff`,`role_idrole`),
  KEY `fk_staff_role1_idx` (`role_idrole`),
  CONSTRAINT `fk_staff_role1` FOREIGN KEY (`role_idrole`) REFERENCES `role` (`idrole`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (10,NULL,NULL,NULL,NULL,NULL,1,'123','dir'),(11,NULL,NULL,NULL,NULL,NULL,2,'1234','manager');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'avto4'
--
/*!50003 DROP PROCEDURE IF EXISTS `generate_sales_report` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generate_sales_report`()
BEGIN
  -- Создаем временную таблицу для хранения результатов отчета
  CREATE TEMPORARY TABLE sales_report (
    month_year varchar(7),
    total_sales decimal(19,2)
  );

  -- Заполняем временную таблицу данными о продажах по месяцам
  INSERT INTO sales_report (month_year, total_sales)
  SELECT DATE_FORMAT(Date_order, '%Y-%m') AS month_year, SUM(price_order) AS total_sales
  FROM catalogauto_has_clients
  GROUP BY DATE_FORMAT(Date_order, '%Y-%m');

  -- Выводим результаты отчета
  SELECT * FROM sales_report;
  
  -- Удаляем временную таблицу
  DROP TABLE sales_report;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-22 16:01:10
