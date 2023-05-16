package com.leyunone.dbshop.bean.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-14
 */
@Getter
@Setter
public class DBQuery {

    /**
     * url
     */
    private String url;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 数据库名
     */
    private String dbName;
    
    private String userName;
    
    private String passWord;
}
