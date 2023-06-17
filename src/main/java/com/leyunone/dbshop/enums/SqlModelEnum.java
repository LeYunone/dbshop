package com.leyunone.dbshop.enums;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-07
 */
public enum SqlModelEnum {

    //SQL语句模板
    //采用{}进行内容填充

    ADD_COLUMN("ALTER TABLE {} ADD COLUMN {} {}({}) COMMENT '{}' ;", "新增字段"),

    MODIFY_COLUMN("ALTER TABLE {} modify column {} {}({}) COMMENT '{}';", "修改字段"),

    DELETE_COLUMN("ALTER TABLE {} DROP COLUMN {}", "删除字段"),

    CREATE_TABLE("CREATE TABLE {} (" +
            "\n{}" +
            "\n{}" +
            "\n)  ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;", "创建表"),
    //末尾有逗号 当后续无语句时 需自行摘除
    CREATE_TABLE_COLUMN("`{}` {}({}) COMMENT '{}',","表中的字段"),
    
    PRIMARY_KEY("PRIMARY KEY ({}) USING BTREE",""),

    /**
     CREATE TABLE `t_message_center` (
     `message_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
     `message_title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标题',
     `message_type` tinyint(2) DEFAULT NULL COMMENT '消息类型 0：告警 1：家庭 2：通知',
     `trigger_ids` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '触发id集合 格式： {"deviceId":"","scenesId":""...}',
     `message_text` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '消息文本内容',
     `message_trigger_text` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '消息触发文本',
     `message_trigger_type` tinyint(2) DEFAULT NULL COMMENT '消息触发类型 ,
     `message_operate` tinyint(2) DEFAULT NULL COMMENT '消息操作类型 0：',
     `is_read` tinyint(2) DEFAULT NULL COMMENT '是否已读 0：未读  1：已读',
     `user_id` bigint(20) DEFAULT NULL COMMENT '消息所属用户',
     `icon` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图标',
     `is_action` tinyint(2) DEFAULT NULL COMMENT '是否处理 0：未处理 1：已处理',
     `is_deleted` tinyint(2) DEFAULT NULL COMMENT '0未删除  1已删除',
     `create_time` datetime DEFAULT NULL COMMENT '创建时间',
     PRIMARY KEY (`message_id`) USING BTREE
     ) ENGINE=InnoDB AUTO_INCREMENT=13641 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='消息中心消息';
     */
    
    ;

    SqlModelEnum(String sqlModel, String desc) {
        this.sqlModel = sqlModel;
        this.desc = desc;
    }

    public String getSqlModel() {
        return sqlModel;
    }

    public void setSqlModel(String sqlModel) {
        this.sqlModel = sqlModel;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String sqlModel;

    private String desc;
}
