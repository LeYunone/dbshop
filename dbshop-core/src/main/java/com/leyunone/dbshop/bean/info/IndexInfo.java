package com.leyunone.dbshop.bean.info;


import java.util.ArrayList;
import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-08-15
 */
public class IndexInfo {

    private List<IndexColumn> columns = new ArrayList<>();

    /**
     * 索引类型
     */
    private Integer type;

    /**
     * 列排序顺序: 升序还是降序
     * A（升序）
     *
     * D（降序）
     * MYSQL 5.7版本 ：为空为 FULLTEXT
     */
    private String ascOrDesc;

    /**
     * 基数
     */
    private int cardinality;

    /**
     * 索引名
     */
    private String indexName;

    private String tableName;

    /**
     * 是否是唯一索引
     */
    private boolean uniqueIndex;

    /**
     * 是否是主键索引 [默认获取]
     */
    private boolean primaryIndex;

    public List<IndexColumn> getColumns() {
        return columns;
    }

    public IndexInfo setColumns(List<IndexColumn> columns) {
        this.columns = columns;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public IndexInfo setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getAscOrDesc() {
        return ascOrDesc;
    }

    public IndexInfo setAscOrDesc(String ascOrDesc) {
        this.ascOrDesc = ascOrDesc;
        return this;
    }

    public int getCardinality() {
        return cardinality;
    }

    public IndexInfo setCardinality(int cardinality) {
        this.cardinality = cardinality;
        return this;
    }

    public String getIndexName() {
        return indexName;
    }

    public IndexInfo setIndexName(String indexName) {
        this.indexName = indexName;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public IndexInfo setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public boolean isUniqueIndex() {
        return uniqueIndex;
    }

    public IndexInfo setUniqueIndex(boolean uniqueIndex) {
        this.uniqueIndex = uniqueIndex;
        return this;
    }

    public boolean isPrimaryIndex() {
        return primaryIndex;
    }

    public IndexInfo setPrimaryIndex(boolean primaryIndex) {
        this.primaryIndex = primaryIndex;
        return this;
    }

    public static class IndexColumn{

        /**
         * 序号
         */
        private Integer index;

        /**
         * 列名
         */
        private String columnName;

        public IndexColumn(){}

        public IndexColumn(Integer index, String columnName) {
            this.index = index;
            this.columnName = columnName;
        }

        @Override
        public int hashCode() {
            int result = index != null ? index.hashCode() : 0;
            result = 31 * result + (columnName != null ? columnName.hashCode() : 0);
            return result;
        }

        public Integer getIndex() {
            return index;
        }

        public IndexColumn setIndex(Integer index) {
            this.index = index;
            return this;
        }

        public String getColumnName() {
            return columnName;
        }

        public IndexColumn setColumnName(String columnName) {
            this.columnName = columnName;
            return this;
        }
    }
}
