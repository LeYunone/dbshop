package com.leyunone.dbshop.controller;

import com.leyunone.dbshop.bean.DataResponse;
import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.vo.DbTableContrastVO;
import com.leyunone.dbshop.bean.vo.TableColumnContrastVO;
import com.leyunone.dbshop.service.ContrastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-24
 */
@RestController
@RequestMapping("/contrast")
public class ContrastController {

    @Autowired
    private ContrastService contrastService;

    /**
     * 左右表对比
     * @return
     */
    @GetMapping("/leftRightTableDo")
    public DataResponse<List<TableColumnContrastVO>> leftRightTableContrast(ContrastQuery contrastQuery) {
        List<TableColumnContrastVO> columnContrasts = contrastService.columnContrastToTable(contrastQuery);
        return DataResponse.of(columnContrasts);
    }
    
    @GetMapping("/leftRightDbDo")
    public DataResponse<List<DbTableContrastVO>> leftRightDbContrast(ContrastQuery query){
        List<DbTableContrastVO> dbTableContrastVOS = contrastService.dbTableContrast(query);
        return DataResponse.of(dbTableContrastVOS);
    }
}
