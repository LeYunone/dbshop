package com.leyunone.dbshop.handler.sql;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.annotate.SqlHandler;
import com.leyunone.dbshop.bean.info.TableDetailInfo;
import com.leyunone.dbshop.enums.SqlModelEnum;
import com.leyunone.dbshop.system.factory.AbstractSqlProductionFactory;
import com.leyunone.dbshop.system.factory.SqlProductionHandlerFactory;
import com.leyunone.dbshop.util.SqlPackUtil;
import com.leyunone.dbshop.util.TextFillUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/10/26 16:29
 */
@SqlHandler(SqlModelEnum.PRIMARY_COLUMN)
public class PrimaryColumnHandler extends AbstractSqlProductionHandler {

    @Autowired
    private SqlProductionHandlerFactory sqlProductionHandlerFactory;
    
    @Override
    public String handler(JSONObject json) {
        TableDetailInfo tableDetailInfo = SqlPackUtil.resoleJsonData(json, TableDetailInfo.class);
        return TextFillUtil.fillStr(SqlModelEnum.PRIMARY_COLUMN.getSqlModel(),tableDetailInfo.getTableName(), CollectionUtil.join(tableDetailInfo.getPrimarys(),","));
    }

    @Override
    AbstractSqlProductionFactory registFactory() {
        return sqlProductionHandlerFactory;
    }
}
