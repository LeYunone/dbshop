package com.leyunone.dbshop.handler.sql;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.annotate.SqlHandler;
import com.leyunone.dbshop.bean.info.ColumnInfo;
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
 * @Date 2023/9/12 15:35
 */
@SqlHandler(SqlModelEnum.DELETE_AUTOINCREMENT)
public class DeleteAutoincrementHandler extends AbstractSqlProductionHandler {

    @Autowired
    private SqlProductionHandlerFactory sqlProductionHandlerFactory;
    
    @Override
    public String handler(JSONObject json) {
        ColumnInfo columnInfo = SqlPackUtil.resoleJsonData(json, ColumnInfo.class);
        return TextFillUtil.fillStr(SqlModelEnum.DELETE_AUTOINCREMENT.getSqlModel(),columnInfo.getTableName()
                ,columnInfo.getColumnName(),columnInfo.getColumnName(),columnInfo.getTypeName(),columnInfo.getColumnSize());
    }

    @Override
    AbstractSqlProductionFactory registFactory() {
        return sqlProductionHandlerFactory;
    }
}
