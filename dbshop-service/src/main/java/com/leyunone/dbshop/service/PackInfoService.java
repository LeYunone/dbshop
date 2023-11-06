package com.leyunone.dbshop.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.DbInfo;
import com.leyunone.dbshop.bean.info.IndexInfo;
import com.leyunone.dbshop.bean.info.TableDetailInfo;
import com.leyunone.dbshop.enums.ColumnResultEnum;
import com.leyunone.dbshop.enums.TableResultEnum;
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
                    .readOnly(data.isReadOnly())
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
    public List<TableDetailInfo> getTables(DatabaseMetaData meta, String dbName) {
        List<TableDetailInfo> tableDetailInfos = new ArrayList<>();
        try {
            ResultSet rs = meta.getTables(dbName, "%", "%",
                    new String[] { "TABLE" });
            while (rs.next()) {
                TableDetailInfo tableDetailInfo = TableDetailInfo.builder().tableName(rs.getString(TableResultEnum.TABLE_NAME.getType()))
                        .tableType(rs.getString(TableResultEnum.TABLE_TYPE.getType())).primarys(CollectionUtil.newHashSet())
                        .remarks(rs.getString(TableResultEnum.REMARKS.getType())).build();
                ResultSet primaryKeys = meta.getPrimaryKeys(dbName, null, tableDetailInfo.getTableName());
                while (primaryKeys.next()){
                    //表主键
                    tableDetailInfo.getPrimarys().add(primaryKeys.getString(TableResultEnum.PK_COLUMN_NAME.getType()));
                }
                tableDetailInfos.add(tableDetailInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("db表解析失败+：Exception：{}",e.getMessage());
        }
        return tableDetailInfos;
    }

    /**
     * 索引信息
     * @return
     */
    public List<IndexInfo> getIndexs(DatabaseMetaData meta,String dbName,String tableName) {
        Map<String, IndexInfo> indexMap = new HashMap<>();
        try {
            ResultSet indexInfo = meta.getIndexInfo(dbName, null, tableName, false, true);
            while (indexInfo.next()) {
                String indexName = indexInfo.getString("INDEX_NAME");
                IndexInfo info = indexMap.get(indexName);
                if(ObjectUtil.isNull(info)){
                    info = new IndexInfo();
                    indexMap.put(indexName,info);
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
                info.getColumns().add(IndexInfo.IndexColumn.builder().index(indexInfo.getInt("ORDINAL_POSITION")).columnName(indexInfo.getString("COLUMN_NAME")).build());
            }
        }catch (Exception e){
        }
        return CollectionUtil.newArrayList(indexMap.values());
    }

    public List<ColumnInfo> getColumns(DatabaseMetaData meta,String dbName,String tableName) {
        List<ColumnInfo> columnInfos = new ArrayList<>();
        try {
            ResultSet columns = meta.getColumns(dbName, null, tableName, null);

            ResultSet primaryKeys = meta.getPrimaryKeys(dbName, null,tableName);
            Set<String> primaryNames = new HashSet<>();
            while (primaryKeys.next()){
                //表主键
                primaryNames.add(primaryKeys.getString(TableResultEnum.PK_COLUMN_NAME.getType()));
            }
            while (columns.next()){
                ColumnInfo column = ColumnInfo.builder().columnName(columns.getString(ColumnResultEnum.COLUMN_NAME.getType()))
                        .tableName(columns.getString(ColumnResultEnum.TABLE_NAME.getType()))
                        .dataType(columns.getString(ColumnResultEnum.DATA_TYPE.getType()))
                        .typeName(columns.getString(ColumnResultEnum.TYPE_NAME.getType()))
                        .tableCat(columns.getString(ColumnResultEnum.TABLE_CAT.getType()))
                        .columnSize(columns.getString(ColumnResultEnum.COLUMN_SIZE.getType()))
                        .decimailDigits(columns.getString(ColumnResultEnum.DECIMAL_DIGITS.getType()))
                        .remarks(columns.getString(ColumnResultEnum.REMARKS.getType()))
                        .build();
                column.setAutoincrement("YES".equals(columns.getString(ColumnResultEnum.IS_AUTOINCREMENT.getType())));
                column.setNullable("YES".equals(columns.getString(ColumnResultEnum.IS_NULLABLE.getType())));
                column.setPrimaryKey(primaryNames.contains(column.getColumnName()));
                columnInfos.add(column);
            }
        }catch (Exception e){
            logger.error("db列解析失败+：Exception：{}",e.getMessage());
            e.printStackTrace();
        }
        return columnInfos;
    }

    //No operations allowed after connection closed.
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DatabaseMetaData databaseMetaData = ConnectService.toTest();
        PackInfoService packInfoService = new PackInfoService();
        List<TableDetailInfo> test20231 = packInfoService.getTables(databaseMetaData, "test2023");
        List<ColumnInfo> columns = packInfoService.getColumns(databaseMetaData, "test2023", null);
        List<TableDetailInfo> test2023 = packInfoService.getTables(databaseMetaData, "test2023");

        System.out.println(test2023.size());
        System.out.println(columns.size());
    }
}
