package com.leyunone.dbshop.service.core.impl;

import cn.hutool.core.util.ObjectUtil;
import com.leyunone.dbshop.bean.info.*;
import com.leyunone.dbshop.bean.query.DBQuery;
import com.leyunone.dbshop.service.core.ConfigService;
import com.leyunone.dbshop.system.factory.DBDataFactory;
import com.leyunone.dbshop.util.AssertUtil;
import com.leyunone.dbshop.util.DbClose;
import com.leyunone.dbshop.util.DbStrategyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.List;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-28
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConnectServiceImpl connectService;
    @Autowired
    private PackInfoServiceImpl packInfoService;
    @Autowired
    private DBDataFactory dbDataFactory;


    /**
     * 数据库连接唯一入库： 加载数据库连接
     *
     * @param query
     */
    @Override
    public DbInfo loadConnectionToData(DBQuery query) {
        String url = query.getUrl();
        Connection connection = connectService.getConnection(url, query.getUserName(), query.getPassWord());
//        AssertUtil.isFalse(ObjectUtil.isNull(connectionToData),"connection is fail");
//        dbDataFactory.regist(DBUtil.getStrategy(query),connectionToData);
//        return packInfoService.getDbInfo(connectionToData);
        DatabaseMetaData metaData = null;
        try {
            metaData = connection.getMetaData();
        } catch (Exception e) {
        }
        AssertUtil.isFalse(ObjectUtil.isNull(metaData));
        //加载数据库信息
        DbInfo dbInfo = packInfoService.getDbInfo(metaData);
        dbInfo.setDbName(query.getDbName());
        dbDataFactory.regist(DbStrategyUtil.getDbStrategy(query), dbInfo, DbInfo.class);
        //加载表信息
        List<TableDetailInfo> tables = packInfoService.getTables(metaData, query.getDbName());
        dbDataFactory.regist(DbStrategyUtil.getDetailStrategy(query), tables, TableDetailInfo.class);

        //加载字段信息
        for (TableDetailInfo tableDetailInfo : tables) {
            List<ColumnInfo> columns = packInfoService.getColumns(metaData, query.getDbName(), tableDetailInfo.getTableName());
            List<IndexInfo> indexs = packInfoService.getIndexs(metaData, query.getDbName(), tableDetailInfo.getTableName());

            query.setTableName(tableDetailInfo.getTableName());
            TableInfo info = new TableInfo().setColumnInfos(columns).setIndexInfos(indexs);
            dbDataFactory.regist(DbStrategyUtil.getTableStrategy(query), info, TableInfo.class);
        }

        DbClose.close(connection);
        return dbInfo;
    }
}
