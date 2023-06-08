package com.leyunone.dbshop.service;

import cn.hutool.core.util.ObjectUtil;
import com.leyunone.dbshop.util.DbClose;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ConnectService.class);
    
    public static DatabaseMetaData toTest() throws SQLException, ClassNotFoundException {
        ConnectService connectService = new ConnectService();
        String url = "jdbc:mysql://localhost:3306/test2023?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true";
        String userName ="root";
        String passWord="root";
        return connectService.getConnectionToData(url,userName,passWord);
    }

    public Connection getConnection(String url,String userName,String passWord) {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,userName,passWord);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return con;
    }
    
    public Connection getLongConnection(String url,String userName,String passWord){
        Connection connection = this.getConnection(url, userName, passWord);
        if(ObjectUtil.isNotNull(connection)){
        }
        return connection;
    }

    public DatabaseMetaData getConnectionToData(String url,String userName,String passWord) {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,userName,passWord);
            return con.getMetaData();
        }catch (Exception e){
        }finally {
//            DbClose.close(con);
        }
        return null;
    }
}

