package com.leyunone.dbshop.bean.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-18
 */
@Getter
@Setter
public class ContrastQuery {

    /**
     * 左表策略
     */
    private String leftStrategy;

    /**
     * 右表策略
     */
    private String rightStrategy;
    
    private String tableName;
    
    private String dbName;
}
