package com.leyunone.dbshop.bean.info;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * :)
 * 
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-08-15
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndexInfo {
    
    private List<IndexColumn> columns = new ArrayList<>();

    /**
     * 索引类型
     */
    private Integer type;

    /**
     * 列排序顺序: 升序还是降序
     */
    private String ascOrDesc;

    /**
     * 基数
     */
    private int cardinality;

    /**
     * 索引名
     */
    private String indexName;
    
    @Getter
    @Setter
    @Builder
    public static class IndexColumn{

        /**
         * 序号
         */
        private Integer index;

        /**
         * 列名
         */
        private String columnName;
    }
}
