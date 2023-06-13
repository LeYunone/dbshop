package com.leyunone.dbshop.service;

import cn.hutool.core.bean.BeanUtil;
import com.leyunone.dbshop.bean.dto.DbTableContrastDTO;
import com.leyunone.dbshop.bean.dto.SqlProductionDTO;
import com.leyunone.dbshop.bean.dto.TableColumnContrastDTO;
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
 * :)
 *  &nullCatalogMeansCurrent=true
 * @Author LeYunone
 * @Date 2023/6/9 9:36
 */
@SpringBootTest
public class SqlPackingTestService {

    @Autowired
    private SqlPackService sqlPackService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private ContrastService contrastService;

    @Test
    public void columnPackTest() {
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

        contrastQuery.setLeftDbName(leftQuery.getDbName());
        contrastQuery.setRightDbName(rightQuery.getDbName());

        contrastQuery.setLeftUrl(leftQuery.getUrl());
        contrastQuery.setRightUrl(rightQuery.getUrl());
        List<TableColumnContrastVO> tableColumnContrastVOS = contrastService.columnContrastToTable(contrastQuery);

        SqlProductionDTO sqlProductionDTO = new SqlProductionDTO();
        sqlProductionDTO.setLeftOrRight(0);
        sqlProductionDTO.setGoRemark(contrastQuery.getGoRemark());
        sqlProductionDTO.setDateTimeBecome0(1);
        sqlProductionDTO.setColumns(BeanUtil.copyToList(tableColumnContrastVOS, TableColumnContrastDTO.class));
        sqlPackService.columnContrastPack(sqlProductionDTO);
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
        contrastQuery.setGoRemark(1);
        contrastQuery.setLeftTablName("t_test");
        contrastQuery.setRightTableName("t_test");

        contrastQuery.setLeftDbName(leftQuery.getDbName());
        contrastQuery.setRightDbName(rightQuery.getDbName());

        contrastQuery.setLeftUrl(leftQuery.getUrl());
        contrastQuery.setRightUrl(rightQuery.getUrl());
        List<DbTableContrastVO> dbTableContrastVOS = contrastService.dbTableContrast(contrastQuery);

        SqlProductionDTO sqlProductionDTO = new SqlProductionDTO();
        sqlProductionDTO.setLeftOrRight(1);
        sqlProductionDTO.setGoRemark(contrastQuery.getGoRemark());
        sqlProductionDTO.setDateTimeBecome0(1);
        sqlProductionDTO.setDbs(BeanUtil.copyToList(dbTableContrastVOS, DbTableContrastDTO.class));
        sqlPackService.tableContrastPack(sqlProductionDTO);
    }
}
