package com.leyunone.dbshop.bean.info;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-16
 */
public class TableDetailInfo {
    
    private String tableName;
    
    private String tableType;
    
    private String remarks;

    private Set<String> primarys = new HashSet<>();

    public String getTableName() {
        return tableName;
    }

    public TableDetailInfo setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getTableType() {
        return tableType;
    }

    public TableDetailInfo setTableType(String tableType) {
        this.tableType = tableType;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public TableDetailInfo setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public Set<String> getPrimarys() {
        return primarys;
    }

    public TableDetailInfo setPrimarys(Set<String> primarys) {
        this.primarys = primarys;
        return this;
    }
}
