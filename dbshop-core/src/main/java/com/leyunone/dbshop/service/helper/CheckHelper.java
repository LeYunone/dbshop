package com.leyunone.dbshop.service.helper;

import com.leyunone.dbshop.bean.ResponseCell;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.TableDetailInfo;
import com.leyunone.dbshop.bean.info.TableInfo;
import com.leyunone.dbshop.bean.query.DbQuery;
import com.leyunone.dbshop.manager.AnnotateLoadingManager;
import com.leyunone.dbshop.system.factory.DbDataFactory;
import com.leyunone.dbshop.util.CollectionFunctionUtils;
import com.leyunone.dbshop.util.DbStrategyUtil;
import com.leyunone.dbshop.util.MyClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * :)
 * 检查者
 *
 * @Author pengli
 * @Date 2025/2/8 14:23
 */
@Service
public class CheckHelper {


    private final DbDataFactory dbDataFactory;
    private final AnnotateLoadingManager annotateLoadingManager;

    public CheckHelper(DbDataFactory dbDataFactory, AnnotateLoadingManager annotateLoadingManager) {
        this.dbDataFactory = dbDataFactory;

        this.annotateLoadingManager = annotateLoadingManager;
    }

    /**
     * 检查无用的表
     *
     * @param classes  检查文件
     * @param url      数据库路径
     * @param dbName   数据库名
     * @param userName 用户名
     * @param password 密码
     */
    public ResponseCell<Set<String>, Set<String>> checkUseLessTable(List<Class<?>> classes, String url, String dbName, String userName, String password, AnnotateLoadingManager.AnnotateObject... annotateObjects) {
        DbQuery dbQuery = new DbQuery()
                .setUrl(url)
                .setDbName(dbName)
                .setUserName(userName)
                .setPassWord(password);

        List<TableDetailInfo> tableData = dbDataFactory.getTableData(DbStrategyUtil.getDetailStrategy(dbQuery));
        Set<String> tableMap = tableData.stream().map(TableDetailInfo::getTableName).collect(Collectors.toSet());
        Set<String> codeTable = new HashSet<>();
        classes.forEach(clazz -> {
            String table = null;
            if (null != annotateObjects && annotateObjects.length > 0) {
                //根据使用者自定义设置的范围
                for (AnnotateLoadingManager.AnnotateObject annotateObject : annotateObjects) {
                    String annotateValue = MyClassUtils.getAnnotateValue(clazz, annotateObject.getClazz(), annotateObject.getValue());
                    if (StringUtils.isNotBlank(annotateValue)) {
                        table = annotateValue;
                        break;
                    }
                }
            } else {
                table = annotateLoadingManager.tableNameAnnotateValue(clazz);
            }
            if (null == table) {
                return;
            }
            if (StringUtils.isNotBlank(table) && !tableMap.contains(table)) {
                codeTable.add(table);
            }
            tableMap.remove(table);
        });
        return ResponseCell.build(tableMap, codeTable);
    }

    /**
     * 检查无用的字段
     *
     * @param classes  检查文件
     * @param url      数据库路径
     * @param dbName   数据库名
     * @param userName 用户名
     * @param password 密码
     */
    public ResponseCell<Map<String, List<String>>, Map<String, List<String>>> checkUseLessField(List<Class<?>> classes, String url, String dbName, String userName, String password, AnnotateLoadingManager.AnnotateObject... annotateObjects) {
        DbQuery dbQuery = new DbQuery()
                .setUrl(url)
                .setDbName(dbName)
                .setUserName(userName)
                .setPassWord(password);
        Map<String, Class<?>> tableEntryMap = new HashMap<>();

        //拿到项目内所有实体类
        classes.forEach(clazz -> {
            String tableName = null;
            if (null != annotateObjects && annotateObjects.length > 0) {
                //根据使用者自定义设置的范围
                tableName = this.customAnnotateIterator(clazz, annotateObjects);
            } else {
                tableName = annotateLoadingManager.tableNameAnnotateValue(clazz);
            }
            if (StringUtils.isNotBlank(tableName)) {
                tableEntryMap.put(tableName, clazz);
            }
        });

        Map<String, List<String>> codeUseLess = new HashMap<>();
        Map<String, List<String>> tableUseLess = new HashMap<>();
        tableEntryMap.forEach((tableName, tableEntry) -> {
            if (StringUtils.isBlank(tableName)) {
                return;
            }
            //属性比表多的
            List<String> fieldDiffs = new ArrayList<>();

            List<String> columns = this.deepFields(tableEntry);
            dbQuery.setTableName(tableName);
            TableInfo tableInfo = dbDataFactory.getTableInfo(DbStrategyUtil.getTableStrategy(dbQuery));
            List<ColumnInfo> columnInfos = tableInfo.getColumnInfos();
            if (!CollectionUtils.isEmpty(columnInfos)) {
                Map<String, ColumnInfo> tableColumns = CollectionFunctionUtils.mapTo(columnInfos, ColumnInfo::getColumnName);

                //属性比表少的 fieldDiff
                columns.forEach(column -> {
                    if (!tableColumns.containsKey(column)) {
                        fieldDiffs.add(column);
                    }
                    tableColumns.remove(column);
                });
                codeUseLess.put(tableName, tableColumns.keySet().stream().map(c -> "字段：" + c + "   备注信息:" + tableColumns.get(c).getRemarks()).collect(Collectors.toList()));
            }
            if (!CollectionUtils.isEmpty(fieldDiffs)) {
                tableUseLess.put(tableName, fieldDiffs);
            }
        });
        return ResponseCell.build(codeUseLess, tableUseLess);
    }

    private String customAnnotateIterator(Class<?> fileClazz, AnnotateLoadingManager.AnnotateObject... annotateObjects) {
        //根据使用者自定义设置的范围
        for (AnnotateLoadingManager.AnnotateObject annotateObject : annotateObjects) {
            String annotateValue = MyClassUtils.getAnnotateValue(fileClazz, annotateObject.getClazz(), annotateObject.getValue());
            if (StringUtils.isNotBlank(annotateValue)) {
                return annotateValue;
            }
        }
        return null;
    }

    private List<String> deepFields(Class<?> clazz, AnnotateLoadingManager.AnnotateObject... annotateObjects) {
        List<String> allColumns = new ArrayList<>();
        while (clazz != null) {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                String column = declaredField.getName();
                if (null != annotateObjects && annotateObjects.length > 0) {
                    //根据使用者自定义设置的范围
                    String s = this.customAnnotateIterator(clazz, annotateObjects);
                    if (StringUtils.isNotBlank(s)) {
                        column = s;
                    }
                } else {
                    String checkIfField = annotateLoadingManager.checkIfField(clazz);
                    if (StringUtils.isNotBlank(checkIfField)) {
                        column = checkIfField;
                    }
                }
                allColumns.add(column);
            }
            clazz = clazz.getSuperclass();
        }
        return allColumns;
    }
}
