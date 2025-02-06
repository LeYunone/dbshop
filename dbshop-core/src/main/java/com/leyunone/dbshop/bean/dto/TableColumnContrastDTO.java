package com.leyunone.dbshop.bean.dto;

import com.leyunone.dbshop.bean.info.ColumnInfo;

/**
 * 表中字段对比的结果集
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-22
 */
public class TableColumnContrastDTO {

    private ColumnInfo leftColumn;
    
    private ColumnInfo rightColumn;
    
    //名 true为有差异
    private Boolean nameDifferent;
    //长度 true为有差异
    private Boolean sizeDifferent;
    //类型 true为有差异
    private Boolean typeDifferent;
    //注释 true为有差异
    private Boolean remarkDifferent;
    //主键 true为差异
    private Boolean primaryKeyDifferent;
    //整形自增 true为差异
    private Boolean autoincrementDifferent;
    //TODO 索引


    public ColumnInfo getLeftColumn() {
        return leftColumn;
    }

    public TableColumnContrastDTO setLeftColumn(ColumnInfo leftColumn) {
        this.leftColumn = leftColumn;
        return this;
    }

    public ColumnInfo getRightColumn() {
        return rightColumn;
    }

    public TableColumnContrastDTO setRightColumn(ColumnInfo rightColumn) {
        this.rightColumn = rightColumn;
        return this;
    }

    public Boolean getNameDifferent() {
        return nameDifferent;
    }

    public TableColumnContrastDTO setNameDifferent(Boolean nameDifferent) {
        this.nameDifferent = nameDifferent;
        return this;
    }

    public Boolean getSizeDifferent() {
        return sizeDifferent;
    }

    public TableColumnContrastDTO setSizeDifferent(Boolean sizeDifferent) {
        this.sizeDifferent = sizeDifferent;
        return this;
    }

    public Boolean getTypeDifferent() {
        return typeDifferent;
    }

    public TableColumnContrastDTO setTypeDifferent(Boolean typeDifferent) {
        this.typeDifferent = typeDifferent;
        return this;
    }

    public Boolean getRemarkDifferent() {
        return remarkDifferent;
    }

    public TableColumnContrastDTO setRemarkDifferent(Boolean remarkDifferent) {
        this.remarkDifferent = remarkDifferent;
        return this;
    }

    public Boolean getPrimaryKeyDifferent() {
        return primaryKeyDifferent;
    }

    public TableColumnContrastDTO setPrimaryKeyDifferent(Boolean primaryKeyDifferent) {
        this.primaryKeyDifferent = primaryKeyDifferent;
        return this;
    }

    public Boolean getAutoincrementDifferent() {
        return autoincrementDifferent;
    }

    public TableColumnContrastDTO setAutoincrementDifferent(Boolean autoincrementDifferent) {
        this.autoincrementDifferent = autoincrementDifferent;
        return this;
    }
}
