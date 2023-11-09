package com.leyunone.dbshop.api;

import ch.qos.logback.classic.db.names.TableName;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.file.FileAppender;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.bean.dto.DbShopDbDTO;

import com.leyunone.dbshop.bean.dto.SqlProductionDTO;
import com.leyunone.dbshop.bean.dto.SqlRuleDTO;
import com.leyunone.dbshop.bean.dto.TableContrastDTO;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.TableDetailInfo;
import com.leyunone.dbshop.bean.info.TableInfo;
import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.query.DBQuery;
import com.leyunone.dbshop.bean.vo.DbTableContrastVO;
import com.leyunone.dbshop.bean.vo.TableColumnContrastVO;
import com.leyunone.dbshop.constant.DbShopConstant;
import com.leyunone.dbshop.service.ConfigService;
import com.leyunone.dbshop.service.ContrastService;
import com.leyunone.dbshop.service.SqlPackService;
import com.leyunone.dbshop.system.factory.DBDataFactory;
import com.leyunone.dbshop.util.CollectionFunctionUtils;
import com.leyunone.dbshop.util.DbStrategyUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
@RequiredArgsConstructor
public class DbShopStartAPIServiceImpl implements DbShopStartAPIService {

    private final ConfigService configService;
    private final ContrastService contrastService;
    private final SqlPackService sqlPackService;
    private final DBDataFactory dataFactory;

    @Override
    public void leftRightTable() {
    }

    @Override
    public void leftRightDb(DbShopDbDTO leftDto, DbShopDbDTO rightDto, SqlRuleDTO sqlRuleDTO) {
        DBQuery leftQuery = new DBQuery();
        leftQuery.setUrl(leftDto.getUrl());
        leftQuery.setDbName(leftDto.getDbName());
        leftQuery.setUserName(leftDto.getUserName());
        leftQuery.setPassWord(leftDto.getPassWord());
        DBQuery rightQuery = new DBQuery();
//        rightQuery.setUrl("jdbc:mysql://localhost:3306/test2023-1?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
        rightQuery.setUrl(rightDto.getUrl());
        rightQuery.setDbName(rightDto.getDbName());
        rightQuery.setUserName(rightDto.getUserName());
        rightQuery.setPassWord(rightDto.getPassWord());

        /**
         * 加载数据库
         */
        configService.loadConnectionToData(leftQuery);
        configService.loadConnectionToData(rightQuery);

        ContrastQuery contrastQuery = new ContrastQuery();
        contrastQuery.setGoRemark(sqlRuleDTO.getGoRemark());
        contrastQuery.setGoDeep(sqlRuleDTO.getGoDeep());

        contrastQuery.setLeftDbName(leftQuery.getDbName());
        contrastQuery.setRightDbName(rightQuery.getDbName());

        contrastQuery.setLeftUrl(leftQuery.getUrl());
        contrastQuery.setRightUrl(rightQuery.getUrl());
        List<DbTableContrastVO> dbTableContrast = contrastService.dbTableContrast(contrastQuery);

        //输出两表对比结果
        System.out.println("=============对比结果===============");
        System.out.println();
        List<String> newTable = new ArrayList<>();
        for (DbTableContrastVO dbTableContrastVO : dbTableContrast) {
            TableDetailInfo mainTable = sqlRuleDTO.getLeftOrRight().equals(0) ? dbTableContrastVO.getLeftTableDetailInfo() : dbTableContrastVO.getRightTableDetailInfo();
            TableDetailInfo anotherTable = !sqlRuleDTO.getLeftOrRight().equals(0) ? dbTableContrastVO.getLeftTableDetailInfo() : dbTableContrastVO.getRightTableDetailInfo();
            if (dbTableContrastVO.getNameDifference()) {
                if (ObjectUtil.isNull(mainTable)) {
                    newTable.add("删除表： " + anotherTable.getTableName());
                } else {
                    newTable.add("新增表： " + mainTable.getTableName());
                }
                continue;
            }
            if (dbTableContrastVO.getHasDifference()) {
                System.out.println("表： [" + mainTable.getTableName() + "] 中以下字段有差异");
                List<TableColumnContrastVO> columnContrasts = dbTableContrastVO.getColumnContrasts();
                for (TableColumnContrastVO columnContrastVO : columnContrasts) {
                    if (columnContrastVO.getNameDifferent() || columnContrastVO.getTypeDifferent() ||
                            columnContrastVO.getSizeDifferent() || columnContrastVO.getAutoincrementDifferent()
                            || columnContrastVO.getPrimaryKeyDifferent() || (DbShopConstant.Rule_Yes.equals(sqlRuleDTO.getGoRemark()) && columnContrastVO.getRemarkDifferent())) {
                        String columnName = ObjectUtil.isNotNull(columnContrastVO.getRightColumn()) ? columnContrastVO.getRightColumn().getColumnName() : columnContrastVO.getLeftColumn().getColumnName();
                        System.out.println("字段:" + columnName);
                    }

                }
                System.out.println();
            }
        }
        System.out.println("-----------表级操作------------");
        newTable.forEach(System.out::println);
        System.out.println("-----------------------------");
        System.out.println();

        SqlProductionDTO sqlProductionDTO = new SqlProductionDTO();
        sqlProductionDTO.setLeftOrRight(sqlRuleDTO.getLeftOrRight());
        sqlProductionDTO.setGoRemark(contrastQuery.getGoRemark());
        sqlProductionDTO.setTransformReg(sqlRuleDTO.getTransformReg());
        sqlProductionDTO.setDeleteTable(sqlRuleDTO.getDeleteTable());
        //TODO HUTOOL 版本问题 谨慎使用BeanUtil.copyToList
        sqlProductionDTO.setTables(JSONObject.parseArray(JSONObject.toJSONString(dbTableContrast), TableContrastDTO.class));
        List<String> resultSql = sqlPackService.tableContrastPack(sqlProductionDTO);

        //写文件
        System.out.println("开始写文件，目标文件: /dbshop.sql");
        File file = new File("dbshop.sql");
        file.delete();
        FileAppender writer = new FileAppender(file, 16, true);
        for (String sql : resultSql) {
            writer.append(sql);
        }
        writer.flush();
        System.out.println("写入完成");

        //打印至控制台
        for (String sql : resultSql) {
            System.out.println(sql);
        }
    }

    /**
     * 根据指定注解 检查没用的表
     *
     * @param dbDTO           表连接信息
     * @param annotationClass 注解
     * @param filePath        文件路径
     * @param dbCode_ true为代码为准  false为数据库为准
     */
    public void checkUselessTable(DbShopDbDTO dbDTO, Class<? extends Annotation> annotationClass, String filePath,boolean dbCode_) {
        if (StringUtils.isBlank(filePath)) return;
        if (!new File(filePath).exists()) return;
        DBQuery dbQuery = new DBQuery();
        dbQuery.setUrl(dbDTO.getUrl());
        dbQuery.setDbName(dbDTO.getDbName());
        dbQuery.setUserName(dbDTO.getUserName());
        dbQuery.setPassWord(dbDTO.getPassWord());

        //加载数据库
        configService.loadConnectionToData(dbQuery);
        List<TableDetailInfo> tableData = dataFactory.getTableData(DbStrategyUtil.getDbStrategy(dbQuery));
        List<Class> clazzs = new ArrayList<>();
        Set<String> tableMap = tableData.stream().map(TableDetailInfo::getTableName).collect(Collectors.toSet());
        List<String> codeTable = new ArrayList<>();
        clazzs.forEach(clazz->{
            Annotation annotation = clazz.getAnnotation(annotationClass);
            String tableName = clazz.getSimpleName();
            if(dbCode_) {
                codeTable.add(tableName);
            }else{
                tableMap.remove(tableName);
            }
        });
        //未被代码使用到的表
        if(!dbCode_ && CollectionUtil.isNotEmpty(tableMap)){
            System.out.println("未被代码使用到的表：=========");
            tableMap.forEach(System.out::println);
            System.out.println("======================");
        }
        if(dbCode_ && CollectionUtil.isNotEmpty(codeTable)){
            System.out.println("未创建的表，多余的实体类：=======");
            codeTable.forEach(System.out::println);
            System.out.println("=======================");
        }

    }

    /**
     * 根据指定注解 检查表与代码字段是否一致
     */
    public void checkTableToCode(DbShopDbDTO dbDTO,Class<? extends Annotation> annotationClass,File file) {
        List<Class> clazzs = new ArrayList<>();
        List<Class> tableEntry = new ArrayList<>();
        clazzs.forEach(clazz->{
            if(ObjectUtil.isNotNull(clazz.getAnnotation(annotationClass))){
                tableEntry.add(clazz);
            }
        });
        DBQuery dbQuery = new DBQuery();
        dbQuery.setUrl(dbDTO.getUrl());
        dbQuery.setDbName(dbDTO.getDbName());
        dbQuery.setUserName(dbDTO.getUserName());
        dbQuery.setPassWord(dbDTO.getPassWord());
        configService.loadConnectionToData(dbQuery);

        for (Class aClass : tableEntry) {
            Field[] fields = aClass.getFields();
            Field[] declaredFields = aClass.getDeclaredFields();
            ArrayList<Field> allField = CollectionUtil.newArrayList(fields);
            allField.addAll(CollectionUtil.newArrayList(declaredFields));
            Annotation annotation = aClass.getAnnotation(annotationClass);
//            dbQuery.setTableName();
            TableInfo tableInfo = dataFactory.getTableInfo(DbStrategyUtil.getTableStrategy(dbQuery));
            List<ColumnInfo> columnInfos = tableInfo.getColumnInfos();
            if(CollectionUtil.isNotEmpty(columnInfos)){
                Map<String, ColumnInfo> tableColumns = CollectionFunctionUtils.mapTo(columnInfos, ColumnInfo::getColumnName);
                boolean hasDifferent = false;

                //属性比表多的
                List<String> fieldDiffs = new ArrayList<>();
                //属性比表少的 fieldDiff
                for (Field field : allField) {
                    if(!tableColumns.containsKey(field.getName())){
                        hasDifferent = true;
                        fieldDiffs.add(file.getName());
                    }
                }

                if(hasDifferent){

                }
            }
        }
    }
}
