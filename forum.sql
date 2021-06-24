/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云MySQL
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : 1.15.243.160:51001
 Source Schema         : forum

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 24/06/2021 15:50:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `art_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '帖子id',
  `art_com_num` int(11) NULL DEFAULT NULL COMMENT '帖子评论数量',
  `art_content` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '帖子内容',
  `art_updated` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '帖子更新时间',
  `art_created` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '帖子创建时间',
  `art_like_num` int(11) NULL DEFAULT NULL COMMENT '帖子喜爱度',
  `art_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '帖子标题',
  `art_type_id` bigint(20) NULL DEFAULT NULL COMMENT '帖子版块id',
  `art_user_id` bigint(20) NULL DEFAULT NULL COMMENT '发贴用户id',
  `art_view_num` int(11) NULL DEFAULT NULL COMMENT '帖子观看量',
  PRIMARY KEY (`art_id`) USING BTREE,
  INDEX `fk_art_type_id`(`art_type_id`) USING BTREE,
  INDEX `fk_art_user_id`(`art_user_id`) USING BTREE,
  CONSTRAINT `fk_art_type_id` FOREIGN KEY (`art_type_id`) REFERENCES `article_type` (`type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_art_user_id` FOREIGN KEY (`art_user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_type
-- ----------------------------
DROP TABLE IF EXISTS `article_type`;
CREATE TABLE `article_type`  (
  `type_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主题id',
  `type_level` tinyint(4) NOT NULL DEFAULT 1 COMMENT '板块排名级别\r\n数字越高 级别越高\r\n默认为1\r\n此字段的意义：可通过此字段调整排名前后\r\n便于管理员调控用户可见信息',
  `type_created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `type_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主题描述',
  `type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主题名',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '主题版主用户id',
  `type_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版块图片url',
  `article_num` bigint(20) NULL DEFAULT NULL COMMENT '帖子数量 并依据此进行查询',
  PRIMARY KEY (`type_id`) USING BTREE,
  INDEX `fk_type_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `fk_type_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `com_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '回复id',
  `com_art_id` bigint(20) NULL DEFAULT NULL COMMENT '主帖id',
  `com_content` varchar(1020) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回复内容',
  `com_created` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '回复时间',
  `com_user_id` bigint(20) NULL DEFAULT NULL COMMENT '回复人id',
  `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0: 未删除 1 已删除',
  PRIMARY KEY (`com_id`) USING BTREE,
  INDEX `fk_com_user_id`(`com_user_id`) USING BTREE,
  INDEX `com_art_id`(`com_art_id`) USING BTREE,
  CONSTRAINT `fk_com_user_id` FOREIGN KEY (`com_user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment_multi
-- ----------------------------
DROP TABLE IF EXISTS `comment_multi`;
CREATE TABLE `comment_multi`  (
  `com_multi_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '多回复id',
  `com_art_id` bigint(20) NOT NULL COMMENT '帖子id',
  `com_id` bigint(20) NOT NULL COMMENT '原回复id(即帖子下的评论)',
  `com_multi_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回复内容',
  `com_multi_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '回复时间',
  `com_multi_user_id` bigint(20) NULL DEFAULT NULL COMMENT '回复用户id',
  `com_multi_replied_user_id` bigint(20) NULL DEFAULT NULL COMMENT '被回复用户id',
  `com_multi_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0为回复层主 1为回复楼中楼其他用户',
  PRIMARY KEY (`com_multi_id`, `com_art_id`, `com_id`) USING BTREE,
  INDEX `fk_user_id`(`com_multi_user_id`) USING BTREE,
  INDEX `fk_com_id`(`com_id`) USING BTREE,
  INDEX `fk_replied_user_id`(`com_multi_replied_user_id`) USING BTREE,
  INDEX `fk_art_id`(`com_art_id`) USING BTREE,
  CONSTRAINT `fk_art_id` FOREIGN KEY (`com_art_id`) REFERENCES `comment` (`com_art_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_com_id` FOREIGN KEY (`com_id`) REFERENCES `comment` (`com_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_replied_user_id` FOREIGN KEY (`com_multi_replied_user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`com_multi_user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for msg
-- ----------------------------
DROP TABLE IF EXISTS `msg`;
CREATE TABLE `msg`  (
  `msg_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
  `msg_type` tinyint(4) NOT NULL COMMENT '1：帖子消息 2：回复消息   3：系统通知',
  `msg_content` varchar(1020) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息内容',
  `msg_send_user` bigint(255) NOT NULL DEFAULT 0 COMMENT '消息发送者 如为系统则默认为0',
  `msg_receive_user` bigint(255) NOT NULL COMMENT '消息接收者',
  `msg_created` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `msg_has_read` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0 未读  1 已读',
  `key_one` bigint(20) NULL DEFAULT NULL COMMENT '相关id',
  `key_two` bigint(20) NULL DEFAULT NULL,
  `key_three` bigint(20) NULL DEFAULT NULL,
  `related_id` bigint(20) NULL DEFAULT NULL COMMENT '当为系统通知时需要的关联id',
  PRIMARY KEY (`msg_id`) USING BTREE,
  INDEX `fk_receive_user`(`msg_receive_user`) USING BTREE,
  CONSTRAINT `fk_receive_user` FOREIGN KEY (`msg_receive_user`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
  `report_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一ID',
  `report_user_id` bigint(20) NOT NULL COMMENT '举报者ID',
  `reported_user_id` bigint(20) NULL DEFAULT NULL COMMENT '被举报者ID(相关)',
  `key_one` bigint(255) NOT NULL COMMENT '通过三个key唯一确定多重回复\r\n目前comment和用户仅需一个id确定',
  `key_two` bigint(255) NULL DEFAULT NULL,
  `key_three` bigint(255) NULL DEFAULT NULL,
  `report_kind` tinyint(4) NOT NULL COMMENT '1：举报comMulti \r\n2：举报comment \r\n3：举报用户 ',
  `report_type_id` int(10) NOT NULL COMMENT '举报的类型ID',
  `report_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `handle` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否已处理 0：未处理 1：已处理',
  `report_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '举报理由',
  PRIMARY KEY (`report_id`) USING BTREE,
  INDEX `fk_type_id`(`report_type_id`) USING BTREE,
  CONSTRAINT `fk_type_id` FOREIGN KEY (`report_type_id`) REFERENCES `report_type` (`type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for report_type
-- ----------------------------
DROP TABLE IF EXISTS `report_type`;
CREATE TABLE `report_type`  (
  `type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '举报类型ID',
  `type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '举报类型名称',
  `type_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '举报类型说明',
  `type_kind` tinyint(2) NOT NULL COMMENT '0： 举报帖子和评论 1：举报用户',
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config`  (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置名',
  `config_value` int(16) NOT NULL COMMENT '配置值',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置备注',
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_config_key`(`config_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一UID',
  `user_blog` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户主页',
  `user_concern` int(11) NULL DEFAULT 0 COMMENT '用户关注数',
  `user_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户邮箱地址 不可重复',
  `user_fans` int(11) NULL DEFAULT 0 COMMENT '用户被关注数',
  `user_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像url',
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称 不可重复',
  `user_psw` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `user_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户手机号码 不可重复',
  `user_sex` tinyint(4) NOT NULL COMMENT '用户性别 1：男 0：女',
  `user_show` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户签名',
  `user_status` tinyint(4) NULL DEFAULT 1 COMMENT '用户状态 1：激活 0：未激活 2：被锁定',
  `user_created` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建时间',
  `user_updated` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '用户更新时间',
  `user_rank` tinyint(4) NOT NULL DEFAULT 1 COMMENT '用户级别：0：超级管理员 1：普通用户\r\n2：版主',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `unique_uid`(`user_id`, `user_status`) USING BTREE,
  UNIQUE INDEX `unique_email`(`user_email`, `user_status`) USING BTREE,
  UNIQUE INDEX `unique_phone`(`user_phone`, `user_status`) USING BTREE,
  UNIQUE INDEX `unique_name`(`user_name`, `user_status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Triggers structure for table comment
-- ----------------------------
DROP TRIGGER IF EXISTS `comment_trigger`;
delimiter ;;
CREATE TRIGGER `comment_trigger` AFTER INSERT ON `comment` FOR EACH ROW BEGIN
	UPDATE article SET art_com_num = art_com_num + 1 WHERE art_id = NEW.com_art_id;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table comment_multi
-- ----------------------------
DROP TRIGGER IF EXISTS `com_mul_trigger`;
delimiter ;;
CREATE TRIGGER `com_mul_trigger` AFTER INSERT ON `comment_multi` FOR EACH ROW BEGIN
	UPDATE article SET art_com_num = art_com_num + 1 WHERE art_id = NEW.com_art_id;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
