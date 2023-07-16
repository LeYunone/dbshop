package com.leyunone.dbshop.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.bean.DataResponse;
import com.leyunone.dbshop.bean.dto.SqlProductionDTO;
import com.leyunone.dbshop.bean.dto.TableColumnContrastDTO;
import com.leyunone.dbshop.bean.vo.TableColumnContrastVO;
import com.leyunone.dbshop.service.ContrastService;
import com.leyunone.dbshop.service.SqlPackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * :)
 * sql语句生成器
 *
 * @Author LeYunone
 * @Date 2023/6/7 15:49
 */
@RestController
@RequestMapping("/sql/production")
public class SqlProductionController {

    @Autowired
    private SqlPackService sqlPackService;
    @Autowired
    private ContrastService contrastService;

    /**
     * 表字段 级别 生成
     *
     * @return
     */
    @PostMapping("/column")
    public DataResponse<?> contrastColumnSql(SqlProductionDTO sqlProductionDTO) {
        if (ObjectUtil.isNull(sqlProductionDTO.getColumns())) {
            List<TableColumnContrastVO> tableColumnContrastVOS = contrastService.columnContrastToTable(sqlProductionDTO.getContrastQuery());
            if (CollectionUtil.isNotEmpty(tableColumnContrastVOS)) {
                sqlProductionDTO.setColumns(JSONObject.parseArray(JSONObject.toJSONString(tableColumnContrastVOS), TableColumnContrastDTO.class));
            }
        }
        sqlPackService.columnContrastPack(sqlProductionDTO);
        return DataResponse.of();
    }

    /**
     * 数据库表 级别 生成
     *
     * @return
     */
    public DataResponse<?> contrastDbSql() {
        return DataResponse.of();
    }
}

