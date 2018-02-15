CREATE DATABASE  IF NOT EXISTS `uusale` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `uusale`;
-- MySQL dump 10.13  Distrib 5.6.17, for osx10.6 (i386)
--
-- Host: 127.0.0.1    Database: uusale
-- ------------------------------------------------------
-- Server version	5.7.20

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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` char(36) COLLATE utf8_unicode_ci NOT NULL,
  `mch_id` char(36) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` decimal(18,5) DEFAULT NULL,
  `imgs` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_product_merchant_idx` (`mch_id`),
  CONSTRAINT `fk_product_merchant` FOREIGN KEY (`mch_id`) REFERENCES `merchant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('10ce8164-9bef-4b35-a99b-fbf729257b8b','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','恍如梦境','在一蜾要工',3.50000,'c03de487-cac8-4180-8cfd-2659e92dcf46.jpg;88abc98b-f36d-4e41-ad9c-dd05d2a7c5ec.png;cb04f59b-f10d-4169-9f83-2e7cd0eb1b9e.jpg','2018-02-15 05:28:09'),('171f0007-63c0-4e94-9bb7-d80d5c3e0d98','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','a','a',1.00000,NULL,'2018-02-15 05:28:09'),('1d5909da-c3a0-47fb-892e-7e462e019b20','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','1',NULL,1.10000,NULL,'2018-02-15 05:28:09'),('2e25808b-92ff-48f9-9150-67cb5b04ccbb','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','手机',NULL,3.00000,'f3c9e8fd-6efb-4b9c-b9f9-da583f641a62.jpg','2018-02-15 05:28:09'),('2e80158a-27f0-4a79-b305-ca2973722364','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','3','5',4.00000,'113f34b0-8cd1-49f4-b598-51c9b907b6fc.JPG;1f8da21e-8a49-48e3-b858-2a677542a984.JPG;9711d612-0ab8-4bfc-93ac-68ebbee3ac2c.JPG','2018-02-15 05:28:09'),('3c5dc092-33b7-4608-9e84-72ab43b3ac46','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','abc','wefsdfsdf',324.00000,'62c37a3b-1600-49da-803d-b8ceeeae84b0.JPG','2018-02-15 05:28:09'),('3f151180-27d4-43b8-aad2-915dc3dae4e1','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','新的商品','这是一个测试用的商品',9.87000,'7d4ce4dd-d021-4580-948a-e130e872dd33.jpg','2018-02-15 05:28:09'),('408f4b0a-a9f9-4532-ad8a-3dcbcc4641a5','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','1','3',2.00000,'4f002ca7-759e-44e0-a538-e2201237139a.JPG','2018-02-15 05:28:09'),('4a819df3-2edd-43b1-8979-256b869dea40','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','s',NULL,3.00000,NULL,'2018-02-15 05:28:09'),('4f9945cc-5681-4f2a-8c36-e4d31495c20c','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','234','234',234.26000,'b9e8cb26-6688-45cd-a633-4b43b004564a.JPG;290f66a8-a729-4257-a707-a3e88081f66e.JPG','2018-02-15 05:28:09'),('6776b45a-5f17-4cfa-a4df-7f5da48a81f0','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','4532','345',543.00000,'31c3cea6-844b-4d41-aa48-df89f27093dd.JPG','2018-02-15 05:28:09'),('6b55db57-bdbb-4bf3-bcd0-0204fbe88d6b','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','西红柿','红红红',8.91000,'a024bb2c-1c15-4c1c-a7db-e5893f783ba8.JPG;39acda34-2bcc-4811-9506-143e70ebeb21.JPG;c6aa2d8a-1ff0-42c1-a5b7-f0033a1f3836.JPG;eba815f6-9659-4a0e-b053-17b7e553f75b.JPG;f309f9c3-e4e7-4212-bc47-1f8ade4901cf.JPG','2018-02-15 05:28:09'),('7d80ca44-1a22-4e17-bc34-9d23dbcbfd29','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','f','3',3.00000,'b452b0a1-1c8b-4d0e-9736-ce7599972897.PNG','2018-02-15 05:28:09'),('8af99a46-8743-4c7d-891b-06ad4eccc2f4','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','生菜','好吃的生菜',2.50000,'b742841e-93e5-4aef-8a16-535177feb8da.JPG;0e561bda-0994-4408-b75f-3529fd76df3c.JPG;31c88032-0f3c-461c-a3f9-fd4cbc9ae048.JPG;2f8bb7b4-6d47-40b3-8595-f19b1dba915e.JPG;1936bbe9-456b-4118-8afc-660fbca5197f.JPG','2018-02-15 05:28:09'),('a8825a9c-4c79-4258-b87d-aa3e5b065c18','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','又一个商品',NULL,3.40000,NULL,'2018-02-15 05:28:09'),('db6b55fe-a238-49c9-87f3-e3be0a83c1d8','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','234','234',234.00000,NULL,'2018-02-15 05:28:09'),('f3034324-d29f-4b87-80b1-e9e2f69a530d','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','2','4',3.00000,'834f66c7-f546-4e64-a2fe-2470b8686005.JPG;88b2dec3-f907-4d70-b650-2509e0263192.JPG;e795b225-bdfc-4085-8563-eff107e57fff.JPG','2018-02-15 05:28:09'),('fdcbd050-7fd6-4dfb-ae61-b99b4ebc4f3d','5ad47cb3-8fbf-4aa2-a517-12a1d73d1dcf','2','ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff765756756',2.21000,'d0719565-2758-43bd-b894-f7ef8bf1b6f0.JPG;227aa96e-2e30-4ad0-92d7-8104d61a0084.JPG;141e8a63-bbfe-4c18-ad38-039dbd1da613.JPG;55c21f05-20c4-4a79-bb86-1af03c1b747c.JPG;381c74be-bfe7-4cdd-a878-ef6c7ef9fcab.JPG','2018-02-15 05:28:09');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-15 13:30:03
