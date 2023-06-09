package com.leyunone.dbshop.util;

import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.enums.SqlModelEnum;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-08
 */
public class SqlPackUtil {
    
    public static String packing(SqlModelEnum modelEnum, ColumnInfo info){
        String sql = "";
        switch (modelEnum){
            case MODIFY_COLUMN:
                sql = modifyPacking(modelEnum,info.getTableName(),info.getColumnName(),info.getDataType(),info.getColumnSize(),info.getRemarks());
                break;
            case ADD_COLUMN:
                sql = addColumnPcacking(modelEnum,info.getTableName(),info.getColumnName(),info.getDataType(),info.getColumnSize(),info.getRemarks());
            default:
        }
        return sql;
    }

    private static String modifyPacking(SqlModelEnum modelEnum, String tableName, String columnName, String dataType, String size, String remark) {
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),new String [] {tableName,columnName,dataType,size,remark});
    }
    
    public static String addColumnPcacking(SqlModelEnum modelEnum,String tableName,String columnName,String dataType, String size, String remark){
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),new String [] {tableName,columnName,dataType,size,remark});
    }
}
