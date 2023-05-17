package com.leyunone.dbshop.system.factory;


import cn.hutool.core.util.ObjectUtil;
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
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-17
 */
@Component
public class DBDataFactory {

    public Map<String, DatabaseMetaData> dataMap = new HashMap<>();
    
    public final static Logger logger = LoggerFactory.getLogger(DbDocFactory.class);
    
    public void regist(String strategy,DatabaseMetaData databaseMetaData) {
        if(StringUtils.isBlank(strategy) || ObjectUtil.isNull(databaseMetaData)){
            logger.error("strategy is empty");
            return;
        }
        dataMap.put(strategy,databaseMetaData);
    }
    
    public DatabaseMetaData getData(String strategy) {
         return dataMap.get(strategy);       
    }

    /**
     * 正则匹配
     * @param strategy
     */
    public List<DatabaseMetaData> getDataMatch(String strategy) {
        List<DatabaseMetaData> datas = dataMap.keySet().stream().filter((t) -> t.matches(strategy)).map(dataMap::get).collect(Collectors.toList());
        return datas;
    }
}


