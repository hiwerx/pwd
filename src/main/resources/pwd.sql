-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        10.6.1-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.2.0.6213
-- --------------------------------------------------------

-- 导出  表 pwd.acct 结构
CREATE TABLE IF NOT EXISTS `acct` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL COMMENT '厂商id',
  `acct_type` varchar(50) DEFAULT NULL COMMENT '账户类型,0网站，1app',
  `acct_name` varchar(255) DEFAULT NULL COMMENT '账户名',
  `phone` varchar(255) DEFAULT NULL COMMENT '绑定手机号',
  `product_id` int(11) NOT NULL COMMENT '关联productId',
  `product_name` varchar(50) DEFAULT NULL COMMENT '名称',
  `product_url` varchar(255) DEFAULT NULL COMMENT '地址',
  `valid` char(50) DEFAULT '1' COMMENT '是否有效，1有效，0无效',
  `usr_id` int(11) DEFAULT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT current_timestamp(),
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `email` varchar(255) DEFAULT NULL COMMENT '绑定邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='账号信息';

-- 导出  表 pwd.auth_product 结构
CREATE TABLE IF NOT EXISTS `auth_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '''授权方名称''',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COMMENT='登录授权方';

-- 正在导出表  pwd.auth_product 的数据：~6 rows (大约)
INSERT INTO `auth_product` (`id`, `name`) VALUES
	(1, '微信'),
	(2, 'QQ'),
	(3, '支付宝'),
	(4, '百度'),
	(5, '新浪'),
	(6, '网易');

-- 导出  表 pwd.company 结构
CREATE TABLE IF NOT EXISTS `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL COMMENT '厂商名称',
  `url` varchar(250) DEFAULT NULL COMMENT '厂商地址',
  `phone` varchar(250) DEFAULT NULL COMMENT '联系电话',
  `type` char(50) DEFAULT NULL COMMENT '厂商类别，0互联网，1电信通讯，2银行，9其他',
  `icon` varchar(250) DEFAULT NULL COMMENT '图标',
  `usr_id` int(11) DEFAULT NULL COMMENT '用户id ,0为公共部分，其他为个人添加',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3 COMMENT='厂商，公司信息';

-- 正在导出表  pwd.company 的数据：~32 rows (大约)
INSERT INTO `company` (`id`, `name`, `url`, `phone`, `type`, `icon`, `usr_id`) VALUES
	(1, '百度', NULL, NULL, '0', NULL, 0),
	(2, '微软', NULL, NULL, '0', NULL, 0),
	(3, '谷歌', NULL, NULL, '0', NULL, 0),
	(4, '腾讯', NULL, NULL, '0', NULL, 0),
	(5, '阿里', NULL, NULL, '0', NULL, 0),
	(6, '字节', NULL, NULL, '0', NULL, 0),
	(7, '电信', NULL, NULL, '1', NULL, 0),
	(8, '工商银行', NULL, NULL, '2', NULL, 0),
	(9, '招商银行', NULL, NULL, '2', NULL, 0),
	(10, '交通银行', NULL, NULL, '2', NULL, 0),
	(11, '建设银行', NULL, NULL, '2', NULL, 0),
	(12, '农业银行', NULL, NULL, '2', NULL, 0),
	(13, '宁夏银行', NULL, NULL, '2', NULL, 0),
	(14, '威海商业银行', NULL, NULL, '2', NULL, 0),
	(15, '湖北银行', NULL, NULL, '2', NULL, 0),
	(16, '京东', NULL, NULL, '0', NULL, 0),
	(17, '拼多多', NULL, NULL, '0', NULL, 0),
	(18, '豆瓣', NULL, NULL, '0', NULL, 0),
	(19, '农商行', NULL, NULL, '2', NULL, 0),
	(20, '力扣', 'https://leetcode.cn/', NULL, '0', NULL, 1),
	(21, '美团', NULL, NULL, '0', NULL, 1),
	(22, '饿了吗', NULL, NULL, '0', NULL, 1),
	(23, '行李箱', NULL, NULL, '3', NULL, 1),
	(24, 'csdn', NULL, NULL, '0', NULL, 2),
	(25, 'gitee', NULL, NULL, '0', NULL, 1),
	(26, '小米', NULL, NULL, '0', NULL, 2),
	(27, '联想', NULL, NULL, '0', NULL, 2),
	(28, '华为', NULL, NULL, '0', NULL, 2),
	(29, '中兴', NULL, NULL, '0', NULL, 2),
	(30, 'ytutuytutututuyrytryey', NULL, NULL, '0', NULL, 1),
	(31, '12qw', NULL, NULL, '1', NULL, 1),
	(32, '1qw2', '111111111111111111111111', NULL, '1', NULL, 1),
	(33, '111111', '123243', '111111111', '1', '121321', 1),
	(34, 'htht', NULL, NULL, '0', NULL, 19);

-- 导出  表 pwd.invite_code 结构
CREATE TABLE IF NOT EXISTS `invite_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL COMMENT '码号',
  `count` int(11) NOT NULL DEFAULT 0 COMMENT '使用次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='邀请码';

-- 导出  表 pwd.other_acct 结构
CREATE TABLE IF NOT EXISTS `other_acct` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `acct_id` int(11) NOT NULL,
  `acct_name` varchar(250) NOT NULL,
  `valid` varchar(250) NOT NULL DEFAULT '1' COMMENT '有效，1有效，0无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='其他登录账户';


-- 导出  表 pwd.product 结构
CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) DEFAULT NULL,
  `icon` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COMMENT='产品信息';

-- 正在导出表  pwd.product 的数据：~10 rows (大约)
INSERT INTO `product` (`id`, `name`, `icon`) VALUES
	(1, '全系', 'fa-solid fa-universal-access'),
	(2, '支付宝', 'fa-brands fa-alipay'),
	(3, '淘宝', 'fa-solid fa-store'),
	(4, '微信', 'fa-brands fa-weixin'),
	(5, 'QQ', 'fa-brands fa-qq'),
	(6, '西瓜', 'fa-brands fa-android'),
	(7, '抖音', 'fa-brands fa-tiktok'),
	(8, '信用卡', 'fa-solid fa-credit-card'),
	(9, '借记卡', 'fa-regular fa-credit-card'),
	(10, '银行App', 'fa-brands fa-android');

-- 导出  表 pwd.pwd 结构
CREATE TABLE IF NOT EXISTS `pwd` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pwd` varchar(500) DEFAULT NULL,
  `valid` varchar(255) DEFAULT '1' COMMENT '是否有效，1有效，0无效',
  `pwd_type` varchar(255) DEFAULT NULL COMMENT '密码类型，0登录密码，1支付密码, 2银行卡交易密码，3ukey登录密码,4授权登录，5短信验证码',
  `auth_product_id` int(11) DEFAULT NULL COMMENT '授权登录产品id,授权登录时此值不为空',
  `acct_id` int(11) DEFAULT NULL COMMENT '账户id',
  `create_time` datetime DEFAULT current_timestamp(),
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='密码表';

-- 导出  表 pwd.que_ans 结构
CREATE TABLE IF NOT EXISTS `que_ans` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `que` varchar(250) NOT NULL COMMENT '问题',
  `ans` varchar(250) NOT NULL COMMENT '答案',
  `acct_id` int(11) NOT NULL COMMENT '账户id',
  `creat_time` datetime NOT NULL DEFAULT current_timestamp(),
  `update_time` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='问题与回答,密保答案';

-- 导出  表 pwd.usr 结构
CREATE TABLE IF NOT EXISTS `usr` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `invite_code` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT current_timestamp(),
  `update_time` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户表';
