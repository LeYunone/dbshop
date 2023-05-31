package com.leyunone.dbshop.service;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.TableInfo;
import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.vo.DbTableContrastVO;
import com.leyunone.dbshop.bean.vo.TableColumnContrastVO;
import com.leyunone.dbshop.constant.DbShopConstant;
import com.leyunone.dbshop.system.factory.DBDataFactory;
import com.leyunone.dbshop.util.DbStrategyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public List<TableColumnContrastVO> columnContrastToTable(ContrastQuery contrastQuery) {
        //左表数据
        List<ColumnInfo> leftColumn = dataFactory.getColumnData(DbStrategyUtil.getLeftStrategy(contrastQuery));

        //右表数据
        List<ColumnInfo> rightColumn = dataFactory.getColumnData(DbStrategyUtil.getRightStrategy(contrastQuery));

        return this.columnContrastdoing(leftColumn, rightColumn);
    }

    /**
     * 两个数据库对比结果集
     *
     * @param contrastQuery
     */
    public void dbTableContrast(ContrastQuery contrastQuery) {
        List<TableInfo> leftTables = dataFactory.getTableData(DbStrategyUtil.getLeftStrategy(contrastQuery));

        List<TableInfo> rightTables = dataFactory.getTableData(DbStrategyUtil.getRightStrategy(contrastQuery));
    }

    /**
     * 对比左右表字段
     *
     * @param left
     * @param right
     * @return
     */
    private List<TableColumnContrastVO> columnContrastdoing(List<ColumnInfo> left, List<ColumnInfo> right) {
        List<TableColumnContrastVO> result = new ArrayList<>();
        if (CollectionUtil.isEmpty(right)) {
            //对比表不存在
        }
        Map<String, ColumnInfo> rightMap = right.stream().collect(Collectors.toMap(ColumnInfo::getColumnName, Function.identity()));
        //比较相同字段名 并且填充空白
        for (ColumnInfo lc : left) {
            TableColumnContrastVO tableColumnContrastVO = new TableColumnContrastVO();
            tableColumnContrastVO.setLeftColumn(lc);
            if (rightMap.containsKey(lc.getColumnName())) {
                tableColumnContrastVO.setRightColumn(lc);
                tableColumnContrastVO.setNameDifferent(DbShopConstant.SAME);
                rightMap.remove(lc.getColumnName());
            } else {
                tableColumnContrastVO.setNameDifferent(DbShopConstant.DIFFERENT);
            }
            result.add(tableColumnContrastVO);
        }
        if (CollectionUtil.isNotEmpty(rightMap)) {
            rightMap.values().forEach((t) -> {
                TableColumnContrastVO tableColumnContrastVO = new TableColumnContrastVO();
                tableColumnContrastVO.setRightColumn(t);
                tableColumnContrastVO.setNameDifferent(DbShopConstant.DIFFERENT);
                result.add(tableColumnContrastVO);
            });
        }
        //比较相同字段名下的字段类型 - size和type和remark
        if (CollectionUtil.isEmpty(result)) return result;
        for (TableColumnContrastVO contrast : result) {
            if (contrast.getNameDifferent()) {
                ColumnInfo leftColumn = contrast.getLeftColumn();
                ColumnInfo rightColumn = contrast.getRightColumn();
                //比较size
                contrast.setSizeDifferent(leftColumn.getColumnSize().equals(rightColumn.getColumnSize()));
                //比较type
                contrast.setTypeDifferent(leftColumn.getDataType().equals(rightColumn.getDataType()));
                //比较remark
                contrast.setRemarkDifferent(leftColumn.getRemarks().equals(rightColumn.getRemarks()));
            }
        }
        return result;
    }

    /**
     * 对比左右表名
     *
     * @param left
     * @param right
     */
    private void tableContrastdoing(List<TableInfo> left, List<TableInfo> right) {
        List<DbTableContrastVO> result = new ArrayList<>();
        if(CollectionUtil.isEmpty(right)){
            //对比数据库中没有表存在
        }
//        Map<String, TableInfo> rightMap = right.stream().collect(Collectors.toMap(TableInfo::getTableName, Function.identity()));
//        for (TableInfo lt : left) {
//            DbTableContrastVO dbTableContrastVO = new DbTableContrastVO();
//            if(rightMap)
//        }
        //拿到表所有字段关系
         
    }
}