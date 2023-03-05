package com.leyunone.dbshop.system.cache;

import com.leyunone.dbshop.model.DBInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-12-26
 *
 * 连接桶缓存
 */
public class ConnectionInfoCache {

    public static Map<String, DBInfo> connectionInfos = new ConcurrentHashMap<>();

    
}
