package com.leyunone.dbshop.bean.vo;

import com.leyunone.dbshop.bean.info.ColumnInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 表中字段对比的结果集
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-22
 */
@Getter
@Setter
public class TableColumnContrastVO {

    private ColumnInfo leftColumn;
    
    private ColumnInfo rightColumn;
    
    //名 true为有差异
    private Boolean nameDifferent;
    //长度 true为有差异
    private Boolean sizeDifferent;
    //类型 true为有差异
    private Boolean typeDifferent;
    //注释 true为有差异
    private Boolean remarkDifferent;
    //主键 true为差异
    private Boolean primaryKeyDifferent;
    //整形自增 true为差异
    private Boolean autoincrementDifferent;
    //有无差异
    private Boolean hasDifferent;
    //TODO 索引
}
