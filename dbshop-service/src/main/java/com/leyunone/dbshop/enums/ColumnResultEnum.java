package com.leyunone.dbshop.enums;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-16
 */
public enum ColumnResultEnum {

    /**
     * 列
     */
    COLUMN_NAME("COLUMN_NAME","字段名"),

    DATA_TYPE("DATA_TYPE","字段类型"),

    TYPE_NAME("TYPE_NAME","类型名"),

    TABLE_CAT("TABLE_CAT","列名称"),

    COLUMN_SIZE("COLUMN_SIZE","长度"),

    DECIMAL_DIGITS("DECIMAL_DIGITS","小数位"),

    REMARKS("REMARKS","备注"),
    
    TABLE_NAME("TABLE_NAME","表名"),

    IS_AUTOINCREMENT("IS_AUTOINCREMENT","整形自增"),

    //以下未知
    TABLE_SCHEM("TABLE_SCHEM",""),

    SQL_DATETIME_SUB("SQL_DATETIME_SUB",""),

    SQL_DATA_TYPE("SQL_DATA_TYPE",""),

    SOURCE_DATA_TYPE("SOURCE_DATA_TYPE",""),

    SCOPE_TABLE("SCOPE_TABLE",""),

    SCOPE_SCHEMA("SCOPE_SCHEMA",""),

    SCOPE_CATALOG("SCOPE_CATALOG",""),

    ORDINAL_POSITION("ORDINAL_POSITION",""),

    NUM_PREC_RADIX("NUM_PREC_RADIX",""),

    NULLABLE("NULLABLE",""),

    IS_NULLABLE("IS_NULLABLE",""),

    IS_GENERATEDCOLUMN("IS_GENERATEDCOLUMN",""),

    COLUMN_DEF("COLUMN_DEF",""),

    CHAR_OCTET_LENGTH("CHAR_OCTET_LENGTH",""),

    BUFFER_LENGTH("BUFFER_LENGTH","")
    ;

    ColumnResultEnum(String type, String desc) {
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
