## ===UPDATE INDEX ===== 
ALTER TABLE t_message_center DROP INDEX IX_m_i_u ;
ALTER TABLE t_message_center ADD INDEX `IX_m_i_u`(user_id,message_type,is_read) ;
 ## ===UPDATE INDEX =====
ALTER TABLE t_message_model modify column message_sms_range TINYINT(3)  COMMENT '消息短讯范围  -1：指定人 0：所有人 1：管理员 2：只有创建者收不到' ;
ALTER TABLE u_user_home ADD COLUMN state TINYINT(3)  COMMENT '成员账号状态，1：正常 2：注销(冻结)' ;
CREATE TABLE d_add_class_copy1 (
`class_id` INT(10) NOT NULL  COMMENT '设备产品id 由设备中心维护', 
`class_name` VARCHAR(50) NOT NULL  COMMENT '设备产品名称 ', 
`add_one_class_id` INT(10) NOT NULL  COMMENT '添加设备类型目录最外层类型', 
`product_id` VARCHAR(50) DEFAULT NULL  COMMENT '设备中心产品ID', 
`product_class_id` INT(10) DEFAULT NULL  COMMENT '设备中心产品类型分类ID', 
`add_mode` INT(10) NOT NULL  COMMENT '添加方式 1:KNX_GATEWAY 2:KNX_SUBSET', 
`icon` VARCHAR(100) DEFAULT NULL  COMMENT '图标', 
`create_time` DATETIME(0) NOT NULL  COMMENT '', 
`update_time` DATETIME(0) DEFAULT NULL  COMMENT '',PRIMARY KEY (`class_id`) USING BTREE ,UNIQUE KEY `PRIMARY` (class_id) USING BTREE 
 )  ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
