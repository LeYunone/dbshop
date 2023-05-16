package com.leyunone.dbshop.controller;

import com.leyunone.dbshop.bean.DataResponse;
import com.leyunone.dbshop.bean.query.DBQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查询db
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-09
 */
@RestController
@RequestMapping("/dbquery")
public class DbQueryController {

    /**
     * 一个指定数据库库的信息
     */
    @GetMapping("/alonedbinfo")
    public DataResponse aloneDbInfo(DBQuery query) {
        //将这个数据库的所有表罗列出来
        return DataResponse.of();
    }

    /**
     * 查看指定数据库 指定表的信息
     */
    @GetMapping("/alonetableinfo")
    public DataResponse aloneTableInfo(DBQuery query){
        return DataResponse.of();
    }
    
    
    
}
