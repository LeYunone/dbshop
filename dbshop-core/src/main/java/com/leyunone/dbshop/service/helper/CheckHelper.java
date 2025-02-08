package com.leyunone.dbshop.service.helper;

import ch.qos.logback.classic.db.names.TableName;
import com.leyunone.dbshop.bean.info.TableDetailInfo;
import com.leyunone.dbshop.bean.query.DbQuery;
import com.leyunone.dbshop.system.factory.DbDataFactory;
import com.leyunone.dbshop.util.DbStrategyUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    private List<Class<?>> tableAnnotate = new ArrayList<>();

    private final DbDataFactory dbDataFactory;

    public CheckHelper(DbDataFactory dbDataFactory) {
        this.dbDataFactory = dbDataFactory;
        
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
    public void checkUseLessTable(List<Class<?>> classes, String url, String dbName, String userName, String password) {
        DbQuery dbQuery = new DbQuery()
                .setUrl(url)
                .setDbName(dbName)
                .setUserName(userName)
                .setPassWord(password);

        List<TableDetailInfo> tableData = dbDataFactory.getTableData(DbStrategyUtil.getDbStrategy(dbQuery));
        Set<String> tableMap = tableData.stream().map(TableDetailInfo::getTableName).collect(Collectors.toSet());
        List<String> codeTable = new ArrayList<>();
        classes.forEach(clazz -> {
            String table = clazz.getSimpleName();
            //mybatis-plus
//            TableName tableName = clazz.getAnnotation(TableName.class);
//            table = Optional.of(tableName.value()).orElse("");
            codeTable.add(table);
            tableMap.remove(table);
        });
    }
}
