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
public class DBUtil {

    public static String getStrategy(DBQuery query) {
        return query.getUrl()+":"+query.getDbName();
    }

    public static String getLeftStrategy(ContrastQuery query) {
        return query.getLeftUrl()+":"+query.getLeftDbName();
    }

    public static String getRightStrategy(ContrastQuery query) {
        return query.getRightUrl()+":"+query.getRightDbName();
    }
}
