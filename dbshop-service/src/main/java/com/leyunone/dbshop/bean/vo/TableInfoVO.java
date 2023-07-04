package com.leyunone.dbshop.bean.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-07-04
 */
@Getter
@Setter
@ToString
public class TableInfoVO {

    private String tableName;

    private String tableType;

    private String remarks;
    
    private List<ColumnInfoVO> columns;
}
