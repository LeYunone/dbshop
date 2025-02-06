package com.leyunone.dbshop.handler.sql;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.annotate.SqlHandler;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.enums.SqlAssembleEnum;
import com.leyunone.dbshop.enums.SqlModelEnum;
import com.leyunone.dbshop.system.factory.AbstractSqlProductionFactory;
import com.leyunone.dbshop.system.factory.SqlProductionHandlerFactory;
import com.leyunone.dbshop.util.SqlPackUtil;
import com.leyunone.dbshop.util.TextFillUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2023/9/12 15:21
 */
@SqlHandler(SqlModelEnum.ADD_COLUMN)
public class AddColumnHandler extends AbstractSqlProductionHandler {
    
    @Autowired
    private SqlProductionHandlerFactory sqlProductionHandlerFactory;
    
    @Override
    public String handler(JSONObject json) {
        ColumnInfo columnInfo = SqlPackUtil.resoleJsonData(json, ColumnInfo.class);
        StringBuilder sb = new StringBuilder("");
        if(columnInfo.getPrimaryKey()){
            sb.append(SqlAssembleEnum.PRIMARY_KEY.getSql());
        }
        if(!columnInfo.getNullable()) {
            sb.append(SqlAssembleEnum.NULLABLE.getSql());
        }
        if (columnInfo.getAutoincrement()) {
            //主键自增
            sb.append(SqlAssembleEnum.CREATE_TABLE_AUTOINCREMENT.getSql());
        }
        return TextFillUtil.fillStr(SqlModelEnum.ADD_COLUMN.getSqlModel(),
                columnInfo.getTableName(), columnInfo.getColumnName(), columnInfo.getTypeName(), columnInfo.getColumnSize(), sb.toString(), columnInfo.getRemarks());
    }

    @Override
    AbstractSqlProductionFactory registFactory() {
        return sqlProductionHandlerFactory;
    }
}
