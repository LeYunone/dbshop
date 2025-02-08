package com.leyunone.dbshop.controller;

import com.leyunone.dbshop.bean.DataResponse;
import com.leyunone.dbshop.bean.info.DbInfo;
import com.leyunone.dbshop.bean.query.DbQuery;
import com.leyunone.dbshop.service.core.impl.ConfigServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-28
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigServiceImpl configService;

    /**
     * 短连接版本 捕捉当前数据库的快照信息
     * @param dbQuery
     * @return
     */
    @RequestMapping("/loadConnection")
    public DataResponse<DbInfo> loadConnection(DbQuery dbQuery){
        DbInfo dbInfo = configService.loadConnectionToData(dbQuery);
        return DataResponse.of(dbInfo);
    }
}
