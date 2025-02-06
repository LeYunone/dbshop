package com.leyunone.dbshop.service.core;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;

/**
 * @author LeYunOne
 * @email 365627310@qq.com
 * 连接服务
 * @date 2022-12-26
 */
public interface ConnectService {

    Connection getConnection(String url, String userName, String passWord);

    Connection getLongConnection(String url, String userName, String passWord);

    DatabaseMetaData getConnectionToData(String url, String userName, String passWord);
}

