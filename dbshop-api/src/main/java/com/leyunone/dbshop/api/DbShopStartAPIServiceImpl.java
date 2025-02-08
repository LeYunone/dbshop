package com.leyunone.dbshop.api;

import ch.qos.logback.classic.db.names.TableName;
import ch.qos.logback.core.FileAppender;
import com.leyunone.dbshop.annotate.VersionDescribe;
import com.leyunone.dbshop.bean.ResponseCell;
import com.leyunone.dbshop.bean.dto.DbShopDbDTO;

import com.leyunone.dbshop.bean.dto.SqlRuleDTO;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.TableInfo;
import com.leyunone.dbshop.bean.query.DbQuery;
import com.leyunone.dbshop.bean.rule.SqlCompareRule;
import com.leyunone.dbshop.bean.vo.DbTableContrastVO;
import com.leyunone.dbshop.service.core.impl.ContrastServiceImpl;
import com.leyunone.dbshop.service.core.impl.SqlPackServiceImpl;
import com.leyunone.dbshop.service.helper.ContrastHelper;
import com.leyunone.dbshop.service.helper.DbLoadingHelper;
import com.leyunone.dbshop.system.factory.DbDataFactory;
import com.leyunone.dbshop.util.CollectionFunctionUtils;
import com.leyunone.dbshop.util.DbShopFileUtil;
import com.leyunone.dbshop.util.DbStrategyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * :)
 * 支持代码行直接使用的api
 * 不通过页面，走单元测试形式，进行指定的数据库或表比较
 * 将比较的结果打印到文件中
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-18
 */
@Service
public class DbShopStartAPIServiceImpl implements DbShopStartAPIService {

    private final DbLoadingHelper dbLoadingHelper;
    private final ContrastHelper contrastHelper;

    public DbShopStartAPIServiceImpl(DbLoadingHelper dbLoadingHelper, ContrastHelper contrastHelper) {
        this.dbLoadingHelper = dbLoadingHelper;
        this.contrastHelper = contrastHelper;
    }

    @Override
    public void leftRightTable() {
    }

    @Override
    public void leftRightDbCompare(DbShopDbDTO leftDto, DbShopDbDTO rightDto, SqlRuleDTO sqlRuleDTO) {
        /**
         * 加载数据库
         */
        dbLoadingHelper.loadingData(leftDto.getUrl(), leftDto.getDbName(), leftDto.getUserName(), leftDto.getPassWord());
        dbLoadingHelper.loadingData(rightDto.getUrl(), rightDto.getDbName(), rightDto.getUserName(), rightDto.getPassWord());

        SqlCompareRule sqlCompareRule = new SqlCompareRule()
                .setGoDeep(sqlRuleDTO.getGoDeep())
                .setGoRemark(sqlRuleDTO.getGoRemark())
                .setLeftOrRight(sqlRuleDTO.getLeftOrRight())
                .setTransformReg(sqlRuleDTO.getTransformReg())
                .setDeleteTable(sqlRuleDTO.getDeleteTable());
        //数据库对比
        ResponseCell<List<DbTableContrastVO>, List<String>> compareResult = contrastHelper.dbCompare(leftDto.getUrl(), rightDto.getUrl(), leftDto.getDbName(), rightDto.getDbName(), sqlCompareRule);

        //打印比较结果
        contrastHelper.printComparerResult(compareResult.getCellData(), sqlCompareRule);

        //打印SQL至控制台
        compareResult.getMateData().forEach(System.out::println);

        //写文件
        System.out.println("开始写文件，目标文件: /dbshop.sql");
        File file = new File("dbshop.sql");
        file.deleteOnExit();
        FileAppender writer = new FileAppender(file, 16, true);
        for (String sql : compareResult.getMateData()) {
            writer.append(sql);
        }
        writer.flush();
        System.out.println("写入完成");
    }

    /**
     * 根据指定注解 检查没用的表
     *
     * @param dbDTO           表连接信息
     * @param annotationClass 注解
     */
    @SafeVarargs
    @Override
    @VersionDescribe(
            version = "V1.0.1",
            describe = "版本适用：" +
                    " 仅Mybatis-plus")
    public final void checkUselessTable(DbShopDbDTO dbDTO, Class<? extends Annotation>... annotationClass) {
        //加载数据库
        dbLoadingHelper.loadingData(dbDTO.getUrl(), dbDTO.getDbName(), dbDTO.getUserName(), dbDTO.getPassWord());

        List<Class<?>> classes = DbShopFileUtil.iterationForJavaClass();


        //未被代码使用到的表`
        if (!CollectionUtils.isEmpty(tableMap)) {
            System.out.println("未被代码使用到的表：=========");
            tableMap.forEach(System.out::println);
            System.out.println("======================");
        }
        codeTable.removeIf(tableMap::contains);
        if (!CollectionUtils.isEmpty(codeTable)) {
            System.out.println("未创建的表，多余的实体类：=======");
            codeTable.forEach(System.out::println);
            System.out.println("=======================");
        }
    }

    /**
     * 根据指定实体类注解 检查表与代码字段是否一致
     */
    @SafeVarargs
    @Override
    @VersionDescribe(
            version = "V1.0.1",
            describe = "版本适用：" +
                    " 仅Mybatis-plus")
    public final void checkTableColumnToCode(DbShopDbDTO dbDTO, Class<? extends Annotation>... annotationClass) {
        List<Class<?>> classes = DbShopFileUtil.iterationForJavaClass();
        Map<String, Class<?>> tableEntryMap = new HashMap<>();
        classes.forEach(clazz -> {
            Annotation annotation = clazz.getAnnotation(annotationClass);
            if (ObjectUtil.isNull(annotation)) {
                return;
            }

            if (annotationClass.isAssignableFrom(TableName.class)) {
                //mybatis-plus
                tableEntryMap.put(clazz.getAnnotation(TableName.class).value(), clazz);
            }
        });
        DbQuery dbQuery = new DbQuery();
        dbQuery.setUrl(dbDTO.getUrl());
        dbQuery.setDbName(dbDTO.getDbName());
        dbQuery.setUserName(dbDTO.getUserName());
        dbQuery.setPassWord(dbDTO.getPassWord());
        configService.loadConnectionToData(dbQuery);

        tableEntryMap.forEach((tableName, tableEntry) -> {
            if (StringUtils.isBlank(tableName)) {
                return;
            }
            //属性比表多的
            List<String> fieldDiffs = new ArrayList<>();

            Field[] fields = this.deepFields(tableEntry);
            dbQuery.setTableName(tableName);
            TableInfo tableInfo = dataFactory.getTableInfo(DbStrategyUtil.getTableStrategy(dbQuery));
            List<ColumnInfo> columnInfos = tableInfo.getColumnInfos();
            if (!CollectionUtils.isEmpty(columnInfos)) {
                Map<String, ColumnInfo> tableColumns = CollectionFunctionUtils.mapTo(columnInfos, ColumnInfo::getColumnName);

                //属性比表少的 fieldDiff
                for (Field field : fields) {
                    if (!tableColumns.containsKey(field.getName())) {
                        fieldDiffs.add(field.getName());
                    }
                    tableColumns.remove(field.getName());
                }
                System.out.println("表：" + tableName + " 实体类未存在的字段");
                tableColumns.forEach((columnName, info) -> {
                    System.out.println(columnName + "   :" + info.getRemarks());
                });
            }

            System.out.println("实体类：" + tableName + " 多余的字段");
            fieldDiffs.forEach(System.out::println);

        });
    }

    private Field[] deepFields(Class<?> clazz) {
        List<Field> allFields = new ArrayList<>();
        while (clazz != null) {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                //mybatis判断
                TableField tableField = declaredField.getAnnotation(TableField.class);
                if (ObjectUtil.isNotNull(tableField) && !tableField.exist()) {
                    continue;
                }
                allFields.add(declaredField);
            }
            clazz = clazz.getSuperclass();
        }
        return allFields.toArray(new Field[]{});
    }

}
