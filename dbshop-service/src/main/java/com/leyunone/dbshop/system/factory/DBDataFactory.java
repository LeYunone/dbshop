package com.leyunone.dbshop.system.factory;


import cn.hutool.core.util.ObjectUtil;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.DbInfo;
import com.leyunone.dbshop.bean.info.TableInfo;
import com.leyunone.dbshop.util.DbStrategyUtil;
import com.mysql.cj.xdevapi.DbDocFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据库信息仓库工厂
 * <p>
 * type+url+dbName
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-17
 */
@Component
public class DBDataFactory {

    /**
     * 存储策略 数据库 URL + db名
     */
    private Map<String, DbInfo> dbMap = new HashMap<>();
    private Map<String, List<TableInfo>> tableMap = new HashMap<>();
    private Map<String, List<ColumnInfo>> columnMap = new HashMap<>();

    public final static Logger logger = LoggerFactory.getLogger(DbDocFactory.class);

    public void regist(String strategy, Object o, Class<?> clazz) {
        if (StringUtils.isBlank(strategy)) {
            logger.error("strategy is empty");
            return;
        }
        if (clazz.isAssignableFrom(DbInfo.class)) {
            dbMap.put(strategy, (DbInfo) o);
        }
        if (clazz.isAssignableFrom(TableInfo.class)) {
            tableMap.put(strategy, (List<TableInfo>) o);
        }
        if (clazz.isAssignableFrom(ColumnInfo.class)) {
            columnMap.put(strategy, (List<ColumnInfo>) o);
        }
    }

    //key : url+db , url+table , url + column
    //
    //
    public DbInfo getDbData(String strategy) {
        return dbMap.get(strategy);
    }
    
    public List<TableInfo> getTableData(String strategy) {
        return tableMap.get(strategy);
    }
    
    public List<ColumnInfo> getColumnData(String strategy){
        return columnMap.get(strategy);
    }
//
//    /**
//     * 正则匹配
//     * @param strategy
//     */
//    public List<DatabaseMetaData> getDataMatch(String strategy) {
//        return dataMap.keySet().stream().filter((t) -> t.matches(strategy)).map(dataMap::get).collect(Collectors.toList());
//    }
}


