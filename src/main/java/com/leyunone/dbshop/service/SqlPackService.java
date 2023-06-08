package com.leyunone.dbshop.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.dbshop.bean.dto.SqlProductionDTO;
import com.leyunone.dbshop.bean.dto.TableColumnContrastDTO;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.enums.SqlModelEnum;
import com.leyunone.dbshop.util.SqlPackUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * :)
 * sql语句组装
 *
 * @Author LeYunone
 * @Date 2023/6/7 16:13
 */
@Service
public class SqlPackService {

    /**
     * 列字段比对结果 组装sql
     *
     * @param sqlProductionDTO
     */
    public void columnContrastPack(SqlProductionDTO sqlProductionDTO) {
        List<TableColumnContrastDTO> columns = sqlProductionDTO.getColumns();
        if (CollectionUtil.isEmpty(columns) || ObjectUtil.isNull(sqlProductionDTO.getLeftOrRight())) return;

        List<String> resultSql = new ArrayList<>();

        for (TableColumnContrastDTO columnContrastDTO : columns) {
            ColumnInfo mainColumn = sqlProductionDTO.getLeftOrRight().equals(0) ? columnContrastDTO.getLeftColumn() : columnContrastDTO.getRightColumn();
            if (!columnContrastDTO.getNameDifferent()) {
                //字段名相同 猜疑是修改
                //0 左表主 1 右表主
                //如果表字段有不同
                if(columnContrastDTO.getSizeDifferent() 
                        || columnContrastDTO.getRemarkDifferent() 
                        || Integer.valueOf(1).equals(sqlProductionDTO.getGoRemark())){
                    SqlPackUtil.packing(SqlModelEnum.MODIFY_COLUMN,mainColumn);
                }
            } else {
                //字段名不同 一定是新增字段
                
            }

        }
    }
}
