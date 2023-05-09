package com.leyunone.dbshop.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 字段信息
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-09
 */
@Getter
@Setter
public class ColumnInfo {

    /**
     * 字段名
     */
    private String columnName;

    /**
     * 是否为主键
     */
    private boolean primaryKey;

    /**
     * 注释
     */
    private String remarks;

    /**
     * 字段长度
     */
    private Integer columnSize;

    /**
     * 字段类型
     */
    private String columnType;
    
}

