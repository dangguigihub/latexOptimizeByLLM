-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: demo
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `ai_models`
--

DROP TABLE IF EXISTS `ai_models`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ai_models` (
  `id` int NOT NULL AUTO_INCREMENT,
  `model_name` varchar(50) NOT NULL,
  `provider` varchar(50) NOT NULL,
  `base_url` varchar(255) DEFAULT NULL,
  `is_enabled` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `model_name` (`model_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_models`
--

LOCK TABLES `ai_models` WRITE;
/*!40000 ALTER TABLE `ai_models` DISABLE KEYS */;
INSERT INTO `ai_models` VALUES (1,'DeepSeek-R1(7B)','OpenAI','https://api.openai.com/v1/chat/completions',1),(2,'Claude-3-opus','Anthropic','https://api.anthropic.com/v1/messages',1),(3,'Llama-3-8B','Meta','https://llama.meta.com/api/v1/inference',0),(4,'ERNIE-4.0','Baidu','https://aip.baidu.com/rpc/2.0/ai_custom',1),(5,'Gemini-1.5','Google','https://generativelanguage.googleapis.com/v1beta',1),(6,'GPT-4-turbo','Meta','https://baidu.com',1),(7,'GPT-4-turbo','Baidu','https://llama.meta.com/api/v1/inference',1);
/*!40000 ALTER TABLE `ai_models` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `model_id` int NOT NULL,
  `original_content` text NOT NULL,
  `polished_content` text,
  `request_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('success','failed') DEFAULT 'success',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `model_id` (`model_id`),
  CONSTRAINT `history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `history_ibfk_2` FOREIGN KEY (`model_id`) REFERENCES `ai_models` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_quotas`
--

DROP TABLE IF EXISTS `user_quotas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_quotas` (
  `user_id` int NOT NULL,
  `model_id` int NOT NULL,
  `total_quota` int DEFAULT '100',
  `used_quota` int DEFAULT '0',
  `api_key` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`model_id`),
  KEY `model_id` (`model_id`),
  CONSTRAINT `user_quotas_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `user_quotas_ibfk_2` FOREIGN KEY (`model_id`) REFERENCES `ai_models` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_quotas`
--

LOCK TABLES `user_quotas` WRITE;
/*!40000 ALTER TABLE `user_quotas` DISABLE KEYS */;
INSERT INTO `user_quotas` VALUES (1,1,1000,327,'afgsfds'),(1,2,500,500,'asfgfd'),(1,4,800,112,'vqw3r4twg'),(2,1,2000,1843,'qfrge'),(2,3,300,0,'gthhgf'),(2,5,1500,892,'qewrgehr'),(3,2,750,750,'ghgre'),(3,4,1200,456,'ggtre'),(3,5,600,213,'fgtre'),(5,1,1500,1321,'fdewerf'),(5,2,800,645,'fefe'),(5,4,900,312,'fdw'),(11,1,1800,921,'1f93ce69-cd69-4b24-8953-c2290cde1519'),(11,3,1000,0,'wteytryyt'),(11,4,1000,0,'rwtehger');
/*!40000 ALTER TABLE `user_quotas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'zs1123','2591268016@qq.com','827ccb0eea8a706c4c34a16891f84e7b','2025-05-15 10:42:28'),(2,'1231','1ed@qq.com','33eaa094f1e906c384166e3a305dea44','2025-05-15 12:57:41'),(3,'2321','259126@qq.com','91fd522141b94c2bbe5c4bc44d0c1313','2025-05-15 13:03:02'),(5,'12312','25ds9126@qq.com','6201efc00177de669e12e33bf0955079','2025-05-15 13:24:32'),(6,'12312d12','25ds219126@qq.com','6201efc00177de669e12e33bf0955079','2025-05-15 13:25:31'),(7,'1231dcv','2591d1s26@qq.com','873afdfba425f8ea51b81e4da40e3fb2','2025-05-15 13:27:37'),(8,'1231dd11','259121dfq26@qq.com','d5308cf785a7e9c500134a96c1039646','2025-05-15 13:28:02'),(9,'ddqfq','25912dacq6@qq.com','1b2f1afd7579c2594007e1533ec3c106','2025-05-15 13:32:31'),(10,'1231e','1e121213d@qq.com','160ccec311c13a6f50f8c149070afec7','2025-05-15 13:50:45'),(11,'abc','2591212ert6@qq.com','202cb962ac59075b964b07152d234b70','2025-05-15 13:55:16'),(12,'abdc','gfhdj@qq.com','2a60bc1f15995300293f8f7711037b3f','2025-05-17 09:02:59');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'demo'
--

--
-- Dumping routines for database 'demo'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-19 16:46:05
