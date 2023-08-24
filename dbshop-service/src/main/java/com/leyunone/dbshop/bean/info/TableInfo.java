package com.leyunone.dbshop.bean.info;

import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class TableInfo {
    
    private String tableName;
    
    private String tableType;
    
    private String remarks;

    private Set<String> primarys = new HashSet<>();
    
    private List<IndexInfo> indexInfos;
}
