package com.leyunone.dbshop.handler.sql;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.annotate.SqlHandler;
import com.leyunone.dbshop.bean.info.IndexInfo;
import com.leyunone.dbshop.enums.SqlModelEnum;
import com.leyunone.dbshop.excutor.SqlProductionExcutor;
import com.leyunone.dbshop.system.factory.AbstractSqlProductionFactory;
import com.leyunone.dbshop.system.factory.SqlProductionHandlerFactory;
import com.leyunone.dbshop.util.SqlPackUtil;
import com.leyunone.dbshop.util.TextFillUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2023/9/14 17:03
 */
@SqlHandler(SqlModelEnum.UPDATE_INDEX)
public class UpdateIndexHandler extends SqlProductionAbstractHandler{

    @Autowired
    private SqlProductionHandlerFactory sqlProductionHandlerFactory;
    @Autowired
    private SqlProductionExcutor sqlProductionExcutor;

    /**
     * 更新索引语句 
     * 先删除索引 后新增
     * @param json
     * @return
     */
    @Override
    public String handler(JSONObject json) {
        IndexInfo indexInfo = SqlPackUtil.resoleJsonData(json, IndexInfo.class);
        if("PRIMARY".equals(indexInfo.getIndexName())) return null;
        String deleteIndex = sqlProductionExcutor.execute(SqlModelEnum.DELETE_INDEX, indexInfo);
        String addIndex = sqlProductionExcutor.execute(SqlModelEnum.ADD_INDEX, indexInfo);
        return TextFillUtil.fillStr(SqlModelEnum.UPDATE_INDEX.getSqlModel(),deleteIndex,addIndex);
    }

    @Override
    AbstractSqlProductionFactory registFactory() {
        return sqlProductionHandlerFactory;
    }
}
