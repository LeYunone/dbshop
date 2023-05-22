package com.leyunone.dbshop.service;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.vo.ColumnContrastVO;
import com.leyunone.dbshop.constant.DbShopConstant;
import com.leyunone.dbshop.system.factory.DBDataFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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

    public void columnContrastToTable(ContrastQuery contrastQuery) {
        String leftStrategy = contrastQuery.getLeftStrategy();

        //左表数据
        DatabaseMetaData leftData = dataFactory.getData(leftStrategy);
        List<ColumnInfo> leftColumn = packInfoService.getColumns(leftData, contrastQuery.getDbName(), contrastQuery.getTableName());

        //右表数据
        DatabaseMetaData rightData = dataFactory.getData(contrastQuery.getRightStrategy());
        List<ColumnInfo> rightColumn = packInfoService.getColumns(rightData, contrastQuery.getDbName(), contrastQuery.getTableName());
    }

    public List<ColumnContrastVO> columnContrastdoing(List<ColumnInfo> left, List<ColumnInfo> right) {
        List<ColumnContrastVO> result = new ArrayList<>();
        if (CollectionUtil.isEmpty(right)) {
            //对比表不存在
        }
        Map<String, ColumnInfo> rightMap = right.stream().collect(Collectors.toMap(ColumnInfo::getColumnName, Function.identity()));
        //比较相同字段名 并且填充空白
        for (ColumnInfo lc : left) {
            ColumnContrastVO columnContrastVO = new ColumnContrastVO();
            columnContrastVO.setLeftColumn(lc);
            if (rightMap.containsKey(lc.getColumnName())) {
                columnContrastVO.setRightColumn(lc);
                columnContrastVO.setNameDifferent(DbShopConstant.SAME);
                rightMap.remove(lc.getColumnName());
            } else {
                columnContrastVO.setNameDifferent(DbShopConstant.DIFFERENT);
            }
            result.add(columnContrastVO);
        }
        if (CollectionUtil.isNotEmpty(rightMap)) {
            rightMap.values().forEach((t) -> {
                ColumnContrastVO columnContrastVO = new ColumnContrastVO();
                columnContrastVO.setRightColumn(t);
                columnContrastVO.setNameDifferent(DbShopConstant.DIFFERENT);
                result.add(columnContrastVO);
            });
        }
        //比较相同字段名下的字段类型 - size和type和remark
        if (CollectionUtil.isEmpty(result)) return result;
        for (ColumnContrastVO contrast : result) {
            if (contrast.getNameDifferent().equals(DbShopConstant.SAME)) {
                
            }
        }
        return result;
    }
}
