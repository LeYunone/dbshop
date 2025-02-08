package com.leyunone.dbshop.service;

import com.leyunone.dbshop.bean.info.DbInfo;
import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.query.DbQuery;
import com.leyunone.dbshop.bean.rule.SqlCompareRule;
import com.leyunone.dbshop.bean.vo.DbTableContrastVO;
import com.leyunone.dbshop.bean.vo.TableContrastVO;
import com.leyunone.dbshop.service.core.impl.ConfigServiceImpl;
import com.leyunone.dbshop.service.core.impl.ContrastServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-06
 */
@SpringBootTest
public class ContrastTestService {

    @Autowired
    private ContrastServiceImpl contrastService;
    @Autowired
    private ConfigServiceImpl configService;

    /**
     *         DBQuery leftQuery = new DBQuery();
     *         leftQuery.setUrl("jdbc:mysql://localhost:3306/test2023?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
     *         leftQuery.setDbName("test2023");
     *         leftQuery.setUserName("root");
     *         leftQuery.setPassWord("root");
     *         DBQuery rightQuery = new DBQuery();
     *         rightQuery.setUrl("jdbc:mysql://localhost:3306/test2023-1?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
     *         rightQuery.setDbName("test2023-1");
     *         rightQuery.setUserName("root");
     *         rightQuery.setPassWord("root");
     */
    @Test
    public void columnContrast(){
        DbQuery leftQuery = new DbQuery();
        leftQuery.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        leftQuery.setDbName("test");
        leftQuery.setUserName("root");
        leftQuery.setPassWord("root");
        DbQuery rightQuery = new DbQuery();
        rightQuery.setUrl("jdbc:mysql://127.0.0.1.201:3306/test?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        rightQuery.setDbName("test");
        rightQuery.setUserName("root");
        rightQuery.setPassWord("root");

        DbInfo leftDbInfo = configService.loadConnectionToData(leftQuery);
        DbInfo rightDbInfo = configService.loadConnectionToData(rightQuery);

        ContrastQuery contrastQuery = new ContrastQuery();
        contrastQuery.setLeftTableName("d_fast_command");
        contrastQuery.setRightTableName("d_fast_command");

        contrastQuery.setLeftDbName(leftQuery.getDbName());
        contrastQuery.setRightDbName("smarthome");

        contrastQuery.setLeftUrl(leftQuery.getUrl());
        contrastQuery.setRightUrl(rightQuery.getUrl());
        TableContrastVO tableContrastVO = contrastService.tableContrastToTable(contrastQuery);
    }

    @Test
    public void tableContrast(){
        DbQuery leftQuery = new DbQuery();
        leftQuery.setUrl("jdbc:mysql://localhost:3306/test2023?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
        leftQuery.setDbName("test2023");
        leftQuery.setUserName("root");
        leftQuery.setPassWord("root");
        DbQuery rightQuery = new DbQuery();
        rightQuery.setUrl("jdbc:mysql://localhost:3306/test2023-1?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
        rightQuery.setDbName("test2023-1");
        rightQuery.setUserName("root");
        rightQuery.setPassWord("root");

        DbInfo leftDbInfo = configService.loadConnectionToData(leftQuery);
        DbInfo rightDbInfo = configService.loadConnectionToData(rightQuery);

        ContrastQuery contrastQuery = new ContrastQuery();
        SqlCompareRule sqlCompareRule = new SqlCompareRule();
        sqlCompareRule.setGoDeep(true);

        contrastQuery.setSqlCompareRule(sqlCompareRule);
        contrastQuery.setLeftDbName("test2023");
        contrastQuery.setRightDbName("test2023-1");

        contrastQuery.setLeftUrl(leftQuery.getUrl());
        contrastQuery.setRightUrl(rightQuery.getUrl());
        List<DbTableContrastVO> dbTableContrastVOS = contrastService.dbTableContrast(contrastQuery);
        System.out.println(dbTableContrastVOS.size());
    }
}
