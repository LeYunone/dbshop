package com.leyunone.dbshop.bean.info;

import lombok.*;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-08-30
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableInfo {

    /**
     * 表中字段信息
     */
    private List<ColumnInfo> columnInfos;

    /**
     * 表索引信息
     */
    private List<IndexInfo> indexInfos;
}
