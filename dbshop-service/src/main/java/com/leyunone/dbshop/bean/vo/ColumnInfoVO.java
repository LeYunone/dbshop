package com.leyunone.dbshop.bean.info;

import lombok.*;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-16
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnInfoVO {

    private String tableName;

    private String columnName;

    private String dataType;

    private String typeName;

    private String tableCat;

    private String columnSize;

    private String decimailDigits;

    private String remarks;

    /**
     * 是否整形自增
     */
    private Boolean autoincrement;

    /**
     * 是否可以为空 true为可为空
     */
    private Boolean nullable;

    /**
     * 是否为主键 true为是
     */
    private Boolean primaryKey;
    
    //树形节点名称
    private String label;
    
    //是否为新增
    private Boolean addColumn;
    
    //是否为删除
    private Boolean deleteColumn;
    
    //是否为更新
    private Boolean updateColumn;
}
