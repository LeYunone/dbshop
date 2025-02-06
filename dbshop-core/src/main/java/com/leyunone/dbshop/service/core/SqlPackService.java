package com.leyunone.dbshop.service.core;

import com.leyunone.dbshop.bean.dto.SqlProductionDTO;

import java.util.List;

/**
 * :)
 * sql语句组装
 *
 * @Author LeYunone
 * @Date 2023/6/7 16:13
 */
public interface SqlPackService {


    /**
     * 列字段比对结果 组装sql
     * Alter table 表名 drop primary key;--删除表主键
     * Alter table 表名 add primary key(`字段`);  --修改某列为主键
     * Alter table 表名 column id int auto_increment=1; --设置自增，默认值为1
     *
     * @param sqlProductionDTO
     */
    List<String> columnContrastPack(SqlProductionDTO sqlProductionDTO);

    /**
     * 两个数据库表的对比后结果sql集
     *
     * @param sqlProductionDTO
     * @return
     */
    List<String> tableContrastPack(SqlProductionDTO sqlProductionDTO);
}

