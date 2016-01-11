CREATE DATABASE  IF NOT EXISTS `zzwork` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `zzwork`;
-- MySQL dump 10.13  Distrib 5.5.37, for debian-linux-gnu (x86_64)
--
-- Host: 192.168.1.200    Database: zzwork
-- ------------------------------------------------------
-- Server version	5.1.73-log

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
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attendance` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT '' COMMENT '姓名',
  `code` varchar(45) NOT NULL DEFAULT '' COMMENT '考勤表的登记号码',
  `account` varchar(45) NOT NULL DEFAULT '' COMMENT '系统中的账户',
  `gmt_work` datetime NOT NULL,
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  `schedule_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`),
  KEY `idx_code` (`code`),
  KEY `idx_account` (`account`),
  KEY `idx_gmt_work` (`gmt_work`),
  KEY `idx_schedule_id` (`schedule_id`)
) ENGINE=MyISAM AUTO_INCREMENT=25988 DEFAULT CHARSET=utf8 COMMENT='考勤原始数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendance_analysis`
--

DROP TABLE IF EXISTS `attendance_analysis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attendance_analysis` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL DEFAULT '',
  `name` varchar(45) NOT NULL DEFAULT '',
  `account` varchar(45) NOT NULL DEFAULT '',
  `day_full` decimal(7,3) DEFAULT '0.000' COMMENT '当月应出勤天数',
  `day_actual` decimal(7,3) DEFAULT '0.000' COMMENT '实际出勤天数',
  `day_leave` decimal(7,3) DEFAULT '0.000' COMMENT '请假天数',
  `day_other` decimal(7,3) DEFAULT '0.000' COMMENT '其他天数(单位小时)',
  `day_unwork` decimal(7,3) DEFAULT '0.000' COMMENT '旷工天数',
  `day_unrecord` int(11) DEFAULT '0' COMMENT '未打卡次数',
  `day_late` int(11) DEFAULT '0' COMMENT '迟到次数',
  `day_early` int(11) DEFAULT '0' COMMENT '早退次数',
  `day_overtime` int(11) DEFAULT '0' COMMENT '加班次数',
  `gmt_target` datetime NOT NULL,
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `schedule_id` int(20) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`),
  KEY `idx_name` (`name`),
  KEY `idx_account` (`account`),
  KEY `idx_gmt_target` (`gmt_target`),
  KEY `idx_schedule_id` (`schedule_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6718 DEFAULT CHARSET=utf8 COMMENT='考勤统计分析结果';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance_analysis`
--

LOCK TABLES `attendance_analysis` WRITE;
/*!40000 ALTER TABLE `attendance_analysis` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendance_analysis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendance_count`
--

DROP TABLE IF EXISTS `attendance_count`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attendance_count` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL DEFAULT '' COMMENT '考勤表的登记号码',
  `name` varchar(45) NOT NULL DEFAULT '' COMMENT '姓名',
  `account` varchar(45) NOT NULL DEFAULT '' COMMENT '系统中的账户',
  `punch0` int(11) NOT NULL DEFAULT '0' COMMENT '未打卡次数',
  `punch20` int(11) NOT NULL DEFAULT '0' COMMENT '迟到/早退 20分钟内',
  `punch60` int(11) NOT NULL DEFAULT '0' COMMENT '迟到/早退 60分钟内',
  `punch_count` int(11) NOT NULL DEFAULT '0' COMMENT '非正常打卡总次数',
  `gmt_month` datetime NOT NULL COMMENT '统计月份',
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`),
  KEY `idx_name` (`name`),
  KEY `idx_account` (`account`),
  KEY `idx_gmt_month` (`gmt_month`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='考勤统计结果';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance_count`
--

LOCK TABLES `attendance_count` WRITE;
/*!40000 ALTER TABLE `attendance_count` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendance_count` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendance_schedule`
--

DROP TABLE IF EXISTS `attendance_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attendance_schedule` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT '' COMMENT '班次名称',
  `isuse` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否正常使用\n1：正常（默认）\n0：未使用',
  `created_by` varchar(45) NOT NULL DEFAULT '' COMMENT '创建者',
  `modified_by` varchar(45) NOT NULL DEFAULT '' COMMENT '修改人',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_isuse` (`isuse`),
  KEY `idx_created_by` (`created_by`),
  KEY `idx_modified_by` (`modified_by`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='排班计划';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance_schedule`
--

LOCK TABLES `attendance_schedule` WRITE;
/*!40000 ALTER TABLE `attendance_schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendance_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendance_schedule_detail`
--

DROP TABLE IF EXISTS `attendance_schedule_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attendance_schedule_detail` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `schedule_id` int(20) NOT NULL DEFAULT '0' COMMENT '班次ID',
  `gmt_month` datetime NOT NULL COMMENT '排班时间，精确到月，例：2013-05-01表示2013年5月的详细排班计划',
  `day_of_year` int(11) DEFAULT NULL COMMENT '一年中第N天',
  `day_of_month` int(11) DEFAULT NULL COMMENT '一个月中的第N天',
  `day_of_week` int(11) DEFAULT NULL COMMENT '一个星期中的第N天',
  `day` datetime DEFAULT NULL COMMENT '工作日',
  `workf` int(20) DEFAULT NULL,
  `workt` int(20) DEFAULT NULL,
  `unixtime` int(20) DEFAULT NULL,
  `created_by` varchar(45) NOT NULL,
  `modified_by` varchar(45) NOT NULL,
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_schedule_id` (`schedule_id`),
  KEY `idx_gmt_month` (`gmt_month`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='排班计划（详细）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance_schedule_detail`
--

LOCK TABLES `attendance_schedule_detail` WRITE;
/*!40000 ALTER TABLE `attendance_schedule_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendance_schedule_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_right`
--

DROP TABLE IF EXISTS `auth_right`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_right` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL COMMENT '权限code',
  `name` varchar(50) NOT NULL COMMENT '权限名',
  `sort` int(11) NOT NULL DEFAULT '0',
  `content` varchar(254) DEFAULT NULL COMMENT '权限',
  `menu` varchar(50) DEFAULT NULL COMMENT '菜单名',
  `menu_url` varchar(254) DEFAULT NULL COMMENT '菜单URL',
  `menu_css` varchar(50) DEFAULT NULL,
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`),
  KEY `idx_sort` (`sort`)
) ENGINE=MyISAM AUTO_INCREMENT=376 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_right`
--

LOCK TABLES `auth_right` WRITE;
/*!40000 ALTER TABLE `auth_right` DISABLE KEYS */;
INSERT INTO `auth_right` VALUES (1,'1000','工作平台',0,'','','','','2011-05-06 18:58:53','2015-01-19 11:55:27'),(2,'10001000','系统管理',0,'^/staff/.*$','系统管理','/staff/index.htm','','2011-05-06 18:58:53','2011-07-09 16:09:41'),(3,'10001001','员工管理',0,'','员工管理','','','2011-05-06 18:58:53','2011-07-09 16:14:08'),(11,'10001005','我的信息',0,'^/profile.*$','我的信息','/profile.htm','','2011-05-17 18:26:29','2011-07-09 16:15:40'),(13,'1002','任务管理系统',0,'','任务管理系统','','','2011-05-18 15:03:17','2011-05-18 15:03:17'),(14,'1003','广告投放系统',0,'^/ad/ad/.*$','广告投放系统','','','2011-05-18 15:03:28','2011-07-14 15:34:13'),(70,'10031000','广告申请',0,'^/ad/ad/.*$|^/ad/material/.*$','广告申请管理','/ad/ad/apply.htm','','2011-05-25 19:44:52','2011-11-17 17:50:30'),(71,'10031001','广告位管理',0,'^/ad/position/.*$|^/ad/exacttype/.*$','广告位管理','/ad/position/index.htm','','2011-05-25 19:45:31','2011-06-15 16:55:06'),(72,'10031002','广告主管理',1,'^/ad/advertiser/.*$','广告主管理','/ad/advertiser/index.htm','','2011-05-25 19:45:52','2011-05-31 09:36:42'),(75,'10031003','广告审核管理',0,'^/ad/check/.*$|^/ad/booking/.*$','广告审核管理','/ad/check/index.htm','','2011-05-31 09:32:38','2012-02-17 13:27:41'),(76,'10031004','广告设计管理',0,'^/ad/design/.*$','广告设计管理','/ad/design/index.htm','','2011-05-31 09:39:54','2011-06-01 09:51:31'),(77,'10021000','所有任务',0,'^/job/definition/.*$|^/job/status/.*$','所有任务','/job/definition/index.htm','','2011-06-02 11:45:44','2011-06-02 11:46:27'),(78,'10021001','运行日志',0,'^/job/status/.*$','运行日志','/job/status/index.htm','','2011-06-02 11:46:06','2011-06-02 11:46:06'),(80,'10001006','工作日程',0,'^/scheduler/event/.*$','工作日程','/scheduler/event/index.htm','','2011-07-05 19:49:29','2011-07-05 19:49:29'),(82,'100010061000','我的日程',0,'','我的日程','/scheduler/event/index.htm?type=staff','','2011-07-07 11:08:15','2011-07-07 11:08:15'),(83,'100010061001','部门日程',0,'','部门日程','/scheduler/event/index.htm?type=dept','','2011-07-07 11:08:30','2011-07-08 19:15:54'),(84,'100010061002','公司日程',0,'','公司日程','/scheduler/event/index.htm?type=all','','2011-07-07 11:08:50','2011-07-07 11:08:50'),(85,'100010001000','权限管理',0,'^/auth/right/.*$','权限管理','/auth/right/index.htm','','2011-07-09 16:10:34','2011-07-09 16:10:34'),(86,'100010001001','角色权限管理',0,'^/auth/role/.*$','角色权限管理','/auth/role/index.htm','','2011-07-09 16:10:54','2011-07-09 16:10:54'),(87,'100010001002','Feedback',0,'^/feedback/.*$','Feedback','/feedback/index.htm','','2011-07-09 16:11:31','2011-07-09 16:11:31'),(88,'100010001003','业务系统管理',0,'^/bs/.*$','业务系统管理','/bs/index.htm','','2011-07-09 16:12:00','2011-07-09 16:12:00'),(89,'100010011000','公司员工',0,'^/staff/.*$','公司员工','/staff/index.htm','','2011-07-09 16:13:37','2011-07-09 16:13:37'),(90,'100010011001','公司部门',0,'^/dept/.*$','公司部门','/dept/index.htm','','2011-07-09 16:14:05','2011-07-09 16:14:05'),(91,'10031005','广告投放统计',0,'^/analysis/adhit/.*$','广告投放统计','/analysis/adhit/index.htm','','2011-07-25 14:10:38','2011-07-25 14:10:52'),(310,'10091000','网关管理',0,'^/gateway/.*$','网关管理','/gateway/index.htm','','2012-04-19 16:58:28','2012-04-20 10:02:12'),(140,'1006','邮件管理系统',0,'','邮件管理系统','','','2011-11-17 15:02:38','2011-11-17 15:02:38'),(141,'10061000','邮件模板管理',0,'^/template/.*$','邮件模板管理','/template/index.htm','','2011-11-17 15:03:07','2011-12-12 18:45:22'),(142,'10061001','SMTP管理',0,'^/account/.*$','SMTP管理','/account/index.htm','','2011-11-17 15:03:32','2011-12-12 18:45:35'),(163,'10061002','实时监控',0,'','实时监控','/monitor.htm','','2011-12-15 08:54:50','2011-12-15 08:54:50'),(204,'10061003','邮件管理',0,'^/mailinfo/.*$','邮件管理','/mailinfo/index.htm','','2011-12-15 14:52:47','2012-04-11 09:25:19'),(309,'1009','短信管理系统',0,'','短信管理系统','#','','2012-04-19 16:58:10','2012-04-19 16:58:10'),(217,'100010061003','撰写周报',0,'^/scheduler/report/.*$','撰写周报','/scheduler/report/compose.htm','','2012-01-10 19:02:04','2012-01-10 19:02:04'),(277,'10031006','广告审核管理v2',0,'','广告审核管理v2','/ad/ad/index.htm','','2012-02-17 11:36:42','2012-04-08 15:43:49'),(295,'10031007','广告预订（SALE）',0,'^/ad/ad/.*$|^/ad/booking/.*$|^/ad/position/child.htm.*$','广告预订（SALE）','/ad/ad/saleAd.htm','','2012-02-21 16:15:15','2012-02-21 16:35:51'),(299,'10061004','邮件列表管理',0,'^/maillist/.*$','邮件列表管理','/maillist/index.htm','','2012-03-16 13:51:42','2012-04-11 09:24:58'),(311,'10091001','模板管理',0,'^/template/.*$','模板管理','/template/index.htm','','2012-04-20 10:02:45','2012-04-20 10:02:45'),(312,'10091002','短信日志',0,'^/smslog/.*$','短信日志','/smslog/index.htm','','2012-04-20 10:03:27','2012-04-20 10:03:27'),(319,'10001007','考勤管理',0,'','考勤管理','#','','2012-06-27 15:56:25','2012-06-27 15:56:25'),(320,'100010071000','考勤数据导入',0,'^/hr/attendance/.*$','考勤数据导入','/hr/attendance/impt.htm','','2012-06-27 15:58:20','2012-06-27 15:58:20'),(321,'100010071001','考勤原始数据',0,'^/hr/attendance/.*$','考勤原始数据','/hr/attendance/index.htm','','2012-06-27 15:58:59','2012-06-28 17:35:01'),(322,'100010071002','考勤结果',0,'^/hr/analysis/.*$','考勤结果','/hr/analysis/index.htm','','2012-06-27 15:59:34','2012-08-16 11:32:26'),(325,'10021002','特殊权限',0,'','','','','2012-07-11 10:51:33','2012-07-11 10:51:33'),(326,'100210021000','后台手动执行任务',0,'^/job/definition/doTask.htm.*$','','','','2012-07-11 10:52:25','2012-07-11 10:52:25'),(345,'10021003','索引任务',0,'^/job/definition/.*$|^/job/status/.*$','索引任务','/job/definition/idx.htm','','2012-08-10 11:34:35','2012-08-10 11:34:45'),(374,'1010','Parox Admin System',0,'','Parox Admin System','#','','2014-06-06 10:45:49','2014-06-06 10:46:05'),(375,'10101000','全部权限',0,'^.*$','全部权限','#','','2014-06-06 10:46:27','2014-06-06 10:46:35');
/*!40000 ALTER TABLE `auth_right` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_role`
--

DROP TABLE IF EXISTS `auth_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '角色名字',
  `remark` varchar(254) DEFAULT NULL,
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_role`
--

LOCK TABLES `auth_role` WRITE;
/*!40000 ALTER TABLE `auth_role` DISABLE KEYS */;
INSERT INTO `auth_role` VALUES (1,'dev','系统开发','2011-05-06 18:58:53','2011-05-19 15:17:24'),(5,'系统管理员','公司系统管理员','2011-05-17 18:27:37','2011-05-17 18:27:50'),(6,'主管','','2011-07-07 11:10:41','2011-07-07 11:10:41'),(7,'经理','','2011-07-07 11:11:16','2011-07-07 11:11:16');
/*!40000 ALTER TABLE `auth_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_role_right`
--

DROP TABLE IF EXISTS `auth_role_right`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_role_right` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `right_id` int(20) NOT NULL,
  `role_id` int(20) NOT NULL,
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_right_id` (`right_id`),
  KEY `Index_role_id` (`role_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1742 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_role_right`
--

LOCK TABLES `auth_role_right` WRITE;
/*!40000 ALTER TABLE `auth_role_right` DISABLE KEYS */;
INSERT INTO `auth_role_right` VALUES (30,11,1,'2011-05-17 18:26:57','2011-05-17 18:26:57'),(170,90,1,'2011-07-09 16:16:47','2011-07-09 16:16:47'),(173,87,5,'2011-07-09 16:17:06','2011-07-09 16:17:06'),(27,3,1,'2011-05-17 18:26:56','2011-05-17 18:26:56'),(26,2,1,'2011-05-17 18:26:55','2011-05-17 18:26:55'),(25,1,1,'2011-05-17 18:26:55','2011-05-17 18:26:55'),(172,90,5,'2011-07-09 16:17:06','2011-07-09 16:17:06'),(34,3,5,'2011-05-17 18:27:53','2011-05-17 18:27:53'),(33,2,5,'2011-05-17 18:27:53','2011-05-17 18:27:53'),(167,87,1,'2011-07-09 16:16:46','2011-07-09 16:16:46'),(137,80,1,'2011-07-05 19:50:48','2011-07-05 19:50:48'),(169,89,1,'2011-07-09 16:16:47','2011-07-09 16:16:47'),(38,11,5,'2011-05-17 18:27:55','2011-05-17 18:27:55'),(166,86,1,'2011-07-09 16:16:46','2011-07-09 16:16:46'),(98,70,1,'2011-05-26 15:00:38','2011-05-26 15:00:38'),(93,14,1,'2011-05-25 19:46:26','2011-05-25 19:46:26'),(95,71,1,'2011-05-25 19:46:26','2011-05-25 19:46:26'),(96,72,1,'2011-05-25 19:46:27','2011-05-25 19:46:27'),(177,1,6,'2011-07-09 16:17:18','2011-07-09 16:17:18'),(175,85,5,'2011-07-09 16:17:07','2011-07-09 16:17:07'),(143,84,6,'2011-07-07 11:17:30','2011-07-07 11:17:30'),(112,75,1,'2011-05-31 09:44:35','2011-05-31 09:44:35'),(198,13,1,'2011-09-01 20:04:51','2011-09-01 20:04:51'),(127,77,1,'2011-06-02 11:46:48','2011-06-02 11:46:48'),(128,78,1,'2011-06-02 11:46:48','2011-06-02 11:46:48'),(144,1,7,'2011-07-07 11:17:34','2011-07-07 11:17:34'),(192,76,1,'2011-07-14 14:15:17','2011-07-14 14:15:17'),(171,89,5,'2011-07-09 16:17:05','2011-07-09 16:17:05'),(168,88,1,'2011-07-09 16:16:46','2011-07-09 16:16:46'),(165,85,1,'2011-07-09 16:16:45','2011-07-09 16:16:45'),(174,88,5,'2011-07-09 16:17:06','2011-07-09 16:17:06'),(156,80,5,'2011-07-07 11:18:48','2011-07-07 11:18:48'),(157,83,5,'2011-07-07 11:18:48','2011-07-07 11:18:48'),(158,84,5,'2011-07-07 11:18:50','2011-07-07 11:18:50'),(159,82,5,'2011-07-07 11:18:50','2011-07-07 11:18:50'),(160,82,1,'2011-07-07 11:18:54','2011-07-07 11:18:54'),(161,83,1,'2011-07-07 11:18:54','2011-07-07 11:18:54'),(163,84,1,'2011-07-07 11:18:57','2011-07-07 11:18:57'),(164,84,1,'2011-07-07 11:18:59','2011-07-07 11:18:59'),(176,86,5,'2011-07-09 16:17:08','2011-07-09 16:17:08'),(179,89,6,'2011-07-09 16:17:22','2011-07-09 16:17:22'),(180,3,6,'2011-07-09 16:17:23','2011-07-09 16:17:23'),(181,90,6,'2011-07-09 16:17:24','2011-07-09 16:17:24'),(201,91,1,'2011-09-06 17:06:14','2011-09-06 17:06:14'),(196,2,6,'2011-08-31 16:30:21','2011-08-31 16:30:21'),(197,88,6,'2011-08-31 16:30:24','2011-08-31 16:30:24'),(320,140,1,'2011-11-17 15:13:09','2011-11-17 15:13:09'),(321,141,1,'2011-11-17 15:13:10','2011-11-17 15:13:10'),(322,142,1,'2011-11-17 15:13:11','2011-11-17 15:13:11'),(347,163,1,'2011-12-15 08:55:10','2011-12-15 08:55:10'),(382,204,1,'2011-12-15 14:55:01','2011-12-15 14:55:01'),(682,217,1,'2012-01-10 19:02:50','2012-01-10 19:02:50'),(683,217,5,'2012-01-10 19:48:50','2012-01-10 19:48:50'),(684,1,5,'2012-01-10 19:48:53','2012-01-10 19:48:53'),(955,299,1,'2012-03-16 13:51:49','2012-03-16 13:51:49'),(856,277,1,'2012-02-17 11:36:48','2012-02-17 11:36:48'),(1741,375,1,'2014-06-06 11:05:59','2014-06-06 11:05:59'),(1740,374,1,'2014-06-06 11:05:58','2014-06-06 11:05:58'),(1337,310,1,'2012-12-06 18:52:19','2012-12-06 18:52:19'),(1336,309,1,'2012-12-06 18:52:17','2012-12-06 18:52:17'),(994,311,1,'2012-04-20 10:04:51','2012-04-20 10:04:51'),(995,312,1,'2012-04-20 10:04:51','2012-04-20 10:04:51'),(1029,322,1,'2012-06-27 16:00:06','2012-06-27 16:00:06'),(1028,321,1,'2012-06-27 16:00:06','2012-06-27 16:00:06'),(1027,320,1,'2012-06-27 16:00:05','2012-06-27 16:00:05'),(1026,319,1,'2012-06-27 16:00:04','2012-06-27 16:00:04'),(1338,86,6,'2012-12-07 11:53:56','2012-12-07 11:53:56'),(1215,345,1,'2012-08-10 11:35:08','2012-08-10 11:35:08'),(1339,85,6,'2012-12-07 11:53:57','2012-12-07 11:53:57');
/*!40000 ALTER TABLE `auth_role_right` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user`
--

DROP TABLE IF EXISTS `auth_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `email` varchar(50) DEFAULT NULL,
  `steping` tinyint(4) DEFAULT '0' COMMENT '0：正常\n其他：不能正常登录',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_username` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=397 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user`
--

LOCK TABLES `auth_user` WRITE;
/*!40000 ALTER TABLE `auth_user` DISABLE KEYS */;
INSERT INTO `auth_user` VALUES (1,'admin','378111df061182a5','admin@zz91.com',0,'2011-05-06 18:58:53','2012-12-06 18:57:26'),(18,'mays','2fe54263d7742123','mays@zz91.net',0,'2011-05-18 08:22:13','2011-10-11 15:10:50'),(396,'wangfl','325750d06d39767222ae4ea3b62ffdff','wangfl@parox.cn',0,'2015-01-14 10:38:11','2015-01-15 13:31:19');
/*!40000 ALTER TABLE `auth_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user_role`
--

DROP TABLE IF EXISTS `auth_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_user_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role_id` int(20) NOT NULL,
  `user_id` int(20) NOT NULL,
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_role_id` (`role_id`),
  KEY `index_user_id` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=756 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user_role`
--

LOCK TABLES `auth_user_role` WRITE;
/*!40000 ALTER TABLE `auth_user_role` DISABLE KEYS */;
INSERT INTO `auth_user_role` VALUES (753,1,18,'2014-06-04 15:00:03','2014-06-04 15:00:03'),(335,5,1,'2012-12-06 18:57:26','2012-12-06 18:57:26'),(755,5,396,'2015-01-14 10:38:11','2015-01-14 10:38:11'),(754,1,396,'2015-01-14 10:38:11','2015-01-14 10:38:11');
/*!40000 ALTER TABLE `auth_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bs`
--

DROP TABLE IF EXISTS `bs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bs` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL COMMENT '项目代号',
  `password` varchar(45) NOT NULL COMMENT '与keys对应，在单点登录中可能会用到',
  `name` varchar(250) NOT NULL COMMENT '业务系统名称',
  `right_code` varchar(45) NOT NULL DEFAULT '' COMMENT '对应权限父节点',
  `url` varchar(250) DEFAULT NULL COMMENT '连接',
  `avatar` varchar(250) DEFAULT NULL COMMENT '代表的图片',
  `note` varchar(1000) DEFAULT NULL COMMENT '详细描述',
  `types` varchar(45) NOT NULL COMMENT '系统类别：\n0：软件产品\n1：业务系统\n2：客户网站',
  `versions` varchar(45) NOT NULL COMMENT '当前版本',
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  KEY `idx_types` (`types`)
) ENGINE=MyISAM AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COMMENT='公司的业务系统';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bs`
--

LOCK TABLES `bs` WRITE;
/*!40000 ALTER TABLE `bs` DISABLE KEYS */;
INSERT INTO `bs` VALUES (1,'16449732-13b0-48c2-8eef-6784ecf903bf','135246','员工工作平台','1000','http://work.zz91.com/index.htm','/images/bsicon/date.png',NULL,'1','1.0.0-snapshot','2011-05-06 18:58:53','2012-12-06 18:27:12'),(60,'c8bb2ece-db27-41df-b9d1-6337c4021dfe','y0a0ozhuaonxgwcn','Parox Admin','1010','','/themes/images/no_image.gif',NULL,'1','','2014-06-06 10:45:29','2014-06-06 10:47:10');
/*!40000 ALTER TABLE `bs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bs_dept`
--

DROP TABLE IF EXISTS `bs_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bs_dept` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `bs_id` int(20) NOT NULL,
  `dept_id` int(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_bs_id` (`bs_id`),
  KEY `idx_dept_id` (`dept_id`)
) ENGINE=MyISAM AUTO_INCREMENT=390 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bs_dept`
--

LOCK TABLES `bs_dept` WRITE;
/*!40000 ALTER TABLE `bs_dept` DISABLE KEYS */;
INSERT INTO `bs_dept` VALUES (389,60,81),(388,60,82),(387,60,83),(386,1,82),(385,1,83),(384,1,81);
/*!40000 ALTER TABLE `bs_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bs_staff`
--

DROP TABLE IF EXISTS `bs_staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bs_staff` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `bs_id` int(20) NOT NULL,
  `staff_id` int(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_bs_id` (`bs_id`),
  KEY `idx_staff_id` (`staff_id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='员工单独关联的业务系统';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bs_staff`
--

LOCK TABLES `bs_staff` WRITE;
/*!40000 ALTER TABLE `bs_staff` DISABLE KEYS */;
INSERT INTO `bs_staff` VALUES (14,1,16);
/*!40000 ALTER TABLE `bs_staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contacts` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NOT NULL,
  `keys` varchar(45) NOT NULL DEFAULT '0' COMMENT '通讯类型\n0:email\n1:手机\n2:固定电话\n3:qq\n4:其他',
  `values` varchar(45) NOT NULL DEFAULT '' COMMENT '通讯内容',
  `gmt_created` datetime NOT NULL,
  `gmt_modified` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工通讯信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dept`
--

DROP TABLE IF EXISTS `dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dept` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '部门名称',
  `code` varchar(50) NOT NULL DEFAULT '0' COMMENT '父ID，根为0',
  `note` varchar(1000) DEFAULT NULL COMMENT '部门信息，可以放在工作平台首页显示',
  `gmt_created` datetime NOT NULL COMMENT '记录创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '记录修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`)
) ENGINE=MyISAM AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 COMMENT='公司部门';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dept`
--

LOCK TABLES `dept` WRITE;
/*!40000 ALTER TABLE `dept` DISABLE KEYS */;
INSERT INTO `dept` VALUES (83,'其他','1002','','2014-06-04 14:55:04','2014-06-04 14:55:04'),(82,'UI','1001','','2014-06-04 14:54:45','2014-06-04 14:54:45'),(81,'DEV','1000','','2014-06-04 14:54:40','2014-06-04 14:54:40');
/*!40000 ALTER TABLE `dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dept_right`
--

DROP TABLE IF EXISTS `dept_right`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dept_right` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `dept_id` int(20) NOT NULL,
  `auth_right_id` int(20) NOT NULL,
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_dept_id` (`dept_id`),
  KEY `idx_auth_right_id` (`auth_right_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3970 DEFAULT CHARSET=utf8 COMMENT='部门权限，可以设置每个部门对应的权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dept_right`
--

LOCK TABLES `dept_right` WRITE;
/*!40000 ALTER TABLE `dept_right` DISABLE KEYS */;
INSERT INTO `dept_right` VALUES (3969,83,83,'2014-06-04 14:56:37','2014-06-04 14:56:37'),(3968,83,82,'2014-06-04 14:56:36','2014-06-04 14:56:36'),(3967,83,80,'2014-06-04 14:56:35','2014-06-04 14:56:35'),(3966,83,11,'2014-06-04 14:56:34','2014-06-04 14:56:34'),(3965,83,87,'2014-06-04 14:56:33','2014-06-04 14:56:33'),(3964,83,2,'2014-06-04 14:56:31','2014-06-04 14:56:31'),(3963,83,1,'2014-06-04 14:56:30','2014-06-04 14:56:30'),(3962,81,83,'2014-06-04 14:56:18','2014-06-04 14:56:18'),(3961,81,82,'2014-06-04 14:56:18','2014-06-04 14:56:18'),(3960,81,80,'2014-06-04 14:56:16','2014-06-04 14:56:16'),(3959,81,11,'2014-06-04 14:56:13','2014-06-04 14:56:13'),(3958,81,2,'2014-06-04 14:56:10','2014-06-04 14:56:10'),(3957,81,87,'2014-06-04 14:56:09','2014-06-04 14:56:09'),(3956,82,83,'2014-06-04 14:55:53','2014-06-04 14:55:53'),(3955,82,82,'2014-06-04 14:55:51','2014-06-04 14:55:51'),(3954,82,80,'2014-06-04 14:55:51','2014-06-04 14:55:51'),(3953,82,11,'2014-06-04 14:55:45','2014-06-04 14:55:45'),(3952,82,87,'2014-06-04 14:55:41','2014-06-04 14:55:41'),(3951,82,2,'2014-06-04 14:55:40','2014-06-04 14:55:40'),(3949,82,1,'2014-06-04 14:55:32','2014-06-04 14:55:32'),(3947,81,1,'2014-06-04 14:55:09','2014-06-04 14:55:09');
/*!40000 ALTER TABLE `dept_right` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feedback` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `bs_id` int(20) NOT NULL DEFAULT '0',
  `account` varchar(50) NOT NULL DEFAULT '',
  `topic` varchar(100) DEFAULT '',
  `content` varchar(2000) DEFAULT '',
  `status` tinyint(4) DEFAULT '0' COMMENT '处理状态：\n0：未处理\n1：已解决\n2：不解决\n3：无法解决',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_bs_id` (`bs_id`),
  KEY `idx_account` (`account`),
  KEY `idx_status` (`status`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='对系统的意见反鐀';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `param`
--

DROP TABLE IF EXISTS `param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `param` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `types` varchar(64) NOT NULL,
  `names` varchar(254) DEFAULT NULL,
  `key` varchar(254) NOT NULL,
  `value` varchar(254) NOT NULL,
  `sort` tinyint(4) DEFAULT '0',
  `used` tinyint(4) NOT NULL DEFAULT '0',
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `param`
--

LOCK TABLES `param` WRITE;
/*!40000 ALTER TABLE `param` DISABLE KEYS */;
/*!40000 ALTER TABLE `param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `param_type`
--

DROP TABLE IF EXISTS `param_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `param_type` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(45) NOT NULL,
  `name` varchar(254) DEFAULT NULL,
  `gmt_created` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key_UNIQUE` (`key`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `param_type`
--

LOCK TABLES `param_type` WRITE;
/*!40000 ALTER TABLE `param_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `param_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheduler_event`
--

DROP TABLE IF EXISTS `scheduler_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheduler_event` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `text` varchar(254) DEFAULT NULL COMMENT '事件标题',
  `details` varchar(2000) DEFAULT NULL COMMENT '详细内容',
  `start_date` datetime NOT NULL COMMENT '开始时间',
  `end_date` datetime NOT NULL COMMENT '结束时间',
  `assign_account` varchar(45) DEFAULT '' COMMENT '分配任务的账户',
  `owner_account` varchar(45) NOT NULL DEFAULT '' COMMENT '任务拥有者',
  `dept_code` varchar(50) DEFAULT '' COMMENT '事件创建或更新时所在的部门',
  `persent` tinyint(4) DEFAULT '0' COMMENT '任务完成百分比\n0-100',
  `importance` tinyint(4) DEFAULT '0' COMMENT '重要性\n不同程度用不同颜色在日历上表现',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_owner_account` (`owner_account`),
  KEY `idx_start_date` (`start_date`),
  KEY `idx_end_date` (`end_date`)
) ENGINE=MyISAM AUTO_INCREMENT=235 DEFAULT CHARSET=utf8 COMMENT='日程';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheduler_event`
--

LOCK TABLES `scheduler_event` WRITE;
/*!40000 ALTER TABLE `scheduler_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `scheduler_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheduler_report`
--

DROP TABLE IF EXISTS `scheduler_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheduler_report` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `dept_code` varchar(45) NOT NULL COMMENT '提交周报时，员工所在部门',
  `account` varchar(45) NOT NULL,
  `text` varchar(254) DEFAULT '' COMMENT '日/周报标题',
  `details` varchar(2000) DEFAULT '' COMMENT '周报详细内容',
  `compose_date` datetime NOT NULL COMMENT '撰写日期',
  `year` char(5) DEFAULT NULL COMMENT '年份',
  `week` tinyint(4) DEFAULT NULL COMMENT '一年中第几周',
  `gmt_created` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_dept_code` (`dept_code`),
  KEY `idx_account` (`account`),
  KEY `idx_week` (`week`),
  KEY `idx_compose_date` (`compose_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='日/周报';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheduler_report`
--

LOCK TABLES `scheduler_report` WRITE;
/*!40000 ALTER TABLE `scheduler_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `scheduler_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheduler_report_event`
--

DROP TABLE IF EXISTS `scheduler_report_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheduler_report_event` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `event_id` int(20) NOT NULL,
  `report_id` int(20) NOT NULL,
  `gmt_created` varchar(45) DEFAULT NULL,
  `gmt_modified` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheduler_report_event`
--

LOCK TABLES `scheduler_report_event` WRITE;
/*!40000 ALTER TABLE `scheduler_report_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `scheduler_report_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NOT NULL COMMENT '员工账号',
  `staff_no` varchar(45) NOT NULL COMMENT '员工工号',
  `dept_code` varchar(50) NOT NULL DEFAULT '0' COMMENT '账号信息',
  `name` varchar(45) NOT NULL COMMENT '员工姓名',
  `email` varchar(45) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `avatar` varchar(250) DEFAULT '' COMMENT '账号头像',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `jobs` varchar(45) DEFAULT '' COMMENT '职位\n0：员工\n1：组长\n2：主管\n3：经理\n4：boss',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '员工状态\n0：试用员工\n1：正式员工\n2：离职',
  `gmt_entry` datetime NOT NULL COMMENT '入职时间',
  `gmt_left` datetime DEFAULT NULL COMMENT '离职时间，在职员工留空',
  `note` varchar(1000) DEFAULT '' COMMENT '备注',
  `gmt_created` datetime NOT NULL COMMENT '记录创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '记录最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_account` (`account`),
  UNIQUE KEY `idx_staff_no` (`staff_no`),
  KEY `idx_status` (`status`),
  KEY `idx_dept_code` (`dept_code`)
) ENGINE=MyISAM AUTO_INCREMENT=394 DEFAULT CHARSET=utf8 COMMENT='员工信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,'admin','0000','1000','系统管理员','admin@zz91.com','1',NULL,NULL,'','1','2006-01-01 00:00:00','2006-01-01 00:00:00','系统管理员账号','2011-05-06 18:58:53','2012-12-06 18:57:26');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-03 16:36:12
