package com.leyunone.dbshop.service;

import cn.hutool.core.util.ObjectUtil;
import com.leyunone.dbshop.bean.info.DbInfo;
import com.leyunone.dbshop.bean.query.DBQuery;
import com.leyunone.dbshop.util.AssertUtil;
import com.leyunone.dbshop.util.DbClose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-16
 */
@Service
public class DBQueryService {

    @Autowired
    private ConnectService connectService;
    @Autowired
    private PackInfoService packInfoService;
    
    public void getConnectionToData(DBQuery query) {
        String url = query.getUrl();
        DatabaseMetaData connectionToData = connectService.getConnectionToData(query.getUrl(), query.getUserName(), query.getPassWord());
        AssertUtil.isFalse(ObjectUtil.isNull(connectionToData),"connection is fail");
        DbInfo dbInfo = packInfoService.getDbInfo(connectionToData);
    }
    
    
}
