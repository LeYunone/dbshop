package com.leyunone.dbshop.controller;

import com.leyunone.dbshop.bean.DataResponse;
import com.leyunone.dbshop.bean.dto.SqlProductionDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * :)
 * sql语句生成器
 * @Author LeYunone
 * @Date 2023/6/7 15:49
 */
@RestController
@RequestMapping("/sql/production")
public class SqlProductionController {

    /**
     * 表字段 级别 生成
     * @return
     */
    public DataResponse<?> contrastColumnSql(SqlProductionDTO sqlProductionDTO) {
        return DataResponse.of();
    }

    /**
     * 数据库表 级别 生成
     * @return
     */
    public DataResponse<?> contrastDbSql() {
        return DataResponse.of();
    }
}

