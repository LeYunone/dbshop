package com.leyunone.dbshop.api;

import com.leyunone.dbshop.bean.dto.DbShopDbDTO;
import com.leyunone.dbshop.bean.dto.SqlRuleDTO;

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
}
