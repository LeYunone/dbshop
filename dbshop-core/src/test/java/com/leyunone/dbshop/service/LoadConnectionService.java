package com.leyunone.dbshop.service;

import com.leyunone.dbshop.bean.query.DBQuery;
import com.leyunone.dbshop.service.core.impl.ConfigServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-05
 */
@SpringBootTest
public class LoadConnectionService {

    @Autowired
    private ConfigServiceImpl configService;
    
    @Test
    public void loadTest(){
        DBQuery query = new DBQuery();
        query.setUrl("jdbc:mysql://localhost:3306/test2023?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
        query.setDbName("test2023");
        query.setPassWord("root");
        query.setUserName("root");
        configService.loadConnectionToData(query);
    }
}
