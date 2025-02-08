package com.leyunone.dbshop.service.core.impl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.bean.ResponseCode;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.TableDetailInfo;
import com.leyunone.dbshop.bean.query.DbQuery;
import com.leyunone.dbshop.bean.vo.ColumnInfoVO;
import com.leyunone.dbshop.bean.vo.TableInfoVO;
import com.leyunone.dbshop.service.core.DBQueryService;
import com.leyunone.dbshop.system.factory.DbDataFactory;
import com.leyunone.dbshop.util.AssertUtil;
import com.leyunone.dbshop.util.DbStrategyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-16
 */
@Service
public class DBQueryServiceImpl implements DBQueryService {

    @Autowired
    private DbDataFactory dbDataFactory;

    /**
     * @param query
     */
    @Override
    public List<TableInfoVO> getTableInfos(DbQuery query) {
        List<TableDetailInfo> tableData = dbDataFactory.getTableData(DbStrategyUtil.getDetailStrategy(query));
        AssertUtil.isFalse(CollectionUtils.isEmpty(tableData), ResponseCode.INFO_NOT_FOUND);
        List<TableInfoVO> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(tableData)) {
            tableData.forEach((tableInfo -> {
                TableInfoVO tableInfoVO = new TableInfoVO();
                BeanUtils.copyProperties(tableInfo, tableInfoVO);
                query.setTableName(tableInfo.getTableName());
                List<ColumnInfo> columnData = dbDataFactory.getColumnData(DbStrategyUtil.getTableStrategy(query));
                if (!CollectionUtils.isEmpty(columnData)) {
                    List<ColumnInfoVO> columns = JSONObject.parseArray(JSONObject.toJSONString(columnData), ColumnInfoVO.class);
                    tableInfoVO.setColumns(columns);
                }
                result.add(tableInfoVO);
            }));
        }
        return result;
    }

    @Override
    public List<ColumnInfoVO> getColumnInfos(DbQuery query) {
        List<ColumnInfo> columnData = dbDataFactory.getColumnData(DbStrategyUtil.getTableStrategy(query));
        List<ColumnInfoVO> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(columnData)) {
            result.addAll(JSONObject.parseArray(JSONObject.toJSONString(columnData), ColumnInfoVO.class));
        }
        return result;
    }

}
