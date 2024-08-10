/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : fledu_chat

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 05/08/2024 16:16:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for character_info
-- ----------------------------
DROP TABLE IF EXISTS `character_info`;
CREATE TABLE `character_info`  (
  `id` bigint(20) NOT NULL,
  `userId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `mission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色任务场景',
  `cid` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for history_chat
-- ----------------------------
DROP TABLE IF EXISTS `history_chat`;
CREATE TABLE `history_chat`  (
  `id` bigint(20) NOT NULL,
  `userId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cid` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `question` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `answer` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for info
-- ----------------------------
DROP TABLE IF EXISTS `info`;
CREATE TABLE `info`  (
  `id` bigint(20) NOT NULL,
  `playerId` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for latest_chat
-- ----------------------------
DROP TABLE IF EXISTS `latest_chat`;
CREATE TABLE `latest_chat`  (
  `id` bigint(20) NOT NULL,
  `userId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cid` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自定义角色',
  `latestChatId` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for new_chat
-- ----------------------------
DROP TABLE IF EXISTS `new_chat`;
CREATE TABLE `new_chat`  (
  `id` bigint(20) NOT NULL,
  `userId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
