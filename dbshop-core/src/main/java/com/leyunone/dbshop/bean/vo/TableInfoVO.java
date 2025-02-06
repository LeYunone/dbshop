package com.leyunone.dbshop.bean.vo;

import java.util.List;


/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-07-04
 */
public class TableInfoVO {

    private String tableName;

    private String tableType;

    private String remarks;

    //树形节点名称
    private String label;
    
    private List<ColumnInfoVO> columns;

    public String getTableName() {
        return tableName;
    }

    public TableInfoVO setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getTableType() {
        return tableType;
    }

    public TableInfoVO setTableType(String tableType) {
        this.tableType = tableType;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public TableInfoVO setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public TableInfoVO setLabel(String label) {
        this.label = label;
        return this;
    }

    public List<ColumnInfoVO> getColumns() {
        return columns;
    }

    public TableInfoVO setColumns(List<ColumnInfoVO> columns) {
        this.columns = columns;
        return this;
    }

    @Override
    public String toString() {
        return "TableInfoVO{" +
                "tableName='" + tableName + '\'' +
                ", tableType='" + tableType + '\'' +
                ", remarks='" + remarks + '\'' +
                ", label='" + label + '\'' +
                ", columns=" + columns +
                '}';
    }
}
