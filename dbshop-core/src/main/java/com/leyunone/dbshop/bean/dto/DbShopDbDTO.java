package com.leyunone.dbshop.bean.dto;


import javax.validation.constraints.NotNull;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-19
 */
public class DbShopDbDTO {


    /**
     * url
     */
    @NotNull(message = "url cant not empty")
    private String url;

    /**
     * 数据库名
     */
    @NotNull(message = "dbName cant not empty")
    private String dbName;

    @NotNull(message = "userName cant not empty")
    private String userName;

    @NotNull(message = "passWord cant not empty")
    private String passWord;

    public String getUrl() {
        return url;
    }

    public DbShopDbDTO setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getDbName() {
        return dbName;
    }

    public DbShopDbDTO setDbName(String dbName) {
        this.dbName = dbName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public DbShopDbDTO setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassWord() {
        return passWord;
    }

    public DbShopDbDTO setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }
}
