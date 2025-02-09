package com.leyunone.dbshop.api;

import com.leyunone.dbshop.bean.dto.DbShopDbDTO;
import com.leyunone.dbshop.bean.dto.SqlRuleDTO;
import com.leyunone.dbshop.manager.AnnotateLoadingManager;

import java.lang.annotation.Annotation;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2023/6/19 14:32
 */
public interface DbShopStartAPIService {

    /**
     * 左右表对比
     */
    void leftRightTable();

    /**
     * 左右数据库比较
     */
    void leftRightDbCompare(DbShopDbDTO leftDto, DbShopDbDTO rightDto, SqlRuleDTO sqlRuleDTO);

    /**
     * 根据指定注解 检查没用的表
     *
     * @param dbDTO           表连接信息
     * @param annotateObjects 使用者不使用工具搭建的注解解析 自定义设置判断表名的注解 见 {@link AnnotateLoadingManager}
     */
    void checkUselessTable(DbShopDbDTO dbDTO, AnnotateLoadingManager.AnnotateObject... annotateObjects);

    /**
     * 检查表中无用字段
     *
     * @param dbDTO
     * @param annotateObjects
     */
    void checkTableColumnToCode(DbShopDbDTO dbDTO, AnnotateLoadingManager.AnnotateObject... annotateObjects);
}
