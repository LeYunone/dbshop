package com.leyunone.dbshop.bean.info;


/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-16
 */
public class DbInfo {
    //数据库已知的用户
    private String userName;
    //数据库的系统函数的逗号分隔列表
    private String systemFunctions;
    //数据库的时间和日期函数的逗号分隔列表
    private String timeDateFunctions;
    //数据库的字符串函数的逗号分隔列表
    private String stringFunctions;
    //数据库供应商用于 'schema' 的首选术语
    private String schemaTerm;
    //数据库URL
    private String url;
    //是否允许只读
    private boolean readOnly;
    //数据库的产品名称
    private String databaseProductName;
    //数据库的版本
    private String databaseProductVersion;
    //驱动程序的名称
    private String driverName;
    //驱动程序的版本
    private String driverVersion;
    
    private String dbName;

    public String getUserName() {
        return userName;
    }

    public DbInfo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getSystemFunctions() {
        return systemFunctions;
    }

    public DbInfo setSystemFunctions(String systemFunctions) {
        this.systemFunctions = systemFunctions;
        return this;
    }

    public String getTimeDateFunctions() {
        return timeDateFunctions;
    }

    public DbInfo setTimeDateFunctions(String timeDateFunctions) {
        this.timeDateFunctions = timeDateFunctions;
        return this;
    }

    public String getStringFunctions() {
        return stringFunctions;
    }

    public DbInfo setStringFunctions(String stringFunctions) {
        this.stringFunctions = stringFunctions;
        return this;
    }

    public String getSchemaTerm() {
        return schemaTerm;
    }

    public DbInfo setSchemaTerm(String schemaTerm) {
        this.schemaTerm = schemaTerm;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public DbInfo setUrl(String url) {
        this.url = url;
        return this;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public DbInfo setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

    public String getDatabaseProductName() {
        return databaseProductName;
    }

    public DbInfo setDatabaseProductName(String databaseProductName) {
        this.databaseProductName = databaseProductName;
        return this;
    }

    public String getDatabaseProductVersion() {
        return databaseProductVersion;
    }

    public DbInfo setDatabaseProductVersion(String databaseProductVersion) {
        this.databaseProductVersion = databaseProductVersion;
        return this;
    }

    public String getDriverName() {
        return driverName;
    }

    public DbInfo setDriverName(String driverName) {
        this.driverName = driverName;
        return this;
    }

    public String getDriverVersion() {
        return driverVersion;
    }

    public DbInfo setDriverVersion(String driverVersion) {
        this.driverVersion = driverVersion;
        return this;
    }

    public String getDbName() {
        return dbName;
    }

    public DbInfo setDbName(String dbName) {
        this.dbName = dbName;
        return this;
    }
}
