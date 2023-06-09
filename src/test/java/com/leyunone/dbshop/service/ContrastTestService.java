package com.leyunone.dbshop.service;

import cn.hutool.db.Db;
import com.leyunone.dbshop.bean.info.DbInfo;
import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.query.DBQuery;
import com.leyunone.dbshop.bean.vo.DbTableContrastVO;
import com.leyunone.dbshop.bean.vo.TableColumnContrastVO;
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
    private ContrastService contrastService;
    @Autowired
    private ConfigService configService;

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
        DBQuery leftQuery = new DBQuery();
        leftQuery.setUrl("jdbc:mysql://192.168.151.233:3306/smarthome?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        leftQuery.setDbName("smarthome");
        leftQuery.setUserName("root");
        leftQuery.setPassWord("gvs@2022");
        DBQuery rightQuery = new DBQuery();
        rightQuery.setUrl("jdbc:mysql://192.168.151.201:3306/smarthome?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        rightQuery.setDbName("smarthome");
        rightQuery.setUserName("root");
        rightQuery.setPassWord("gvs@2021");

        DbInfo leftDbInfo = configService.loadConnectionToData(leftQuery);
        DbInfo rightDbInfo = configService.loadConnectionToData(rightQuery);
        
        ContrastQuery contrastQuery = new ContrastQuery();
        contrastQuery.setLeftTablName("d_fast_command");
        contrastQuery.setRightTableName("d_fast_command");
        
        contrastQuery.setLeftDbName(leftQuery.getDbName());
        contrastQuery.setRightDbName("smarthome");
        
        contrastQuery.setLeftUrl(leftQuery.getUrl());
        contrastQuery.setRightUrl(rightQuery.getUrl());
        List<TableColumnContrastVO> tableColumnContrastVOS = contrastService.columnContrastToTable(contrastQuery);
    }
    
    @Test
    public void tableContrast(){
        DBQuery leftQuery = new DBQuery();
        leftQuery.setUrl("jdbc:mysql://localhost:3306/test2023?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
        leftQuery.setDbName("test2023");
        leftQuery.setUserName("root");
        leftQuery.setPassWord("root");
        DBQuery rightQuery = new DBQuery();
        rightQuery.setUrl("jdbc:mysql://localhost:3306/test2023-1?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
        rightQuery.setDbName("test2023-1");
        rightQuery.setUserName("root");
        rightQuery.setPassWord("root");

        DbInfo leftDbInfo = configService.loadConnectionToData(leftQuery);
        DbInfo rightDbInfo = configService.loadConnectionToData(rightQuery);

        ContrastQuery contrastQuery = new ContrastQuery();

        contrastQuery.setLeftDbName("test2023");
        contrastQuery.setRightDbName("test2023-1");

        contrastQuery.setLeftUrl(leftQuery.getUrl());
        contrastQuery.setRightUrl(rightQuery.getUrl());
        
        contrastQuery.setGoDeep(1);
        List<DbTableContrastVO> dbTableContrastVOS = contrastService.dbTableContrast(contrastQuery);
        System.out.println(dbTableContrastVOS.size());
    }
}
