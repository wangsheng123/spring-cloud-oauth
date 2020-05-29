/*
Navicat MySQL Data Transfer

Source Server         : 59.110.138.14
Source Server Version : 50726
Source Host           : 59.110.138.14:3306
Source Database       : oauth-center

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2020-05-29 17:42:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `client_id` varchar(48) NOT NULL COMMENT '应用标识',
  `resource_ids` varchar(256) DEFAULT NULL COMMENT '资源限定串(逗号分割)',
  `client_secret` varchar(256) DEFAULT NULL COMMENT '应用密钥(bcyt) 加密',
  `client_secret_str` varchar(256) DEFAULT NULL COMMENT '应用密钥(明文)',
  `scope` varchar(256) DEFAULT NULL COMMENT '范围',
  `authorized_grant_types` varchar(256) DEFAULT NULL COMMENT '5种oauth授权方式(authorization_code,password,refresh_token,client_credentials)',
  `web_server_redirect_uri` varchar(256) DEFAULT NULL COMMENT '回调地址 ',
  `authorities` varchar(256) DEFAULT NULL COMMENT '权限',
  `access_token_validity` int(11) DEFAULT NULL COMMENT 'access_token有效期',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT 'refresh_token有效期',
  `additional_information` varchar(4096) DEFAULT '{}' COMMENT '{}',
  `autoapprove` varchar(256) DEFAULT NULL COMMENT '是否自动授权 是-true',
  `status` int(1) DEFAULT NULL,
  `if_limit` int(11) NOT NULL DEFAULT '0',
  `limit_count` int(11) NOT NULL DEFAULT '10000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_client_service
-- ----------------------------
DROP TABLE IF EXISTS `sys_client_service`;
CREATE TABLE `sys_client_service` (
  `clientId` int(11) NOT NULL COMMENT '应用标识',
  `serviceId` int(11) NOT NULL COMMENT '服务权限标识',
  PRIMARY KEY (`clientId`,`serviceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sys_gateway_routes
-- ----------------------------
DROP TABLE IF EXISTS `sys_gateway_routes`;
CREATE TABLE `sys_gateway_routes` (
  `id` char(32) NOT NULL COMMENT 'id',
  `uri` varchar(100) NOT NULL COMMENT 'uri路径',
  `predicates` varchar(1000) DEFAULT NULL COMMENT '判定器',
  `filters` varchar(1000) DEFAULT NULL COMMENT '过滤器',
  `order` int(11) DEFAULT NULL COMMENT '排序',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `delFlag` int(11) DEFAULT '0' COMMENT '删除标志 0 不删除 1 删除',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务网关路由表';

-- ----------------------------
-- Table structure for sys_service
-- ----------------------------
DROP TABLE IF EXISTS `sys_service`;
CREATE TABLE `sys_service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `url` varchar(1024) DEFAULT NULL,
  `path` varchar(1024) DEFAULT NULL,
  `css` varchar(32) DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  `isMenu` int(11) DEFAULT NULL COMMENT '是否服务 1 是 2 不是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb4;

INSERT INTO `oauth-center`.`oauth_client_details` ( `client_id`, `resource_ids`, `client_secret`, `client_secret_str`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`, `status`, `if_limit`, `limit_count`) VALUES ( 'client_1', NULL, '$2a$10$wZb5GFXtVMuxDEaRcwU6M.hfAaauZTBY.P5bZtANgqsJDC8a7umyy', NULL, 'all', 'password,refresh_token,client_credentials', NULL, NULL, '18000', '18000', '{}', 'true', '1', '0', '10000');
