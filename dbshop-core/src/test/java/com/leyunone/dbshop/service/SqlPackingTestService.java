package com.leyunone.dbshop.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;

import com.leyunone.dbshop.bean.dto.SqlProductionDTO;
import com.leyunone.dbshop.bean.dto.TableContrastDTO;
import com.leyunone.dbshop.bean.info.DbInfo;
import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.query.DBQuery;
import com.leyunone.dbshop.bean.vo.DbTableContrastVO;
import com.leyunone.dbshop.bean.vo.TableContrastVO;
import com.leyunone.dbshop.constant.DbShopConstant;
import com.leyunone.dbshop.enums.DataTypeRegularEnum;
import com.leyunone.dbshop.service.core.impl.ConfigServiceImpl;
import com.leyunone.dbshop.service.core.impl.ContrastServiceImpl;
import com.leyunone.dbshop.service.core.impl.SqlPackServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * :)
 *  &nullCatalogMeansCurrent=true
 * @Author LeYunone
 * @Date 2023/6/9 9:36
 */
@SpringBootTest
public class SqlPackingTestService {

    @Autowired
    private SqlPackServiceImpl sqlPackService;
    @Autowired
    private ConfigServiceImpl configService;
    @Autowired
    private ContrastServiceImpl contrastService;

    @Test
    public void columnPackTest() {
        DBQuery leftQuery = new DBQuery();
        leftQuery.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        leftQuery.setDbName("smarthome");
        leftQuery.setUserName("root");
        leftQuery.setPassWord("root");
        DBQuery rightQuery = new DBQuery();
        rightQuery.setUrl("jdbc:mysql://127.0.0.1:3306/test2?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        rightQuery.setDbName("smarthome");
        rightQuery.setUserName("root");
        rightQuery.setPassWord("root");

        DbInfo leftDbInfo = configService.loadConnectionToData(leftQuery);
        DbInfo rightDbInfo = configService.loadConnectionToData(rightQuery);

        ContrastQuery contrastQuery = new ContrastQuery();
        contrastQuery.setLeftTableName("d_fast_command");
        contrastQuery.setRightTableName("d_fast_command");

        contrastQuery.setLeftDbName(leftQuery.getDbName());
        contrastQuery.setRightDbName("smarthome");

        contrastQuery.setLeftDbName(leftQuery.getDbName());
        contrastQuery.setRightDbName(rightQuery.getDbName());

        contrastQuery.setLeftUrl(leftQuery.getUrl());
        contrastQuery.setRightUrl(rightQuery.getUrl());
        TableContrastVO tableContrastVO = contrastService.tableContrastToTable(contrastQuery);

        SqlProductionDTO sqlProductionDTO = new SqlProductionDTO();
        sqlProductionDTO.setLeftOrRight(0);
        sqlProductionDTO.setGoRemark(contrastQuery.getGoRemark());
        sqlProductionDTO.setTransformReg(CollectionUtil.newArrayList(DataTypeRegularEnum.values()));
        sqlProductionDTO.setTables(BeanUtil.copyToList(tableContrastVO.getColumnContrasts(), TableContrastDTO.class));
        sqlPackService.columnContrastPack(sqlProductionDTO);
        List<String> strings = sqlPackService.columnContrastPack(sqlProductionDTO);
        System.out.println();
    }

    @Test
    public void dbPackTest() {
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
        contrastQuery.setGoRemark(true);
        contrastQuery.setGoDeep(DbShopConstant.RULE_YES);
        contrastQuery.setLeftTableName("t_test");
        contrastQuery.setRightTableName("t_test");

        contrastQuery.setLeftDbName(leftQuery.getDbName());
        contrastQuery.setRightDbName(rightQuery.getDbName());

        contrastQuery.setLeftUrl(leftQuery.getUrl());
        contrastQuery.setRightUrl(rightQuery.getUrl());
        List<DbTableContrastVO> dbTableContrastVOS = contrastService.dbTableContrast(contrastQuery);

        SqlProductionDTO sqlProductionDTO = new SqlProductionDTO();
        sqlProductionDTO.setLeftOrRight(0);
        sqlProductionDTO.setGoRemark(contrastQuery.getGoRemark());
        sqlProductionDTO.setTransformReg(CollectionUtil.newArrayList(DataTypeRegularEnum.values()));
        sqlProductionDTO.setTables(BeanUtil.copyToList(dbTableContrastVOS, TableContrastDTO.class));
        sqlPackService.tableContrastPack(sqlProductionDTO);
    }
}

