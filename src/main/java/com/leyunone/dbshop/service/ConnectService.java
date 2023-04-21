package com.leyunone.dbshop.service;

import com.leyunone.dbshop.model.DBInfo;
import org.springframework.stereotype.Service;

import java.sql.*;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 *  连接服务
 * @date 2022-12-26
 */
@Service
public class ConnectService {

    public void getConnection(String url,String userName,String passWord) throws SQLException, ClassNotFoundException {
        //组装连接信息
        DBInfo dbInfo = DBInfo.builder().url(url).userName(userName).passWord(passWord).build();

        Class.forName("com.mysql.jdbc.Driver");

        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/test2023?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true","root","root");

        DatabaseMetaData dbmd=con.getMetaData();
        System.out.println("数据库已知的用户: " + dbmd.getUserName());
        System.out.println("数据库的系统函数的逗号分隔列表: " + dbmd.getSystemFunctions());
        System.out.println("数据库的时间和日期函数的逗号分隔列表: " + dbmd.getTimeDateFunctions());
        System.out.println("数据库的字符串函数的逗号分隔列表: " + dbmd.getStringFunctions());
        System.out.println("数据库供应商用于 'schema' 的首选术语: " + dbmd.getSchemaTerm());
        System.out.println("数据库URL: " + dbmd.getURL());
        System.out.println("是否允许只读:" + dbmd.isReadOnly());
        System.out.println("数据库的产品名称:" + dbmd.getDatabaseProductName());
        System.out.println("数据库的版本:" + dbmd.getDatabaseProductVersion());
        System.out.println("驱动程序的名称:" + dbmd.getDriverName());
        System.out.println("驱动程序的版本:" + dbmd.getDriverVersion());
        System.out.println("数据库中使用的表类型");
        ResultSet rs = dbmd.getTableTypes();
        while (rs.next()) {
            System.out.println(rs.getString("TABLE_TYPE"));
        }
        System.out.println("======================================");
        ResultSet tables = dbmd.getTables(null, null, "%", new String[]{"t_"});
        while(rs.next()) {
            System.out.println(tables.getString("TABLE_CAT"));
            System.out.println(tables.getString("TABLE_SCHEM"));
            System.out.println(tables.getString("TABLE_NAME"));
            System.out.println(tables.getString("TABLE_TYPE"));
            System.out.println(tables.getString("REMARKS"));

        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ConnectService connectService = new ConnectService();
        connectService.getConnection("","","");
    }
}

