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
    
    private String leftUrl;
    
    private String rightUrl;
    
    private String leftDbName;
    
    private String rightDbName;
    
    private String leftTablName;
    
    private String rightTableName;
}
