package com.leyunone.dbshop.service;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.dbshop.api.DbShopStartAPIService;
import com.leyunone.dbshop.bean.dto.DbShopDbDTO;
import com.leyunone.dbshop.bean.dto.SqlRuleDTO;
import com.leyunone.dbshop.enums.DataTypeRegularEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-19
 */
@SpringBootTest
public class ApiTestService {

    @Autowired
    private DbShopStartAPIService dbShopStartAPIService;
    
    @Test
    public void startTest(){
        DbShopDbDTO leftQuery = new DbShopDbDTO();
//        leftQuery.setUrl("jdbc:mysql://192.168.151.233:3306/smarthome?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
//        leftQuery.setDbName("smarthome");
//        leftQuery.setUserName("root");
//        leftQuery.setPassWord("gvs@2022");
        leftQuery.setUrl("jdbc:mysql://rm-wz9e0gb1cx2986u05bo.mysql.rds.aliyuncs.com:3306/smarthome?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        leftQuery.setDbName("smarthome");
        leftQuery.setUserName("smarthome_select");
        leftQuery.setPassWord("smarthome_select@2Ozl.Com");
        DbShopDbDTO rightQuery = new DbShopDbDTO();
        rightQuery.setUrl("jdbc:mysql://192.168.151.201:3306/smarthome?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        rightQuery.setDbName("smarthome");
        rightQuery.setUserName("root");
        rightQuery.setPassWord("gvs@2021");
        SqlRuleDTO sqlRuleDTO = new SqlRuleDTO();
        //备注级对比 0关闭 1开启
        sqlRuleDTO.setGoRemark(true);
        //深度对比 0关闭 1开启
        sqlRuleDTO.setGoDeep(true);
        //那张为主表 0 左表  1 右表
        sqlRuleDTO.setLeftOrRight(1);
        //是否封装删除表sql
        sqlRuleDTO.setDeleteTable(true);
        //类型转化规则        	       
        sqlRuleDTO.setTransformReg(CollectionUtil.newArrayList(DataTypeRegularEnum.BIT1_TO_TINYINT1,DataTypeRegularEnum.DATETIME_TO_0));
        dbShopStartAPIService.leftRightDb(leftQuery,rightQuery,sqlRuleDTO);
    }
}
