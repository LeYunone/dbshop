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
public class ColumnInfo {
    
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
     * 是否可以为空 true为不可
     */
    private Boolean nullable;
}
