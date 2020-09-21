/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.137.130
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 192.168.137.130:3306
 Source Schema         : city_cms

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 21/09/2020 12:25:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cms_camera
-- ----------------------------
DROP TABLE IF EXISTS `cms_camera`;
CREATE TABLE `cms_camera`  (
  `camera_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '摄像头id',
  `camera_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '摄像头名称',
  `camera_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '摄像头描述',
  `camera_coordinate` bigint(0) NULL DEFAULT NULL COMMENT '摄像头坐标id',
  `camera_area` int(0) NULL DEFAULT NULL COMMENT '摄像头地区id',
  PRIMARY KEY (`camera_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
