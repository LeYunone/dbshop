package com.leyunone.dbshop.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.bean.DataResponse;
import com.leyunone.dbshop.bean.info.ColumnInfoVO;
import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.vo.ColumnContrastVO;
import com.leyunone.dbshop.bean.vo.DbTableContrastVO;
import com.leyunone.dbshop.bean.vo.TableColumnContrastVO;
import com.leyunone.dbshop.service.ContrastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
     *
     * @return
     */
    @GetMapping("/leftRightTableDo")
    public DataResponse<ColumnContrastVO> leftRightTableContrast(ContrastQuery contrastQuery) {
        List<TableColumnContrastVO> columnContrasts = contrastService.tableContrastToTable(contrastQuery);
        //FIXME 后台做页面上的数据分析 因为不会js TAT
        ColumnContrastVO columnContrastVO = new ColumnContrastVO();
        columnContrastVO.setContrastColumnResults(columnContrasts);
        if (CollectionUtil.isNotEmpty(columnContrasts)) {
            List<ColumnInfoVO> leftContrast = new ArrayList<>();
            List<ColumnInfoVO> rightContrast = new ArrayList<>();
            columnContrastVO.setLeftContrast(leftContrast);
            columnContrastVO.setRightContrast(rightContrast);
            for (TableColumnContrastVO tableColumnContrastVO : columnContrasts) {
                ColumnInfoVO leftColumn = JSONObject.parseObject(JSONObject.toJSONString(tableColumnContrastVO.getLeftColumn()), ColumnInfoVO.class);
                ColumnInfoVO rightColumn = JSONObject.parseObject(JSONObject.toJSONString(tableColumnContrastVO.getRightColumn()), ColumnInfoVO.class);
                if (tableColumnContrastVO.getNameDifferent()) {
                    //新增或删除
                    if (ObjectUtil.isNotNull(tableColumnContrastVO.getLeftColumn())) {
                        //右表新增
                        rightColumn = new ColumnInfoVO();
                        BeanUtil.copyProperties(leftColumn,rightColumn);
                        leftColumn.setAddColumn(true);
                        rightContrast.add(leftColumn);
                        leftContrast.add(rightColumn);
                    }
                    if (ObjectUtil.isNotNull(tableColumnContrastVO.getRightColumn())) {
                        //左表新增
                        leftColumn = new ColumnInfoVO();
                        BeanUtil.copyProperties(rightColumn,leftColumn);
                        rightColumn.setAddColumn(true);
                        leftContrast.add(rightColumn);
                        rightContrast.add(leftColumn);
                    }
                } else {
                    //更新
                    leftColumn.setUpdateColumn(tableColumnContrastVO.getHasDifferent());
                    rightColumn.setUpdateColumn(tableColumnContrastVO.getHasDifferent());
                    leftContrast.add(leftColumn);
                    rightContrast.add(rightColumn);
                }
            }
        }
        return DataResponse.of(columnContrastVO);
    }

    @GetMapping("/leftRightDbDo")
    public DataResponse<List<DbTableContrastVO>> leftRightDbContrast(ContrastQuery query) {
        List<DbTableContrastVO> dbTableContrastVOS = contrastService.dbTableContrast(query);
        return DataResponse.of(dbTableContrastVOS);
    }
}
