package com.leyunone.dbshop.enums;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-16
 */
public enum TableResultEnum {

    /**
     *  jdbc表对象
     */
    TABLE_NAME("TABLE_NAME", "表名"),

    TABLE_TYPE("TABLE_TYPE", "表类型"),

    REMARKS("REMARKS", "备注"),

    PK_COLUMN_NAME("COLUMN_NAME", "表主键");

    TableResultEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private String type;

    private String desc;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
