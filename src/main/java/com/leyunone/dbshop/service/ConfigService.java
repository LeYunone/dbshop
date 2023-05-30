package com.leyunone.dbshop.service;

import cn.hutool.core.util.ObjectUtil;
import com.leyunone.dbshop.bean.info.DbInfo;
import com.leyunone.dbshop.bean.query.DBQuery;
import com.leyunone.dbshop.system.factory.DBDataFactory;
import com.leyunone.dbshop.util.AssertUtil;
import com.leyunone.dbshop.util.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

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
        Connection connection = connectService.getConnection(query.getUrl(), query.getUserName(), query.getPassWord());
//        AssertUtil.isFalse(ObjectUtil.isNull(connectionToData),"connection is fail");
//        dbDataFactory.regist(DBUtil.getStrategy(query),connectionToData);
//        return packInfoService.getDbInfo(connectionToData);
        
        //加载数据库信息
        
        //加载表信息
        
        //加载字段信息
        
        
    }
}
