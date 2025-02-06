package com.leyunone.dbshop.service.core;


import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.vo.DbTableContrastVO;
import com.leyunone.dbshop.bean.vo.TableContrastVO;

import java.util.List;

/**
 * 对比服务
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-17
 */
public interface ContrastService {

    TableContrastVO tableContrastToTable(ContrastQuery contrastQuery);

    /**
     * 两个数据库对比结果集
     *
     * @param contrastQuery
     */
    List<DbTableContrastVO> dbTableContrast(ContrastQuery contrastQuery);

}
