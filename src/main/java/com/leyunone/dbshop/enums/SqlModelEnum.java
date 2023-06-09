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
    
    DELETE_COLUMN("ALTER TABLE {} DROP COLUMN {}","删除字段"),

    CREATE_TABLE("CREATE TABLE {}", "创建表"),

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
