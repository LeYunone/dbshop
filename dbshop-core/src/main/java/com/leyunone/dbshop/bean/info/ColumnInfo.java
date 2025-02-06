package com.leyunone.dbshop.bean.info;


/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-16
 */
public class ColumnInfo {
    
    private String tableName;
    
    private String columnName;
    
    private String dataType;
    
    private String typeName;
    
    private String tableCat;
    
    private String columnSize;
    
    private String decimailDigits;
    
    private String remarks;

    /**
     * 是否整形自增
     */
    private Boolean autoincrement;

    /**
     * 是否可以为空 true为可为空
     */
    private Boolean nullable;

    /**
     * 是否为主键 true为是
     */
    private Boolean primaryKey;

    public String getTableName() {
        return tableName;
    }

    public ColumnInfo setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getColumnName() {
        return columnName;
    }

    public ColumnInfo setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public String getDataType() {
        return dataType;
    }

    public ColumnInfo setDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public ColumnInfo setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public String getTableCat() {
        return tableCat;
    }

    public ColumnInfo setTableCat(String tableCat) {
        this.tableCat = tableCat;
        return this;
    }

    public String getColumnSize() {
        return columnSize;
    }

    public ColumnInfo setColumnSize(String columnSize) {
        this.columnSize = columnSize;
        return this;
    }

    public String getDecimailDigits() {
        return decimailDigits;
    }

    public ColumnInfo setDecimailDigits(String decimailDigits) {
        this.decimailDigits = decimailDigits;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public ColumnInfo setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public Boolean getAutoincrement() {
        return autoincrement;
    }

    public ColumnInfo setAutoincrement(Boolean autoincrement) {
        this.autoincrement = autoincrement;
        return this;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public ColumnInfo setNullable(Boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    public Boolean getPrimaryKey() {
        return primaryKey;
    }

    public ColumnInfo setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }
}
