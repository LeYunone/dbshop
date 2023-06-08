package com.leyunone.dbshop.service;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.DbInfo;
import com.leyunone.dbshop.bean.info.TableInfo;
import com.leyunone.dbshop.enums.ColumnResultEnum;
import com.leyunone.dbshop.enums.TableResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 组装信息服务
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-16
 */
@Service
public class PackInfoService {

    private final static Logger logger = LoggerFactory.getLogger(PackInfoService.class);

    /**
     * 数据库信息
     * @param data
     * @return
     */
    public DbInfo getDbInfo(DatabaseMetaData data) {
        DbInfo dbInfo = null;
        try {
            dbInfo = DbInfo.builder().userName(data.getUserName())
                    .systemFunctions(data.getSystemFunctions())
                    .timeDateFunctions(data.getTimeDateFunctions())
                    .stringFunctions(data.getStringFunctions())
                    .schemaTerm(data.getSchemaTerm())
                    .url(data.getURL())
                    .isReadOnly(data.isReadOnly())
                    .databaseProductName(data.getDatabaseProductName())
                    .databaseProductVersion(data.getDatabaseProductVersion())
                    .driverName(data.getDriverName())
                    .driverVersion(data.getDriverVersion())
                    .build();
        } catch (Exception e) {
            logger.error("db信息解析失败+：Exception:{}", e.getMessage());
        }
        return dbInfo;
    }

    /**
     * 表信息
     * @param meta
     * @param dbName
     */
    public List<TableInfo> getTables(DatabaseMetaData meta, String dbName) {
        List<TableInfo> tableInfos = new ArrayList<>();
        try {
            ResultSet rs = meta.getTables(dbName, "%", "%",
                    new String[] { "TABLE" });
            while (rs.next()) {
                TableInfo tableInfo = TableInfo.builder().tableName(rs.getString(TableResultEnum.TABLE_NAME.getType()))
                        .tableType(rs.getString(TableResultEnum.TABLE_TYPE.getType())).primarys(CollectionUtil.newHashSet())
                        .remarks(rs.getString(TableResultEnum.REMARKS.getType())).build();
                ResultSet primaryKeys = meta.getPrimaryKeys(null, null, rs.getString(TableResultEnum.TABLE_NAME.getType()));
                while (primaryKeys.next()){
                    //表主键
                    tableInfo.getPrimarys().add(primaryKeys.getString(TableResultEnum.PK_COLUMN_NAME.getType()));
                }
                tableInfos.add(tableInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("db表解析失败+：Exception：{}",e.getMessage());
        }
        return tableInfos;
    }
    
    public List<ColumnInfo> getColumns(DatabaseMetaData meta,String dbName,String tableName) {
        List<ColumnInfo> columnInfos = new ArrayList<>();
        try {
            ResultSet columns = meta.getColumns(dbName, null, tableName, null);
            while (columns.next()){
                ColumnInfo column = ColumnInfo.builder().columnName(columns.getString(ColumnResultEnum.COLUMN_NAME.getType()))
                        .tableName(columns.getString(ColumnResultEnum.TABLE_NAME.getType()))
                        .dataType(columns.getString(ColumnResultEnum.DATA_TYPE.getType()))
                        .tableCat(columns.getString(ColumnResultEnum.TABLE_CAT.getType()))
                        .columnSize(columns.getString(ColumnResultEnum.COLUMN_SIZE.getType()))
                        .decimailDigits(columns.getString(ColumnResultEnum.DECIMAL_DIGITS.getType()))
                        .remarks(columns.getString(ColumnResultEnum.REMARKS.getType()))
                        .build();
                columnInfos.add(column);
            }
        }catch (Exception e){
            logger.error("db列解析失败+：Exception：{}",e.getMessage());
        }
        return columnInfos;
    }
    
    //No operations allowed after connection closed.
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DatabaseMetaData databaseMetaData = ConnectService.toTest();
        PackInfoService packInfoService = new PackInfoService();
        List<ColumnInfo> columns = packInfoService.getColumns(databaseMetaData, "test2023", null);

        List<TableInfo> test2023 = packInfoService.getTables(databaseMetaData, "test2023");
        System.out.println(test2023.size());
        System.out.println(columns.size());
    }
}
