package com.leyunone.dbshop.bean.dto;

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
public class IndexDTO {
    
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

        @Override
        public int hashCode() {
            int result = index != null ? index.hashCode() : 0;
            result = 31 * result + (columnName != null ? columnName.hashCode() : 0);
            return result;
        }
    }

    /**
     * 只重写hashcode方法用来比较
     * @return
     */
    @Override
    public int hashCode() {
        int result = columns != null ? columns.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (ascOrDesc != null ? ascOrDesc.hashCode() : 0);
        result = 31 * result + cardinality;
        result = 31 * result + (indexName != null ? indexName.hashCode() : 0);
        return result;
    }
}
