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
            " {}" +
            "{}" +
            ")  ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;", "创建表"),
    //`user_id` bigint(19) DEFAULT NULL COMMENT '用户id',
    CREATE_TABLE_COLUMN("'{}' {} COMMENT '{}'","表中的字段")

    /**
     * CREATE TABLE `h_home_review` (
     *   `id` int(9) NOT NULL AUTO_INCREMENT,
     *   `invited_user_id` bigint(19) DEFAULT NULL COMMENT '受邀请人id',
     *   `user_id` bigint(19) DEFAULT NULL COMMENT '邀请人id',
     *   `status` int(1) DEFAULT NULL COMMENT '状态  0：未审核  1：已审核   2：已过期   3：已撤销',
     *   `home_id` int(9) DEFAULT NULL COMMENT '家庭id',
     *   `join_time` datetime DEFAULT NULL COMMENT '请求加入时间',
     *   `join_type` int(1) DEFAULT NULL COMMENT '请求加入方式',
     *   `role_id` int(1) DEFAULT NULL COMMENT '角色id ',
     *   `end_time` datetime DEFAULT NULL COMMENT '结束时间',
     *   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
     *   `update_time` datetime DEFAULT NULL COMMENT '更新时间',
     *   `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除（0：否 1：是）',
     *   PRIMARY KEY (`id`) USING BTREE
     * ) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
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
