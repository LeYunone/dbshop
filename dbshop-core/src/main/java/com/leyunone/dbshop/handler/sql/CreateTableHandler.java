package com.leyunone.dbshop.handler.sql;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.annotate.SqlHandler;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.IndexInfo;
import com.leyunone.dbshop.bean.info.TableDetailInfo;
import com.leyunone.dbshop.enums.SqlAssembleEnum;
import com.leyunone.dbshop.enums.SqlModelEnum;
import com.leyunone.dbshop.system.factory.AbstractSqlProductionFactory;
import com.leyunone.dbshop.system.factory.SqlProductionHandlerFactory;
import com.leyunone.dbshop.util.MyCollectionUtils;
import com.leyunone.dbshop.util.SqlPackUtil;
import com.leyunone.dbshop.util.TextFillUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

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
public class CreateTableHandler extends AbstractSqlProductionHandler {

    @Autowired
    private SqlProductionHandlerFactory sqlProductionHandlerFactory;

    @Override
    public String handler(JSONObject json) {
        List<ColumnInfo> columnInfos = SqlPackUtil.resoleJsonDatas(json, ColumnInfo.class);
        TableDetailInfo tableDetailInfo = SqlPackUtil.resoleJsonData(json, TableDetailInfo.class);
        List<IndexInfo> indexInfos = SqlPackUtil.resoleJsonDatas(json, IndexInfo.class);
        String columnSqls = this.createTableInColumnPacking(columnInfos);
        String primarykeys = this.createTableprimaryKeyPacking(tableDetailInfo, columnInfos);
        String indexSqls = this.createTableIndexKeyPacking(tableDetailInfo, indexInfos);
        if (StringUtils.isNotBlank(primarykeys) || StringUtils.isNotBlank(indexSqls)) {
            columnSqls = columnSqls + ",\n";
        }
        if (StringUtils.isNotBlank(indexSqls)) {
            primarykeys = primarykeys + ",\n";
        }
        return TextFillUtil.fillStr(SqlModelEnum.CREATE_TABLE.getSqlModel(), tableDetailInfo.getTableName(), columnSqls, primarykeys, indexSqls);
    }

    @Override
    AbstractSqlProductionFactory registFactory() {
        return sqlProductionHandlerFactory;
    }

    //新增表中 字段语句的包装
    private String createTableInColumnPacking(List<ColumnInfo> columnInfos) {
        if (CollectionUtils.isEmpty(columnInfos)) {
            return null;
        }
        List<String> sql = new ArrayList<>();
        for (ColumnInfo columnInfo : columnInfos) {
            StringBuilder attr = new StringBuilder("");
            if (!columnInfo.getNullable()) {
                attr.append(SqlAssembleEnum.NULLABLE.getSql());
            } else {
                attr.append(SqlAssembleEnum.NULL.getSql());
            }
            if (columnInfo.getAutoincrement()) {
                //主键自增
                attr.append(SqlAssembleEnum.CREATE_TABLE_AUTOINCREMENT.getSql());
            }

            sql.add(TextFillUtil.fillStr(SqlModelEnum.CREATE_TABLE_COLUMN.getSqlModel(),
                    columnInfo.getColumnName(), columnInfo.getTypeName(), columnInfo.getColumnSize(), attr.toString(), columnInfo.getRemarks()));
        }
        return MyCollectionUtils.join(sql, ", \n");
    }

    //新增表中 主键语句的包装
    private String createTableprimaryKeyPacking(TableDetailInfo tableDetailInfo, List<ColumnInfo> columnInfos) {
        if (CollectionUtils.isEmpty(columnInfos)) {
            return null;
        }

        //TODO 主键sql
        Set<String> primarys = tableDetailInfo.getPrimarys();
        if (CollectionUtils.isEmpty(primarys) || CollectionUtils.isEmpty(columnInfos)) {
            return "";
        }
        List<String> primaryNames = new ArrayList<>();
        if (!CollectionUtils.isEmpty(primarys)) {
            //匹配主键
            columnInfos.forEach((columnInfo) -> {
                if (primarys.contains(columnInfo.getColumnName())) {
                    primaryNames.add("`" + columnInfo.getColumnName() + "`");
                }
            });
        }
        //阈值保护
        if (CollectionUtils.isEmpty(primaryNames)) {
            return "";
        }
        return TextFillUtil.fillStr(SqlModelEnum.CREATE_TABLE_PRIMARY_KEY.getSqlModel(), MyCollectionUtils.join(primaryNames, ","));
    }

    private String createTableIndexKeyPacking(TableDetailInfo tableDetailInfo, List<IndexInfo> indexInfos) {
        if (CollectionUtils.isEmpty(indexInfos)) {
            return "";
        }
        Set<String> primarys = tableDetailInfo.getPrimarys();
        List<String> sql = new ArrayList<>();
        for (IndexInfo indexInfo : indexInfos) {
            if ("PRIMARY".equals(indexInfo.getIndexName())
                    && this.checkPrimaryIndex(indexInfo.getColumns(), primarys)) {
                //默认主键唯一索引 建表中不需要重复构建
                continue;
            }
            String indexType = "KEY";
            if (indexInfo.isUniqueIndex()) {
                indexType = "UNIQUE KEY";
            } else if (StringUtils.isBlank(indexInfo.getAscOrDesc())) {
                //FIXME 强行判断 MYSQL 5.7版本 ：为空为 FULLTEXT
                indexType = "FULLTEXT KEY";
            }
            if (CollectionUtils.isEmpty(indexInfo.getColumns())) {
                return "";
            }
            String[] columns = new String[indexInfo.getColumns().size()];
            indexInfo.getColumns().forEach((t) -> columns[t.getIndex() - 1] = t.getColumnName());
            sql.add(TextFillUtil.fillStr(SqlModelEnum.CREATE_TABLE_INDEX.getSqlModel(), indexType, indexInfo.getIndexName(),
                    MyCollectionUtils.join(MyCollectionUtils.newArrayList(columns), ",")));
        }
        return MyCollectionUtils.join(sql, ", \n");
    }

    private boolean checkPrimaryIndex(List<IndexInfo.IndexColumn> columns, Set<String> primarys) {
        boolean check = false;
        if (columns.size() == primarys.size()) {
            for (IndexInfo.IndexColumn column : columns) {
                if (!primarys.contains(column.getColumnName())) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }
}
