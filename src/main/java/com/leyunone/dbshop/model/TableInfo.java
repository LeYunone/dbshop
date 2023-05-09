package com.leyunone.dbshop.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 表信息
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-09
 */
@Getter
@Setter
public class TableInfo {

    /**
     * 表名
     */
    private String tableName;
    /**
     * 表类型
     */
    private String tableType;
    /**
     * 注释
     */
    private String remark;

    private List<ColumnInfo> columnInfos;
}
