package com.leyunone.dbshop.bean.dto;


import java.util.ArrayList;
import java.util.List;

/**
 * :)
 * 
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-08-15
 */
public class IndexDTO {
    
    private List<IndexColumn> columns = new ArrayList<>();

    /**
     * 索引类型
     */
    private Integer type;

    /**
     * 列排序顺序: 升序还是降序
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

    public List<IndexColumn> getColumns() {
        return columns;
    }

    public IndexDTO setColumns(List<IndexColumn> columns) {
        this.columns = columns;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public IndexDTO setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getAscOrDesc() {
        return ascOrDesc;
    }

    public IndexDTO setAscOrDesc(String ascOrDesc) {
        this.ascOrDesc = ascOrDesc;
        return this;
    }

    public int getCardinality() {
        return cardinality;
    }

    public IndexDTO setCardinality(int cardinality) {
        this.cardinality = cardinality;
        return this;
    }

    public String getIndexName() {
        return indexName;
    }

    public IndexDTO setIndexName(String indexName) {
        this.indexName = indexName;
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

    /**
     * 只重写hashcode方法用来比较
     * @return
     */
    @Override
    public int hashCode() {
        int result = columns != null ? columns.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (ascOrDesc != null ? ascOrDesc.hashCode() : 0);
        result = 31 * result + cardinality;
        result = 31 * result + (indexName != null ? indexName.hashCode() : 0);
        return result;
    }
}
