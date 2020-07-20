/*
SQLyog v10.2 
MySQL - 5.7.17-log : Database - erp
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`erp` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `erp`;

/*Table structure for table `bus_customer` */

DROP TABLE IF EXISTS `bus_customer`;

CREATE TABLE `bus_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customername` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `connectionperson` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `bus_customer` */

insert  into `bus_customer`(`id`,`customername`,`zip`,`address`,`telephone`,`connectionperson`,`phone`,`bank`,`account`,`email`,`fax`,`available`) values (1,'小张超市','111','武汉','027-9123131','张大明','13812312312321312','中国银行','654431331343413','213123@sina.com','430000',1),(2,'小明超市','222','深圳','0755-9123131','张小明','13812312312321312','中国银行','654431331343413','213123@sina.com','430000',1),(3,'快七超市','430000','武汉','027-11011011','雷生','13434134131','招商银行','6543123341334133','6666@66.com','545341',1);

/*Table structure for table `bus_goods` */

DROP TABLE IF EXISTS `bus_goods`;

CREATE TABLE `bus_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodsname` varchar(255) DEFAULT NULL,
  `produceplace` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `goodspackage` varchar(255) DEFAULT NULL,
  `productcode` varchar(255) DEFAULT NULL,
  `promitcode` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `dangernum` int(11) DEFAULT NULL,
  `goodsimg` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  `providerid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_sq0btr2v2lq8gt8b4gb8tlk0i` (`providerid`) USING BTREE,
  CONSTRAINT `bus_goods_ibfk_1` FOREIGN KEY (`providerid`) REFERENCES `bus_provider` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `bus_goods` */

insert  into `bus_goods`(`id`,`goodsname`,`produceplace`,`size`,`goodspackage`,`productcode`,`promitcode`,`description`,`price`,`number`,`dangernum`,`goodsimg`,`available`,`providerid`) values (1,'娃哈哈','武汉','120ML','瓶','PH12345','PZ1234','小孩子都爱的',2,488,10,'defaultgoods.jpg',1,3),(2,'旺旺雪饼','仙桃仙桃','包','袋','PH12312312','PZ12312','好吃不上火',4,1100,10,'defaultgoods.jpg',1,1),(3,'旺旺大礼包','仙桃','盒','盒','11','11','111',28,1090,100,'defaultgoods.jpg',1,1);

/*Table structure for table `bus_inport` */

DROP TABLE IF EXISTS `bus_inport`;

CREATE TABLE `bus_inport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `paytype` varchar(255) DEFAULT NULL,
  `inporttime` datetime DEFAULT NULL,
  `operateperson` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `inportprice` double DEFAULT NULL,
  `providerid` int(11) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_1o4bujsyd2kl4iqw48fie224v` (`providerid`) USING BTREE,
  KEY `FK_ho29poeu4o2dxu4rg1ammeaql` (`goodsid`) USING BTREE,
  CONSTRAINT `bus_inport_ibfk_1` FOREIGN KEY (`providerid`) REFERENCES `bus_provider` (`id`),
  CONSTRAINT `bus_inport_ibfk_2` FOREIGN KEY (`goodsid`) REFERENCES `bus_goods` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `bus_inport` */

insert  into `bus_inport`(`id`,`paytype`,`inporttime`,`operateperson`,`number`,`remark`,`inportprice`,`providerid`,`goodsid`) values (1,'微信','2018-05-07 00:00:00','张三',100,'备注',3.5,3,1),(2,'支付宝','2018-05-07 00:00:00','张三',1000,'无',2.5,1,3),(3,'银联','2018-05-07 00:00:00','张三',100,'1231',111,1,3),(4,'银联','2018-05-07 00:00:00','张三',1000,'无',2,3,1),(5,'银联','2018-05-07 00:00:00','张三',100,'无',1,3,1),(6,'银联','2018-05-07 00:00:00','张三',100,'1231',2.5,1,2),(8,'支付宝','2018-05-07 00:00:00','张三',100,'',1,3,1),(10,'支付宝','2018-08-07 17:17:57','超级管理员',100,'sadfasfdsa',1.5,3,1),(11,'支付宝','2018-09-17 16:12:25','超级管理员',21,'21',21,1,3),(14,'支付宝','2020-07-19 22:27:22','超级管理员',1000,'测试测试',10,1,2),(16,'支付宝','2020-07-20 15:33:15','超级管理员',100,'退货测试',10,1,3);

/*Table structure for table `bus_outport` */

DROP TABLE IF EXISTS `bus_outport`;

CREATE TABLE `bus_outport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `providerid` int(11) DEFAULT NULL,
  `paytype` varchar(255) DEFAULT NULL,
  `outporttime` datetime DEFAULT NULL,
  `operateperson` varchar(255) DEFAULT NULL,
  `outportprice` double(10,2) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `bus_outport` */

insert  into `bus_outport`(`id`,`providerid`,`paytype`,`outporttime`,`operateperson`,`outportprice`,`number`,`remark`,`goodsid`) values (1,3,'微信','2019-08-16 08:19:50','超级管理员',12321321.00,1,'',1),(2,3,'微信','2019-08-16 08:26:54','超级管理员',12321321.00,11,'11',1),(4,1,'支付宝','2020-07-20 15:42:11','超级管理员',100.00,10,'退货测试',3);

/*Table structure for table `bus_provider` */

DROP TABLE IF EXISTS `bus_provider`;

CREATE TABLE `bus_provider` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `providername` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `connectionperson` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `bus_provider` */

insert  into `bus_provider`(`id`,`providername`,`zip`,`address`,`telephone`,`connectionperson`,`phone`,`bank`,`account`,`email`,`fax`,`available`) values (1,'旺旺食品','434000','仙桃仙桃','0728-4124312','小明','13413441141','中国农业银行','654124345131','12312321@sina.com','5123123',1),(2,'达利园食品','430000','汉川','0728-4124312','大明','13413441141','中国农业银行','654124345131','12312321@sina.com','5123123',1),(3,'娃哈哈集团','513131','杭州','21312','小晨','12312','建设银行','512314141541','123131','312312',1);

/*Table structure for table `bus_sales` */

DROP TABLE IF EXISTS `bus_sales`;

CREATE TABLE `bus_sales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerid` int(11) DEFAULT NULL,
  `paytype` varchar(255) DEFAULT NULL,
  `salestime` datetime DEFAULT NULL,
  `operateperson` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `saleprice` decimal(10,2) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `bus_sales` */

/*Table structure for table `bus_salesback` */

DROP TABLE IF EXISTS `bus_salesback`;

CREATE TABLE `bus_salesback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerid` int(11) DEFAULT NULL,
  `paytype` varchar(255) DEFAULT NULL,
  `salesbacktime` datetime DEFAULT NULL,
  `salebackprice` double(10,2) DEFAULT NULL,
  `operateperson` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `bus_salesback` */

/*Table structure for table `sys_dept` */

DROP TABLE IF EXISTS `sys_dept`;

CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `open` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL COMMENT '状态【0不可用1可用】',
  `ordernum` int(11) DEFAULT NULL COMMENT '排序码【为了调事显示顺序】',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_dept` */

insert  into `sys_dept`(`id`,`pid`,`title`,`open`,`remark`,`address`,`available`,`ordernum`,`createtime`) values (1,0,'总经办',1,'大BOSS','深圳',1,1,'2019-04-10 14:06:32'),(2,1,'销售部',0,'销售屌丝','武汉',1,2,'2019-04-10 14:06:32'),(3,1,'运营部',0,'无','武汉',1,3,'2019-04-10 14:06:32'),(4,1,'生产部',0,'无','武汉',1,4,'2019-04-10 14:06:32'),(5,2,'销售一部',0,'销售一部','武汉市',1,5,'2019-04-10 14:06:32'),(7,3,'运营一部',0,'运营一部','武汉',1,7,'2019-04-10 14:06:32'),(18,4,'生产一部',0,'生产食品','武汉',1,8,'2019-04-13 09:49:38'),(19,2,'销售二部',0,'销售二部','武汉市',1,9,'2020-07-09 23:40:36'),(26,2,'销售三部',0,'销售','济南市',1,10,'2020-07-10 17:59:44');

/*Table structure for table `sys_loginfo` */

DROP TABLE IF EXISTS `sys_loginfo`;

CREATE TABLE `sys_loginfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(255) DEFAULT NULL,
  `loginip` varchar(255) DEFAULT NULL,
  `logintime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=409 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_loginfo` */

insert  into `sys_loginfo`(`id`,`loginname`,`loginip`,`logintime`) values (6,'超级管理员-system','127.0.0.1','2018-12-22 09:20:43'),(9,'超级管理员-system','127.0.0.1','2018-12-22 09:29:58'),(10,'超级管理员-system','127.0.0.1','2018-12-22 09:36:49'),(169,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-08 21:18:25'),(170,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-08 21:43:20'),(171,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-08 22:07:11'),(172,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-08 22:21:12'),(173,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 09:33:48'),(174,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 09:46:23'),(175,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 09:56:30'),(176,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 10:03:07'),(177,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 10:12:14'),(178,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 10:15:44'),(179,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 11:05:11'),(180,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 13:27:43'),(181,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 13:37:47'),(182,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 14:21:14'),(183,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 14:23:12'),(184,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 14:52:41'),(185,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 15:23:04'),(186,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 15:41:01'),(187,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 22:42:45'),(188,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 23:05:20'),(189,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 23:16:52'),(190,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 23:35:20'),(191,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 23:39:51'),(192,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 23:42:29'),(193,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 23:49:15'),(194,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 23:51:07'),(195,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 23:52:34'),(196,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-09 23:55:19'),(197,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 00:08:27'),(198,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 11:45:32'),(199,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 11:52:48'),(200,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 15:59:48'),(201,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 16:36:25'),(202,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 16:40:59'),(203,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 16:46:16'),(204,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 16:54:14'),(205,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 17:01:36'),(206,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 17:07:17'),(207,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 17:09:19'),(208,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 17:36:56'),(209,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 18:55:51'),(210,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 19:03:32'),(211,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 19:26:55'),(212,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 19:51:42'),(213,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 23:26:59'),(214,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 23:37:37'),(215,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-10 23:58:40'),(216,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 00:02:20'),(217,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 11:24:05'),(218,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 11:27:55'),(219,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 13:16:06'),(220,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 13:55:52'),(221,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 14:06:00'),(222,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 16:03:55'),(223,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 16:10:20'),(224,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 16:17:15'),(225,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 16:39:42'),(226,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 16:43:53'),(227,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 16:53:38'),(228,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 17:08:39'),(229,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 17:35:34'),(230,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 17:42:42'),(231,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 18:51:32'),(232,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 18:53:43'),(233,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 18:55:52'),(234,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 19:43:58'),(235,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 21:06:35'),(236,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 21:52:07'),(237,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 22:04:36'),(238,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 22:32:45'),(239,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 22:46:33'),(240,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-11 22:53:16'),(241,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-12 21:49:44'),(242,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-12 21:55:32'),(243,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-12 22:20:40'),(244,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-12 22:24:30'),(245,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-12 22:32:40'),(246,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-12 22:39:02'),(247,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-12 23:10:05'),(248,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-12 23:13:45'),(249,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-12 23:22:23'),(250,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-12 23:29:48'),(251,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-12 23:33:19'),(252,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-13 10:46:07'),(253,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-13 10:48:01'),(254,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-13 10:50:32'),(255,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-13 10:56:49'),(256,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-13 14:06:29'),(257,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-13 16:04:04'),(258,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-13 17:47:32'),(259,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-13 18:10:34'),(260,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-13 20:40:42'),(261,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 09:37:56'),(262,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 11:31:05'),(263,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 11:55:38'),(264,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 17:39:09'),(265,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 18:17:27'),(266,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 18:20:10'),(267,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 18:21:02'),(268,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 18:24:22'),(269,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 18:31:57'),(270,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 19:52:28'),(271,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 20:21:50'),(272,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 20:45:40'),(273,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 20:50:39'),(274,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 21:26:35'),(275,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 21:28:58'),(276,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 21:34:22'),(277,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 21:39:52'),(278,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 21:49:28'),(279,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 21:50:55'),(280,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 21:55:41'),(281,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 21:59:59'),(282,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 22:03:42'),(283,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 22:16:42'),(284,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 22:38:59'),(285,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 22:40:35'),(286,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 22:42:44'),(287,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-14 22:48:15'),(288,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 00:11:59'),(289,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 00:14:04'),(290,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 00:17:03'),(291,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 00:26:37'),(292,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 00:31:02'),(293,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 00:42:07'),(294,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 00:47:02'),(295,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 01:09:40'),(296,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 11:27:21'),(297,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 11:30:18'),(298,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 11:51:03'),(299,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 14:04:04'),(300,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 14:10:53'),(301,'刘八-lb','0:0:0:0:0:0:0:1','2020-07-15 14:11:08'),(302,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 14:11:40'),(303,'刘八-lb','0:0:0:0:0:0:0:1','2020-07-15 14:12:28'),(304,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 14:13:26'),(305,'刘八-lb','0:0:0:0:0:0:0:1','2020-07-15 14:13:45'),(306,'李四-ls','0:0:0:0:0:0:0:1','2020-07-15 14:15:22'),(307,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 14:21:15'),(308,'测试-ceshi','0:0:0:0:0:0:0:1','2020-07-15 14:21:46'),(309,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 14:22:01'),(310,'王五-ww','0:0:0:0:0:0:0:1','2020-07-15 14:22:33'),(311,'赵六-zl','0:0:0:0:0:0:0:1','2020-07-15 14:23:44'),(312,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 17:42:42'),(313,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 17:55:44'),(314,'测试-ceshi','0:0:0:0:0:0:0:1','2020-07-15 18:03:30'),(315,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 18:03:44'),(316,'刘八-lb','0:0:0:0:0:0:0:1','2020-07-15 18:04:24'),(317,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 19:12:28'),(318,'测试-ceshi','0:0:0:0:0:0:0:1','2020-07-15 19:14:31'),(319,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 20:13:58'),(320,'测试-ceshi','0:0:0:0:0:0:0:1','2020-07-15 20:16:12'),(321,'刘八-lb','0:0:0:0:0:0:0:1','2020-07-15 23:21:51'),(322,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 23:22:17'),(323,'刘八-lb','0:0:0:0:0:0:0:1','2020-07-15 23:23:05'),(324,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 23:23:44'),(325,'刘八-lb','0:0:0:0:0:0:0:1','2020-07-15 23:27:52'),(326,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 23:29:18'),(327,'刘八-lb','0:0:0:0:0:0:0:1','2020-07-15 23:29:44'),(328,'刘八-lb','0:0:0:0:0:0:0:1','2020-07-15 23:30:09'),(329,'刘八-lb','0:0:0:0:0:0:0:1','2020-07-15 23:39:25'),(330,'刘八-lb','0:0:0:0:0:0:0:1','2020-07-15 23:41:50'),(331,'刘八-lb','0:0:0:0:0:0:0:1','2020-07-15 23:42:21'),(332,'刘八-lb','0:0:0:0:0:0:0:1','2020-07-15 23:42:52'),(333,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-15 23:48:51'),(334,'刘八-lb','0:0:0:0:0:0:0:1','2020-07-15 23:50:38'),(335,'刘八-lb','0:0:0:0:0:0:0:1','2020-07-15 23:51:00'),(336,'刘八-lb','0:0:0:0:0:0:0:1','2020-07-15 23:51:32'),(337,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-16 11:54:42'),(338,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-16 11:57:11'),(339,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-16 11:58:50'),(340,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-16 12:00:59'),(341,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-16 13:37:28'),(342,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-16 13:41:19'),(343,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-16 18:39:36'),(344,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-16 18:57:50'),(345,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-16 21:49:28'),(346,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-16 22:18:11'),(347,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-16 22:34:33'),(348,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-17 11:30:36'),(349,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-17 11:46:48'),(350,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-17 12:18:46'),(351,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-17 12:54:29'),(352,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 11:35:10'),(353,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 11:37:25'),(354,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 11:38:13'),(355,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 11:39:49'),(356,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 11:50:58'),(357,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 12:31:43'),(358,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 12:34:17'),(359,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 16:26:29'),(360,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 16:45:22'),(361,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 17:10:56'),(362,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 17:13:59'),(363,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 18:18:26'),(364,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 18:20:52'),(365,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 18:22:19'),(366,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 18:24:30'),(367,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 18:33:13'),(368,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 18:44:28'),(369,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 18:45:11'),(370,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 18:57:12'),(371,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 19:12:48'),(372,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 19:28:01'),(373,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 19:35:52'),(374,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 19:54:07'),(375,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 20:04:56'),(376,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-18 20:09:02'),(377,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-19 18:49:57'),(378,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-19 19:06:31'),(379,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-19 19:10:28'),(380,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-19 21:04:00'),(381,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-19 21:09:42'),(382,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-19 21:25:46'),(383,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-19 21:28:02'),(384,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-19 21:29:36'),(385,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-19 22:07:39'),(386,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-19 22:21:01'),(387,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-19 22:31:05'),(388,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 09:49:37'),(389,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 09:59:27'),(390,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 10:03:55'),(391,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 10:07:18'),(392,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 10:12:48'),(393,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 10:14:11'),(394,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 10:15:17'),(395,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 10:29:49'),(396,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 11:25:21'),(397,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 11:41:49'),(398,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 12:45:13'),(399,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 14:33:09'),(400,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 15:31:36'),(401,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 15:35:16'),(402,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 15:41:32'),(403,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 15:52:15'),(404,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 15:56:44'),(405,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 15:58:33'),(406,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 16:04:12'),(407,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 16:05:32'),(408,'超级管理员-system','0:0:0:0:0:0:0:1','2020-07-20 16:06:55');

/*Table structure for table `sys_notice` */

DROP TABLE IF EXISTS `sys_notice`;

CREATE TABLE `sys_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` text,
  `createtime` datetime DEFAULT NULL,
  `opername` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_notice` */

insert  into `sys_notice`(`id`,`title`,`content`,`createtime`,`opername`) values (1,'关于系统V1.3更新公告','2ddd','2020-07-09 10:35:24','管理员'),(10,'关于系统V1.2更新公告','12312312<img src=\"/resources/layui/images/face/42.gif\" alt=\"[抓狂]\">','2018-08-07 00:00:00','超级管理员'),(11,'关于系统V1.1更新公告','21321321321<img src=\"/resources/layui/images/face/18.gif\" alt=\"[右哼哼]\">','2018-08-07 00:00:00','超级管理员'),(12,'\"dafa\"','\"dafdaf\"','2020-07-09 10:13:46','超级管理员');

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL COMMENT '权限类型[menu/permission]',
  `title` varchar(255) DEFAULT NULL,
  `percode` varchar(255) DEFAULT NULL COMMENT '权限编码[只有type= permission才有  user:view]',
  `icon` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `open` int(11) DEFAULT NULL,
  `ordernum` int(11) DEFAULT NULL,
  `available` int(11) DEFAULT NULL COMMENT '状态【0不可用1可用】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`pid`,`type`,`title`,`percode`,`icon`,`href`,`target`,`open`,`ordernum`,`available`) values (1,0,'menu','尚学堂进销存管理系统',NULL,'&#xe68e;','','',1,1,1),(2,1,'menu','基础管理',NULL,'&#xe857;','','',1,2,1),(3,1,'menu','进货管理',NULL,'&#xe645;','',NULL,0,3,1),(4,1,'menu','销售管理',NULL,'&#xe611;','','',0,4,1),(5,1,'menu','系统管理',NULL,'&#xe614;','','',0,5,1),(6,1,'menu','其它管理',NULL,'&#xe628;','','',0,6,1),(7,2,'menu','客户管理',NULL,'&#xe651;','/bus/toCustomerManager','',0,7,1),(8,2,'menu','供应商管理',NULL,'&#xe658;','/bus/toProviderManager','',0,8,1),(9,2,'menu','商品管理',NULL,'&#xe657;','/bus/toGoodsManager','',0,9,1),(10,3,'menu','商品进货',NULL,'&#xe756;','/bus/toInportManager','',0,10,1),(11,3,'menu','商品退货查询',NULL,'&#xe65a;','/bus/toOutportManager','',0,11,1),(12,4,'menu','商品销售',NULL,'&#xe65b;','','',0,12,1),(13,4,'menu','销售退货查询',NULL,'&#xe770;','','',0,13,1),(14,5,'menu','部门管理',NULL,'&#xe770;','/sys/toDeptManager','',0,14,1),(15,5,'menu','菜单管理',NULL,'&#xe857;','/sys/toMenuManager','',0,15,1),(16,5,'menu','权限管理','','&#xe857;','/sys/toPermissionManager','',0,16,1),(17,5,'menu','角色管理','','&#xe650;','/sys/toRoleManager','',0,17,1),(18,5,'menu','用户管理','','&#xe612;','/sys/toUserManager','',0,18,1),(21,6,'menu','登陆日志',NULL,'&#xe675;','/sys/toLoginfoManager','',0,21,1),(22,6,'menu','系统公告',NULL,'&#xe756;','/sys/toNoticeManager',NULL,0,22,1),(23,6,'menu','图标管理',NULL,'&#xe670;','https://www.layui.com/doc/element/icon.html',NULL,0,23,1),(30,14,'permission','添加部门','dept:create','',NULL,NULL,0,24,1),(31,14,'permission','修改部门','dept:update','',NULL,NULL,0,26,1),(32,14,'permission','删除部门','dept:delete','',NULL,NULL,0,27,1),(34,15,'permission','添加菜单','menu:create','','','',0,29,1),(35,15,'permission','修改菜单','menu:update','',NULL,NULL,0,30,1),(36,15,'permission','删除菜单','menu:delete','',NULL,NULL,0,31,1),(38,16,'permission','添加权限','permission:create','',NULL,NULL,0,33,1),(39,16,'permission','修改权限','permission:update','',NULL,NULL,0,34,1),(40,16,'permission','删除权限','permission:delete','',NULL,NULL,0,35,1),(42,17,'permission','添加角色','role:create','',NULL,NULL,0,37,1),(43,17,'permission','修改角色','role:update','',NULL,NULL,0,38,1),(44,17,'permission','角色删除','role:delete','',NULL,NULL,0,39,1),(46,17,'permission','分配权限','role:selectPermission','',NULL,NULL,0,41,1),(47,18,'permission','添加用户','user:create','',NULL,NULL,0,42,1),(48,18,'permission','修改用户','user:update','',NULL,NULL,0,43,1),(49,18,'permission','删除用户','user:delete','',NULL,NULL,0,44,1),(51,18,'permission','用户分配角色','user:selectRole','',NULL,NULL,0,46,1),(52,18,'permission','重置密码','user:resetPwd',NULL,NULL,NULL,0,47,1),(53,14,'permission','部门查询','dept:view',NULL,NULL,NULL,0,48,1),(54,15,'permission','菜单查询','menu:view',NULL,NULL,NULL,0,49,1),(55,16,'permission','权限查询','permission:view',NULL,NULL,NULL,0,50,1),(56,17,'permission','角色查询','role:view',NULL,NULL,NULL,0,51,1),(57,18,'permission','用户查询','user:view',NULL,NULL,NULL,0,52,1),(68,7,'permission','客户查询','customer:view',NULL,NULL,NULL,0,60,1),(69,7,'permission','客户添加','customer:create',NULL,NULL,NULL,0,61,1),(70,7,'permission','客户修改','customer:update',NULL,NULL,NULL,0,62,1),(71,7,'permission','客户删除','customer:delete',NULL,NULL,NULL,0,63,1),(73,21,'permission','日志查询','info:view',NULL,NULL,NULL,0,65,1),(74,21,'permission','日志删除','info:delete',NULL,NULL,NULL,0,66,1),(75,21,'permission','日志批量删除','info:batchdelete',NULL,NULL,NULL,0,67,1),(76,22,'permission','公告查询','notice:view',NULL,NULL,NULL,0,68,1),(77,22,'permission','公告添加','notice:create',NULL,NULL,NULL,0,69,1),(78,22,'permission','公告修改','notice:update',NULL,NULL,NULL,0,70,1),(79,22,'permission','公告删除','notice:delete',NULL,NULL,NULL,0,71,1),(81,8,'permission','供应商查询','provider:view',NULL,NULL,NULL,0,73,1),(82,8,'permission','供应商添加','provider:create',NULL,NULL,NULL,0,74,1),(83,8,'permission','供应商修改','provider:update',NULL,NULL,NULL,0,75,1),(84,8,'permission','供应商删除','provider:delete',NULL,NULL,NULL,0,76,1),(86,22,'permission','公告查看','notice:viewnotice',NULL,NULL,NULL,0,78,1),(91,9,'permission','商品查询','goods:view',NULL,NULL,NULL,0,79,1),(92,9,'permission','商品添加','goods:create',NULL,NULL,NULL,0,80,1),(93,14,'permission','部门批量删除','dept:batchdelete',NULL,NULL,NULL,0,81,1),(94,15,'permission','菜单批量删除','menu:batchdelete',NULL,NULL,NULL,0,82,1);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`remark`,`available`,`createtime`) values (1,'超级管理员','拥有所有菜单权限',1,'2019-04-10 14:06:32'),(4,'基础数据管理员','基础数据管理员',1,'2019-04-10 14:06:32'),(5,'仓库管理员','仓库管理员',1,'2019-04-10 14:06:32'),(6,'销售管理员','销售管理员',1,'2019-04-10 14:06:32'),(7,'系统管理员','系统管理员',1,'2019-04-10 14:06:32');

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `rid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`pid`,`rid`) USING BTREE,
  KEY `FK_tcsr9ucxypb3ce1q5qngsfk33` (`rid`) USING BTREE,
  CONSTRAINT `sys_role_permission_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `sys_permission` (`id`) ON DELETE CASCADE,
  CONSTRAINT `sys_role_permission_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`rid`,`pid`) values (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,21),(1,22),(1,23),(1,30),(1,31),(1,32),(1,34),(1,35),(1,36),(1,38),(1,39),(1,40),(1,42),(1,43),(1,44),(1,46),(1,47),(1,48),(1,49),(1,51),(1,52),(1,53),(1,54),(1,55),(1,56),(1,57),(1,68),(1,69),(1,70),(1,71),(1,73),(1,74),(1,75),(1,76),(1,77),(1,78),(1,79),(1,81),(1,82),(1,83),(1,84),(1,86),(1,91),(1,92),(1,93),(1,94),(4,1),(4,2),(4,7),(4,8),(4,9),(4,68),(4,69),(4,70),(4,71),(4,81),(4,82),(4,83),(4,84),(4,91),(4,92),(5,1),(5,3),(5,4),(5,10),(5,11),(5,12),(5,13),(6,1),(6,4),(6,12),(6,13),(7,1),(7,5),(7,14),(7,15),(7,16),(7,17),(7,18),(7,30),(7,31),(7,32),(7,34),(7,35),(7,36),(7,38),(7,39),(7,40),(7,42),(7,43),(7,44),(7,46),(7,47),(7,48),(7,49),(7,51),(7,52),(7,53),(7,54),(7,55),(7,56),(7,57),(7,93),(7,94);

/*Table structure for table `sys_role_user` */

DROP TABLE IF EXISTS `sys_role_user`;

CREATE TABLE `sys_role_user` (
  `rid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`uid`,`rid`) USING BTREE,
  KEY `FK_203gdpkwgtow7nxlo2oap5jru` (`rid`) USING BTREE,
  CONSTRAINT `sys_role_user_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `sys_role_user_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_role_user` */

insert  into `sys_role_user`(`rid`,`uid`) values (1,1),(1,2),(4,3),(5,4),(5,7),(6,5),(6,7),(7,6);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `loginname` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  `deptid` int(11) DEFAULT NULL,
  `hiredate` datetime DEFAULT NULL,
  `mgr` int(11) DEFAULT NULL,
  `available` int(11) DEFAULT '1',
  `ordernum` int(11) DEFAULT NULL,
  `type` int(255) DEFAULT NULL COMMENT '用户类型[0超级管理员1，管理员，2普通用户]',
  `imgpath` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `salt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_3rrcpvho2w1mx1sfiuuyir1h` (`deptid`) USING BTREE,
  CONSTRAINT `sys_user_ibfk_1` FOREIGN KEY (`deptid`) REFERENCES `sys_dept` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`name`,`loginname`,`address`,`sex`,`remark`,`pwd`,`deptid`,`hiredate`,`mgr`,`available`,`ordernum`,`type`,`imgpath`,`salt`) values (1,'超级管理员','system','系统深处的男人',1,'超级管理员','532ac00e86893901af5f0be6b704dbc7',1,'2018-06-25 11:06:34',NULL,1,1,0,'../resources/images/defaultusertitle.jpg','04A93C74C8294AA09A8B974FD1F4ECBB'),(2,'李四','ls','武汉',0,'KING','b07b848d69e0553b80e601d31571797e',1,'2018-06-25 11:06:36',NULL,1,2,1,'../resources/images/defaultusertitle.jpg','FC1EE06AE4354D3FBF7FDD15C8FCDA71'),(3,'王五','ww','武汉',1,'管理员','3c3f971eae61e097f59d52360323f1c8',3,'2018-06-25 11:06:38',2,1,3,1,'../resources/images/defaultusertitle.jpg','3D5F956E053C4E85B7D2681386E235D2'),(4,'赵六','zl','武汉',1,'程序员','2e969742a7ea0c7376e9551d578e05dd',4,'2018-06-25 11:06:40',3,1,4,1,'../resources/images/defaultusertitle.jpg','6480EE1391E34B0886ACADA501E31145'),(5,'孙七','sq','武汉',1,'程序员','47b4c1ad6e4b54dd9387a09cb5a03de1',2,'2018-06-25 11:06:43',4,1,5,1,'../resources/images/defaultusertitle.jpg','FE3476C3E3674E5690C737C269FCBF8E'),(6,'刘八','lb','深圳',1,'程序员','bcee2b05b4b591106829aec69a094806',4,'2018-08-06 11:21:12',3,1,6,1,'../resources/images/defaultusertitle.jpg','E6CCF54A09894D998225878BBD139B20'),(7,'测试','ceshi','济南',1,'测试','648d496b979027dc848e02a6a7d05534',4,'2020-07-15 00:00:00',4,1,7,1,NULL,'6C5C6515426647AFAC8B4C5926E15396');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
