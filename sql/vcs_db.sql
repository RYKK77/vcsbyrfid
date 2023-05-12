/*
 Navicat Premium Data Transfer

 Source Server         : dbsql
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : vcs_db

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 12/05/2023 14:53:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_log_operation
-- ----------------------------
DROP TABLE IF EXISTS `user_log_operation`;
CREATE TABLE `user_log_operation`  (
  `log_id` bigint(20) NOT NULL COMMENT '日志ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`log_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '日志关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vcs_code
-- ----------------------------
DROP TABLE IF EXISTS `vcs_code`;
CREATE TABLE `vcs_code`  (
  `id` bigint(20) NOT NULL COMMENT '预警ID',
  `code` int(11) NOT NULL COMMENT '验证码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '验证码对照表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vcs_device
-- ----------------------------
DROP TABLE IF EXISTS `vcs_device`;
CREATE TABLE `vcs_device`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '设备ID',
  `dName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备类型',
  `longitude` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '经度',
  `latitude` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '纬度',
  `isValid` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否有效',
  `isSecret` tinyint(4) NOT NULL DEFAULT 0,
  `isDeleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vcs_log
-- ----------------------------
DROP TABLE IF EXISTS `vcs_log`;
CREATE TABLE `vcs_log`  (
  `id` bigint(20) NOT NULL COMMENT '日志ID',
  `storage_path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储地址',
  `record_range` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '记录时间范围',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '日志存储' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vcs_nvehicle
-- ----------------------------
DROP TABLE IF EXISTS `vcs_nvehicle`;
CREATE TABLE `vcs_nvehicle`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '车辆ID',
  `carNumber` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '车牌号',
  `state` int(11) NOT NULL DEFAULT 0 COMMENT '车辆状态',
  `useRange` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '正常使用时段',
  `isDeleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `createdTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatedTime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `userId` bigint(20) NOT NULL COMMENT '用户ID',
  `mold` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '自行车' COMMENT '车辆类型',
  `lastPlaceId` bigint(20) NOT NULL DEFAULT 0 COMMENT '最后一次车辆记录点（以RFID设备ID标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '车辆' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vcs_record
-- ----------------------------
DROP TABLE IF EXISTS `vcs_record`;
CREATE TABLE `vcs_record`  (
  `id` bigint(20) NOT NULL COMMENT '记录ID',
  `type` int(11) NOT NULL DEFAULT 0 COMMENT '记录类型',
  `createdTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deviceId` bigint(20) NOT NULL COMMENT '设备ID',
  `nvehicleId` bigint(20) NOT NULL COMMENT '车辆ID',
  `userId` bigint(20) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vcs_remind
-- ----------------------------
DROP TABLE IF EXISTS `vcs_remind`;
CREATE TABLE `vcs_remind`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '提醒ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `remind_user_id` bigint(20) NOT NULL COMMENT '被提醒用户ID',
  `remind_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '提醒内容',
  `remind_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提醒时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '提醒' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vcs_rfid
-- ----------------------------
DROP TABLE IF EXISTS `vcs_rfid`;
CREATE TABLE `vcs_rfid`  (
  `id` bigint(20) NOT NULL COMMENT '信息ID',
  `checkCode` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '校验码',
  `validDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '有效期',
  `isValid` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否有效',
  `createdTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatedTime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `userId` bigint(20) NOT NULL COMMENT '用户ID',
  `nvehicleId` bigint(20) NOT NULL COMMENT '车辆ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'RFID信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vcs_user
-- ----------------------------
DROP TABLE IF EXISTS `vcs_user`;
CREATE TABLE `vcs_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `sid` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户学号',
  `userName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '张三' COMMENT '用户姓名',
  `mail` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'unknow@qq.com' COMMENT '用户邮箱',
  `password` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户密码',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户手机号',
  `role` tinyint(4) NOT NULL DEFAULT 1 COMMENT '用户角色',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1652153102582800387 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vcs_warning
-- ----------------------------
DROP TABLE IF EXISTS `vcs_warning`;
CREATE TABLE `vcs_warning`  (
  `id` bigint(20) NOT NULL COMMENT '预警ID',
  `warningContent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '预警内容',
  `warningType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '预警类型',
  `createdTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '预警时间',
  `userId` bigint(20) NOT NULL COMMENT '用户ID',
  `nvehicleId` bigint(20) NOT NULL COMMENT '车辆ID',
  `deviceId` bigint(20) NOT NULL COMMENT '设备ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '预警记录' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
