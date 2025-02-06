package com.leyunone.dbshop.service.core;


import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.DbInfo;
import com.leyunone.dbshop.bean.info.IndexInfo;
import com.leyunone.dbshop.bean.info.TableDetailInfo;

import java.sql.DatabaseMetaData;
import java.util.List;

/**
 * 组装信息服务
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-16
 */
public interface PackInfoService {


    /**
     * 数据库信息
     *
     * @param data
     * @return
     */
    DbInfo getDbInfo(DatabaseMetaData data);

    /**
     * 表信息
     *
     * @param meta
     * @param dbName
     */
    List<TableDetailInfo> getTables(DatabaseMetaData meta, String dbName);

    /**
     * 索引信息
     *
     * @return
     */
    List<IndexInfo> getIndexs(DatabaseMetaData meta, String dbName, String tableName);

    List<ColumnInfo> getColumns(DatabaseMetaData meta, String dbName, String tableName);
}
