package com.leyunone.dbshop.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-18
 */
public class DbClose {

    private static final Logger logger = LoggerFactory.getLogger(DbClose.class);

    public static void close(Connection con) {
        if (con == null) return;
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
