package com.leyunone.dbshop.service;

import com.leyunone.dbshop.model.DBInfo;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

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

        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/test2023","root","root");

        DatabaseMetaData dbmd=con.getMetaData();

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ConnectService connectService = new ConnectService();

        connectService.getConnection("","","");
    }
}

