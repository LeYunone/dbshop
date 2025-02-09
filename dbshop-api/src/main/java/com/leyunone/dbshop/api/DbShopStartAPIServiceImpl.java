package com.leyunone.dbshop.api;

import com.leyunone.dbshop.annotate.VersionDescribe;
import com.leyunone.dbshop.bean.ResponseCell;
import com.leyunone.dbshop.bean.dto.DbShopDbDTO;

import com.leyunone.dbshop.bean.dto.SqlRuleDTO;
import com.leyunone.dbshop.bean.rule.SqlCompareRule;
import com.leyunone.dbshop.bean.vo.DbTableContrastVO;
import com.leyunone.dbshop.manager.AnnotateLoadingManager;
import com.leyunone.dbshop.service.helper.CheckHelper;
import com.leyunone.dbshop.service.helper.ContrastHelper;
import com.leyunone.dbshop.service.helper.DbLoadingHelper;
import com.leyunone.dbshop.util.DbShopFileUtil;
import com.leyunone.dbshop.util.FileAppender;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
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
    private final CheckHelper checkHelper;

    public DbShopStartAPIServiceImpl(DbLoadingHelper dbLoadingHelper, ContrastHelper contrastHelper, CheckHelper checkHelper) {
        this.dbLoadingHelper = dbLoadingHelper;
        this.contrastHelper = contrastHelper;
        this.checkHelper = checkHelper;
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
     * @param annotateObjects 使用者不使用工具搭建的注解解析 自定义设置判断表名的注解 见 {@link AnnotateLoadingManager}
     */
    @Override
    @VersionDescribe(
            version = "V1.0.2",
            describe = "版本描述：" +
                    " 当前默认配置仅有：Mybatis-plus  建议自行打上解析注解AnnotateObject")
    public final void checkUselessTable(DbShopDbDTO dbDTO, AnnotateLoadingManager.AnnotateObject... annotateObjects) {
        //加载数据库
        dbLoadingHelper.loadingData(dbDTO.getUrl(), dbDTO.getDbName(), dbDTO.getUserName(), dbDTO.getPassWord());

        List<Class<?>> allClass = DbShopFileUtil.iterationForJavaClass();
        ResponseCell<Set<String>, Set<String>> checkUseLessResult = checkHelper.checkUseLessTable(allClass, dbDTO.getUrl(), dbDTO.getDbName(), dbDTO.getUserName(), dbDTO.getPassWord(), annotateObjects);
        Set<String> tableMap = checkUseLessResult.getCellData();
        Set<String> codeTable = checkUseLessResult.getMateData();
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
    @Override
    @VersionDescribe(
            version = "V1.0.2",
            describe = "版本描述：" +
                    " 当前默认配置仅有：Mybatis-plus  建议自行打上解析注解AnnotateObject")
    public final void checkTableColumnToCode(DbShopDbDTO dbDTO, AnnotateLoadingManager.AnnotateObject... annotationClass) {
        List<Class<?>> classes = DbShopFileUtil.iterationForJavaClass();
        //加载数据库
        dbLoadingHelper.loadingData(dbDTO.getUrl(), dbDTO.getDbName(), dbDTO.getUserName(), dbDTO.getPassWord());

        ResponseCell<Map<String, List<String>>, Map<String, List<String>>> checkUseLessResult = checkHelper.checkUseLessField(classes, dbDTO.getUrl(), dbDTO.getDbName(), dbDTO.getUserName(), dbDTO.getPassWord(), annotationClass);
        Map<String, List<String>> codeUseLess = checkUseLessResult.getCellData();
        Map<String, List<String>> tableUseLess = checkUseLessResult.getMateData();
        codeUseLess.forEach((key, value) -> {
            System.out.println("表：" + key + " 实体类(/可能未存在)未存在的字段");
            value.forEach(System.out::println);
        });
        tableUseLess.forEach((key, value) -> {
            System.out.println("实体类：" + key + " 多余的字段");
            value.forEach(System.out::println);
        });
    }

}
