package com.leyunone.dbshop.service.helper;

import com.leyunone.dbshop.bean.query.DbQuery;
import com.leyunone.dbshop.service.core.ConfigService;
import com.leyunone.dbshop.service.core.ConnectService;
import org.springframework.stereotype.Service;

/**
 * :)
 * 数据加载者
 *
 * @Author pengli
 * @Date 2025/2/8 11:15
 */
@Service
public class DbLoadingHelper {

    private final ConnectService connectService;
    private final ConfigService configService;

    public DbLoadingHelper(ConnectService connectService, ConfigService configService) {
        this.connectService = connectService;
        this.configService = configService;
    }

    public void loadingData(String url, String dbName, String userName, String password) {
        DbQuery dbQuery = new DbQuery();
        dbQuery.setUrl(url);
        dbQuery.setDbName(dbName);
        dbQuery.setUserName(userName);
        dbQuery.setPassWord(password);

        configService.loadConnectionToData(dbQuery);
    }
}
