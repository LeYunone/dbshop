package com.leyunone.dbshop.util;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.TableInfo;
import com.leyunone.dbshop.enums.SqlAssembleEnum;
import com.leyunone.dbshop.enums.SqlModelEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-08
 */
public class SqlPackUtil {

    /**
     * 由包装工具决定入参类型
     *   MODIFY_COLUMN = MODIFY_COLUMN
     *   ADD_COLUMN = ADD_COLUMN
     *   DELETE_COLUMN = DELETE_COLUMN
     *   
     *                    PRIMARY_KEY
     *  CREATE_TABLE => = CREATE_TABLE_COLUMN
     *  
     *  CREATE_TABLE_COLUMN = ↑
     * @param modelEnum 模板枚举
     * @param tableInfo 表信息
     * @param info 字段信息 可能是集合
     * @return
     */
    public static String packing(SqlModelEnum modelEnum, TableInfo tableInfo, Object info) {
        String sql = "";
        switch (modelEnum) {
            case MODIFY_COLUMN:
                sql = modifyColumnPacking(modelEnum, (ColumnInfo) info);
                break;
            case ADD_COLUMN:
                sql = addColumnPcacking(modelEnum, (ColumnInfo) info);
                break;
            case DELETE_COLUMN:
                sql = deleteColumnPacking(modelEnum, (ColumnInfo) info);
                break;
            case CREATE_TABLE:
                sql = createTableInfo(modelEnum, tableInfo, (List<ColumnInfo>) info);
                break;
            case DROP_TABLE:
                sql = dropTablePacking(modelEnum,tableInfo);
                break;
            case ADD_AUTOINCREMENT:
                sql = addAutoincrementPacking(modelEnum, (ColumnInfo) info);
                break;
            case  ADD_PRIMARY_KEY:
                sql = addPrimaryKeyPacking(modelEnum, (ColumnInfo) info);
                break;
            case DELETE_AUTOINCREMENT:
                sql = deleteAutoincrementPacking(modelEnum, (ColumnInfo) info);
                break;
            case DELETE_PRIMARY_KEY:
                sql = deletePrimaryKeyPacking(modelEnum, (ColumnInfo) info);
                break;
            default:
        }
        return sql;
    }

    public static String packing(SqlModelEnum modelEnum, Object info) {
        return packing(modelEnum, null, info);
    }

    //修改字段语句包装
    private static String modifyColumnPacking(SqlModelEnum modelEnum, ColumnInfo columnInfo) {
        StringBuilder sb = new StringBuilder("");
        if(columnInfo.getNullable()) {
            sb.append(SqlAssembleEnum.NULLABLE.getSql());
        }
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),
                columnInfo.getTableName(), columnInfo.getColumnName(), columnInfo.getTypeName(), columnInfo.getColumnSize(), sb.toString(), columnInfo.getRemarks());
    }

    //新增字段语句包装
    private static String addColumnPcacking(SqlModelEnum modelEnum, ColumnInfo columnInfo) {
        StringBuilder sb = new StringBuilder("");
        if(columnInfo.getNullable()) {
            sb.append(SqlAssembleEnum.NULLABLE.getSql());
        }
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),
                columnInfo.getTableName(), columnInfo.getColumnName(), columnInfo.getTypeName(), columnInfo.getColumnSize(), sb.toString(), columnInfo.getRemarks());
    }

    //删除字段语句包装
    private static String deleteColumnPacking(SqlModelEnum modelEnum, ColumnInfo columnInfo) {
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),
                columnInfo.getTableName(), columnInfo.getColumnName());
    }

    //新增表语句包装
    private static String createTableInfo(SqlModelEnum modelEnum, TableInfo tableInfo, List<ColumnInfo> columnInfos) {
        List<String> sqls = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(columnInfos)) {
            columnInfos.forEach((t) -> sqls.add(createTableInColumnPacking(SqlModelEnum.CREATE_TABLE_COLUMN, t)));
        }
        String primarykeys = createTableprimaryKeyPacking(tableInfo, columnInfos);
        String columnSqls = CollectionUtil.join(sqls, "\n");
        if (StringUtils.isBlank(primarykeys)) {
            //TODO 22.6.17版本 建表语句中字段后只涉及主键
            //删除字段语句中的最后一个逗号
            if (StringUtils.isNotBlank(columnSqls)) {
                columnSqls = columnSqls.substring(0, columnSqls.length() - 1);
            }
        }
        return TextFillUtil.fillStr(modelEnum.getSqlModel(), tableInfo.getTableName(), columnSqls, primarykeys);
    }

    //新增表中 字段语句的包装
    private static String createTableInColumnPacking(SqlModelEnum modelEnum, ColumnInfo columnInfo) {
        StringBuilder attr = new StringBuilder("");
        if(!columnInfo.getNullable()){
            attr.append(SqlAssembleEnum.NULLABLE.getSql());
        }else{
            attr.append(SqlAssembleEnum.NULL.getSql());
        }
        if (columnInfo.getAutoincrement()) {
            //主键自增
            attr.append(SqlAssembleEnum.CREATE_TABLE_AUTOINCREMENT.getSql());
        }
        
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),
                columnInfo.getColumnName(), columnInfo.getTypeName(), columnInfo.getColumnSize(), attr.toString(), columnInfo.getRemarks());
    }
    
    //新增表中 主键语句的包装
    private static String createTableprimaryKeyPacking(TableInfo tableInfo, List<ColumnInfo> columnInfos) {
        //TODO 主键sql
        Set<String> primarys = tableInfo.getPrimarys();
        if (CollectionUtil.isEmpty(primarys) || CollectionUtil.isEmpty(columnInfos)) return "";
        List<String> primaryNames = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(primarys)) {
            //匹配主键
            columnInfos.forEach((columnInfo) -> {
                if (primarys.contains(columnInfo.getColumnName())) {
                    primaryNames.add("`" + columnInfo.getColumnName() + "`");
                }
            });
        }
        //阈值保护
        if (CollectionUtil.isEmpty(primaryNames)) return "";
        return TextFillUtil.fillStr(SqlModelEnum.CREATE_TABLE_PRIMARY_KEY.getSqlModel(), CollectionUtil.join(primaryNames, ","));
    }
    
    //删除表语句包装
    private static String dropTablePacking(SqlModelEnum modelEnum,TableInfo tableInfo){
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),tableInfo.getTableName());
    }
    
    private static String addAutoincrementPacking(SqlModelEnum modelEnum,ColumnInfo columnInfo){
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),columnInfo.getTableName()
                ,columnInfo.getColumnName(),columnInfo.getColumnName(),columnInfo.getTypeName(),columnInfo.getColumnSize());
    }
    
    private static String addPrimaryKeyPacking(SqlModelEnum modelEnum,ColumnInfo columnInfo){
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),columnInfo.getTableName(),columnInfo.getColumnName());
    }
    
    private static String deleteAutoincrementPacking(SqlModelEnum modelEnum,ColumnInfo columnInfo){
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),columnInfo.getTableName()
                ,columnInfo.getColumnName(),columnInfo.getColumnName(),columnInfo.getTypeName(),columnInfo.getColumnSize());
    }
    
    private static String deletePrimaryKeyPacking(SqlModelEnum modelEnum,ColumnInfo columnInfo){
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),columnInfo.getTableName());
    }
    
    
}
