package com.leyunone.dbshop.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.dbshop.bean.dto.SqlProductionDTO;
import com.leyunone.dbshop.bean.dto.TableColumnContrastDTO;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.enums.SqlModelEnum;
import com.leyunone.dbshop.excutor.RulePointExcutor;
import com.leyunone.dbshop.system.factory.TransformRuleHandlerFactory;
import com.leyunone.dbshop.util.SqlPackUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RulePointExcutor rulePointExcutor;
    @Autowired
    private TransformRuleHandlerFactory factory;
    
    /**
     * 列字段比对结果 组装sql
     *
     * @param sqlProductionDTO
     */
    public List<String> columnContrastPack(SqlProductionDTO sqlProductionDTO) {
        List<TableColumnContrastDTO> columns = sqlProductionDTO.getColumns();
        if (CollectionUtil.isEmpty(columns) || ObjectUtil.isNull(sqlProductionDTO.getLeftOrRight())) return new ArrayList<>();

        List<String> resultSql = new ArrayList<>();

        for (TableColumnContrastDTO columnContrastDTO : columns) {
            ColumnInfo mainColumn = sqlProductionDTO.getLeftOrRight().equals(0) ? columnContrastDTO.getLeftColumn() : columnContrastDTO.getRightColumn();
            if (!columnContrastDTO.getNameDifferent()) {
                //字段名相同 猜疑是修改
                //0 左表主 1 右表主
                //如果表字段有不同
                if (columnContrastDTO.getSizeDifferent()
                        || columnContrastDTO.getRemarkDifferent()
                        || Integer.valueOf(1).equals(sqlProductionDTO.getGoRemark())) {
                    resultSql.add(SqlPackUtil.packing(SqlModelEnum.MODIFY_COLUMN, mainColumn));
                }
            } else {
                //字段名不同 新增或删除
                if(ObjectUtil.isNull(mainColumn)){
                    //主表不存在字段则删除
                    resultSql.add(SqlPackUtil.packing(SqlModelEnum.DELETE_COLUMN, mainColumn));
                }else{
                    //主表存在字段新增
                    resultSql.add(SqlPackUtil.packing(SqlModelEnum.ADD_COLUMN, mainColumn));
                }
            }
        }
        
        if(CollectionUtil.isNotEmpty(resultSql)){
            //sql转化规则
            //TODO 暂时指定策略工厂
            List<String> execute = rulePointExcutor.execute(factory, sqlProductionDTO.getTypeTransformRule());
            //二次处理结果集
        }
        return resultSql;
    }

    public void tableContrastPack(SqlProductionDTO sqlProductionDTO) {
        
    }
}
