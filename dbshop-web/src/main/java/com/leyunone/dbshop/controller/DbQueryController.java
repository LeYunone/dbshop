package com.leyunone.dbshop.controller;

import com.leyunone.dbshop.bean.DataResponse;
import com.leyunone.dbshop.bean.query.DbQuery;
import com.leyunone.dbshop.bean.vo.ColumnInfoVO;
import com.leyunone.dbshop.bean.vo.TableInfoVO;
import com.leyunone.dbshop.service.core.impl.DBQueryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
    private DBQueryServiceImpl dbQueryService;

    /**
     * 一个指定数据库库的信息
     */
    @GetMapping("/tables")
    public DataResponse<List<TableInfoVO>> dbTables(DbQuery query) {
        List<TableInfoVO> tableInfos = dbQueryService.getTableInfos(query);
        //FIXME 后台做tree树结构封装
        if (!CollectionUtils.isEmpty(tableInfos)) {
            tableInfos.forEach((table -> {
                table.setLabel(table.getTableName());
                if (!CollectionUtils.isEmpty(table.getColumns())) {
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
    public DataResponse<List<ColumnInfoVO>> dbColumns(DbQuery query) {
        List<ColumnInfoVO> columnInfos = dbQueryService.getColumnInfos(query);
        return DataResponse.of(columnInfos);
    }
}
