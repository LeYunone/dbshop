package com.leyunone.dbshop.handler.sql;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.annotate.SqlHandler;
import com.leyunone.dbshop.bean.info.IndexInfo;
import com.leyunone.dbshop.enums.SqlModelEnum;
import com.leyunone.dbshop.system.factory.AbstractSqlProductionFactory;
import com.leyunone.dbshop.system.factory.SqlProductionHandlerFactory;
import com.leyunone.dbshop.util.SqlPackUtil;
import com.leyunone.dbshop.util.TextFillUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * :)
 *
 * @Author pengli
 * @Date 2023/9/13 15:56
 */
@SqlHandler(SqlModelEnum.ADD_INDEX)
public class AddIndexHandler extends SqlProductionAbstractHandler {

    @Autowired
    private SqlProductionHandlerFactory sqlProductionHandlerFactory;

    @Override
    public String handler(JSONObject json) {
        IndexInfo indexInfo = SqlPackUtil.resoleJsonData(json, IndexInfo.class);
        String indexType = "INDEX";
        if (indexInfo.isUniqueIndex()) {
            indexType = "UNIQUE INDEX";
        } else if (StringUtils.isBlank(indexInfo.getAscOrDesc())) {
            //FIXME 强行判断 MYSQL 5.7版本 ：为空为 FULLTEXT
            indexType = "FULLTEXT INDEX";
        }
        if (CollectionUtil.isEmpty(indexInfo.getColumns())) return "";
        String[] columns = new String[indexInfo.getColumns().size()];
        indexInfo.getColumns().forEach((t) -> columns[t.getIndex() - 1] = t.getColumnName());
        return TextFillUtil.fillStr(SqlModelEnum.ADD_INDEX.getSqlModel(), indexInfo.getTableName(), indexType, indexInfo.getIndexName(),
                CollectionUtil.join(CollectionUtil.newArrayList(columns), ","));
    }

    @Override
    AbstractSqlProductionFactory registFactory() {
        return sqlProductionHandlerFactory;
    }
}
