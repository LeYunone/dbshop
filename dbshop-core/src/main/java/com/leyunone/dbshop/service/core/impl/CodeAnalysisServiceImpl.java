package com.leyunone.dbshop.service.core.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.dbshop.bean.info.TableDetailInfo;
import com.leyunone.dbshop.bean.query.DBQuery;
import com.leyunone.dbshop.service.core.CodeAnalysisService;
import com.leyunone.dbshop.system.factory.DBDataFactory;
import com.leyunone.dbshop.util.DbStrategyUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * :)
 * 代码剖析服务
 *
 * @Author leyunone
 * @Date 2023/9/18 17:15
 */
@Service
public class CodeAnalysisServiceImpl implements CodeAnalysisService {

    private final DBDataFactory dbDataFactory;

    public CodeAnalysisServiceImpl(DBDataFactory dbDataFactory) {
        this.dbDataFactory = dbDataFactory;
    }

    /**
     * 获得代码中涉及的无用表
     * <p>
     * 1、注解中没有这个值
     * 2、mapper.xml中没有这张表的Sql
     */
    @Override
    public void useLessTable(Class<? extends Annotation> annotation, File file, DBQuery query) {
        if (ObjectUtil.isNull(file)) {
            return;
        }
        List<TableDetailInfo> tableData = dbDataFactory.getTableData(DbStrategyUtil.getTableStrategy(query));
        if (CollectionUtil.isEmpty(tableData)) {
            return;
        }

        if (ObjectUtil.isNull(annotation)) {
            //TODO 注解为空的情况下 老项目读取配置文件
        } else {
            //读取文件，拿到所有被指定注解修饰的类.java或.class

        }
    }
}
