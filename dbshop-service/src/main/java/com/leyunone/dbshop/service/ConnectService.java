package com.leyunone.dbshop.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.enums.ColumnResultEnum;
import com.leyunone.dbshop.util.DbClose;
import com.mysql.cj.jdbc.DatabaseMetaDataUsingInfoSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.sql.*;

/**
 * @author LeYunOne
 * @email 365627310@qq.com
 * 连接服务
 * @date 2022-12-26
 */
@Service
public class ConnectService {

    private static final Logger logger = LoggerFactory.getLogger(ConnectService.class);

    public static DatabaseMetaData toTest() throws SQLException, ClassNotFoundException {
        ConnectService connectService = new ConnectService();
        String url = "jdbc:mysql://192.168.151.233:3306/smarthome?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true";
        String userName = "root";
        String passWord = "gvs@2022";
        return connectService.getConnectionToData(url, userName, passWord);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DatabaseMetaData meta = toTest();
        ResultSet rs = null;
        try {
            rs = meta.getIndexInfo("smarthome", "smarthome", "test", false, true);
            while (rs.next()) {
                String ascOrDesc = rs.getString("ASC_OR_DESC");         // 列排序顺序: 升序还是降序
                int cardinality = rs.getInt("CARDINALITY");             // 基数
                short ordinalPosition = rs.getShort("ORDINAL_POSITION");// 在索引列顺序号
                boolean nonUnique = rs.getBoolean("NON_UNIQUE");        // 非唯一索引(Can index values be non-unique. false when TYPE is  tableIndexStatistic   )
                String indexQualifier = rs.getString("INDEX_QUALIFIER");// 索引目录（可能为空）
                String indexName = rs.getString("INDEX_NAME");          // 索引的名称
                short indexType = rs.getShort("TYPE");          // 索引类型
                String columnName = rs.getString("COLUMN_NAME");
                String filter_condition = rs.getString("FILTER_CONDITION");
                String pages = rs.getString("PAGES");
                String table_cat = rs.getString("TABLE_CAT");
                String tableName = rs.getString("TABLE_NAME");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("filter_condition",filter_condition);
                jsonObject.put("表名",tableName);
                jsonObject.put("pages",pages);
                jsonObject.put("table_cat",table_cat);
                jsonObject.put("字段名",columnName);
                jsonObject.put("排序顺序: 升序还是降序",ascOrDesc);
                jsonObject.put("基数",cardinality);
                jsonObject.put("在索引列顺序号",ordinalPosition);
                jsonObject.put("非唯一索引",nonUnique);
                jsonObject.put("索引目录",indexQualifier);
                jsonObject.put("索引的名称",indexName);
                jsonObject.put("索引类型",indexType);

                System.out.println(jsonObject.toJSONString());
            }
        } catch (SQLException e) {
        } finally {
        }
    }

    public Connection getConnection(String url, String userName, String passWord) {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, userName, passWord);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return con;
    }

    public Connection getLongConnection(String url, String userName, String passWord) {
        Connection connection = this.getConnection(url, userName, passWord);
        if (ObjectUtil.isNotNull(connection)) {
        }
        return connection;
    }

    public DatabaseMetaData getConnectionToData(String url, String userName, String passWord) {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, userName, passWord);
            return con.getMetaData();
        } catch (Exception e) {
        } finally {
//            DbClose.close(con);
        }
        return null;
    }
}

