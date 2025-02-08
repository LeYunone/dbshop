package com.leyunone.dbshop.service.core.impl;

import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.DbInfo;
import com.leyunone.dbshop.bean.info.IndexInfo;
import com.leyunone.dbshop.bean.info.TableDetailInfo;
import com.leyunone.dbshop.enums.ColumnResultEnum;
import com.leyunone.dbshop.enums.TableResultEnum;
import com.leyunone.dbshop.service.core.PackInfoService;
import com.leyunone.dbshop.util.MyCollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 组装信息服务
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-16
 */
@Service
public class PackInfoServiceImpl implements PackInfoService {

    private final static Logger logger = LoggerFactory.getLogger(PackInfoServiceImpl.class);

    /**
     * 数据库信息
     *
     * @param data
     * @return
     */
    @Override
    public DbInfo getDbInfo(DatabaseMetaData data) {
        DbInfo dbInfo = null;
        try {
            dbInfo = new DbInfo()
                    .setUserName(data.getUserName())
                    .setSystemFunctions(data.getSystemFunctions())
                    .setTimeDateFunctions(data.getTimeDateFunctions())
                    .setStringFunctions(data.getStringFunctions())
                    .setSchemaTerm(data.getSchemaTerm())
                    .setUrl(data.getURL())
                    .setReadOnly(data.isReadOnly())
                    .setDatabaseProductName(data.getDatabaseProductName())
                    .setDatabaseProductVersion(data.getDatabaseProductVersion())
                    .setDriverName(data.getDriverName())
                    .setDriverVersion(data.getDriverVersion());
        } catch (Exception e) {
            logger.error("db信息解析失败+：Exception:{}", e.getMessage());
        }
        return dbInfo;
    }

    /**
     * 表信息
     *
     * @param meta
     * @param dbName
     */
    @Override
    public List<TableDetailInfo> getTables(DatabaseMetaData meta, String dbName) {
        List<TableDetailInfo> tableDetailInfos = new ArrayList<>();
        try {
            ResultSet rs = meta.getTables(dbName, "%", "%",
                    new String[]{"TABLE"});
            while (rs.next()) {
                TableDetailInfo tableDetailInfo = new TableDetailInfo()
                        .setTableName(rs.getString(TableResultEnum.TABLE_NAME.getType()))
                        .setTableType(rs.getString(TableResultEnum.TABLE_TYPE.getType()))
                        .setPrimarys(new HashSet<>())
                        .setRemarks(rs.getString(TableResultEnum.REMARKS.getType()));
                ResultSet primaryKeys = meta.getPrimaryKeys(dbName, null, tableDetailInfo.getTableName());
                while (primaryKeys.next()) {
                    //表主键
                    tableDetailInfo.getPrimarys().add(primaryKeys.getString(TableResultEnum.PK_COLUMN_NAME.getType()));
                }
                tableDetailInfos.add(tableDetailInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("db表解析失败+：Exception：{}", e.getMessage());
        }
        return tableDetailInfos;
    }

    /**
     * 索引信息
     *
     * @return
     */
    @Override
    public List<IndexInfo> getIndexs(DatabaseMetaData meta, String dbName, String tableName) {
        Map<String, IndexInfo> indexMap = new HashMap<>();
        try {
            ResultSet indexInfo = meta.getIndexInfo(dbName, null, tableName, false, true);
            while (indexInfo.next()) {
                String indexName = indexInfo.getString("INDEX_NAME");
                IndexInfo info = indexMap.get(indexName);
                if (null == info) {
                    info = new IndexInfo();
                    indexMap.put(indexName, info);
                }
//                    boolean nonUnique = indexInfo.getBoolean("NON_UNIQUE");        // 非唯一索引(Can index values be non-unique. false when TYPE is  tableIndexStatistic   )
//                    String indexQualifier = indexInfo.getString("INDEX_QUALIFIER");// 索引目录（可能为空）
                info.setAscOrDesc(indexInfo.getString("ASC_OR_DESC"));
                info.setCardinality(indexInfo.getInt("CARDINALITY"));
                info.setIndexName(indexInfo.getString("INDEX_NAME"));
                info.setTableName(indexInfo.getString("TABLE_NAME"));
                info.setPrimaryIndex("PRIMARY".equals(info.getIndexName()));
                info.setUniqueIndex(!indexInfo.getBoolean("NON_UNIQUE"));
                info.setType(indexInfo.getInt("TYPE"));
                info.getColumns().add(new IndexInfo.IndexColumn()
                        .setIndex(indexInfo.getInt("ORDINAL_POSITION"))
                        .setColumnName(indexInfo.getString("COLUMN_NAME")));
            }
        } catch (Exception e) {
        }
        return MyCollectionUtils.newArrayList(indexMap.values());
    }

    @Override
    public List<ColumnInfo> getColumns(DatabaseMetaData meta, String dbName, String tableName) {
        List<ColumnInfo> columnInfos = new ArrayList<>();
        try {
            ResultSet columns = meta.getColumns(dbName, null, tableName, null);

            ResultSet primaryKeys = meta.getPrimaryKeys(dbName, null, tableName);
            Set<String> primaryNames = new HashSet<>();
            while (primaryKeys.next()) {
                //表主键
                primaryNames.add(primaryKeys.getString(TableResultEnum.PK_COLUMN_NAME.getType()));
            }
            while (columns.next()) {
                ColumnInfo column = new ColumnInfo()
                        .setColumnName(columns.getString(ColumnResultEnum.COLUMN_NAME.getType()))
                        .setTableName(columns.getString(ColumnResultEnum.TABLE_NAME.getType()))
                        .setDataType(columns.getString(ColumnResultEnum.DATA_TYPE.getType()))
                        .setTypeName(columns.getString(ColumnResultEnum.TYPE_NAME.getType()))
                        .setTableCat(columns.getString(ColumnResultEnum.TABLE_CAT.getType()))
                        .setColumnSize(columns.getString(ColumnResultEnum.COLUMN_SIZE.getType()))
                        .setDecimailDigits(columns.getString(ColumnResultEnum.DECIMAL_DIGITS.getType()))
                        .setRemarks(columns.getString(ColumnResultEnum.REMARKS.getType()));
                column.setAutoincrement("YES".equals(columns.getString(ColumnResultEnum.IS_AUTOINCREMENT.getType())));
                column.setNullable("YES".equals(columns.getString(ColumnResultEnum.IS_NULLABLE.getType())));
                column.setPrimaryKey(primaryNames.contains(column.getColumnName()));
                columnInfos.add(column);
            }
        } catch (Exception e) {
            logger.error("db列解析失败+：Exception：{}", e.getMessage());
            e.printStackTrace();
        }
        return columnInfos;
    }

    //No operations allowed after connection closed.
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DatabaseMetaData databaseMetaData = ConnectServiceImpl.toTest();
        PackInfoServiceImpl packInfoService = new PackInfoServiceImpl();
        List<TableDetailInfo> test20231 = packInfoService.getTables(databaseMetaData, "test2023");
        List<ColumnInfo> columns = packInfoService.getColumns(databaseMetaData, "test2023", null);
        List<TableDetailInfo> test2023 = packInfoService.getTables(databaseMetaData, "test2023");

        System.out.println(test2023.size());
        System.out.println(columns.size());
    }
}
