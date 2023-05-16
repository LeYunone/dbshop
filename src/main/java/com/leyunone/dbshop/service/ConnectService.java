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
        String url = "jdbc:mysql://localhost:3306/test2023?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true";
        String userName ="root";
        String passWord="root";
        connectService.getConnection(url,userName,passWord);
    }

    public Connection getConnection(String url,String userName,String passWord) {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,userName,passWord);
        }catch (Exception e){
            
        }
        return con;
    }
}

