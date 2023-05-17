package com.leyunone.dbshop.service;

import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.system.factory.DBDataFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.util.List;
import java.util.Queue;

/**
 * 对比服务
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-17
 */
@Service
@RequiredArgsConstructor
public class ContrastService {

    private final DBDataFactory dataFactory;
    private final PackInfoService packInfoService;

    public void columnContrastToTable(ContrastQuery contrastQuery) {
        String leftStrategy = contrastQuery.getLeftStrategy();

        //左表数据
        DatabaseMetaData leftData = dataFactory.getData(leftStrategy);
        List<ColumnInfo> leftColumn = packInfoService.getColumns(leftData, contrastQuery.getDbName(), contrastQuery.getTableName());

        //右表数据
        DatabaseMetaData rightData = dataFactory.getData(contrastQuery.getRightStrategy());
        List<ColumnInfo> rightColumn = packInfoService.getColumns(rightData, contrastQuery.getDbName(), contrastQuery.getTableName());
    }
 
    public void columnContrastdoing(List<ColumnInfo> left,List<ColumnInfo> right){
    }
}
