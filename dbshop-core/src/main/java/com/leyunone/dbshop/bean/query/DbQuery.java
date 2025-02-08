package com.leyunone.dbshop.bean.query;


/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-14
 */
public class DbQuery {

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

    public String getUrl() {
        return url;
    }

    public DbQuery setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public DbQuery setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getDbName() {
        return dbName;
    }

    public DbQuery setDbName(String dbName) {
        this.dbName = dbName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public DbQuery setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassWord() {
        return passWord;
    }

    public DbQuery setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }
}
