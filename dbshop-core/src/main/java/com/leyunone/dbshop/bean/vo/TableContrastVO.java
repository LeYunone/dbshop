package com.leyunone.dbshop.bean.vo;


import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-08-31
 */
public class TableContrastVO {

    private List<TableColumnContrastVO> columnContrasts;
    
    private List<IndexContrastVO> indexContrasts;

    public List<TableColumnContrastVO> getColumnContrasts() {
        return columnContrasts;
    }

    public TableContrastVO setColumnContrasts(List<TableColumnContrastVO> columnContrasts) {
        this.columnContrasts = columnContrasts;
        return this;
    }

    public List<IndexContrastVO> getIndexContrasts() {
        return indexContrasts;
    }

    public TableContrastVO setIndexContrasts(List<IndexContrastVO> indexContrasts) {
        this.indexContrasts = indexContrasts;
        return this;
    }
}

