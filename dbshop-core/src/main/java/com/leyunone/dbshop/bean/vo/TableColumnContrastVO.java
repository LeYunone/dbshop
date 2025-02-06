package com.leyunone.dbshop.bean.vo;

import com.leyunone.dbshop.bean.info.ColumnInfo;

/**
 * 表中字段对比的结果集
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-22
 */
public class TableColumnContrastVO {

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
    //有无差异
    private Boolean hasDifferent;
    //TODO 索引


    public ColumnInfo getLeftColumn() {
        return leftColumn;
    }

    public TableColumnContrastVO setLeftColumn(ColumnInfo leftColumn) {
        this.leftColumn = leftColumn;
        return this;
    }

    public ColumnInfo getRightColumn() {
        return rightColumn;
    }

    public TableColumnContrastVO setRightColumn(ColumnInfo rightColumn) {
        this.rightColumn = rightColumn;
        return this;
    }

    public Boolean getNameDifferent() {
        return nameDifferent;
    }

    public TableColumnContrastVO setNameDifferent(Boolean nameDifferent) {
        this.nameDifferent = nameDifferent;
        return this;
    }

    public Boolean getSizeDifferent() {
        return sizeDifferent;
    }

    public TableColumnContrastVO setSizeDifferent(Boolean sizeDifferent) {
        this.sizeDifferent = sizeDifferent;
        return this;
    }

    public Boolean getTypeDifferent() {
        return typeDifferent;
    }

    public TableColumnContrastVO setTypeDifferent(Boolean typeDifferent) {
        this.typeDifferent = typeDifferent;
        return this;
    }

    public Boolean getRemarkDifferent() {
        return remarkDifferent;
    }

    public TableColumnContrastVO setRemarkDifferent(Boolean remarkDifferent) {
        this.remarkDifferent = remarkDifferent;
        return this;
    }

    public Boolean getPrimaryKeyDifferent() {
        return primaryKeyDifferent;
    }

    public TableColumnContrastVO setPrimaryKeyDifferent(Boolean primaryKeyDifferent) {
        this.primaryKeyDifferent = primaryKeyDifferent;
        return this;
    }

    public Boolean getAutoincrementDifferent() {
        return autoincrementDifferent;
    }

    public TableColumnContrastVO setAutoincrementDifferent(Boolean autoincrementDifferent) {
        this.autoincrementDifferent = autoincrementDifferent;
        return this;
    }

    public Boolean getHasDifferent() {
        return hasDifferent;
    }

    public TableColumnContrastVO setHasDifferent(Boolean hasDifferent) {
        this.hasDifferent = hasDifferent;
        return this;
    }
}
