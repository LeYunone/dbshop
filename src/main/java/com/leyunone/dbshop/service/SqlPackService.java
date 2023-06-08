package com.leyunone.dbshop.service;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.dbshop.bean.dto.SqlProductionDTO;
import com.leyunone.dbshop.bean.dto.TableColumnContrastDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * :)
 * sql语句组装
 * @Author LeYunone
 * @Date 2023/6/7 16:13
 */
@Service
public class SqlPackService {
    
    /**
     * 列字段比对结果 组装sql
     * @param sqlProductionDTO
     */
    public void columnContrastPack(SqlProductionDTO sqlProductionDTO) {
        List<TableColumnContrastDTO> columns = sqlProductionDTO.getColumns();
        if(CollectionUtil.isEmpty(columns)) return;
        
        List<String> resultSql = new ArrayList<>();
        
        for(TableColumnContrastDTO columnContrastDTO : columns){
                
        }
    }
}
