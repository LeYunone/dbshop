package com.leyunone.dbshop.service.core;

import com.leyunone.dbshop.bean.query.DbQuery;

import java.lang.annotation.Annotation;

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
    void useLessTable(Class<? extends Annotation> annotation, DbQuery query);
}
