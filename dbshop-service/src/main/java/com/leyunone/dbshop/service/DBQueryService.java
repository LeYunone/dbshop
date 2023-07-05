package com.leyunone.dbshop.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.bean.ResponseCode;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.ColumnInfoVO;
import com.leyunone.dbshop.bean.info.TableInfo;
import com.leyunone.dbshop.bean.query.DBQuery;
import com.leyunone.dbshop.bean.vo.TableInfoVO;
import com.leyunone.dbshop.system.factory.DBDataFactory;
import com.leyunone.dbshop.util.AssertUtil;
import com.leyunone.dbshop.util.DbStrategyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-16
 */
@Service
public class DBQueryService {

    @Autowired
    private DBDataFactory dbDataFactory;

    /**
     * 
     * @param query
     */
    public List<TableInfoVO> getTableInfos(DBQuery query) {
        List<TableInfo> tableData = dbDataFactory.getTableData(DbStrategyUtil.getTableStrategy(query));
        AssertUtil.isFalse(CollectionUtil.isEmpty(tableData), ResponseCode.INFO_NOT_FOUND);
        List<TableInfoVO> result = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(tableData)){
            tableData.forEach((tableInfo -> {
                TableInfoVO tableInfoVO = new TableInfoVO();
                BeanUtil.copyProperties(tableInfo,tableInfoVO);
                query.setTableName(tableInfo.getTableName());
                List<ColumnInfo> columnData = dbDataFactory.getColumnData(DbStrategyUtil.getColumnStrategy(query));
                if(CollectionUtil.isNotEmpty(columnData)){
                    List<ColumnInfoVO> columns = JSONObject.parseArray(JSONObject.toJSONString(columnData), ColumnInfoVO.class);
                    tableInfoVO.setColumns(columns);
                }
                result.add(tableInfoVO);
            }));
        }
        return result;
    }
    
    public List<ColumnInfoVO> getColumnInfos(DBQuery query){
        List<ColumnInfo> columnData = dbDataFactory.getColumnData(DbStrategyUtil.getColumnStrategy(query));
        List<ColumnInfoVO> result = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(columnData)){
            result.addAll(JSONObject.parseArray(JSONObject.toJSONString(columnData),ColumnInfoVO.class));
        }
        return result;
    }
    
}
