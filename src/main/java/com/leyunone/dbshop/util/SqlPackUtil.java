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
                sql = modifyColumnPacking(modelEnum,info.getTableName(),info.getColumnName(),info.getTypeName(),info.getColumnSize(),info.getRemarks());
                break;
            case ADD_COLUMN:
                sql = addColumnPcacking(modelEnum,info.getTableName(),info.getColumnName(),info.getTypeName(),info.getColumnSize(),info.getRemarks());
                break;
            case DELETE_COLUMN:
                sql = deleteColumnPacking(modelEnum,info.getTableName(),info.getColumnName());
                break;
            case CREATE_TABLE:
                break;
            case CREATE_TABLE_COLUMN:
                break;
            default:
        }
        return sql;
    }

    private static String modifyColumnPacking(SqlModelEnum modelEnum, String tableName, String columnName, String typeName, String size, String remark) {
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),new String [] {tableName,columnName,typeName,size,remark});
    }
    
    private static String addColumnPcacking(SqlModelEnum modelEnum,String tableName,String columnName,String typeName, String size, String remark){
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),new String [] {tableName,columnName,typeName,size,remark});
    }
    
    private static String deleteColumnPacking(SqlModelEnum modelEnum,String tableName,String columnName){
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),new String [] {tableName,columnName});
    }
    
    private static String createTableInColumnPacking(SqlModelEnum modelEnum,List<>)
}
