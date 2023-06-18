package com.leyunone.dbshop.enums;

/**
 * :)
 * sql语句组装集
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-18
 */
public enum SqlAssembleEnum {

    /**
     * sql语句集合
     */
    CREATE_TABLE_AUTOINCREMENT("AUTO_INCREMENT ","整形自增"),

    NULLABLE("NOT NULL ","不可为空"),
    
    NULL("DEFAULT NULL ","可为空")
    ;

    SqlAssembleEnum(String sql, String desc) {
        this.sql = sql;
        this.desc = desc;
    }

    private String sql;
    
    private String desc;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
