package com.leyunone.dbshop.bean.vo;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-16
 */
public class ColumnInfoVO {

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
    
    //树形节点名称
    private String label;
    
    //是否为新增
    private Boolean addColumn;
    
    //是否为更新
    private Boolean updateColumn;

    public String getTableName() {
        return tableName;
    }

    public ColumnInfoVO setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getColumnName() {
        return columnName;
    }

    public ColumnInfoVO setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public String getDataType() {
        return dataType;
    }

    public ColumnInfoVO setDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public ColumnInfoVO setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public String getTableCat() {
        return tableCat;
    }

    public ColumnInfoVO setTableCat(String tableCat) {
        this.tableCat = tableCat;
        return this;
    }

    public String getColumnSize() {
        return columnSize;
    }

    public ColumnInfoVO setColumnSize(String columnSize) {
        this.columnSize = columnSize;
        return this;
    }

    public String getDecimailDigits() {
        return decimailDigits;
    }

    public ColumnInfoVO setDecimailDigits(String decimailDigits) {
        this.decimailDigits = decimailDigits;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public ColumnInfoVO setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public Boolean getAutoincrement() {
        return autoincrement;
    }

    public ColumnInfoVO setAutoincrement(Boolean autoincrement) {
        this.autoincrement = autoincrement;
        return this;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public ColumnInfoVO setNullable(Boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    public Boolean getPrimaryKey() {
        return primaryKey;
    }

    public ColumnInfoVO setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public ColumnInfoVO setLabel(String label) {
        this.label = label;
        return this;
    }

    public Boolean getAddColumn() {
        return addColumn;
    }

    public ColumnInfoVO setAddColumn(Boolean addColumn) {
        this.addColumn = addColumn;
        return this;
    }

    public Boolean getUpdateColumn() {
        return updateColumn;
    }

    public ColumnInfoVO setUpdateColumn(Boolean updateColumn) {
        this.updateColumn = updateColumn;
        return this;
    }
}
