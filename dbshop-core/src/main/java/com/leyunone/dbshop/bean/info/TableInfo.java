package com.leyunone.dbshop.bean.info;


import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-08-30
 */
public class TableInfo {

    /**
     * 表中字段信息
     */
    private List<ColumnInfo> columnInfos;

    /**
     * 表索引信息
     */
    private List<IndexInfo> indexInfos;

    public List<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public TableInfo setColumnInfos(List<ColumnInfo> columnInfos) {
        this.columnInfos = columnInfos;
        return this;
    }

    public List<IndexInfo> getIndexInfos() {
        return indexInfos;
    }

    public TableInfo setIndexInfos(List<IndexInfo> indexInfos) {
        this.indexInfos = indexInfos;
        return this;
    }
}
