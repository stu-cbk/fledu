/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : fledu_video

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 18/07/2024 23:10:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for history_data
-- ----------------------------
DROP TABLE IF EXISTS `history_data`;
CREATE TABLE `history_data`  (
  `id` bigint(20) NOT NULL,
  `status` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `taskIdList` varchar(1500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `taskStatus` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `totalTask` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of history_data
-- ----------------------------
INSERT INTO `history_data` VALUES (1801459908913987585, '2', '[\"240614115633023807725828\",\"240614115633089816887023\",\"240614115633130652315829\",\"240614115633183459617024\",\"240614115633222640395830\"]', '[1,1,1,1,1]', 5);
INSERT INTO `history_data` VALUES (1805574998680498177, '2', '[\"240625201606568195619385\",\"240625201606627490969386\",\"240625201606671477821206\",\"240625201606760240421207\",\"240625201606833351799388\"]', '[1,1,1,1,1]', 5);
INSERT INTO `history_data` VALUES (1808537927105150978, '3', '[\"240704003112478502375598\",\"240704003112535474135599\",\"240704003112579428455600\",\"240704003112628744225225\",\"240704003112674292125601\"]', '[1,1,1,1,1]', 5);
INSERT INTO `history_data` VALUES (1811365588064137218, '0', NULL, NULL, NULL);
INSERT INTO `history_data` VALUES (1811366685281370114, '0', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
