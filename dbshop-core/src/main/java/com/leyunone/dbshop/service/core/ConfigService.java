package com.leyunone.dbshop.service.core;

import com.leyunone.dbshop.bean.info.*;
import com.leyunone.dbshop.bean.query.DbQuery;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-28
 */
public interface ConfigService {

    /**
     * 数据库连接唯一入库： 加载数据库连接
     *
     * @param query
     */
    DbInfo loadConnectionToData(DbQuery query);
}
