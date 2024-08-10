/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : fledu_ppt

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 18/07/2024 23:08:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for history_data
-- ----------------------------
DROP TABLE IF EXISTS `history_data`;
CREATE TABLE `history_data`  (
  `id` bigint(20) NOT NULL,
  `userId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `outlineSid` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `outline` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `coverImgSrc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pptSid` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pptUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `lastChangeTime` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of history_data
-- ----------------------------
INSERT INTO `history_data` VALUES (1800153299331936257, '1787841399642460162', '8e13c6a54c5e4ec28394554e8dfe0da5', '{\"chapters\":[{\"chapterContents\":[{\"chapterTitle\":\"秦国起源与早期发展\"},{\"chapterTitle\":\"商鞅变法及其影响\"},{\"chapterTitle\":\"秦始皇统一六国\"}],\"chapterTitle\":\"秦朝建立与发展\"},{\"chapterContents\":[{\"chapterTitle\":\"中央集权体制确立\"},{\"chapterTitle\":\"法家思想与律法体系\"},{\"chapterTitle\":\"郡县制实施与管理\"}],\"chapterTitle\":\"政治制度与法律\"},{\"chapterContents\":[{\"chapterTitle\":\"农业技术改进与增产\"},{\"chapterTitle\":\"商业贸易与货币流通\"},{\"chapterTitle\":\"社会阶层与民众生活\"}],\"chapterTitle\":\"经济与社会发展\"},{\"chapterContents\":[{\"chapterTitle\":\"对外战争与领土扩张\"},{\"chapterTitle\":\"长城建设与边防策略\"},{\"chapterTitle\":\"军队组织结构与武器\"}],\"chapterTitle\":\"军事扩张与国防\"},{\"chapterContents\":[{\"chapterTitle\":\"文字统一与文化传播\"},{\"chapterTitle\":\"科技进步与工程奇迹\"},{\"chapterTitle\":\"思想控制与书籍焚毁\"}],\"chapterTitle\":\"文化与科技成就\"},{\"chapterContents\":[{\"chapterTitle\":\"内部矛盾与权力斗争\"},{\"chapterTitle\":\"农民起义与陈胜吴广\"},{\"chapterTitle\":\"秦二世时期政权崩溃\"}],\"chapterTitle\":\"秦朝衰亡原因分析\"},{\"chapterContents\":[{\"chapterTitle\":\"对中国后世影响\"},{\"chapterTitle\":\"对世界文明贡献\"},{\"chapterTitle\":\"历史评价与学术研究\"}],\"chapterTitle\":\"秦朝历史遗产\"}],\"end\":\"\",\"subTitle\":\"探索中国首个统一封建王朝兴衰\",\"title\":\"秦朝历史概述\"}', 'https://bjcdn.openstorage.cn/xinghuo-privatedata/xo8ihd1o.jpg', 'afc19d56eced44f9bd6778a64b272654', '秦朝历史概述', 'https://bjcdn.openstorage.cn/xinghuo-privatedata/%2Ftmp/apiTempFileafc19d56eced44f9bd6778a64b2726545850350932434594037/%E7%A7%A6%E6%9C%9D%E5%8E%86%E5%8F%B2%E6%A6%82%E8%BF%B0.pptx', '2024-06-10 13:35:25');

SET FOREIGN_KEY_CHECKS = 1;
