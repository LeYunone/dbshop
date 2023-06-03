package com.leyunone.dbshop.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.db.DbUtil;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.DbInfo;
import com.leyunone.dbshop.bean.info.TableInfo;
import com.leyunone.dbshop.bean.query.DBQuery;
import com.leyunone.dbshop.system.factory.DBDataFactory;
import com.leyunone.dbshop.util.AssertUtil;
import com.leyunone.dbshop.util.DbClose;
import com.leyunone.dbshop.util.DbStrategyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-28
 */
@Service
public class ConfigService {

    @Autowired
    private ConnectService connectService;
    @Autowired
    private PackInfoService packInfoService;
    @Autowired
    private DBDataFactory dbDataFactory;


    /**
     * 数据库连接唯一入库： 加载数据库连接
     * @param query
     */
    public DbInfo loadConnectionToData(DBQuery query) {
        String url = query.getUrl();
        Connection connection = connectService.getConnection(url, query.getUserName(), query.getPassWord());
//        AssertUtil.isFalse(ObjectUtil.isNull(connectionToData),"connection is fail");
//        dbDataFactory.regist(DBUtil.getStrategy(query),connectionToData);
//        return packInfoService.getDbInfo(connectionToData);
        DatabaseMetaData metaData = null;
        try {
            metaData = connection.getMetaData();
        }catch (Exception e){
        }
        AssertUtil.isFalse(ObjectUtil.isNull(metaData));
        //加载数据库信息
        DbInfo dbInfo = packInfoService.getDbInfo(metaData);
        dbDataFactory.regist(DbStrategyUtil.getDbStrategy(query),dbInfo,DbInfo.class);
        //加载表信息
        List<TableInfo> tables = packInfoService.getTables(metaData, query.getDbName());
        dbDataFactory.regist(DbStrategyUtil.getTableStrategy(query),tables,TableInfo.class);

        //加载字段信息
        List<ColumnInfo> columns = packInfoService.getColumns(metaData, query.getDbName(), null);
        Map<String, List<ColumnInfo>> columnMap = columns.stream().collect(Collectors.groupingBy(ColumnInfo::getTableName));
        for(String tableNam: columnMap.keySet()){
            query.setTableName(tableNam);
            dbDataFactory.regist(DbStrategyUtil.getColumnStrategy(query),columnMap.get(tableNam),ColumnInfo.class);
        }
        DbClose.close(connection);
        return dbInfo;
    }
}
