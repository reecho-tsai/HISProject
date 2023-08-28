/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

# ------------------------------------------------------------
# SCHEMA DUMP FOR TABLE: check_apply
# ------------------------------------------------------------

USE `hisvue`;

DROP TABLE IF EXISTS `check_apply`;
CREATE TABLE `check_apply`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `register_id` int(11) DEFAULT NULL COMMENT '病历号',
    `item_id`     int(11) DEFAULT NULL COMMENT '检查项目id',
    `item_name`   varchar(100)  DEFAULT NULL COMMENT '检查项目名称',
    `fee`         decimal(8, 2) DEFAULT NULL COMMENT '检查费用',
    `status`      int(11) DEFAULT '1' COMMENT '状态 1 待缴费 2 待检查 3 已检查 4 已退费',
    `active`      int(11) DEFAULT '1' COMMENT '是否有效，1 有效，0 失效',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='检查申请';

# ------------------------------------------------------------
# SCHEMA DUMP FOR TABLE: check_item
# ------------------------------------------------------------

DROP TABLE IF EXISTS `check_item`;
CREATE TABLE `check_item`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(100)  DEFAULT NULL COMMENT '检查名称',
    `content`     varchar(300)  DEFAULT NULL COMMENT '检查内容',
    `fee`         decimal(8, 2) DEFAULT NULL COMMENT '检查费用',
    `active`      int(11) DEFAULT '1' COMMENT '是否有效，1 有效，0 失效',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='检查项目';

# ------------------------------------------------------------
# SCHEMA DUMP FOR TABLE: constant_item
# ------------------------------------------------------------

DROP TABLE IF EXISTS `constant_item`;
CREATE TABLE `constant_item`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `type_id`     int(11) DEFAULT NULL COMMENT '类别id',
    `code`        varchar(100) DEFAULT NULL COMMENT '常数项代码',
    `name`        varchar(100) DEFAULT NULL COMMENT '常数项名称',
    `sort`        int(11) DEFAULT NULL COMMENT '排序id',
    `active`      int(11) DEFAULT '1' COMMENT '是否有效，1 有效，0 失效',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='常数项表';

# ------------------------------------------------------------
# SCHEMA DUMP FOR TABLE: constant_type
# ------------------------------------------------------------

DROP TABLE IF EXISTS `constant_type`;
CREATE TABLE `constant_type`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code`        varchar(100) DEFAULT NULL COMMENT '代码',
    `name`        varchar(100) DEFAULT NULL COMMENT '名称',
    `active`      int(11) DEFAULT '1' COMMENT '是否有效，1 有效，0 失效',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='常数类别';

# ------------------------------------------------------------
# SCHEMA DUMP FOR TABLE: department
# ------------------------------------------------------------

DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(100) DEFAULT NULL COMMENT '名称',
    `address`     varchar(200) DEFAULT NULL COMMENT '办公地址',
    `leader`      varchar(100) DEFAULT NULL COMMENT '负责人',
    `leader_id`   int(11) DEFAULT NULL COMMENT '负责人编号',
    `active`      int(11) DEFAULT '1' COMMENT '是否有效，1 有效，0 失效',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='科室';

# ------------------------------------------------------------
# SCHEMA DUMP FOR TABLE: inspect_apply
# ------------------------------------------------------------

DROP TABLE IF EXISTS `inspect_apply`;
CREATE TABLE `inspect_apply`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `item_id`     int(11) DEFAULT NULL COMMENT '项目id',
    `item_name`   varchar(100)  DEFAULT NULL COMMENT '项目名称',
    `fee`         decimal(8, 2) DEFAULT NULL COMMENT '检查费用',
    `status`      int(11) DEFAULT NULL COMMENT '状态',
    `active`      int(11) DEFAULT '1' COMMENT '是否有效，1 有效，0 失效',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `register_id` int(11) DEFAULT NULL COMMENT '病历号',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='检验申请';

# ------------------------------------------------------------
# SCHEMA DUMP FOR TABLE: inspect_item
# ------------------------------------------------------------

DROP TABLE IF EXISTS `inspect_item`;
CREATE TABLE `inspect_item`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(100)  DEFAULT NULL COMMENT '检验名称',
    `content`     varchar(300)  DEFAULT '' COMMENT '检验内容',
    `fee`         decimal(8, 2) DEFAULT NULL COMMENT '检验费用',
    `active`      int(11) DEFAULT '1' COMMENT '是否有效，1 有效，0 失效',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='检验项目';

# ------------------------------------------------------------
# SCHEMA DUMP FOR TABLE: regist_level
# ------------------------------------------------------------

DROP TABLE IF EXISTS `regist_level`;
CREATE TABLE `regist_level`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(100)  DEFAULT NULL COMMENT '挂号名称',
    `fee`         decimal(8, 2) DEFAULT NULL COMMENT '挂号费用',
    `active`      int(11) DEFAULT '1' COMMENT '是否有效，1 有效，0 失效',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `role_id`     int(11) DEFAULT NULL COMMENT '对应的角色id',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='挂号级别';

# ------------------------------------------------------------
# SCHEMA DUMP FOR TABLE: register
# ------------------------------------------------------------

DROP TABLE IF EXISTS `register`;
CREATE TABLE `register`
(
    `id`              int(11) NOT NULL AUTO_INCREMENT COMMENT 'id病历号',
    `name`            varchar(100)  DEFAULT NULL COMMENT '姓名',
    `gender`          int(11) DEFAULT NULL COMMENT '性别 男0，女1',
    `id_no`           varchar(100)  DEFAULT NULL COMMENT '身份证号',
    `birthday`        date          DEFAULT NULL COMMENT '出生日期',
    `age`             int(11) DEFAULT NULL COMMENT '年龄',
    `address`         varchar(200)  DEFAULT NULL COMMENT '家庭住址',
    `regsit_level_id` int(11) DEFAULT NULL COMMENT '挂号级别',
    `dept_id`         int(11) DEFAULT NULL COMMENT '挂号科室',
    `doctor_id`       int(11) DEFAULT NULL COMMENT '看诊医生',
    `readme`          varchar(500)  DEFAULT NULL COMMENT '主诉',
    `present`         varchar(500)  DEFAULT NULL COMMENT '现病史',
    `present_treat`   varchar(500)  DEFAULT NULL COMMENT '现病史治疗情况',
    `history`         varchar(500)  DEFAULT NULL COMMENT '既往史',
    `allergy`         varchar(500)  DEFAULT NULL COMMENT '过敏史',
    `book`            int(11) DEFAULT NULL COMMENT '是否要病历本',
    `fee`             decimal(8, 2) DEFAULT NULL COMMENT '挂号费用',
    `status`          int(11) DEFAULT NULL COMMENT '状态',
    `active`          int(11) DEFAULT '1' COMMENT '是否有效，1 有效，0 失效',
    `create_time`     datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='诊疗信息';

# ------------------------------------------------------------
# SCHEMA DUMP FOR TABLE: sys_permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(100) DEFAULT NULL COMMENT '权限名称',
    `url`         varchar(100) DEFAULT NULL COMMENT '资源路径',
    `type`        int(11) DEFAULT NULL COMMENT '0 资源 1 导航',
    `parent_id`   int(11) DEFAULT NULL COMMENT '上级id',
    `active`      int(11) DEFAULT '1' COMMENT '是否有效，1 有效，0 失效',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='权限';

# ------------------------------------------------------------
# SCHEMA DUMP FOR TABLE: sys_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        char(10) DEFAULT NULL COMMENT '角色名称',
    `active`      int(11) DEFAULT '1' COMMENT '是否有效，1 有效，0 失效',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='角色';

# ------------------------------------------------------------
# SCHEMA DUMP FOR TABLE: sys_role_permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id`       int(11) DEFAULT NULL COMMENT '角色id',
    `permission_id` varchar(1000) DEFAULT NULL COMMENT '权限ids,以逗号分割',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色权限';

# ------------------------------------------------------------
# SCHEMA DUMP FOR TABLE: sys_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`    varchar(100) DEFAULT NULL COMMENT '用户名',
    `password`    varchar(100) DEFAULT NULL COMMENT '密码',
    `real_name`   varchar(100) DEFAULT NULL COMMENT '真实姓名',
    `telephone`   varchar(20)  DEFAULT NULL COMMENT '电话号码',
    `user_type`   int(11) DEFAULT NULL COMMENT '医生类型',
    `dept_id`     int(11) DEFAULT NULL COMMENT '归属科室',
    `last_login`  datetime     DEFAULT NULL COMMENT '最后登录时间',
    `active`      int(11) DEFAULT '1' COMMENT '是否有效，1 有效，0 失效',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `work_time`   char(50)     DEFAULT NULL COMMENT '工作时间',
    `num_max`     int(5) DEFAULT NULL COMMENT '接诊人数',
    PRIMARY KEY (`id`),
    UNIQUE KEY `telephone` (`telephone`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='用户';

# ------------------------------------------------------------
# SCHEMA DUMP FOR TABLE: sys_user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` int(11) DEFAULT NULL COMMENT '用户id',
    `role_id` varchar(1000) DEFAULT NULL COMMENT '角色ids',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色';

# ------------------------------------------------------------
# Table structure for work
# ------------------------------------------------------------
DROP TABLE IF EXISTS `work`;
CREATE TABLE `work`
(
    `id`         int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `name`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
    `user_type`  int(0) NULL DEFAULT NULL COMMENT '医生类型',
    `dept_id`    int(0) NULL DEFAULT NULL COMMENT '归属科室',
    `work_time0` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工作时间',
    `numb`       int(0) NULL DEFAULT NULL COMMENT '当前接诊人数',
    `numb_max`   int(0) NULL DEFAULT NULL COMMENT '最大人数',
    `state`      int(0) NULL DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

# ------------------------------------------------------------
# DATA DUMP FOR TABLE: check_apply
# ------------------------------------------------------------

INSERT INTO `check_apply` (`id`,`register_id`,`item_id`,`item_name`,`fee`,`status`,`active`,`create_time`) VALUES (2,1,1,'一般体检',100.00,3,1,'2022-07-04 10:49:49');

# ------------------------------------------------------------
# DATA DUMP FOR TABLE: check_item
# ------------------------------------------------------------

INSERT INTO `check_item` (`id`,`name`,`content`,`fee`,`active`,`create_time`) VALUES (1,'一般体检','内、外、眼、耳鼻喉',100.00,1,'2022-08-29 14:15:56'),(2,'血常规(进口五分类)','各种血液病',25.00,1,'2022-08-29 14:15:56'),(3,'常规生化大套','46项,含肝功、肾功、心功、电解质、血脂、血糖、尿酸）肝胆肾疾病、脂代谢、糖尿病、尿酸、心肌酶、电解质',1200.00,1,'2022-08-29 14:15:56'),(4,'心电图','心脏疾病',25.00,1,'2022-08-29 14:15:56'),(5,'常规肝功能','肝胆疾病',112.00,1,'2022-08-29 14:15:56'),(6,'常规肾功能','肾功能、电解质、尿酸',126.00,1,'2022-08-29 14:15:56'),(7,'常规血脂（含血糖）','血脂代谢、糖尿病',60.00,1,'2022-08-29 14:15:56'),(8,'糖化血红蛋白','糖尿病(指近期2-3个月血糖平均水平)',80.00,1,'2022-08-29 14:15:56'),(9,'AFP（甲胎蛋白）定量','肝癌、肝硬化、重症肝炎',50.00,1,'2022-08-29 14:15:56'),(10,'CA-153 (糖类抗原153)','乳腺、肺部、胃肠肿瘤',82.00,1,'2022-08-29 14:15:56'),(11,'CA-242（糖类抗原242）','消化系统肿瘤',84.00,1,'2022-08-29 14:15:56'),(12,'乙肝病毒DNA定量','乙肝病情评估、疗效评估、预后判断',150.00,1,'2022-08-29 14:15:56'),(13,'血液流变学测定+动态血沉','反映血液的粘稠度',98.00,1,'2022-08-29 14:15:56'),(14,'颈椎正侧位片','颈椎疾病',140.00,1,'2022-08-29 14:15:56');

# ------------------------------------------------------------
# DATA DUMP FOR TABLE: constant_item
# ------------------------------------------------------------

INSERT INTO `constant_item` (`id`,`type_id`,`code`,`name`,`sort`,`active`,`create_time`) VALUES (1,1,'1','男',1,1,'2022-08-29 11:40:57'),(2,1,'2','女',2,1,'2022-08-29 11:40:57'),(3,2,'1','已挂号',1,1,'2022-08-29 11:40:57'),(4,2,'2','已接诊',2,1,'2022-08-29 11:40:57'),(5,2,'3','已退号',3,1,'2022-08-29 11:40:57'),(6,4,'1','医疗科室',1,1,'2022-08-29 11:40:57'),(7,4,'2','非医疗科室',2,1,'2022-08-29 11:40:57');

# ------------------------------------------------------------
# DATA DUMP FOR TABLE: constant_type
# ------------------------------------------------------------

INSERT INTO `constant_type` (`id`,`code`,`name`,`active`,`create_time`) VALUES (1,'GENDER','性别码',1,'2022-08-26 21:49:05'),(2,'REGISTER_STATUS','挂号状态',1,'2022-08-26 21:53:59'),(3,'DOCTOR_TYPE','医生类型',1,'2022-08-27 15:12:07'),(4,'DEPT_TYPE','科室类别',1,'2022-08-27 17:01:37');

# ------------------------------------------------------------
# DATA DUMP FOR TABLE: department
# ------------------------------------------------------------

INSERT INTO `department` (`id`,`name`,`address`,`leader`,`leader_id`,`active`,`create_time`) VALUES (1,'呼吸内科','3楼303','皇甫谧',9,1,'2022-08-29 11:40:57'),(2,'消化内科','4楼401','孙思邈',5,1,'2022-08-29 11:40:57'),(3,'神经内科','5楼501','华佗',4,1,'2022-08-29 11:40:57'),(4,'心血管内科','2楼203','李时珍',6,1,'2022-08-29 11:40:57'),(5,'肾内科','2楼201','扁鹊',8,1,'2022-08-29 11:40:57'),(6,'保卫科','1楼101','林莽',40,1,'2022-08-29 11:40:57'),(7,'免疫科','3楼304','张仲景',7,1,'2022-08-29 11:40:57'),(8,'体检科','3楼407','王幻巧',41,1,'2022-07-01 16:34:09');

# ------------------------------------------------------------
# DATA DUMP FOR TABLE: inspect_apply
# ------------------------------------------------------------

INSERT INTO `inspect_apply` (`id`,`item_id`,`item_name`,`fee`,`status`,`active`,`create_time`,`register_id`) VALUES (1,2,'血常规（五分类）',100.00,4,1,'2022-07-05 10:21:16',1),(2,10,'肝功能5项',100.00,1,1,'2022-07-05 10:21:25',1);

# ------------------------------------------------------------
# DATA DUMP FOR TABLE: inspect_item
# ------------------------------------------------------------

INSERT INTO `inspect_item` (`id`,`name`,`content`,`fee`,`active`,`create_time`) VALUES (1,'血常规（三分类）','WBC、RBC、PLT、HGB、HCT等18项+3个直方图',60.00,1,'2022-08-29 14:05:45'),(2,'血常规（五分类）','WBC、RBC、PLT、HGB、HCT等26个项目+五分类+异常淋巴',100.00,1,'2022-08-29 14:05:45'),(3,'血型','ABO血型鉴定+RH血型鉴定',20.00,1,'2022-08-29 14:05:45'),(4,'凝血四项 ','PT、APTT、TT、Fib ',80.00,1,'2022-08-29 14:05:45'),(5,'嗜酸性细胞直接计数','嗜酸性细胞直接计数',10.00,1,'2022-08-29 14:05:45'),(6,'异常白细胞形态检查','白细胞形态检查',20.00,1,'2022-08-29 14:05:45'),(7,'尿液分析+镜检','尿化学分析11项+镜检5项',45.00,1,'2022-08-29 14:05:45'),(8,'胸、腹水常规检查','颜色、透明度、李凡他试验、WBC等',123.00,1,'2022-08-29 14:05:45'),(9,'脑脊液常规检查CSF','颜色、透明度、潘氏试验、WBC等',500.00,1,'2022-08-29 14:05:45'),(10,'肝功能5项','ALT、AST、TBIL、DBIL、LBIl',100.00,1,'2022-08-29 14:05:45'),(11,'肝功能10项','P、ALB、GLB、GGT、LDH、ALP+肝功5项',200.00,1,'2022-08-29 14:05:45'),(12,'血流变','全血粘度、血浆粘度测定，红细胞流变特性、HCT、ESR等',886.00,1,'2022-08-29 14:05:45'),(13,'乙肝五项（定性）','HBsAg、Anti-HBs、HBeAg、Anti-HBe、Anti-HBc',500.00,1,'2022-08-29 14:05:45'),(14,'免疫九项（酶免法）','乙肝5项+TP+HAV+Anti-HCV+Anti-HIV',1000.00,1,'2022-08-29 14:05:45');

# ------------------------------------------------------------
# DATA DUMP FOR TABLE: regist_level
# ------------------------------------------------------------

INSERT INTO `regist_level` (`id`,`name`,`fee`,`active`,`create_time`,`role_id`) VALUES (11,'专家诊',200.00,1,'2022-07-01 15:19:16',3),(12,'教授',150.00,1,'2022-07-01 15:19:16',4),(13,'副教授',100.00,1,'2022-07-01 15:19:16',5),(14,'主治',50.00,1,'2022-07-01 15:19:16',6),(15,'普通诊',10.00,1,'2022-07-01 15:19:16',7);

# ------------------------------------------------------------
# DATA DUMP FOR TABLE: register
# ------------------------------------------------------------

INSERT INTO `register` (`id`,`name`,`gender`,`id_no`,`birthday`,`age`,`address`,`regsit_level_id`,`dept_id`,`doctor_id`,`readme`,`present`,`present_treat`,`history`,`allergy`,`book`,`fee`,`status`,`active`,`create_time`) VALUES (1,'李白',0,'110101199003079550','1990-03-07',30,'北京市-东城区',15,8,48,'体检','无','无','无','无',1,15.00,2,1,'2022-07-02 16:31:06'),(2,'李春光',0,'110101199003079552','1990-03-07',30,'北京市-东城区',14,8,47,'体检','无','无','无','无',1,55.00,3,1,'2022-07-04 16:24:56');

# ------------------------------------------------------------
# DATA DUMP FOR TABLE: sys_permission
# ------------------------------------------------------------

INSERT INTO `sys_permission` (`id`,`name`,`url`,`type`,`parent_id`,`active`,`create_time`) VALUES (1,'用户管理','',0,0,1,'2022-07-06 08:54:20'),(2,'基本信息管理','',0,0,1,'2022-07-06 08:56:38'),(3,'挂号收费','',0,0,1,'2022-07-06 08:59:31'),(4,'医生工作站','',0,0,1,'2022-07-06 08:59:42'),(5,'用户列表','user',1,1,1,'2022-07-06 09:03:08'),(6,'角色管理','role',1,1,1,'2022-07-06 09:03:45'),(7,'权限管理','permission',1,1,1,'2022-07-06 09:04:29'),(8,'常数类别管理','constant-type',1,2,1,'2022-07-06 09:05:03'),(9,'常数项目管理','constant-item',1,2,1,'2022-07-06 09:05:26'),(10,'挂号级别','registlevel',1,2,1,'2022-07-06 09:05:38'),(11,'科室管理','department',1,2,1,'2022-07-06 09:05:50'),(12,'检查项目管理','check-item',1,2,1,'2022-07-06 09:06:06'),(13,'检验项目管理','inspect-item',1,2,1,'2022-07-06 09:06:19'),(14,'挂号','register',1,3,1,'2022-07-06 09:06:40'),(15,'退号','return-num',1,3,1,'2022-07-06 09:08:07'),(16,'收费','fee',1,3,1,'2022-07-06 09:08:25'),(17,'退费','refund',1,3,1,'2022-07-06 09:08:38'),(18,'病历首页','doctor',1,4,1,'2022-07-06 09:09:11'),(19,'科室病例','dept_list',1,4,1,'2022-07-06 09:09:26'),(20,'挂号列表','cases',1,4,1,'2022-07-06 09:09:44');

# ------------------------------------------------------------
# DATA DUMP FOR TABLE: sys_role
# ------------------------------------------------------------

INSERT INTO `sys_role` (`id`,`name`,`active`,`create_time`) VALUES (1,'超级管理员',1,'2022-08-29 11:40:57'),(2,'管理员',1,'2022-08-29 11:40:57'),(3,'专家',1,'2022-08-29 11:40:57'),(4,'教授',1,'2022-08-29 11:40:57'),(5,'副教授',1,'2022-08-29 11:40:57'),(6,'主治医生',1,'2022-08-29 11:40:57'),(7,'医师',1,'2022-08-29 11:40:57'),(8,'护士',1,'2022-08-29 11:40:57'),(9,'保安',1,'2022-08-29 11:40:57');

# ------------------------------------------------------------
# DATA DUMP FOR TABLE: sys_role_permission
# ------------------------------------------------------------

INSERT INTO `sys_role_permission` (`id`,`role_id`,`permission_id`) VALUES (1,1,'1,5,6,7,2,8,9,10,11,12,13,3,14,15,16,17,20,4'),(2,2,'5,2,8,9,10,11,12,13,1'),(3,3,'12,13,18,19,2,4'),(4,4,'12,13,18,19,2,4'),(5,5,'16,17,18,3,4'),(6,6,'14,18,3,4'),(7,7,'14,15,18,3,4'),(8,8,'3,14,15,16,17');

# ------------------------------------------------------------
# DATA DUMP FOR TABLE: sys_user
# ------------------------------------------------------------

INSERT INTO `sys_user` (`id`,`username`,`password`,`real_name`,`telephone`,`user_type`,`dept_id`,`last_login`,`active`,`create_time`) VALUES (1,'admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','超级管理员','15243456759',1,0,'2022-07-07 09:58:13',1,'2022-08-29 11:40:57'),(2,'admin001','1267057f1ec769c39b88211c4c666382a7dc9fe22f765a66a37918a6a6ab581a','管理员1号','18905631669',2,0,NULL,1,'2022-08-29 11:40:57'),(3,'admin002','1267057f1ec769c39b88211c4c666382a7dc9fe22f765a66a37918a6a6ab581a','管理员2号','18905691669',2,0,NULL,1,'2022-08-29 11:40:57'),(4,'华佗','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','华佗','15305560455',3,3,NULL,1,'2022-08-29 11:40:57'),(5,'孙思邈','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','孙思邈','15305560453',3,2,NULL,1,'2022-08-29 11:40:57'),(6,'李时珍','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','李时珍','15305560451',3,4,NULL,1,'2022-08-29 11:40:57'),(7,'张仲景','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','张仲景','15305560459',3,7,NULL,1,'2022-08-29 11:40:57'),(8,'扁鹊','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','扁鹊','15305560411',3,5,NULL,1,'2022-08-29 11:40:57'),(9,'皇甫谧','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','皇甫谧','15304461455',3,1,NULL,1,'2022-08-29 11:40:57'),(10,'冯兴国','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','冯兴国','15305561415',4,2,NULL,1,'2022-08-29 11:40:57'),(11,'孙顺达','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','孙顺达','15305561435',4,1,NULL,1,'2022-08-29 11:40:57'),(12,'吴家栋','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','吴家栋','15306561455',5,3,NULL,1,'2022-08-29 11:40:57'),(13,'于学忠','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','于学忠','15305061455',5,4,NULL,1,'2022-08-29 11:40:57'),(14,'孙邦原','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','孙邦原','15305561675',6,5,NULL,1,'2022-08-29 11:40:57'),(15,'冷德友','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','冷德友','15305561985',6,7,NULL,1,'2022-08-29 11:40:57'),(16,'王海','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','王海','15305561455',7,4,NULL,1,'2022-08-29 11:40:57'),(17,'黄强辉','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','黄强辉','15705521455',7,2,NULL,1,'2022-08-29 11:40:57'),(18,'范长江','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','范长江','15715561455',7,2,NULL,1,'2022-08-29 11:40:57'),(19,'文元良','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','文元良','15725561455',7,3,NULL,1,'2022-08-29 11:40:57'),(20,'虞越彬','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','虞越彬','15735561455',7,1,NULL,1,'2022-08-29 11:40:57'),(21,'皇紫萱','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','皇紫萱','15304562455',8,2,NULL,1,'2022-08-29 11:40:57'),(22,'张凌旋','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','张凌旋','18701161455',8,3,NULL,1,'2022-08-29 11:40:57'),(23,'梁萧玉','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','梁萧玉','15705261455',8,4,NULL,1,'2022-08-29 11:40:57'),(24,'杨凝竹','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','杨凝竹','13705561455',8,5,NULL,1,'2022-08-29 11:40:57'),(25,'护士小姐姐','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','杨惜萍','15708561455',8,7,NULL,1,'2022-08-29 11:40:57'),(26,'护士','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','杨果','15701361455',8,1,NULL,1,'2022-08-29 11:40:57'),(27,'护士姐姐','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','杨水琴','15709561455',8,2,NULL,1,'2022-08-29 11:40:57'),(28,'血染素衣泪倾城','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','张秋曼','15701521455',8,3,NULL,1,'2022-08-29 11:40:57'),(29,'护士小姐姐','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','梁秋梅','15401561455',8,4,NULL,1,'2022-08-29 11:40:57'),(30,'墨染殇雪','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','王云','15701561425',8,5,NULL,1,'2022-08-29 11:40:57'),(31,'护士小姐姐','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','杨新巧','15701531455',8,7,NULL,1,'2022-08-29 11:40:57'),(32,'良人情未深','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','张若柳','15701891455',8,7,NULL,1,'2022-08-29 11:40:57'),(33,'护士小姐姐','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','凌秀娟','15701251455',8,1,NULL,1,'2022-08-29 11:40:57'),(34,'护士小姐姐','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','王艳红','15701461455',8,2,NULL,1,'2022-08-29 11:40:57'),(35,'护士小姐姐','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','梁云琼','15701301455',8,3,NULL,1,'2022-08-29 11:40:57'),(36,'护士小姐姐','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','柳梦洁','15701671455',8,4,NULL,1,'2022-08-29 11:40:57'),(37,'保安大叔','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','秦维','13704561455',9,6,NULL,1,'2022-08-29 11:40:57'),(38,'保安大哥','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','秦成周','13715561455',9,6,NULL,1,'2022-08-29 11:40:57'),(39,'保安小姐','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','林燕齐','13839562455',9,6,NULL,1,'2022-08-29 11:40:57'),(40,'保安大队长','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','林莽','13855561455',9,6,NULL,1,'2022-08-29 11:40:57'),(41,'Truda Daisy','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','王幻巧','13839562475',3,8,'2022-07-07 09:09:15',1,'2022-07-02 16:27:54'),(42,'Truda Daisy','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','犹飞白','13839562975',4,8,NULL,1,'2022-07-02 16:27:54'),(43,'Bella Ted','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','荆丹云','13835962475',4,8,NULL,1,'2022-07-02 16:27:54'),(44,'Xavier Eugene','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','石琳怡','13739562975',5,8,NULL,1,'2022-07-02 16:27:54'),(45,'Xavier Daisy','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','闽和暖','13739562411',5,8,NULL,1,'2022-07-02 16:27:54'),(46,'Eugene Daisy','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','浮君丽','13749562422',6,8,NULL,1,'2022-07-02 16:27:54'),(47,'Emily Daisy','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','温雨雪','13833562475',6,8,NULL,1,'2022-07-02 16:27:54'),(48,'Emily Leacock','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','董丽华','13831562475',7,8,'2022-07-07 09:08:18',1,'2022-07-02 16:27:54'),(49,'Emily ad','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','烟运洁','13839561475',7,8,NULL,1,'2022-07-02 16:27:54'),(50,'Clark Gibbon','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','倪水彤','13839222475',7,8,NULL,1,'2022-07-02 16:27:54'),(51,'Gibbon Daisy','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','飞秀隽','13839332475',8,8,NULL,1,'2022-07-02 16:27:54'),(52,'Truda Bessie','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','栋妮娜','13839322475',8,8,NULL,1,'2022-07-02 16:27:54'),(53,'Bessie Daisy','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','呼滢','13839562075',8,8,NULL,1,'2022-07-02 16:27:54'),(54,'Quinn Daisy','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','倪水彤','13839508475',8,8,NULL,1,'2022-07-02 16:27:54'),(55,'Quinn Bessie','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','洛云韶','13836462475',8,8,NULL,1,'2022-07-02 16:27:54');
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `telephone`, `user_type`, `dept_id`, `last_login`,
                        `active`, `create_time`, `work_time`)
VALUES (0, '全天在岗医生', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '全天候', '12345678900',
        8, 2,
        NULL, 1, '2022-08-29 11:40:57', '1a1b2a2b3a3b4a4b5a5b6a6b7a7b');
# ------------------------------------------------------------
# DATA DUMP FOR TABLE: sys_user_role
# ------------------------------------------------------------


/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
