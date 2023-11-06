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
@ToString
public class IndexInfo {

    private List<IndexColumn> columns = new ArrayList<>();

    /**
     * 索引类型
     */
    private Integer type;

    /**
     * 列排序顺序: 升序还是降序
     * A（升序）
     *
     * D（降序）
     * MYSQL 5.7版本 ：为空为 FULLTEXT
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

    private String tableName;

    /**
     * 是否是唯一索引
     */
    private boolean uniqueIndex;

    /**
     * 是否是主键索引 [默认获取]
     */
    private boolean primaryIndex;

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

        public IndexColumn(){}

        public IndexColumn(Integer index, String columnName) {
            this.index = index;
            this.columnName = columnName;
        }

        @Override
        public int hashCode() {
            int result = index != null ? index.hashCode() : 0;
            result = 31 * result + (columnName != null ? columnName.hashCode() : 0);
            return result;
        }
    }
}
