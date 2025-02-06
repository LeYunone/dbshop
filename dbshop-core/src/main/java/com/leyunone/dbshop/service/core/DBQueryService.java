package com.leyunone.dbshop.service.core;


import com.leyunone.dbshop.bean.query.DBQuery;
import com.leyunone.dbshop.bean.vo.ColumnInfoVO;
import com.leyunone.dbshop.bean.vo.TableInfoVO;

import java.util.List;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-16
 */
public interface DBQueryService {


    /**
     * 
     * @param query
     */
    List<TableInfoVO> getTableInfos(DBQuery query);
    
    List<ColumnInfoVO> getColumnInfos(DBQuery query);
    
}
