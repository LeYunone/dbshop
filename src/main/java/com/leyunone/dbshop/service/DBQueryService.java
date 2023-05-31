package com.leyunone.dbshop.service;

import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.TableInfo;
import com.leyunone.dbshop.bean.query.DBQuery;
import com.leyunone.dbshop.system.factory.DBDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-16
 */
@Service
public class DBQueryService {

    @Autowired
    private ConnectService connectService;
    @Autowired
    private PackInfoService packInfoService;
    @Autowired
    private DBDataFactory dbDataFactory;

    /**
     * 
     * @param query
     */
    public List<TableInfo> getTableInfos(DBQuery query) {
        return null;
    }
    
    public List<ColumnInfo> getColumnInfos(DBQuery query){
        return null;
    }
    
}
