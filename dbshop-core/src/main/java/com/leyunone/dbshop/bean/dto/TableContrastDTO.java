package com.leyunone.dbshop.bean.dto;

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
public class TableContrastDTO {

    private TableDetailInfo leftTableDetailInfo;
    
    private TableDetailInfo rightTableDetailInfo;
    
    private List<ColumnInfo> leftColumnInfo;
    
    private List<ColumnInfo> rightColumnInfo;
    
    private List<TableColumnContrastDTO> columnContrasts;

    private List<IndexInfo> leftIndexInfo;

    private List<IndexInfo> rightIndexInfo;
    
    private List<IndexContrastDTO> indexContrasts;
    
    private Boolean hasDifference;

    private Boolean nameDifference;

    public TableDetailInfo getLeftTableDetailInfo() {
        return leftTableDetailInfo;
    }

    public TableContrastDTO setLeftTableDetailInfo(TableDetailInfo leftTableDetailInfo) {
        this.leftTableDetailInfo = leftTableDetailInfo;
        return this;
    }

    public TableDetailInfo getRightTableDetailInfo() {
        return rightTableDetailInfo;
    }

    public TableContrastDTO setRightTableDetailInfo(TableDetailInfo rightTableDetailInfo) {
        this.rightTableDetailInfo = rightTableDetailInfo;
        return this;
    }

    public List<ColumnInfo> getLeftColumnInfo() {
        return leftColumnInfo;
    }

    public TableContrastDTO setLeftColumnInfo(List<ColumnInfo> leftColumnInfo) {
        this.leftColumnInfo = leftColumnInfo;
        return this;
    }

    public List<ColumnInfo> getRightColumnInfo() {
        return rightColumnInfo;
    }

    public TableContrastDTO setRightColumnInfo(List<ColumnInfo> rightColumnInfo) {
        this.rightColumnInfo = rightColumnInfo;
        return this;
    }

    public List<TableColumnContrastDTO> getColumnContrasts() {
        return columnContrasts;
    }

    public TableContrastDTO setColumnContrasts(List<TableColumnContrastDTO> columnContrasts) {
        this.columnContrasts = columnContrasts;
        return this;
    }

    public List<IndexInfo> getLeftIndexInfo() {
        return leftIndexInfo;
    }

    public TableContrastDTO setLeftIndexInfo(List<IndexInfo> leftIndexInfo) {
        this.leftIndexInfo = leftIndexInfo;
        return this;
    }

    public List<IndexInfo> getRightIndexInfo() {
        return rightIndexInfo;
    }

    public TableContrastDTO setRightIndexInfo(List<IndexInfo> rightIndexInfo) {
        this.rightIndexInfo = rightIndexInfo;
        return this;
    }

    public List<IndexContrastDTO> getIndexContrasts() {
        return indexContrasts;
    }

    public TableContrastDTO setIndexContrasts(List<IndexContrastDTO> indexContrasts) {
        this.indexContrasts = indexContrasts;
        return this;
    }

    public Boolean getHasDifference() {
        return hasDifference;
    }

    public TableContrastDTO setHasDifference(Boolean hasDifference) {
        this.hasDifference = hasDifference;
        return this;
    }

    public Boolean getNameDifference() {
        return nameDifference;
    }

    public TableContrastDTO setNameDifference(Boolean nameDifference) {
        this.nameDifference = nameDifference;
        return this;
    }
}
