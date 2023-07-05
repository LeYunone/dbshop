package com.leyunone.dbshop.bean.info;

import lombok.*;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-16
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
}
