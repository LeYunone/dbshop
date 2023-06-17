package com.leyunone.dbshop.util;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.TableInfo;
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
     *
     * @param modelEnum
     * @param info
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
            case CREATE_TABLE_COLUMN:
                sql = createTableInColumnPacking(modelEnum, (ColumnInfo) info);
                break;
            default:
        }
        return sql;
    }

    public static String packing(SqlModelEnum modelEnum, Object info) {
        return packing(modelEnum, null, info);
    }

    private static String modifyColumnPacking(SqlModelEnum modelEnum, ColumnInfo columnInfo) {
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),
                columnInfo.getTableName(), columnInfo.getColumnName(), columnInfo.getTypeName(), columnInfo.getColumnSize(), columnInfo.getRemarks());
    }

    private static String addColumnPcacking(SqlModelEnum modelEnum, ColumnInfo columnInfo) {
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),
                columnInfo.getTableName(), columnInfo.getColumnName(), columnInfo.getTypeName(), columnInfo.getColumnSize(), columnInfo.getRemarks());
    }

    private static String deleteColumnPacking(SqlModelEnum modelEnum, ColumnInfo columnInfo) {
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),
                columnInfo.getTableName(), columnInfo.getColumnName());
    }

    private static String createTableInfo(SqlModelEnum modelEnum, TableInfo tableInfo, List<ColumnInfo> columnInfos) {
        List<String> sqls = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(columnInfos)) {
            columnInfos.forEach((t) -> sqls.add(createTableInColumnPacking(SqlModelEnum.CREATE_TABLE_COLUMN, t)));
        }
        String primarykeys = primaryKeyPacking(SqlModelEnum.PRIMARY_KEY, tableInfo, columnInfos);
        String columnSqls = CollectionUtil.join(sqls, "\n");
        if(StringUtils.isBlank(primarykeys)){
            //TODO 22.6.17版本 建表语句中字段后只涉及主键
            //删除字段语句中的最后一个逗号
            if(StringUtils.isNotBlank(columnSqls)){
                columnSqls = columnSqls.substring(0,columnSqls.length()-1);
            }
        }
        return TextFillUtil.fillStr(modelEnum.getSqlModel(), tableInfo.getTableName(),columnSqls,primarykeys);
    }

    private static String createTableInColumnPacking(SqlModelEnum modelEnum, ColumnInfo columnInfo) {
        return TextFillUtil.fillStr(modelEnum.getSqlModel(),
                columnInfo.getColumnName(), columnInfo.getTypeName(), columnInfo.getColumnSize(), columnInfo.getRemarks());
    }

    private static String primaryKeyPacking(SqlModelEnum modelEnum, TableInfo tableInfo, List<ColumnInfo> columnInfos) {
        //TODO 主键sql
        Set<String> primarys = tableInfo.getPrimarys();
        if (CollectionUtil.isEmpty(primarys) || CollectionUtil.isEmpty(columnInfos)) return "";
        List<String> primaryNames = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(primarys)) {
            //匹配主键
            columnInfos.forEach((columnInfo) -> {
                if(primarys.contains(columnInfo.getColumnName())){
                    primaryNames.add("`"+columnInfo.getColumnName()+"`");
                }
            });
        }
        //阈值保护
        if(CollectionUtil.isEmpty(primaryNames)) return "";
        return TextFillUtil.fillStr(modelEnum.getSqlModel(), CollectionUtil.join(primaryNames,","));
    }
}
