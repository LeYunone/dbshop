package com.leyunone.dbshop.bean.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 两个数据库对比总结果
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-28
 */
@Getter
@Setter
public class DbTableContrastVO {

    private String tableName;
    
    private List<TableColumnContrastVO> columnContrastVOS;
    
    private Boolean hasDifference;
}
