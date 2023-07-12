package com.leyunone.dbshop.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.dbshop.bean.DataResponse;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.ColumnInfoVO;
import com.leyunone.dbshop.bean.info.TableInfo;
import com.leyunone.dbshop.bean.query.DBQuery;
import com.leyunone.dbshop.bean.vo.TableInfoVO;
import com.leyunone.dbshop.service.DBQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 查询db
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-09
 */
@RestController
@RequestMapping("/dbquery")
public class DbQueryController {

    @Autowired
    private DBQueryService dbQueryService;

    /**
     * 一个指定数据库库的信息
     */
    @GetMapping("/tables")
    public DataResponse<List<TableInfoVO>> dbTables(DBQuery query) {
        List<TableInfoVO> tableInfos = dbQueryService.getTableInfos(query);
        //FIXME 后台做tree树结构封装
        if (CollectionUtil.isNotEmpty(tableInfos)) {
            tableInfos.forEach((table -> {
                table.setLabel(table.getTableName());
                if (CollectionUtil.isNotEmpty(table.getColumns())) {
                    table.getColumns().forEach((column) -> column.setLabel(column.getColumnName()));
                }
            }));
        }
        return DataResponse.of(tableInfos);
    }


    /**
     * 字段
     *
     * @param query
     * @return
     */
    @GetMapping("/columns")
    public DataResponse<List<ColumnInfoVO>> dbColumns(DBQuery query) {
        List<ColumnInfoVO> columnInfos = dbQueryService.getColumnInfos(query);
        return DataResponse.of(columnInfos);
    }
}
