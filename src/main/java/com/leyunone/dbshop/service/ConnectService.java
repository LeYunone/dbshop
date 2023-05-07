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

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ConnectService connectService = new ConnectService();
        connectService.getConnection("","","");
    }

    public void getConnection(String url,String userName,String passWord) throws SQLException, ClassNotFoundException {
        //组装连接信息
        DBInfo dbInfo = DBInfo.builder().url(url).userName(userName).passWord(passWord).build();

        Class.forName("com.mysql.jdbc.Driver");

        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/test2023?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true","root","root");

        DatabaseMetaData meta = con.getMetaData();
        ResultSet rs = meta.getTables("blog", null, null,
                new String[] { "TABLE" });
        while (rs.next()) {
            System.out.println("表名：" + rs.getString(3));
            System.out.println("表所属用户名：" + rs.getString(2));
            System.out.println("------------------------------");
            ResultSet columns = meta.getColumns("blog", null, rs.getString(3), "menu_id");
            System.out.println(columns);
        }
//        this.dbBaseInfo(con);
    }

    /**
     * 数据库基本信息
     * @param con
     */
    private void dbBaseInfo(Connection con) {
        try {
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
        }catch (Exception e){
            
        }
    }
}

