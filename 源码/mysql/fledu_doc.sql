/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : fledu_doc

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 18/07/2024 23:08:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alone
-- ----------------------------
DROP TABLE IF EXISTS `alone`;
CREATE TABLE `alone`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `b` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `d` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alone
-- ----------------------------
INSERT INTO `alone` VALUES (1, '你觉得和周围的人相处融洽，有“物以类聚”之感。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (2, '你觉得缺个伴儿。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (3, '你觉得没人可以求助、分享，或依靠。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (4, '你觉得孤单。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (5, '你觉得是朋友群中的一员。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (6, '你觉得自己外向而友好。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (7, '你觉得你不能和周遭的人分享自己的兴趣和想法 。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (8, '你觉得和任何人都不再亲近了。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (9, '你觉得和身边的人有很多共同点。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (10, '你觉得和别人很亲近。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (11, '你觉得自己遭人冷落。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (12, '你觉得自己和别人的交往没有意义。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (13, '你觉得没人真的了解你。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (14, '你觉得自己与他人隔绝了。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (15, '你觉得如果你想，就一定能找个伴儿。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (16, '你觉得还是有人真正理解你。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (17, '你觉得害羞。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (18, '你觉得你身边虽然有人，但他们却没真正和你在一起。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (19, '你觉得还是有人可以说说话。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `alone` VALUES (20, '你觉得还是有人可以求助、分享，或依靠。', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');

-- ----------------------------
-- Table structure for anxiety
-- ----------------------------
DROP TABLE IF EXISTS `anxiety`;
CREATE TABLE `anxiety`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `b` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `d` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of anxiety
-- ----------------------------
INSERT INTO `anxiety` VALUES (1, '我感到比往常更加神经过敏的焦虑', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (2, '我无缘无故感到担心', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (3, '我容易心烦意乱或感到恐慌', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (4, '我感到我的身体好像被分成几块，支离破碎', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (5, '我感到事事都很顺利，不会有倒霉的事情发生', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (6, '我的四肢拌动和震颤', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (7, '我因头痛、颈痛和背痛而烦恼', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (8, '我感到无力而且容易疲劳', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (9, '我感到平静，能安静坐下来', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (10, '我感到我的心跳较快', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (11, '我因阵阵的眩晕而不舒服', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (12, '我有阵阵要晕倒的感觉', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (13, '我呼吸时进气和出气都不费力', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (14, '我的手指和脚趾感到麻木和刺激', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (15, '我因胃痛和消化不良而苦恼', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (16, '我必须频繁排尿', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (17, '我的手总是温暖而干燥', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (18, '我觉得脸发烧发红', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (19, '我容易入睡，晚上休息很好', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `anxiety` VALUES (20, '我做恶梦', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');

-- ----------------------------
-- Table structure for delay
-- ----------------------------
DROP TABLE IF EXISTS `delay`;
CREATE TABLE `delay`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `b` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of delay
-- ----------------------------
INSERT INTO `delay` VALUES (1, '不到最后期限不交活', '是', '否');
INSERT INTO `delay` VALUES (2, '上班时间总在网上瞎逛，快到下班才开始忙工作', '是', '否');
INSERT INTO `delay` VALUES (3, '没工作计划，不懂时间管理', '是', '否');
INSERT INTO `delay` VALUES (4, '总是“伪加班”，白天可做完的事，总是拖到下班后加班做', '是', '否');
INSERT INTO `delay` VALUES (5, '总是认为时间还有，不急', '是', '否');
INSERT INTO `delay` VALUES (6, '懒散，日复一日，总想着明天再做', '是', '否');
INSERT INTO `delay` VALUES (7, '每当别人询问工作进展时，经常说“让我再看看”', '是', '否');
INSERT INTO `delay` VALUES (8, '办公室里零食一大堆，上班时间经常吃零食', '是', '否');
INSERT INTO `delay` VALUES (9, '要做事时，脑子里能冒出各种理由：现在先做别的事，这个稍后', '是', '否');
INSERT INTO `delay` VALUES (10, '自我麻痹：还来得及，不行就通宵赶工', '是', '否');
INSERT INTO `delay` VALUES (11, '处理问题不分主次，忙了半天，最紧要的事没做', '是', '否');
INSERT INTO `delay` VALUES (12, '经常因为时间过于紧迫，草草交差', '是', '否');
INSERT INTO `delay` VALUES (13, '厚脸皮，别人怎么催，也定力十足，习以为常了', '是', '否');
INSERT INTO `delay` VALUES (14, '从不主动汇报工作', '是', '否');
INSERT INTO `delay` VALUES (15, '团队合作时，别人都面露难色，不愿和你合作', '是', '否');

-- ----------------------------
-- Table structure for depression
-- ----------------------------
DROP TABLE IF EXISTS `depression`;
CREATE TABLE `depression`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `b` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `d` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of depression
-- ----------------------------
INSERT INTO `depression` VALUES (1, '我觉得闷闷不乐，情绪低沉', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (2, '我觉得一天之中早晨最好', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (3, '我一阵阵地哭出来或是想哭', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (4, '我夜间睡眠不好', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (5, '我吃的和平时一样多', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (6, '我与异性接触时和以往一样感到愉快', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (7, '我发觉我的体重在下降', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (8, '我有便秘的苦恼', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (9, '我心跳比平时快', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (10, '我无缘无故感到疲乏', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (11, '我的头脑和平时一样清楚', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (12, '我觉得经常做的事情并没有困难', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (13, '我觉得不安而平静不下来', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (14, '我对将来抱有希望', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (15, '我比平常容易激动', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (16, '我觉得做出决定是容易的', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (17, '我觉得自己是个有用的人，有人需要我', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (18, '我的生活过得很有意思', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (19, '我认为如果我死了别人会生活的更好些', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');
INSERT INTO `depression` VALUES (20, '平常感兴趣的事我仍然照样感兴趣', '没有或很少时间', '小部分时间', '相当多时间', '绝大部分或全部时间');

-- ----------------------------
-- Table structure for history_chat
-- ----------------------------
DROP TABLE IF EXISTS `history_chat`;
CREATE TABLE `history_chat`  (
  `id` bigint(20) NOT NULL,
  `fileId` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `question` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `answer` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for history_data
-- ----------------------------
DROP TABLE IF EXISTS `history_data`;
CREATE TABLE `history_data`  (
  `id` bigint(20) NOT NULL,
  `fileId` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `answer` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `score` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of history_data
-- ----------------------------
INSERT INTO `history_data` VALUES (1806394791390121985, '660616f48050431eb8c62493f7c6870a', '1787841399642460162', '0', '[1,0,0,0,1,0,1,0,1,0,2,3,1,0,1,0,1,0,1,0]', 38);
INSERT INTO `history_data` VALUES (1806395933058699266, '8319a4e662cf489a981857f54f26e491', '1787841399642460162', '1', '[1,0,0,0,1,0,1,0,1,0,2,3,1,0,1,0,1,0,1,0]', 50);
INSERT INTO `history_data` VALUES (1806396208083406850, 'a51d7b57bf244d1ab6fd0d36ed1a2d80', '1787841399642460162', '2', '[1,0,0,0,1,0,1,0,1,0,0,1,0,1,0]', 9);
INSERT INTO `history_data` VALUES (1806396595129585666, 'd163de8b80a14af6b26dae710684c2af', '1787841399642460162', '4', '[1,0,0,0,1,0,1,0,1,0,2,3,1,0,1,0,1,0,4,4,0,1,0,1,0]', 49);
INSERT INTO `history_data` VALUES (1806396816471396354, '26911ccfbcff49e0b32e0b3265728a0b', '1787841399642460162', '5', '[1,0,0,0,1,0,1,0,1,0,0,0,1,0,1,0,1,0,0,1,0,1,0,1,0,0,1,0]', 17);

-- ----------------------------
-- Table structure for mature
-- ----------------------------
DROP TABLE IF EXISTS `mature`;
CREATE TABLE `mature`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `b` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `d` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `e` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mature
-- ----------------------------
INSERT INTO `mature` VALUES (1, '我所在单位的领导（或学校的老师）对待我的态度是：', '老是吹毛求疵地批评我', '只要有一点不对，马上就批评我，从不表扬我', '只要我不太出格，他们就不会指责我', '他们说我工作和学习还是认真的', '我有错误他们固然要批评，我有成绩他们也会表扬我');
INSERT INTO `mature` VALUES (2, '如果在比赛中我输了，我通常的做法是：', '找出输的原因，提高技术，争取下次赢', '对获得胜利的一方表示钦佩', '认为对方没啥了不起，在别的方面自己（或自己一方）比对方强', '认为对方这次赢的原因不足为奇，很快就忘记了', '认为对方这次赢的原因是运气好，如果自己运气好的话也会赢对方');
INSERT INTO `mature` VALUES (3, '当生活中遇到重大挫折（如高考落榜、失恋）时，我便会感到：', '这辈子算完了', '也许能在其他方面获得成功，予以补偿', '不甘心失败，决不惜付出任何代价，一定要实现自己的愿望', '没什么大不了的，我可以调整自己的计划或目标', '自己本来就不应当抱有这样高的期望或抱负');
INSERT INTO `mature` VALUES (4, '别人喜欢我的程度是：', '某些人很喜欢我，另一些人一点也不喜欢我', '一般人都有点喜欢我，但不以我为知己', '谁也不喜欢我', '大多数人都在一定程度上喜欢我', '我不了解别人的看法');
INSERT INTO `mature` VALUES (5, '我对谈论自己失败经历的态度是：', '只要有人对我失败的经历感兴趣，我就告诉他', '如果说在谈话中涉及到，我就无所顾忌地说出来', '我不愿让别人怜悯自己，因此很少谈自己失败的经历', '为了维护自尊我从不谈自己失败的经历', '我感到自己似乎没有遇到什么失败');
INSERT INTO `mature` VALUES (6, '在一般情况下，与我意见不相同的人都是：', '想法怪僻、难以理解的人', '没什么文化知识修养的人', '有相当理由坚持自己看法的人', '生活阅历和我不同的人', '素养比我丰富的人');
INSERT INTO `mature` VALUES (7, '我喜欢在游戏或竞赛中遇到的对手是：', '技艺很高超的人，使我有机会向他学习', '比我技艺略高些的人，这样玩起来更有劲', '技术稍逊于我的人，这样我可以总是赢他，显示自己的实力', '和我的技术不相上下的人，这样在平等的情况下最有益于展开竞争', '一个有比赛道德的人，不管他的技术水平如何');
INSERT INTO `mature` VALUES (8, '我喜欢的社会环境是：', '比现在更安宁、平静的社会环境', '就像现在这样的社会环境就很好', '正向好的方面发展的社会环境', '变化剧烈的社会环境，使我能利用这个机会发展自己', '比现在更富足的社会环境');
INSERT INTO `mature` VALUES (9, '我对待争论的态度是：', '随时准备进行激烈争论', '只对自己感兴趣的问题才争论', '我很少与人争论，喜欢自己独立思考各种观点的利弊', '我不喜欢争论，尽量避免', '无所谓');
INSERT INTO `mature` VALUES (10, '受到别人指责时，我通常的反应是：', '分析别人为什么指责我，自己在哪些地方有错', '保持沉默毫不在意，过后置之脑后', '也对他进行指责', '尽量照别人的意思去做', '如果我认为自己是对的，就为自己辩护');
INSERT INTO `mature` VALUES (11, '我认为亲友的帮助对一个人事业成功的影响是：', '总是有害的，这会使他在无人帮助的时候面对困难而一筹莫展', '通常是弊大于利，常常帮倒忙', '有时会有帮助，但这不是最主要的', '为了获得事业成功，这是必需的', '对一个人起步时有帮助');
INSERT INTO `mature` VALUES (12, '我认为对待社会生活环境的正确态度是：', '使自己适应周围的社会生活环境', '尽量利用生活环境中的有利因素发展自己', '改造生活环境中的不良因素，使生活环境变好', '遇到不良的社会生活环境，就下决心脱离这个环境', '好死不如歹活，不管周围生活环境是好是坏');
INSERT INTO `mature` VALUES (13, '我对死亡的态度是：', '从来不考虑死的问题', '经常想到死，但对死不十分惧怕', '把死看作是自然现象，但平时很少想到', '每次想到死就毛骨悚然', '不但不怕，反而认为自己死了是解脱');
INSERT INTO `mature` VALUES (14, '为了让别人对自己有好的印象，我的做法是：', '在未见面时就做准备', '虽很少预先准备，但在见面时提醒自己应给人留下一种好的印象', '懒得考虑给人一个好的印象', '我从来不做预先准备，也讨厌别人掩盖自己的本来面目', '为了工作和生活上的需要，有时应认真考虑如何给人以良好的印象');
INSERT INTO `mature` VALUES (15, '我认为要使自己生活得愉快而有意义，就必须生活在：', '天然关系融洽的亲友们中间', '有学识的人们中间', '志同道合的朋友们中间', '人数众多的亲戚、同学和同事们中间', '生活在什么人中间都一样');
INSERT INTO `mature` VALUES (16, '在工作或学习中遇到困难时，我通常是：', '向比我懂得多的任何人请教', '只向我的亲密朋友请教', '我总是尽自己的最大努力去独立解决，实在不行才去请求别人的帮助', '我只是咬紧牙关不请求别人来帮助', '我没发现可以请教的人');
INSERT INTO `mature` VALUES (17, '当自己的亲人错误地责怪我时，我通常会：', '心里憋气，但不吱声', '为了家庭和睦，违心地承认自己做错了事', '当即发火，并进行争论，以维护自己的自尊', '克制自己，耐心地解释和说明', '一笑了之，从不放在心上');
INSERT INTO `mature` VALUES (18, '在与别人的交往中，我通常是：', '喜欢故意引起别人对自己的注意', '希望别人注意我，但又想不明显地表示出来', '喜欢别人注意我，但并不刻意去追求这一点', '不喜欢别人注意我', '对于是否会引人注意，我从不在乎');
INSERT INTO `mature` VALUES (19, '外表对我来说：', '非常重要，常花很多时间修饰自己的外表', '比较重要，常花不多的时间作修饰', '不重要，只要让人看得过去就行了', '完全没有意义，我从不修饰自己的外表', '重要是重要，但实际上花时间不多');
INSERT INTO `mature` VALUES (20, '我喜欢与之经常交往的人是：', '异性，因为他们（或她们）与我更合得来', '同性，因为我和他们（或她们）更容易相处', '和我合得来的人，不管他们与我的性别是否相同', '我不喜欢与家庭以外的人多交往', '我只喜欢与少数合得来的同性朋友交往');
INSERT INTO `mature` VALUES (21, '当我必须在大庭广众中讲话时，我总是：', '因为紧张发窘而讲不清话', '尽管不习惯，但还是竭力保持神态自若的样子', ':我把这看成是一次考验，精神抖擞地去讲', '我喜欢出风头，这时讲话更出色', '无论如何也要推辞，不敢去讲话');
INSERT INTO `mature` VALUES (22, '我对用看手相、测八字来算命的看法是：', '我发现算命能了解过去和未来，而且很准', '算命人多数是骗子', '我不清楚算命到底是胡说，还是确有道理', '我不相信算命能预测人的过去和未来', '尽管我知道算命是迷信，但还是时常一试');
INSERT INTO `mature` VALUES (23, '在参加几个人的讨论会中，我通常是：', '第一个发表意见', '我对自己了解的问题发表看法', '我很少在小组会上发言', '我从来不在小组会上发言', '我虽然不带头发言，但总是要说上几句');
INSERT INTO `mature` VALUES (24, '我对社会的看法是：', '社会上到处都有丑恶的东西，我希望能逃避现实', '在社会上生活，要想永远保持正直、清白是很难的', '社会是复杂而迷人的大舞台，我很喜欢研究社会现象', '不管社会如何，我只希望自己能生活得愉快', '不管生活环境如何，我都要努力奋斗，无愧于自己的一生');
INSERT INTO `mature` VALUES (25, '当我在人生道路上遇到考验（如参加高考、竞选职位）时，我总是：', '很兴奋，因为这是表现自己的机会', '视作平常之事，因为我已经习惯了', '感到有些害怕，但仍硬着头皮去做', '非常害怕失败，宁愿放弃尝试', '听天由命吧！');

-- ----------------------------
-- Table structure for social_avoidance
-- ----------------------------
DROP TABLE IF EXISTS `social_avoidance`;
CREATE TABLE `social_avoidance`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `b` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of social_avoidance
-- ----------------------------
INSERT INTO `social_avoidance` VALUES (1, '即使在不熟悉的社交场合里我仍感到放松', '是', '否');
INSERT INTO `social_avoidance` VALUES (2, '我尽量避免迫使我参加交际应酬的情形', '是', '否');
INSERT INTO `social_avoidance` VALUES (3, '我同陌生人在一起时很容易放松', '是', '否');
INSERT INTO `social_avoidance` VALUES (4, '我并不特别想去回避人们', '是', '否');
INSERT INTO `social_avoidance` VALUES (5, '我通常发现社交场合令人心烦意乱', '是', '否');
INSERT INTO `social_avoidance` VALUES (6, '在社交场合我通常感觉平静及舒适', '是', '否');
INSERT INTO `social_avoidance` VALUES (7, '在同异性交谈时，我通常感觉放松', '是', '否');
INSERT INTO `social_avoidance` VALUES (8, '我尽量避免与人家讲话，除非特别熟', '是', '否');
INSERT INTO `social_avoidance` VALUES (9, '如果有同新人相会的机会，我会抓住的', '是', '否');
INSERT INTO `social_avoidance` VALUES (10, '在非正式的聚会上如有异性参加，我通常觉得焦虑和紧张', '是', '否');
INSERT INTO `social_avoidance` VALUES (11, '我通常与人们在一起时感到焦虑，除非与他们特别熟', '是', '否');
INSERT INTO `social_avoidance` VALUES (12, '我与一群人在一起时通常感到放松', '是', '否');
INSERT INTO `social_avoidance` VALUES (13, '我经常想离开人群', '是', '否');
INSERT INTO `social_avoidance` VALUES (14, '在置身于不认识的人群中时．我感到不自在', '是', '否');
INSERT INTO `social_avoidance` VALUES (15, '在初次遇见某些人时，我通常是放松的', '是', '否');
INSERT INTO `social_avoidance` VALUES (16, '被介绍给别人使我感到紧张的焦虑', '是', '否');
INSERT INTO `social_avoidance` VALUES (17, '尽管满房间都是生人，我可能还是会进去的', '是', '否');
INSERT INTO `social_avoidance` VALUES (18, '我会避免走上前去加入到一大群人中间', '是', '否');
INSERT INTO `social_avoidance` VALUES (19, '当上司想同我谈话时，我很高兴与他谈话', '是', '否');
INSERT INTO `social_avoidance` VALUES (20, '我经常想出一些借口以回避社交活动', '是', '否');
INSERT INTO `social_avoidance` VALUES (21, '我尽量避开正式的社交场合', '是', '否');
INSERT INTO `social_avoidance` VALUES (22, '在网上或社交聚会上与人们交谈对我不成问题', '是', '否');
INSERT INTO `social_avoidance` VALUES (23, '我有时充当为人们相互介绍的角色', '是', '否');
INSERT INTO `social_avoidance` VALUES (24, '在一大群人中间，我极少能感到自在', '是', '否');
INSERT INTO `social_avoidance` VALUES (25, '当与—群人在一起时，我通常感觉忐忑不安', '是', '否');
INSERT INTO `social_avoidance` VALUES (26, '我喜欢躲开人群', '是', '否');
INSERT INTO `social_avoidance` VALUES (27, '不管是什么社交活动，我一般是能去就去', '是', '否');
INSERT INTO `social_avoidance` VALUES (28, '我发现同他人在一起时放松很容易', '是', '否');

SET FOREIGN_KEY_CHECKS = 1;
