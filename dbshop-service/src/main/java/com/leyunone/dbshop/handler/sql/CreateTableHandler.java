package com.leyunone.dbshop.handler.sql;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.annotate.SqlHandler;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.TableDetailInfo;
import com.leyunone.dbshop.enums.SqlAssembleEnum;
import com.leyunone.dbshop.enums.SqlModelEnum;
import com.leyunone.dbshop.system.factory.AbstractSqlProductionFactory;
import com.leyunone.dbshop.system.factory.SqlProductionHandlerFactory;
import com.leyunone.dbshop.util.SqlPackUtil;
import com.leyunone.dbshop.util.TextFillUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2023/9/12 15:24
 */
@SqlHandler(SqlModelEnum.CREATE_TABLE)
public class CreateTableHandler extends SqlProductionAbstractHandler{
    
    @Autowired
    private SqlProductionHandlerFactory sqlProductionHandlerFactory;
    
    @Override
    public String handler(JSONObject json) {
        List<ColumnInfo> columnInfos = SqlPackUtil.resoleJsonDatas(json, ColumnInfo.class);
        TableDetailInfo tableDetailInfo = SqlPackUtil.resoleJsonData(json, TableDetailInfo.class);
        List<String> sqls = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(columnInfos)) {
            columnInfos.forEach((t) -> sqls.add(createTableInColumnPacking(t)));
        }
        String primarykeys = createTableprimaryKeyPacking(tableDetailInfo, columnInfos);
        String columnSqls = CollectionUtil.join(sqls, "\n");
        if (StringUtils.isBlank(primarykeys)) {
            //TODO 22.6.17版本 建表语句中字段后只涉及主键
            //删除字段语句中的最后一个逗号
            if (StringUtils.isNotBlank(columnSqls)) {
                columnSqls = columnSqls.substring(0, columnSqls.length() - 1);
            }
        }
        return TextFillUtil.fillStr(SqlModelEnum.CREATE_TABLE.getSqlModel(), tableDetailInfo.getTableName(), columnSqls, primarykeys);
    }

    @Override
    AbstractSqlProductionFactory registFactory() {
        return sqlProductionHandlerFactory;
    }

    //新增表中 字段语句的包装
    private String createTableInColumnPacking(ColumnInfo columnInfo) {
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

        return TextFillUtil.fillStr(SqlModelEnum.CREATE_TABLE.getSqlModel(),
                columnInfo.getColumnName(), columnInfo.getTypeName(), columnInfo.getColumnSize(), attr.toString(), columnInfo.getRemarks());
    }

    //新增表中 主键语句的包装
    private String createTableprimaryKeyPacking(TableDetailInfo tableDetailInfo, List<ColumnInfo> columnInfos) {
        //TODO 主键sql
        Set<String> primarys = tableDetailInfo.getPrimarys();
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
}
