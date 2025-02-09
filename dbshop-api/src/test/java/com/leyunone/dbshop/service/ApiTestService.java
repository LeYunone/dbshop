package com.leyunone.dbshop.service;

import com.leyunone.dbshop.annotate.VersionDescribe;
import com.leyunone.dbshop.api.DbShopStartAPIService;
import com.leyunone.dbshop.bean.dto.DbShopDbDTO;
import com.leyunone.dbshop.bean.dto.SqlRuleDTO;
import com.leyunone.dbshop.enums.DataTypeRegularEnum;
import com.leyunone.dbshop.manager.AnnotateLoadingManager;
import com.leyunone.dbshop.util.MyCollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

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
    public void startTest() {
        DbShopDbDTO leftQuery = new DbShopDbDTO();
        leftQuery.setUrl("jdbc:mysql://127.0.0.1:3306/2024_test?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        leftQuery.setDbName("2024_test");
        leftQuery.setUserName("root");
        leftQuery.setPassWord("root");
        DbShopDbDTO rightQuery = new DbShopDbDTO();
        rightQuery.setUrl("jdbc:mysql://127.0.0.1:3306/2024_test_1?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        rightQuery.setDbName("2024_test_1");
        rightQuery.setUserName("root");
        rightQuery.setPassWord("root");
        SqlRuleDTO sqlRuleDTO = new SqlRuleDTO();
        //备注级对比 0关闭 1开启
        sqlRuleDTO.setGoRemark(true);
        //深度对比 0关闭 1开启
        sqlRuleDTO.setGoDeep(true);
        //那张为主表 0 左表  1 右表
        sqlRuleDTO.setLeftOrRight(0);


        //是否封装删除表sql
        sqlRuleDTO.setDeleteTable(true);
        //类型转化规则        	       
        sqlRuleDTO.setTransformReg(MyCollectionUtils.newArrayList(DataTypeRegularEnum.BIT1_TO_TINYINT1, DataTypeRegularEnum.DATETIME_TO_0));
        dbShopStartAPIService.leftRightDbCompare(leftQuery, rightQuery, sqlRuleDTO);
    }

    @Test
    public void checkUseLessTableTest() {
        DbShopDbDTO leftQuery = new DbShopDbDTO();
        leftQuery.setUrl("jdbc:mysql://127.0.0.1:3306/2024_test?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        leftQuery.setDbName("2024_test");
        leftQuery.setUserName("root");
        leftQuery.setPassWord("root");
        AnnotateLoadingManager.AnnotateObject annotateObject = new AnnotateLoadingManager.AnnotateObject(VersionDescribe.class, "describe");
        dbShopStartAPIService.checkUselessTable(leftQuery, annotateObject);
    }

    @Test
    public void checkUseLessFieldTest() {
        DbShopDbDTO leftQuery = new DbShopDbDTO();
        leftQuery.setUrl("jdbc:mysql://127.0.0.1:3306/2024_test?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        leftQuery.setDbName("2024_test");
        leftQuery.setUserName("root");
        leftQuery.setPassWord("root");
        AnnotateLoadingManager.AnnotateObject annotateObject = new AnnotateLoadingManager.AnnotateObject(VersionDescribe.class, "describe");

        dbShopStartAPIService.checkTableColumnToCode(leftQuery,annotateObject);
    }
}
