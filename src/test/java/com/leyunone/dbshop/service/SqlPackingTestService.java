package com.leyunone.dbshop.service;

import cn.hutool.core.bean.BeanUtil;
import com.leyunone.dbshop.bean.dto.SqlProductionDTO;
import com.leyunone.dbshop.bean.dto.TableColumnContrastDTO;
import com.leyunone.dbshop.bean.info.DbInfo;
import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.query.DBQuery;
import com.leyunone.dbshop.bean.vo.TableColumnContrastVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * :)
 *
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
        List<TableColumnContrastVO> tableColumnContrastVOS = contrastService.columnContrastToTable(contrastQuery);

        SqlProductionDTO sqlProductionDTO = new SqlProductionDTO();
        sqlProductionDTO.setLeftOrRight(1);
        sqlProductionDTO.setGoRemark(contrastQuery.getGoRemark());
        sqlProductionDTO.setDateTimeBecome0(1);
        sqlProductionDTO.setColumns(BeanUtil.copyToList(tableColumnContrastVOS, TableColumnContrastDTO.class));
        sqlPackService.columnContrastPack(sqlProductionDTO);
    }
}
