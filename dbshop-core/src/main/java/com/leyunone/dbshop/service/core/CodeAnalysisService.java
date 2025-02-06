package com.leyunone.dbshop.service.core;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.dbshop.bean.info.TableDetailInfo;
import com.leyunone.dbshop.bean.query.DBQuery;
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
public interface CodeAnalysisService {

    /**
     * 获得代码中涉及的无用表
     * <p>
     * 1、注解中没有这个值
     * 2、mapper.xml中没有这张表的Sql
     */
    void useLessTable(Class<? extends Annotation> annotation, File file, DBQuery query);
}
