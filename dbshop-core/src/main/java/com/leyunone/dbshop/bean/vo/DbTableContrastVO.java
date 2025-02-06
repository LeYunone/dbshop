package com.leyunone.dbshop.bean.vo;

import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.IndexInfo;
import com.leyunone.dbshop.bean.info.TableDetailInfo;

import java.util.List;

/**
 * 两个数据库对比总结果
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-28
 */
public class DbTableContrastVO {

    private TableDetailInfo leftTableDetailInfo;
    
    private TableDetailInfo rightTableDetailInfo;
    
    private List<ColumnInfo> leftColumnInfo;
    
    private List<ColumnInfo> rightColumnInfo;
    
    private List<IndexInfo> leftIndexInfo;
    
    private List<IndexInfo> rightIndexInfo;
    
    private List<TableColumnContrastVO> columnContrasts;
    
    private List<IndexContrastVO> indexContrasts;
    
    private Boolean hasDifference;

    private Boolean nameDifference;
    
    private Boolean indexDifference;

    public TableDetailInfo getLeftTableDetailInfo() {
        return leftTableDetailInfo;
    }

    public DbTableContrastVO setLeftTableDetailInfo(TableDetailInfo leftTableDetailInfo) {
        this.leftTableDetailInfo = leftTableDetailInfo;
        return this;
    }

    public TableDetailInfo getRightTableDetailInfo() {
        return rightTableDetailInfo;
    }

    public DbTableContrastVO setRightTableDetailInfo(TableDetailInfo rightTableDetailInfo) {
        this.rightTableDetailInfo = rightTableDetailInfo;
        return this;
    }

    public List<ColumnInfo> getLeftColumnInfo() {
        return leftColumnInfo;
    }

    public DbTableContrastVO setLeftColumnInfo(List<ColumnInfo> leftColumnInfo) {
        this.leftColumnInfo = leftColumnInfo;
        return this;
    }

    public List<ColumnInfo> getRightColumnInfo() {
        return rightColumnInfo;
    }

    public DbTableContrastVO setRightColumnInfo(List<ColumnInfo> rightColumnInfo) {
        this.rightColumnInfo = rightColumnInfo;
        return this;
    }

    public List<IndexInfo> getLeftIndexInfo() {
        return leftIndexInfo;
    }

    public DbTableContrastVO setLeftIndexInfo(List<IndexInfo> leftIndexInfo) {
        this.leftIndexInfo = leftIndexInfo;
        return this;
    }

    public List<IndexInfo> getRightIndexInfo() {
        return rightIndexInfo;
    }

    public DbTableContrastVO setRightIndexInfo(List<IndexInfo> rightIndexInfo) {
        this.rightIndexInfo = rightIndexInfo;
        return this;
    }

    public List<TableColumnContrastVO> getColumnContrasts() {
        return columnContrasts;
    }

    public DbTableContrastVO setColumnContrasts(List<TableColumnContrastVO> columnContrasts) {
        this.columnContrasts = columnContrasts;
        return this;
    }

    public List<IndexContrastVO> getIndexContrasts() {
        return indexContrasts;
    }

    public DbTableContrastVO setIndexContrasts(List<IndexContrastVO> indexContrasts) {
        this.indexContrasts = indexContrasts;
        return this;
    }

    public Boolean getHasDifference() {
        return hasDifference;
    }

    public DbTableContrastVO setHasDifference(Boolean hasDifference) {
        this.hasDifference = hasDifference;
        return this;
    }

    public Boolean getNameDifference() {
        return nameDifference;
    }

    public DbTableContrastVO setNameDifference(Boolean nameDifference) {
        this.nameDifference = nameDifference;
        return this;
    }

    public Boolean getIndexDifference() {
        return indexDifference;
    }

    public DbTableContrastVO setIndexDifference(Boolean indexDifference) {
        this.indexDifference = indexDifference;
        return this;
    }
}
