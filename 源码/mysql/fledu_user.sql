/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : fledu_user

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 18/07/2024 23:08:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` bigint(20) NOT NULL,
  `userName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userPassword` varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userPhone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userEmail` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` bigint(20) NOT NULL,
  `userName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userPassword` varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userPhone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userEmail` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1787841399642460162, 'admin', '123456', NULL, NULL);
INSERT INTO `student` VALUES (1787872248194777090, 'admin1', '123456', NULL, NULL);
INSERT INTO `student` VALUES (1787880864419192833, 'admin2', '123456', NULL, NULL);
INSERT INTO `student` VALUES (1792478589953597441, 'admin4', '123456', NULL, NULL);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` bigint(20) NOT NULL,
  `userName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userPassword` varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userPhone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userEmail` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1799085519128035329, 'admin', '123456', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
