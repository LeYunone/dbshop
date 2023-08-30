package com.leyunone.dbshop.system.factory;


import cn.hutool.core.util.ObjectUtil;
import com.leyunone.dbshop.bean.info.*;
import com.mysql.cj.xdevapi.DbDocFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, List<TableDetailInfo>> tableDetailMap = new HashMap<>();
    private Map<String, TableInfo> tableMap = new HashMap<>();

    public final static Logger logger = LoggerFactory.getLogger(DbDocFactory.class);

    public void regist(String strategy, Object o, Class<?> clazz) {
        if (StringUtils.isBlank(strategy)) {
            logger.error("strategy is empty");
            return;
        }
        if (clazz.isAssignableFrom(DbInfo.class)) {
            dbMap.put(strategy, (DbInfo) o);
        }
        if (clazz.isAssignableFrom(TableDetailInfo.class)) {
            tableDetailMap.put(strategy, (List<TableDetailInfo>) o);
        }
        if (clazz.isAssignableFrom(TableInfo.class)) {
            tableMap.put(strategy, (TableInfo) o);
        }
    }

    //key : url+db , url+table , url + column
    //
    //
    public DbInfo getDbData(String strategy) {
        return dbMap.get(strategy);
    }
    
    public List<TableDetailInfo> getTableData(String strategy) {
        return tableDetailMap.get(strategy);
    }
    
    public List<ColumnInfo> getColumnData(String strategy){
        List<ColumnInfo> columnInfos = new ArrayList<>();
        TableInfo tableInfo = tableMap.get(strategy);
        if(ObjectUtil.isNotNull(tableInfo)){
            columnInfos = tableInfo.getColumnInfos();
        }
        return columnInfos;
    }

    public List<IndexInfo> getIndexData(String strategy){
        List<IndexInfo> indexInfos = new ArrayList<>();
        TableInfo tableInfo = tableMap.get(strategy);
        if(ObjectUtil.isNotNull(tableInfo)){
            indexInfos = tableInfo.getIndexInfos();
        }
        return indexInfos;
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


