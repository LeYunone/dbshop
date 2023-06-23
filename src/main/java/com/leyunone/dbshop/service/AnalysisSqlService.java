package com.leyunone.dbshop.service;

import com.leyunone.dbshop.bean.dto.SqlProductionDTO;
import com.leyunone.dbshop.bean.dto.TableColumnContrastDTO;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.constant.DbShopConstant;
import com.leyunone.dbshop.enums.SqlModelEnum;
import com.leyunone.dbshop.util.SqlPackUtil;
import com.leyunone.dbshop.util.TextFillUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * :)
 *  解析sql的服务
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-23
 */
@Service
public class AnalysisSqlService {


    /**
     * 表字段修改分析
     * @param columnContrastDTO
     * @return
     */
    public List<String> modifyColumnSql(TableColumnContrastDTO columnContrastDTO, SqlProductionDTO sqlProductionDTO){
        List<String> resultSql = new ArrayList<>();
        //0 左表主 1 右表主
        ColumnInfo mainColumn = sqlProductionDTO.getLeftOrRight().equals(0) ? columnContrastDTO.getLeftColumn() : columnContrastDTO.getRightColumn();
        /**
         * 字段名相同 猜疑是修改
         * 基础属性不同 
         * 规则级不同 : goRemark = 1
         */
        if (columnContrastDTO.getSizeDifferent()
                || columnContrastDTO.getTypeDifferent()
                || (DbShopConstant.Rule_Yes.equals(sqlProductionDTO.getGoRemark()) && columnContrastDTO.getRemarkDifferent())) {
            resultSql.add(SqlPackUtil.packing(SqlModelEnum.MODIFY_COLUMN, mainColumn));
        }
        if(columnContrastDTO.getAutoincrementDifferent()){
            //整形自增
            if(mainColumn.getAutoincrement()){
                //主表为自增 新增自增 
                resultSql.add(SqlPackUtil.packing(SqlModelEnum.ADD_AUTOINCREMENT,mainColumn));
            }else{
                //主表无自增 删除自增
                resultSql.add(SqlPackUtil.packing(SqlModelEnum.DELETE_AUTOINCREMENT,mainColumn));
            }
        }
        if(columnContrastDTO.getPrimaryKeyDifferent()){
            //主表字段为主键
            if(mainColumn.getPrimaryKey()){
                //主表为主键 设置主键
                resultSql.add(SqlPackUtil.packing(SqlModelEnum.ADD_PRIMARY_KEY,mainColumn));
            }else {
                //主表无主键 删除
                resultSql.add(SqlPackUtil.packing(SqlModelEnum.DELETE_PRIMARY_KEY,mainColumn));
            }
        }
        return resultSql;
    }
}
