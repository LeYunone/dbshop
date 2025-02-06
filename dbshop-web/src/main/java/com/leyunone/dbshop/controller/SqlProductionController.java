package com.leyunone.dbshop.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.bean.DataResponse;
import com.leyunone.dbshop.bean.dto.SqlProductionDTO;
import com.leyunone.dbshop.bean.dto.TableContrastDTO;
import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.vo.TableColumnContrastVO;
import com.leyunone.dbshop.service.core.impl.ContrastServiceImpl;
import com.leyunone.dbshop.service.core.impl.SqlPackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private SqlPackServiceImpl sqlPackService;
    @Autowired
    private ContrastServiceImpl contrastService;

    /**
     * 表字段 级别 生成
     *
     * @return
     */
    @PostMapping("/column")
    public DataResponse<?> contrastColumnSql(@RequestBody SqlProductionDTO sqlProductionDTO) {
        //对比与解析的共通规则赋予
        ContrastQuery contrastQuery = sqlProductionDTO.getContrastQuery();
        if (ObjectUtil.isNotNull(contrastQuery)) {
            contrastQuery.setGoRemark(ObjectUtil.isNotNull(sqlProductionDTO.getGoRemark())?sqlProductionDTO.getGoRemark():contrastQuery.getGoRemark());
        }

        if (CollectionUtil.isEmpty(sqlProductionDTO.getTables())) {
            List<TableColumnContrastVO> tableColumnContrastVOS = contrastService.tableContrastToTable(sqlProductionDTO.getContrastQuery()).getColumnContrasts();
            if (CollectionUtil.isNotEmpty(tableColumnContrastVOS)) {
                sqlProductionDTO.setTables(JSONObject.parseArray(JSONObject.toJSONString(tableColumnContrastVOS), TableContrastDTO.class));
            }
        }
        List<String> sqls = sqlPackService.columnContrastPack(sqlProductionDTO);
        return DataResponse.of(sqls);
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

