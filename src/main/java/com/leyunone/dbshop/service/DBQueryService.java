package com.leyunone.dbshop.service;

import cn.hutool.core.util.ObjectUtil;
import com.leyunone.dbshop.bean.query.DBQuery;
import com.leyunone.dbshop.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-16
 */
@Service
public class DBQueryService {

    @Autowired
    private ConnectService connectService;
    
    public void getDBInfo(DBQuery query) {
        String url = query.getUrl();
        Connection connection = connectService.getConnection(query.getUrl(), query.getUserName(), query.getPassWord());
        AssertUtil.isFalse(ObjectUtil.isNull(connection),"connection is fail");
        
        
    }
}
