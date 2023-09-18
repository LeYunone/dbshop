package com.leyunone.dbshop.handler.sql;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.annotate.SqlHandler;
import com.leyunone.dbshop.bean.info.IndexInfo;
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
 * @Date 2023/9/14 16:52
 */
@SqlHandler(SqlModelEnum.DELETE_INDEX)
public class DeleteIndexHandler extends SqlProductionAbstractHandler{

    @Autowired
    private SqlProductionHandlerFactory sqlProductionHandlerFactory;
    
    @Override
    public String handler(JSONObject json) {
        IndexInfo indexInfo = SqlPackUtil.resoleJsonData(json, IndexInfo.class);
        return TextFillUtil.fillStr(SqlModelEnum.DELETE_INDEX.getSqlModel(),indexInfo.getTableName(),indexInfo.getIndexName());

    }

    @Override
    AbstractSqlProductionFactory registFactory() {
        return sqlProductionHandlerFactory;
    }
}
