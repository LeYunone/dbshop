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
        ResultSet rs = meta.getTables("test2023", null, null,
                new String[] { "TABLE" });
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            System.out.println("表名：" + tableName);
            System.out.println("表类型:"+rs.getString("TABLE_TYPE"));
            System.out.println("表注释:"+rs.getString("REMARKS"));
            System.out.println("表所属用户名：" + rs.getString(2));
            ResultSet primaryKeys = meta.getPrimaryKeys(null, null, tableName);
            while (primaryKeys.next()){
                System.out.println("表主键： "+ primaryKeys.getString("COLUMN_NAME"));
            }
            ResultSet columns = meta.getColumns(null, null, tableName, "%");

            System.out.println("======字段========");
            while (columns.next()){
                System.out.println("字段名:"+columns.getString("COLUMN_NAME")+
                                    "  字段类型："+columns.getString("DATA_TYPE")+
                                    "  字段类型名:"+columns.getString("TYPE_NAME")+
                                    "  TABLE_CAT:"+columns.getString("TABLE_CAT")+
                                    "  TABLE_SCHEM:"+columns.getString("TABLE_SCHEM")+
                                    "  TABLE_NAME:"+columns.getString("TABLE_NAME")+
                                    "  COLUMN_SIZE:"+columns.getString("COLUMN_SIZE")+
                                    "  DECIMAL_DIGITS:"+columns.getString("DECIMAL_DIGITS")+
                                    "  NUM_PREC_RADIX:"+columns.getString("NUM_PREC_RADIX")+
                                    "  REMARKS:"+columns.getString("REMARKS"));
            }
            System.out.println("------------------------------");
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

