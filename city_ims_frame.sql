/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.137.130
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 192.168.137.130:3306
 Source Schema         : city_ims

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 21/09/2020 12:24:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ims_area
-- ----------------------------
DROP TABLE IF EXISTS `ims_area`;
CREATE TABLE `ims_area`  (
  `area_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '地区id',
  `area_code` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地区代码',
  `area_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地区名称',
  `city_code` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '城市代码',
  PRIMARY KEY (`area_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3145 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ims_city
-- ----------------------------
DROP TABLE IF EXISTS `ims_city`;
CREATE TABLE `ims_city`  (
  `city_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '城市id',
  `city_code` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '城市代码',
  `city_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '城市名',
  `province_code` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '省份代码',
  PRIMARY KEY (`city_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 346 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ims_coordinate
-- ----------------------------
DROP TABLE IF EXISTS `ims_coordinate`;
CREATE TABLE `ims_coordinate`  (
  `coordinate_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '坐标id',
  `longitude` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '经度',
  `latitude` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`coordinate_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ims_image
-- ----------------------------
DROP TABLE IF EXISTS `ims_image`;
CREATE TABLE `ims_image`  (
  `image_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '图片id',
  `image_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片名',
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片url',
  PRIMARY KEY (`image_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ims_info
-- ----------------------------
DROP TABLE IF EXISTS `ims_info`;
CREATE TABLE `ims_info`  (
  `info_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `info_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '消息名称',
  `info_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '消息描述',
  `info_image` bigint(0) NULL DEFAULT NULL COMMENT '消息附属图片',
  `info_source` int(0) NULL DEFAULT NULL COMMENT '上传来源（1用户2摄像头）',
  `info_status` int(0) NULL DEFAULT NULL COMMENT '消息地点状态（0识别错误1无积水2积水3内涝4冰雪）',
  `info_area` int(0) NULL DEFAULT NULL COMMENT '消息地区id',
  `info_coordinate` bigint(0) NULL DEFAULT NULL COMMENT '消息坐标id',
  `info_identify` int(0) NULL DEFAULT NULL COMMENT '1自动识别2人工修改',
  `info_flag` int(0) NULL DEFAULT NULL COMMENT '是否展示(0不展示1展示)',
  `info_create_time` datetime(0) NULL DEFAULT NULL COMMENT '消息创建时间',
  `info_update_time` datetime(0) NULL DEFAULT NULL COMMENT '消息更新时间',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ims_province
-- ----------------------------
DROP TABLE IF EXISTS `ims_province`;
CREATE TABLE `ims_province`  (
  `province_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '省份id',
  `province_code` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '省份代码',
  `province_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '省份名',
  PRIMARY KEY (`province_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
