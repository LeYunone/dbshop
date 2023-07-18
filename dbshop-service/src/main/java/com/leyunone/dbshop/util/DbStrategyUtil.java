package com.leyunone.dbshop.util;

import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.query.DBQuery;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-12-25
 */
public class DbStrategyUtil {

    public static final String DB_STRATEGY = ":db";

    public static final String COLUMN_STRATEGY = ":column";

    public static final String TABLE_STRATEGY = ":table";


    public static String getDbStrategy(DBQuery query) {
        return query.getUrl() + ":" + query.getDbName() + DB_STRATEGY;
    }

    public static String getTableStrategy(DBQuery query) {
        return query.getUrl() + ":" + query.getDbName() + TABLE_STRATEGY;
    }

    public static String getColumnStrategy(DBQuery query) {
        return query.getUrl() + ":" + query.getDbName() + ":" + query.getTableName() + COLUMN_STRATEGY;
    }

    public static DBQuery loadContrastRule(ContrastQuery contrastQuery, boolean yesLeft) {
        try {
            if (yesLeft) {
                return DBQuery.builder().tableName(contrastQuery.getLeftTableName()).dbName(contrastQuery.getLeftDbName()).url(contrastQuery.getLeftUrl()).build();
            }else{
                return DBQuery.builder().tableName(contrastQuery.getRightTableName()).dbName(contrastQuery.getRightDbName()).url(contrastQuery.getRightUrl()).build();
            }
        }catch (Exception e){
            AssertUtil.isFalse(true,"参数非法");
        }
        return null;
    }
}
