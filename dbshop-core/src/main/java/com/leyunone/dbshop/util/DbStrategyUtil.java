package com.leyunone.dbshop.util;

import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.query.DbQuery;

/**
 * @author LeYunOne
 * @email 365627310@qq.com
 * @date 2022-12-25
 */
public class DbStrategyUtil {

    public static final String DB_STRATEGY = ":db";

    public static final String TABLE_STRATEGY = ":table";

    public static final String TABLE_INFO_STRATEGY = ":detail";

    /**
     * 数据库信息的策略
     * @param query
     * @return
     */
    public static String getDbStrategy(DbQuery query) {
        return query.getUrl() + ":" + query.getDbName() + DB_STRATEGY;
    }

    /**
     * 所有表的策略
     * @param query
     * @return
     */
    public static String getDetailStrategy(DbQuery query) {
        return query.getUrl() + ":" + query.getDbName() + TABLE_STRATEGY;
    }

    /**
     * 一张表中的详情策略
     * @param query
     * @return
     */
    public static String getTableStrategy(DbQuery query) {
        return query.getUrl() + ":" + query.getDbName() + ":" + query.getTableName() + TABLE_INFO_STRATEGY;
    }

    public static DbQuery loadContrastRule(ContrastQuery contrastQuery, boolean yesLeft) {
        String tableName = null;
        String dbName = null;
        String url = null;
        try {
            if (yesLeft) {
                tableName = contrastQuery.getLeftTableName();
                dbName = contrastQuery.getLeftDbName();
                url = contrastQuery.getLeftUrl();
            } else {
                tableName = contrastQuery.getRightTableName();
                dbName = contrastQuery.getRightDbName();
                url = contrastQuery.getRightUrl();
            }
        } catch (Exception e) {
            AssertUtil.isFalse(true, "参数非法");
        }
        return new DbQuery().setTableName(tableName).setDbName(dbName).setUrl(url);
    }
}
