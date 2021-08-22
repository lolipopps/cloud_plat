/*
Navicat MySQL Data Transfer

Source Server         : 47.100.71.215
Source Server Version : 80021
Source Host           : 47.100.71.215:3306
Source Database       : cloud_plat

Target Server Type    : MYSQL
Target Server Version : 80021
File Encoding         : 65001

Date: 2021-08-22 21:56:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint unsigned DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `is_parent` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '0', '', '2018-08-10 20:40:40', '0', '', '2018-08-11 00:03:06', '1.00', '0', '总部', '');
INSERT INTO `sys_dept` VALUES ('2', '1', '', '2018-08-10 20:40:48', '0', '', '2018-08-11 00:27:05', '1.00', '0', '技术部', '');
INSERT INTO `sys_dept` VALUES ('3', '2', '', '2018-08-10 20:40:58', '0', '', '2018-08-11 01:29:42', '1.00', '0', '研发中心', null);
INSERT INTO `sys_dept` VALUES ('4', '2', '', '2018-08-10 20:58:15', '0', '', '2018-08-10 22:02:15', '2.00', '0', '大数据', null);
INSERT INTO `sys_dept` VALUES ('5', '2', '', '2018-08-10 20:58:27', '0', '', '2018-08-11 17:26:38', '3.00', '-1', '人工智障', null);
INSERT INTO `sys_dept` VALUES ('6', '0', '', '2018-08-10 22:02:04', '0', '', '2018-08-11 00:02:53', '2.00', '0', '成都分部', '');
INSERT INTO `sys_dept` VALUES ('7', '6', '', '2018-08-10 22:05:01', '0', '', '2018-08-11 17:48:44', '2.00', '0', 'Vue', null);
INSERT INTO `sys_dept` VALUES ('8', '6', '', '2018-08-11 01:03:56', '0', '', '2018-08-11 17:50:04', '1.00', '0', 'JAVA', '\0');
INSERT INTO `sys_dept` VALUES ('9', '0', '', '2018-08-11 18:29:57', '0', '', '2018-08-12 18:45:01', '3.00', '0', '人事部', '');
INSERT INTO `sys_dept` VALUES ('10', '9', null, '2018-08-11 18:30:13', '0', null, '2018-08-11 18:30:13', '1.00', '0', '游客', '\0');
INSERT INTO `sys_dept` VALUES ('11', '9', '', '2018-08-11 20:25:16', '0', '', '2018-08-11 22:47:48', '2.00', '0', 'VIP', '\0');

-- ----------------------------
-- Table structure for sys_dept_header
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_header`;
CREATE TABLE `sys_dept_header` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `user_id` bigint unsigned DEFAULT NULL,
  `dept_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dept_header
-- ----------------------------
INSERT INTO `sys_dept_header` VALUES ('1254427833757995008', 'admin', '2020-04-26 23:11:16', '0', 'admin', '2020-04-26 23:11:16', '0', '1', '40322777781112832');
INSERT INTO `sys_dept_header` VALUES ('1254427833757995009', 'admin', '2020-04-26 23:11:16', '0', 'admin', '2020-04-26 23:11:16', '0', '4', '40322777781112832');
INSERT INTO `sys_dept_header` VALUES ('1254427833757995010', 'admin', '2020-04-26 23:11:16', '0', 'admin', '2020-04-26 23:11:16', '1', '4', '40322777781112832');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `cost_time` int unsigned DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `ip_info` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `request_param` longtext,
  `request_type` varchar(255) DEFAULT NULL,
  `request_url` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `log_type` tinyint(1) DEFAULT NULL,
  `lat` varchar(255) DEFAULT NULL,
  `lng` varchar(255) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  `area_code` varchar(255) DEFAULT NULL,
  `service_id` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=648 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint unsigned DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  `component` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `level` int unsigned DEFAULT NULL,
  `button_type` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `show_always` bit(1) DEFAULT NULL,
  `is_menu` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '120', '', '2018-06-04 19:02:29', '0', '', '2018-09-29 23:11:56', '', 'sys', '0', '1.00', 'Main', '/sys', '系统管理', 'ios-settings', '1', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('2', '1', '', '2018-06-04 19:02:32', '0', '', '2018-08-13 15:15:33', '', 'user-manage', '0', '1.10', 'sys/user-manage/userManage', 'user-manage', '用户管理', 'md-person', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('3', '1', '', '2018-06-04 19:02:35', '0', '', '2018-10-13 13:51:36', '', 'role-manage', '0', '1.60', 'sys/role-manage/roleManage', 'role-manage', '角色权限管理', 'md-contacts', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('4', '1', '', '2018-06-04 19:02:37', '0', '', '2018-09-23 23:32:02', '', 'menu-manage', '0', '1.70', 'sys/menu-manage/menuManage', 'menu-manage', '菜单权限管理', 'md-menu', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('5', '2', '', '2018-06-03 22:04:06', '0', '', '2018-09-19 22:16:44', '', '', '1', '1.11', '', '/freight/user/admin/add*', '添加用户', '', '3', 'add', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('6', '2', '', '2018-06-03 22:06:09', '0', '', '2018-06-06 14:46:51', '', '', '1', '1.13', '', '/freight/user/admin/disable/**', '禁用用户', '', '3', 'disable', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('7', '2', '', '2018-06-03 22:33:52', '0', '', '2018-06-28 16:44:48', '', '', '1', '1.14', '', '/freight/user/admin/enable/**', '启用用户', '', '3', 'enable', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('13', '2', '', '2018-06-06 14:45:16', '0', '', '2018-09-19 22:16:48', '', '', '1', '1.12', '', '/freight/user/admin/edit*', '编辑用户', '', '3', 'edit', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('14', '2', '', '2018-06-06 14:46:32', '0', '', '2018-08-10 21:41:16', '', '', '1', '1.15', '', '/freight/user/delByIds/**', '删除用户', '', '3', 'delete', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('15', '3', '', '2018-06-06 15:22:03', '0', '', '2018-09-19 22:07:34', '', '', '1', '1.21', '', '/freight/role/save*', '添加角色', '', '3', 'add', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('16', '3', '', '2018-06-06 15:30:59', '0', '', '2018-09-19 22:07:37', '', '', '1', '1.22', '', '/freight/role/edit*', '编辑角色', '', '3', 'edit', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('17', '3', '', '2018-06-06 15:31:26', '0', '', '2018-08-10 21:41:23', '', '', '1', '1.23', '', '/freight/role/delAllByIds/**', '删除角色', '', '3', 'delete', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('18', '3', null, '2018-06-06 15:31:59', '0', null, '2018-06-06 15:31:59', null, null, '1', '1.24', null, '/freight/role/editRolePerm**', '分配权限', null, '3', 'editPerm', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('19', '3', '', '2018-06-06 15:33:41', '0', '', '2018-09-19 22:07:46', '', '', '1', '1.25', '', '/freight/role/setDefault*', '设为默认角色', '', '3', 'setDefault', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('20', '4', '', '2018-06-06 15:51:46', '0', '', '2018-09-19 22:07:52', '', '', '1', '1.31', '', '/freight/permission/add*', '添加菜单', '', '3', 'add', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('21', '4', '', '2018-06-06 15:52:44', '0', '', '2018-09-19 22:07:57', '', '', '1', '1.32', '', '/freight/permission/edit*', '编辑菜单', '', '3', 'edit', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('22', '4', '', '2018-06-06 15:53:17', '0', '', '2018-08-10 21:41:33', '', '', '1', '1.33', '', '/freight/permission/delByIds/**', '删除菜单', '', '3', 'delete', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('23', '2', '', '2018-06-29 14:51:09', '0', '', '2018-10-08 11:13:27', '', '', '1', '1.16', '', '无', '上传图片', '', '3', 'upload', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('24', '120', null, null, '0', '18170878718', '2021-01-09 01:03:19', '', 'monitor', '0', '9.00', 'Main', '/monitor', '系统监控', 'ios-analytics', '1', '', '0', '', '', '');
INSERT INTO `sys_menu` VALUES ('25', '24', '', '2018-08-09 17:44:57', '0', 'admin', '2019-01-20 00:37:29', '', 'druid', '0', '2.40', 'sys/monitor/monitor', 'druid', 'SQL监控', 'md-analytics', '2', '', '0', 'https://127.0.0.1:9999/druid/login.html', '', null);
INSERT INTO `sys_menu` VALUES ('26', '24', '', '2018-08-09 17:54:08', '0', 'admin', '2019-01-20 00:37:41', '', 'swagger', '0', '2.50', 'sys/monitor/monitor', 'swagger', '接口文档', 'md-document', '2', '', '0', 'https://127.0.0.1:9999/swagger-ui.html', '', null);
INSERT INTO `sys_menu` VALUES ('27', '1', null, '2018-08-10 15:06:10', '0', null, '2018-08-10 15:06:10', null, 'department-manage', '0', '1.20', 'sys/department-manage/departmentManage', 'department-manage', '部门管理', 'md-git-branch', '2', '', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('28', '24', '', '2018-08-13 17:34:43', '0', 'admin', '2020-03-25 20:31:16', '', 'log-manage', '0', '2.20', 'sys/log-manage/logManage', 'log-manage', '日志管理', 'md-list-box', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('29', '28', '', '2018-08-13 17:36:16', '0', '', '2018-08-13 17:56:11', '', '', '1', '2.11', '', '/freight/log/delByIds/**', '删除日志', '', '3', 'delete', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('30', '28', '', '2018-08-13 17:41:48', '0', '', '2018-09-19 22:08:57', '', '', '1', '2.12', '', '/freight/log/delAll*', '清空日志', '', '3', 'undefined', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('32', '24', '', '2018-08-13 18:08:45', '0', '', '2019-07-07 21:24:26', '', 'actuator', '0', '2.30', 'sys/actuator/actuator', 'actuator', 'Actuator监控', 'logo-angular', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('33', '147', '', '2018-08-13 18:15:35', '0', 'admin', '2019-11-22 11:47:07', '', 'vue-template', '0', '3.10', 'Main', '/vue-template', '后台Vue模版', 'ios-albums', '1', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('34', '33', '', '2018-08-13 18:23:08', '0', '18170878718', '2020-12-20 22:43:17', '', 'simple-table', '0', '3.10', 'freight-vue-template/simple-table/simpleTable', 'simple-table', '简单表格+动态列', 'ios-grid-outline', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('35', '33', '', '2018-08-13 18:26:33', '0', '18170878718', '2020-12-20 22:45:53', '', 'search-table', '0', '3.40', 'freight-vue-template/search-table/searchTable', 'search-table', '搜索表格', 'md-grid', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('36', '33', '', '2018-08-13 18:29:54', '0', '18170878718', '2020-12-20 22:45:43', '', 'complex-table', '0', '3.50', 'freight-vue-template/complex-table/complexTable', 'complex-table', '复杂表格', 'ios-grid', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('37', '33', '', '2018-08-13 18:37:23', '0', 'admin', '2019-04-12 15:38:08', '', 'tree', '0', '3.22', 'freight-vue-template/tree/tree', 'tree', '树形结构', 'ios-git-network', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('38', '32', null, '2018-08-14 00:36:13', '0', null, '2018-08-14 00:36:13', null, '', '1', '2.31', '', '无', '查看数据', '', '3', 'view', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('39', '33', '', '2018-08-15 17:12:57', '0', '18170878718', '2020-12-20 22:45:16', '', 'new-window', '0', '3.21', 'freight-vue-template/new-window/newWindow', 'new-window', '新窗口操作', 'ios-browsers', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('40', '149', '', '2018-08-18 13:44:58', '0', '', '2018-08-18 20:55:04', '', 'message-manage', '0', '1.30', 'message/message-manage/messageManage', 'message-manage', '消息管理', 'md-mail', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('44', '27', '', '2018-08-24 10:02:33', '0', '', '2018-09-19 22:06:57', '', '', '1', '1.21', '', '/freight/department/add*', '添加部门', '', '3', 'add', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('45', '27', '', '2018-08-24 10:03:13', '0', '', '2018-09-19 22:07:02', '', '', '1', '1.22', '', '/freight/department/edit*', '编辑部门', '', '3', 'edit', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('46', '27', null, '2018-08-24 10:03:49', '0', null, '2018-08-24 10:03:49', null, '', '1', '1.23', '', '/freight/department/delByIds/*', '删除部门', '', '3', 'delete', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('47', '40', '', '2018-08-24 10:06:59', '0', '', '2018-09-19 22:07:07', '', '', '1', '1.31', '', '/freight/message/add*', '添加消息', '', '3', 'add', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('48', '40', '', '2018-08-24 10:08:04', '0', '', '2018-09-19 22:07:12', '', '', '1', '1.32', '', '/freight/message/edit*', '编辑消息', '', '3', 'edit', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('49', '40', null, '2018-08-24 10:08:42', '0', null, '2018-08-24 10:08:42', null, '', '1', '1.33', '', '/freight/message/delByIds/*', '删除消息', '', '3', 'delete', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('55', '151', '', '2018-09-23 23:26:40', '0', 'admin', '2018-11-15 15:17:43', '', 'oss-manage', '0', '1.40', 'file/oss-manage/ossManage', 'oss-manage', '文件对象存储', 'ios-folder', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('56', '2', '', '2018-09-25 14:28:34', '0', '', '2018-09-25 15:12:46', '', '', '1', '1.17', '', '/freight/user/importData*', '导入用户', '', '3', 'input', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('57', '33', '', '2018-09-25 15:17:39', '0', '18170878718', '2020-12-20 22:45:32', '', 'excel', '0', '3.60', 'freight-vue-template/excel/excel', 'excel', 'Excel导入导出', 'md-exit', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('58', '40', null, '2018-09-25 21:47:55', '0', null, '2018-09-25 21:47:55', null, '', '1', '1.40', '', '/freight/messageSend/save*', '添加已发送消息', '', '3', 'add', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('59', '40', null, '2018-09-25 21:48:43', '0', null, '2018-09-25 21:48:43', null, '', '1', '1.50', '', '/freight/messageSend/update*', '编辑已发送消息', '', '3', 'edit', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('60', '40', null, '2018-09-25 21:49:39', '0', null, '2018-09-25 21:49:39', null, '', '1', '1.60', '', '/freight/messageSend/delByIds/*', '删除已发送消息', '', '3', 'delete', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('61', '55', '', '2018-09-26 11:15:55', '0', '', '2018-10-08 11:10:05', '', '', '1', '1.41', '', '无', '上传文件', '', '3', 'upload', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('62', '1', '', '2018-09-29 23:13:24', '0', 'admin', '2018-11-14 13:24:26', '', 'setting', '0', '1.90', 'sys/setting-manage/settingManage', 'setting', '系统配置', 'ios-settings-outline', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('63', '62', null, '2018-10-08 00:12:59', '0', null, '2018-10-08 00:12:59', null, '', '1', '1.81', '', '/freight/setting/seeSecret/**', '查看私密配置', '', '3', 'view', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('64', '62', '', '2018-10-08 01:44:32', '0', '', '2018-10-08 01:50:03', '', '', '1', '1.82', '', '/freight/setting/*/set*', '编辑配置', '', '3', 'edit', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('65', '55', null, '2018-10-08 11:09:58', '0', null, '2018-10-08 11:09:58', null, '', '1', '1.42', '', '/freight/file/rename*', '重命名文件', '', '3', 'edit', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('66', '55', null, '2018-10-08 11:10:54', '0', null, '2018-10-08 11:10:54', null, '', '1', '1.43', '', '/freight/file/copy*', '复制文件', '', '3', 'edit', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('67', '55', null, '2018-10-08 11:11:43', '0', null, '2018-10-08 11:11:43', null, '', '1', '1.44', '', '/freight/file/delete/*', '删除文件', '', '3', 'delete', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('68', '33', '', '2018-10-13 18:29:02', '0', 'admin', '2018-10-20 22:47:45', '', 'custom-tree', '0', '3.80', 'freight-vue-template/custom-tree/customTree', 'custom-tree', '自定义树', 'md-git-network', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('69', '33', '', '2018-10-14 11:39:17', '0', 'admin', '2019-02-04 17:03:07', '', 'render', '0', '3.30', 'freight-vue-template/render/render', 'render', 'Render函数示例', 'md-aperture', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('70', '33', '', '2018-10-16 00:00:29', '0', 'admin', '2018-10-20 22:47:49', '', 'tree&table', '0', '3.90', 'freight-vue-template/tree&table/tree&table', 'tree&table', '树+表格', 'md-list', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('71', '33', 'admin', '2018-10-22 21:33:42', '0', 'admin', '2018-10-22 21:37:12', '', 'card-list', '0', '3.91', 'freight-vue-template/card-list/cardList', 'card-list', '卡片列表', 'md-card', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('72', '147', 'admin', '2018-10-23 13:15:03', '0', 'admin', '2018-10-23 14:57:38', '', 'product-template', '0', '4.00', 'Main', '/product-template', '前台产品级组件', 'md-ribbon', '1', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('73', '72', 'admin', '2018-10-23 13:17:19', '0', 'admin', '2019-11-22 16:19:01', '', 'banner', '0', '4.10', 'freight-product-template/banner/Banner', 'banner', '轮播组件', 'md-book', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('74', '72', 'admin', '2018-10-23 14:15:22', '0', 'admin', '2019-11-22 16:19:24', '', 'product', '0', '4.20', 'freight-product-template/product/Product', 'product', '产品组件', 'md-pricetags', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('75', '72', 'admin', '2018-10-23 16:53:53', '0', 'admin', '2019-11-22 16:19:32', '', 'category', '0', '4.30', 'freight-product-template/category/Category', 'category', '分类栏组件', 'md-apps', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('76', '150', null, null, '0', '18170878718', '2020-12-20 23:58:29', '', 'dict', '0', '1.80', 'data/dict-manage/dictManage', 'dict', '字典管理', 'md-bookmarks', '2', '', '0', '', '', '');
INSERT INTO `sys_menu` VALUES ('77', '76', 'admin', '2018-11-17 21:47:05', '0', 'admin', '2018-11-17 21:47:53', '', '', '1', '1.81', '', '/freight/dict/add*', '添加字典', '', '3', 'add', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('78', '76', 'admin', '2018-11-17 21:47:48', '0', 'admin', '2018-11-17 21:47:48', null, '', '1', '1.82', '', '/freight/dict/edit*', '编辑字典', '', '3', 'edit', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('79', '76', 'admin', '2018-11-17 21:48:34', '0', 'admin', '2018-11-17 21:48:34', null, '', '1', '1.83', '', '/freight/dict/delByIds/**', '删除字典', '', '3', 'delete', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('80', '76', 'admin', '2018-11-17 21:49:21', '0', 'admin', '2018-11-17 21:49:21', null, '', '1', '1.84', '', '/freight/dictData/add*', '添加字典数据', '', '3', 'add', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('81', '76', 'admin', '2018-11-17 21:49:53', '0', 'admin', '2018-11-17 21:49:53', null, '', '1', '1.85', '', '/freight/dictData/edit*', '编辑字典数据', '', '3', 'edit', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('82', '76', 'admin', '2018-11-17 21:50:18', '0', 'admin', '2018-11-17 21:50:18', null, '', '1', '1.86', '', '/freight/dictData/delByIds/**', '删除字典数据', '', '3', 'delete', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('109', '147', 'admin', '2019-01-28 17:00:15', '0', 'admin', '2019-11-22 11:47:14', '', 'vue-generator', '0', '3.00', 'Main', '/vue-generator', 'Vue代码生成', 'md-send', '1', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('110', '109', 'admin', '2019-01-28 17:08:06', '0', '18170878718', '2020-12-20 22:43:30', '', 'table-generator', '0', '0.00', 'freight-vue-generator/tableGenerator', 'table', '增删改表格生', 'md-grid', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('111', '109', 'admin', '2019-01-28 17:17:49', '0', '18170878718', '2020-12-20 22:44:51', '', 'tree-generator', '0', '1.00', 'freight-vue-generator/treeGenerator', 'tree', '树形结构生成', 'md-git-branch', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('112', '109', 'admin', '2019-02-01 15:12:20', '0', '18170878718', '2020-12-20 22:45:02', '', 'test', '0', '3.00', 'freight-vue-generator/test', 'test', '代码生成测试页', 'ios-bug', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('114', '147', 'admin', '2019-03-01 01:46:44', '0', 'admin', '2019-06-26 20:38:55', '', 'components', '0', '5.00', 'Main', '/components', '业务组件', 'md-cube', '1', '', '0', '', '\0', null);
INSERT INTO `sys_menu` VALUES ('115', '114', 'admin', '2019-03-01 01:47:30', '0', '18170878718', '2020-12-20 23:19:38', '', 'components', '0', '0.00', 'freight-components/freightComponents', 'components', '业务组件', 'md-cube', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('116', '147', 'admin', '2019-03-01 01:52:09', '0', 'admin', '2019-06-26 20:38:47', '', 'library', '0', '6.00', 'Main', '/library', '第三方依赖工具/组件', 'ios-link', '1', '', '0', '', '\0', null);
INSERT INTO `sys_menu` VALUES ('117', '116', 'admin', '2019-03-01 01:52:34', '0', '18170878718', '2020-12-20 23:19:12', '', 'library', '0', '0.00', 'freight-library/freightLibrary', 'library', '第三方依赖工具', 'ios-link', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('119', '24', 'admin', '2019-03-22 15:57:11', '0', 'admin', '2019-03-22 15:57:11', null, 'redis', '0', '2.21', 'sys/redis/redis', 'redis', 'Redis缓存管理', 'md-barcode', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('120', '0', null, null, '0', '18170878719', '2021-01-24 00:07:34', 'undefined', 'freight', '-1', '0.00', 'false', '', '管理系统', 'md-home', '0', '', '0', '', '', '');
INSERT INTO `sys_menu` VALUES ('123', '0', null, null, '0', '18170878718', '2020-12-21 00:09:08', 'window', 'pay', '-1', '4.00', '', '', '支付系统', 'md-cash', '0', '', '0', 'www.baidu.com', '', '\0');
INSERT INTO `sys_menu` VALUES ('128', '33', 'admin', '2019-04-12 15:46:07', '0', 'admin', '2019-11-25 17:33:43', '', 'single-window', '0', '1.00', 'freight-vue-template/single-window/singleWindow', 'single-window', '动态组件单页操作', 'md-easel', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('129', '24', 'admin', '2019-06-08 00:04:19', '0', 'admin', '2019-06-08 00:04:19', null, 'admin', '0', '2.29', 'sys/monitor/monitor', '/admin', 'Admin监控', 'md-speedometer', '2', '', '0', 'https://127.0.0.1:9999/freight/admin', '', null);
INSERT INTO `sys_menu` VALUES ('130', '2', 'admin', '2019-06-27 01:51:39', '0', 'admin', '2019-06-27 01:51:39', null, '', '1', '1.18', '', '/freight/user/resetPass', '重置密码', '', '3', 'other', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('133', '147', 'admin', '2019-08-19 17:33:34', '0', 'admin', '2020-08-14 14:34:16', '', 'sso', '0', '8.00', 'Main', '/sso', '单点登录测试站', 'md-log-in', '1', '', '0', '', '\0', null);
INSERT INTO `sys_menu` VALUES ('134', '133', 'admin', '2019-08-19 17:34:15', '0', 'admin', '2019-08-19 17:34:15', null, 'sso', '0', '0.00', 'sso/sso', 'sso', '单点登录测试站', 'md-log-in', '2', '', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('135', '147', 'admin', '2019-11-22 12:10:32', '0', 'admin', '2019-11-22 12:10:32', null, 'charts', '0', '3.10', 'Main', '/charts', '图表数据展示', 'md-analytics', '1', '', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('136', '135', 'admin', '2019-11-22 12:14:19', '0', 'admin', '2019-11-22 12:20:50', '', 'dashboard1', '0', '0.00', 'freight-charts/dashboard1/dashboard1', 'dashboard1', 'Dashboard1', 'md-speedometer', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('137', '135', 'admin', '2019-11-22 12:18:45', '0', 'admin', '2019-11-22 12:18:45', null, 'dashboard2', '0', '1.00', 'freight-charts/dashboard2/dashboard2', 'dashboard2', 'Dashboard2', 'ios-speedometer', '2', '', '0', null, '', null);
INSERT INTO `sys_menu` VALUES ('138', '120', null, null, '0', '18170878718', '2021-01-09 01:03:09', '', 'open', '0', '10.00', 'Main', '/open', '开放平台', 'ios-apps', '1', '', '0', '', '', '');
INSERT INTO `sys_menu` VALUES ('139', '138', 'admin', '2019-11-25 12:51:06', '0', 'admin', '2019-11-25 12:51:06', null, 'client', '0', '0.00', 'open/client/client', 'client', '接入网站管理', 'md-browsers', '2', '', '0', '', '', null);
INSERT INTO `sys_menu` VALUES ('140', '138', 'admin', '2019-11-26 15:23:09', '0', 'admin', '2019-11-26 15:23:09', null, 'doc', '0', '1.00', 'sys/monitor/monitor', 'doc', '开放平台文档', 'md-document', '2', '', '0', 'https://127.0.0.1:9999/freight/admin', '', null);
INSERT INTO `sys_menu` VALUES ('147', '0', '18170878718', '2020-12-20 22:54:59', '0', '18170878718', '2020-12-20 22:54:59', null, 'code-complate', '-1', '3.00', null, null, '代码模板', 'md-add', '0', null, '0', null, '', '');
INSERT INTO `sys_menu` VALUES ('149', '120', null, null, '0', '18170878718', '2021-01-09 01:03:28', '', 'message', '0', '7.00', 'Main', '/message', '消息管理', 'ios-alarm', '1', '', '0', '', '', '');
INSERT INTO `sys_menu` VALUES ('150', '120', '18170878718', '2020-12-20 23:32:12', '0', '18170878718', '2020-12-20 23:32:12', null, 'data', '0', '6.00', 'Main', '/data', '数据管理', 'ios-analytics', '1', '', '0', null, '', '');
INSERT INTO `sys_menu` VALUES ('151', '120', null, null, '0', '18170878718', '2021-01-09 01:03:46', '', 'file', '0', '8.00', 'Main', '/file', '文件管理', 'md-folder', '1', '', '0', '', '', '');
INSERT INTO `sys_menu` VALUES ('153', '120', null, null, '0', 'admin', '2021-01-31 15:16:08', '', 'appoint', '0', '2.00', 'Main', '/appoint', '供需管理', 'md-reorder', '1', '', '0', '', '', '');
INSERT INTO `sys_menu` VALUES ('154', '153', null, null, '0', 'admin', '2021-01-31 15:16:24', '', 'appoint-goods', '0', '1.00', 'appoint/appoint-goods/appointGoods', 'appoint-goods', '货物管理', 'md-book', '2', '', '0', '', '', '');
INSERT INTO `sys_menu` VALUES ('156', '153', null, null, '0', 'admin', '2021-01-31 15:16:52', '', 'appoint-vehicle', '0', '2.00', 'appoint/appoint-vehicle/appointVehicle', 'appoint-vehicle', '预约管理', 'md-albums', '2', '', '0', '', '', '');
INSERT INTO `sys_menu` VALUES ('157', '161', null, null, '0', '18170878719', '2021-01-24 00:10:58', '', 'order-order', '0', '3.00', 'order/order-order/orderOrder', 'order-order', '订单详情', 'ios-aperture', '2', '', '0', '', '', '');
INSERT INTO `sys_menu` VALUES ('158', '120', '18170878718', '2021-01-09 01:07:12', '0', '18170878718', '2021-01-09 01:07:12', null, 'user', '0', '4.00', 'Main', '/user', '角色管理', 'md-contacts', '1', '', '0', null, '', '');
INSERT INTO `sys_menu` VALUES ('159', '158', '18170878718', '2021-01-09 01:09:05', '0', '18170878718', '2021-01-09 01:09:05', null, 'user-company', '0', '1.00', 'user/user-company/userCompany', 'user-company', '货主角色', 'md-bookmarks', '2', '', '0', null, '', '');
INSERT INTO `sys_menu` VALUES ('160', '158', null, null, '0', '18170878718', '2021-01-09 01:18:55', '', 'user-driver', '0', '1.00', 'user/user-driver/userDriver', 'user-driver', '司机角色', 'md-car', '2', '', '0', '', '', '');
INSERT INTO `sys_menu` VALUES ('161', '120', null, null, '0', '18170878719', '2021-01-24 00:12:53', '', 'order', '0', '3.00', 'Main', '/order', '订单管理', 'ios-apps', '1', '', '0', '', '', '');

-- ----------------------------
-- Table structure for sys_oauth_client
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth_client`;
CREATE TABLE `sys_oauth_client` (
  `client_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端ID',
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源列表',
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '客户端密钥',
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '域',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '认证类型',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '重定向地址',
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色列表',
  `access_token_validity` int DEFAULT NULL COMMENT 'token 有效期',
  `refresh_token_validity` int DEFAULT NULL COMMENT '刷新令牌有效期',
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '令牌扩展字段JSON',
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否自动放行',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `id` bigint NOT NULL,
  `del_flag` int DEFAULT NULL,
  `auto_approve` bit(1) DEFAULT NULL,
  `home_uri` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `redirect_uri` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='终端信息表';

-- ----------------------------
-- Records of sys_oauth_client
-- ----------------------------
INSERT INTO `sys_oauth_client` VALUES ('app', null, 'app', 'server', 'password,refresh_token', null, null, null, null, null, 'true', null, null, null, null, '0', null, null, null, null, null);
INSERT INTO `sys_oauth_client` VALUES ('gen', null, 'gen', 'server', 'password,refresh_token', null, null, null, null, null, 'true', null, null, null, null, '0', null, null, null, null, null);
INSERT INTO `sys_oauth_client` VALUES ('plat', null, 'plat', 'server', 'password,refresh_token,authorization_code,client_credentials', 'http://localhost:4040/sso1/login,http://localhost:4041/sso1/login', null, null, null, null, 'true', null, null, null, null, '0', null, null, null, null, null);
INSERT INTO `sys_oauth_client` VALUES ('test', null, 'test', 'server', 'password,refresh_token', null, null, null, null, null, 'true', null, null, null, null, '0', null, null, null, null, null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `default_role` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `data_type` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '', '2018-04-22 23:03:49', 'admin', '2018-11-15 23:02:59', 'ROLE_ADMIN', '0', null, '超级管理员 拥有所有权限', '0');
INSERT INTO `sys_role` VALUES ('2', '', '2018-05-02 21:40:03', '18170878718', '2020-12-21 00:26:58', 'ROLE_USER', '0', '\0', '普通注册用户 路过看看', '0');
INSERT INTO `sys_role` VALUES ('3', '', '2018-06-06 00:08:00', 'admin', '2018-11-02 20:42:24', 'ROLE_TEST', '0', null, '测试权限按钮显示', '1');
INSERT INTO `sys_role` VALUES ('4', '18170878718', '2020-12-21 00:26:51', '18170878718', '2021-01-09 00:28:47', 'ROLE_OPERA', '0', '\0', '平台运营', '0');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `role_id` bigint unsigned DEFAULT NULL,
  `dept_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES ('1', 'admin', '2018-11-02 20:42:43', '0', 'admin', '2018-11-02 20:42:43', '3', '1');
INSERT INTO `sys_role_dept` VALUES ('2', 'admin', '2018-11-02 20:42:43', '0', 'admin', '2018-11-02 20:42:43', '3', '2');
INSERT INTO `sys_role_dept` VALUES ('3', 'admin', '2018-11-02 20:42:43', '0', 'admin', '2018-11-02 20:42:43', '3', '3');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `role_id` bigint unsigned DEFAULT NULL,
  `menu_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2185 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1359', '18170878718', '2020-12-21 00:29:07', '0', '18170878718', '2020-12-21 00:29:07', '2', '120');
INSERT INTO `sys_role_menu` VALUES ('1360', '18170878718', '2020-12-21 00:29:07', '0', '18170878718', '2020-12-21 00:29:07', '2', '24');
INSERT INTO `sys_role_menu` VALUES ('1361', '18170878718', '2020-12-21 00:29:07', '0', '18170878718', '2020-12-21 00:29:07', '2', '26');
INSERT INTO `sys_role_menu` VALUES ('1362', '18170878718', '2020-12-21 00:29:07', '0', '18170878718', '2020-12-21 00:29:07', '2', '150');
INSERT INTO `sys_role_menu` VALUES ('1363', '18170878718', '2020-12-21 00:29:07', '0', '18170878718', '2020-12-21 00:29:07', '2', '76');
INSERT INTO `sys_role_menu` VALUES ('1364', '18170878718', '2020-12-21 00:29:07', '0', '18170878718', '2020-12-21 00:29:07', '2', '78');
INSERT INTO `sys_role_menu` VALUES ('1365', '18170878718', '2020-12-21 00:29:07', '0', '18170878718', '2020-12-21 00:29:07', '2', '79');
INSERT INTO `sys_role_menu` VALUES ('1366', '18170878718', '2020-12-21 00:29:07', '0', '18170878718', '2020-12-21 00:29:07', '2', '80');
INSERT INTO `sys_role_menu` VALUES ('1367', '18170878718', '2020-12-21 00:29:07', '0', '18170878718', '2020-12-21 00:29:07', '2', '81');
INSERT INTO `sys_role_menu` VALUES ('1368', '18170878718', '2020-12-21 00:29:07', '0', '18170878718', '2020-12-21 00:29:07', '2', '82');
INSERT INTO `sys_role_menu` VALUES ('1369', '18170878718', '2020-12-21 00:29:07', '0', '18170878718', '2020-12-21 00:29:07', '2', '151');
INSERT INTO `sys_role_menu` VALUES ('1370', '18170878718', '2020-12-21 00:29:07', '0', '18170878718', '2020-12-21 00:29:07', '2', '55');
INSERT INTO `sys_role_menu` VALUES ('1371', '18170878718', '2020-12-21 00:29:07', '0', '18170878718', '2020-12-21 00:29:07', '2', '61');
INSERT INTO `sys_role_menu` VALUES ('1372', '18170878718', '2020-12-21 00:29:07', '0', '18170878718', '2020-12-21 00:29:07', '2', '65');
INSERT INTO `sys_role_menu` VALUES ('1373', '18170878718', '2020-12-21 00:29:07', '0', '18170878718', '2020-12-21 00:29:07', '2', '66');
INSERT INTO `sys_role_menu` VALUES ('1374', '18170878718', '2020-12-21 00:29:07', '0', '18170878718', '2020-12-21 00:29:07', '2', '67');
INSERT INTO `sys_role_menu` VALUES ('2083', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '120');
INSERT INTO `sys_role_menu` VALUES ('2084', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('2085', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '2');
INSERT INTO `sys_role_menu` VALUES ('2086', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '5');
INSERT INTO `sys_role_menu` VALUES ('2087', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '13');
INSERT INTO `sys_role_menu` VALUES ('2088', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '6');
INSERT INTO `sys_role_menu` VALUES ('2089', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '7');
INSERT INTO `sys_role_menu` VALUES ('2090', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '14');
INSERT INTO `sys_role_menu` VALUES ('2091', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '23');
INSERT INTO `sys_role_menu` VALUES ('2092', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '56');
INSERT INTO `sys_role_menu` VALUES ('2093', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '130');
INSERT INTO `sys_role_menu` VALUES ('2094', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '27');
INSERT INTO `sys_role_menu` VALUES ('2095', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '44');
INSERT INTO `sys_role_menu` VALUES ('2096', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '45');
INSERT INTO `sys_role_menu` VALUES ('2097', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '46');
INSERT INTO `sys_role_menu` VALUES ('2098', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '3');
INSERT INTO `sys_role_menu` VALUES ('2099', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '15');
INSERT INTO `sys_role_menu` VALUES ('2100', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '16');
INSERT INTO `sys_role_menu` VALUES ('2101', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '17');
INSERT INTO `sys_role_menu` VALUES ('2102', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '18');
INSERT INTO `sys_role_menu` VALUES ('2103', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '19');
INSERT INTO `sys_role_menu` VALUES ('2104', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '4');
INSERT INTO `sys_role_menu` VALUES ('2105', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '20');
INSERT INTO `sys_role_menu` VALUES ('2106', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '21');
INSERT INTO `sys_role_menu` VALUES ('2107', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '22');
INSERT INTO `sys_role_menu` VALUES ('2108', '18170878719', '2021-01-24 00:12:15', '0', '18170878719', '2021-01-24 00:12:15', '1', '62');
INSERT INTO `sys_role_menu` VALUES ('2109', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '63');
INSERT INTO `sys_role_menu` VALUES ('2110', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '64');
INSERT INTO `sys_role_menu` VALUES ('2111', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '153');
INSERT INTO `sys_role_menu` VALUES ('2112', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '154');
INSERT INTO `sys_role_menu` VALUES ('2113', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '156');
INSERT INTO `sys_role_menu` VALUES ('2114', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '157');
INSERT INTO `sys_role_menu` VALUES ('2115', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '158');
INSERT INTO `sys_role_menu` VALUES ('2116', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '159');
INSERT INTO `sys_role_menu` VALUES ('2117', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '160');
INSERT INTO `sys_role_menu` VALUES ('2118', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '150');
INSERT INTO `sys_role_menu` VALUES ('2119', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '76');
INSERT INTO `sys_role_menu` VALUES ('2120', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '77');
INSERT INTO `sys_role_menu` VALUES ('2121', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '78');
INSERT INTO `sys_role_menu` VALUES ('2122', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '79');
INSERT INTO `sys_role_menu` VALUES ('2123', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '80');
INSERT INTO `sys_role_menu` VALUES ('2124', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '81');
INSERT INTO `sys_role_menu` VALUES ('2125', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '82');
INSERT INTO `sys_role_menu` VALUES ('2126', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '149');
INSERT INTO `sys_role_menu` VALUES ('2127', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '40');
INSERT INTO `sys_role_menu` VALUES ('2128', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '47');
INSERT INTO `sys_role_menu` VALUES ('2129', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '48');
INSERT INTO `sys_role_menu` VALUES ('2130', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '49');
INSERT INTO `sys_role_menu` VALUES ('2131', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '58');
INSERT INTO `sys_role_menu` VALUES ('2132', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '59');
INSERT INTO `sys_role_menu` VALUES ('2133', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '60');
INSERT INTO `sys_role_menu` VALUES ('2134', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '151');
INSERT INTO `sys_role_menu` VALUES ('2135', '18170878719', '2021-01-24 00:12:16', '0', '18170878719', '2021-01-24 00:12:16', '1', '55');
INSERT INTO `sys_role_menu` VALUES ('2136', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '61');
INSERT INTO `sys_role_menu` VALUES ('2137', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '65');
INSERT INTO `sys_role_menu` VALUES ('2138', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '66');
INSERT INTO `sys_role_menu` VALUES ('2139', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '67');
INSERT INTO `sys_role_menu` VALUES ('2140', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '24');
INSERT INTO `sys_role_menu` VALUES ('2141', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '28');
INSERT INTO `sys_role_menu` VALUES ('2142', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '29');
INSERT INTO `sys_role_menu` VALUES ('2143', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '30');
INSERT INTO `sys_role_menu` VALUES ('2144', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '119');
INSERT INTO `sys_role_menu` VALUES ('2145', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '129');
INSERT INTO `sys_role_menu` VALUES ('2146', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '32');
INSERT INTO `sys_role_menu` VALUES ('2147', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '38');
INSERT INTO `sys_role_menu` VALUES ('2148', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '25');
INSERT INTO `sys_role_menu` VALUES ('2149', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '26');
INSERT INTO `sys_role_menu` VALUES ('2150', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '161');
INSERT INTO `sys_role_menu` VALUES ('2151', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '138');
INSERT INTO `sys_role_menu` VALUES ('2152', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '139');
INSERT INTO `sys_role_menu` VALUES ('2153', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '140');
INSERT INTO `sys_role_menu` VALUES ('2154', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '147');
INSERT INTO `sys_role_menu` VALUES ('2155', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '109');
INSERT INTO `sys_role_menu` VALUES ('2156', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '110');
INSERT INTO `sys_role_menu` VALUES ('2157', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '111');
INSERT INTO `sys_role_menu` VALUES ('2158', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '112');
INSERT INTO `sys_role_menu` VALUES ('2159', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '33');
INSERT INTO `sys_role_menu` VALUES ('2160', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '128');
INSERT INTO `sys_role_menu` VALUES ('2161', '18170878719', '2021-01-24 00:12:17', '0', '18170878719', '2021-01-24 00:12:17', '1', '34');
INSERT INTO `sys_role_menu` VALUES ('2162', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '39');
INSERT INTO `sys_role_menu` VALUES ('2163', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '37');
INSERT INTO `sys_role_menu` VALUES ('2164', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '69');
INSERT INTO `sys_role_menu` VALUES ('2165', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '35');
INSERT INTO `sys_role_menu` VALUES ('2166', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '36');
INSERT INTO `sys_role_menu` VALUES ('2167', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '57');
INSERT INTO `sys_role_menu` VALUES ('2168', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '68');
INSERT INTO `sys_role_menu` VALUES ('2169', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '70');
INSERT INTO `sys_role_menu` VALUES ('2170', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '71');
INSERT INTO `sys_role_menu` VALUES ('2171', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '135');
INSERT INTO `sys_role_menu` VALUES ('2172', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '136');
INSERT INTO `sys_role_menu` VALUES ('2173', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '137');
INSERT INTO `sys_role_menu` VALUES ('2174', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '72');
INSERT INTO `sys_role_menu` VALUES ('2175', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '73');
INSERT INTO `sys_role_menu` VALUES ('2176', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '74');
INSERT INTO `sys_role_menu` VALUES ('2177', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '75');
INSERT INTO `sys_role_menu` VALUES ('2178', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '114');
INSERT INTO `sys_role_menu` VALUES ('2179', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '115');
INSERT INTO `sys_role_menu` VALUES ('2180', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '116');
INSERT INTO `sys_role_menu` VALUES ('2181', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '117');
INSERT INTO `sys_role_menu` VALUES ('2182', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '133');
INSERT INTO `sys_role_menu` VALUES ('2183', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '134');
INSERT INTO `sys_role_menu` VALUES ('2184', '18170878719', '2021-01-24 00:12:18', '0', '18170878719', '2021-01-24 00:12:18', '1', '123');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `pass_strength` varchar(2) DEFAULT NULL,
  `birth` datetime(6) DEFAULT NULL,
  `user_type` bigint DEFAULT NULL,
  `is_auth` int DEFAULT NULL,
  `regist_status` int DEFAULT NULL,
  `dept_id` bigint DEFAULT NULL,
  `dept_title` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '2018-05-01 16:13:51', '18170878718', '2020-12-21 00:48:09', '[\"510000\",\"510100\",\"510104\"]', 'https://ooo.0o0.ooo/2019/04/28/5cc5a71a6e3b6.png', 'admin', '26906084@qq.com', 'A0750C81D57EF059A9C0C6C003C0F95E', '管理员', '$2a$10$RpFJjxYiXdEsAGnWp/8fsOetMuOON96Ntk/Ym2M/RKRyU0GZseaDC', '男', '0', '1', 'admin', '0', '天府1街', '弱', '2020-04-15 00:00:00.000000', '0', null, '1', '1', null, null);

-- ----------------------------
-- Table structure for sys_user_bak
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_bak`;
CREATE TABLE `sys_user_bak` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '随机盐',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '简介',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `dept_id` int DEFAULT NULL COMMENT '部门ID',
  `lock_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '0-正常，9-锁定',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '0-正常，1-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `user_idx1_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_user_bak
-- ----------------------------
INSERT INTO `sys_user_bak` VALUES ('1', 'admin', '$2a$10$RpFJjxYiXdEsAGnWp/8fsOetMuOON96Ntk/Ym2M/RKRyU0GZseaDC', null, '17034642999', '', '1', '0', '0', '2018-04-20 07:15:18', '2019-01-31 14:29:07', null, null);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `role_id` bigint unsigned DEFAULT NULL,
  `user_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('5', 'admin', '2020-04-27 22:01:24', '0', 'admin', '2020-04-27 22:01:24', '3', '3');
INSERT INTO `sys_user_role` VALUES ('6', 'admin', '2020-11-13 19:03:11', '0', 'admin', '2020-12-05 19:03:20', '1', '23');
INSERT INTO `sys_user_role` VALUES ('7', null, '2020-11-24 20:59:37', '0', null, '2020-11-24 20:59:37', '2', '6');
INSERT INTO `sys_user_role` VALUES ('8', null, '2020-11-24 21:01:31', '0', null, '2020-11-24 21:01:31', '2', '7');
INSERT INTO `sys_user_role` VALUES ('9', null, '2020-11-25 22:06:59', '0', null, '2020-11-25 22:06:59', '2', '8');
INSERT INTO `sys_user_role` VALUES ('10', null, '2020-11-25 22:19:20', '0', null, '2020-11-25 22:19:20', '2', '9');
INSERT INTO `sys_user_role` VALUES ('11', null, '2020-11-25 22:25:33', '0', null, '2020-11-25 22:25:33', '2', '10');
INSERT INTO `sys_user_role` VALUES ('12', null, '2020-11-25 23:00:39', '0', null, '2020-11-25 23:00:39', '2', '11');
INSERT INTO `sys_user_role` VALUES ('13', null, '2020-11-25 23:05:18', '0', null, '2020-11-25 23:05:18', '2', '12');
INSERT INTO `sys_user_role` VALUES ('14', null, '2020-11-25 23:06:28', '0', null, '2020-11-25 23:06:28', '2', '13');
INSERT INTO `sys_user_role` VALUES ('15', null, '2020-11-25 23:07:15', '0', null, '2020-11-25 23:07:15', '2', '14');
INSERT INTO `sys_user_role` VALUES ('16', null, '2020-11-25 23:10:06', '0', null, '2020-11-25 23:10:06', '2', '15');
INSERT INTO `sys_user_role` VALUES ('17', null, '2020-11-25 23:12:15', '0', null, '2020-11-25 23:12:15', '2', '16');
INSERT INTO `sys_user_role` VALUES ('18', null, '2020-11-25 23:13:38', '0', null, '2020-11-25 23:13:38', '2', '17');
INSERT INTO `sys_user_role` VALUES ('19', null, '2020-11-25 23:17:24', '0', null, '2020-11-25 23:17:24', '2', '18');
INSERT INTO `sys_user_role` VALUES ('20', null, '2020-11-25 23:18:17', '0', null, '2020-11-25 23:18:17', '2', '19');
INSERT INTO `sys_user_role` VALUES ('21', null, '2020-11-25 23:19:40', '0', null, '2020-11-25 23:19:40', '2', '20');
INSERT INTO `sys_user_role` VALUES ('22', null, '2020-11-25 23:24:30', '0', null, '2020-11-25 23:24:30', '2', '21');
INSERT INTO `sys_user_role` VALUES ('23', null, '2020-11-29 20:26:08', '0', null, '2020-11-29 20:26:08', '2', '25');
INSERT INTO `sys_user_role` VALUES ('24', null, '2020-11-29 20:26:35', '0', null, '2020-11-29 20:26:35', '2', '26');
INSERT INTO `sys_user_role` VALUES ('25', null, '2020-12-01 16:05:01', '0', null, '2020-12-01 16:05:01', '2', '27');
INSERT INTO `sys_user_role` VALUES ('26', null, '2020-12-01 16:06:09', '0', null, '2020-12-01 16:06:09', '2', '28');
INSERT INTO `sys_user_role` VALUES ('27', null, '2020-12-01 16:06:54', '0', null, '2020-12-01 16:06:54', '2', '29');
INSERT INTO `sys_user_role` VALUES ('28', null, '2020-12-01 16:07:20', '0', null, '2020-12-01 16:07:20', '2', '30');
INSERT INTO `sys_user_role` VALUES ('29', null, '2020-12-01 16:57:49', '0', null, '2020-12-01 16:57:49', '2', '31');
INSERT INTO `sys_user_role` VALUES ('30', null, '2020-12-02 13:45:34', '0', null, '2020-12-02 13:45:34', '2', '32');
INSERT INTO `sys_user_role` VALUES ('31', null, '2020-12-02 13:48:40', '0', null, '2020-12-02 13:48:40', '2', '33');
INSERT INTO `sys_user_role` VALUES ('34', null, '2020-12-15 10:15:51', '0', null, '2020-12-15 10:15:51', '2', '35');
INSERT INTO `sys_user_role` VALUES ('35', null, '2020-12-15 10:57:09', '0', null, '2020-12-15 10:57:09', '2', '36');
INSERT INTO `sys_user_role` VALUES ('36', null, '2020-12-15 11:10:50', '0', null, '2020-12-15 11:10:50', '2', '37');
INSERT INTO `sys_user_role` VALUES ('37', null, '2020-12-15 14:48:21', '0', null, '2020-12-15 14:48:21', '2', '38');
INSERT INTO `sys_user_role` VALUES ('38', null, '2020-12-15 15:13:33', '0', null, '2020-12-15 15:13:33', '2', '39');
INSERT INTO `sys_user_role` VALUES ('39', null, '2020-12-15 16:00:41', '0', null, '2020-12-15 16:00:41', '2', '40');
INSERT INTO `sys_user_role` VALUES ('40', null, '2020-12-15 17:15:48', '0', null, '2020-12-15 17:15:48', '2', '41');
INSERT INTO `sys_user_role` VALUES ('41', null, '2020-12-15 17:17:30', '0', null, '2020-12-15 17:17:30', '2', '42');
INSERT INTO `sys_user_role` VALUES ('42', null, '2020-12-15 17:18:53', '0', null, '2020-12-15 17:18:53', '2', '43');
INSERT INTO `sys_user_role` VALUES ('43', null, '2020-12-15 17:19:47', '0', null, '2020-12-15 17:19:47', '2', '44');
INSERT INTO `sys_user_role` VALUES ('44', null, '2020-12-19 17:41:17', '0', null, '2020-12-19 17:41:17', '2', '45');
INSERT INTO `sys_user_role` VALUES ('56', '18170878718', '2020-12-21 00:47:40', '0', '18170878718', '2020-12-21 00:47:40', '2', '34');
INSERT INTO `sys_user_role` VALUES ('57', '18170878718', '2020-12-21 00:47:52', '0', '18170878718', '2020-12-21 00:47:52', '1', '1');
INSERT INTO `sys_user_role` VALUES ('58', '18170878718', '2020-12-21 00:48:51', '0', '18170878718', '2020-12-21 00:48:51', '4', '2');
INSERT INTO `sys_user_role` VALUES ('59', null, '2021-01-02 10:33:12', '0', null, '2021-01-02 10:33:12', '4', '54');
INSERT INTO `sys_user_role` VALUES ('60', null, '2021-01-02 10:39:10', '0', null, '2021-01-02 10:39:10', '4', '55');
INSERT INTO `sys_user_role` VALUES ('61', null, '2021-01-02 15:32:26', '0', null, '2021-01-02 15:32:26', '4', '56');
INSERT INTO `sys_user_role` VALUES ('62', null, '2021-01-06 15:29:27', '0', null, '2021-01-06 15:29:27', '4', '57');
INSERT INTO `sys_user_role` VALUES ('63', 'admin', '2021-01-31 15:19:14', '0', 'admin', '2021-01-31 15:19:14', '1', '60');
