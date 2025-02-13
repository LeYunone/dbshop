package com.leyunone.dbshop.excutor;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.IndexInfo;
import com.leyunone.dbshop.bean.info.TableDetailInfo;
import com.leyunone.dbshop.enums.SqlModelEnum;
import com.leyunone.dbshop.handler.sql.AbstractSqlProductionHandler;
import com.leyunone.dbshop.system.factory.SqlProductionHandlerFactory;
import com.leyunone.dbshop.util.SqlPackUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2023/9/12 15:48
 */
@Service
public class SqlProductionExcutor {

    @Autowired
    private SqlProductionHandlerFactory sqlProductionHandlerFactory;

    /**
     * 由包装工具决定入参类型
     * MODIFY_COLUMN = MODIFY_COLUMN
     * ADD_COLUMN = ADD_COLUMN
     * DELETE_COLUMN = DELETE_COLUMN
     * <p>
     * PRIMARY_KEY
     * CREATE_TABLE => = CREATE_TABLE_COLUMN
     * <p>
     * CREATE_TABLE_COLUMN = ↑
     *
     * @return
     */
    public String execute(SqlModelEnum sqlModelEnum, Object... objects) {
        JSONObject json = new JSONObject();
        for (Object o : objects) {
            if (o == null) {
                continue;
            }
            String data = JSONObject.toJSONString(o);
            if (o.getClass().isAssignableFrom(ColumnInfo.class)) {
                json.put(SqlPackUtil.COLUMN, data);
            } else if (o.getClass().isAssignableFrom(TableDetailInfo.class)) {
                json.put(SqlPackUtil.TABLE, data);
            } else if (o.getClass().isAssignableFrom(IndexInfo.class)) {
                json.put(SqlPackUtil.INDEX, data);
            } else if (List.class.isAssignableFrom(o.getClass())) {
                if (!CollectionUtils.isEmpty(((List) o))) {
                    if (((List) o).get(0).getClass().isAssignableFrom(ColumnInfo.class)) {
                        json.put(SqlPackUtil.COLUMNS, data);
                    } else if (((List) o).get(0).getClass().isAssignableFrom(IndexInfo.class)) {
                        json.put(SqlPackUtil.INDEXS, data);
                    }
                }
            }
        }
        AbstractSqlProductionHandler handler = sqlProductionHandlerFactory.getHandler(sqlModelEnum);
        String result = null;
        if (null != handler) {
            result = handler.handler(json);
        }
        return StringUtils.isBlank(result) ? "" : result;
    }
}
