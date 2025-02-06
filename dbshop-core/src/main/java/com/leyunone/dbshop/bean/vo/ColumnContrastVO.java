package com.leyunone.dbshop.bean.vo;


import java.util.List;

/**
 * :)
 * 字段对比结果集
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-07-11
 */
public class ColumnContrastVO {

    /**
     * 左表结果集
     */
    private List<ColumnInfoVO> leftContrast;

    /**
     * 右表结果集
     */
    private List<ColumnInfoVO> rightContrast;
    
    private List<TableColumnContrastVO> contrastColumnResults;

    public List<ColumnInfoVO> getLeftContrast() {
        return leftContrast;
    }

    public ColumnContrastVO setLeftContrast(List<ColumnInfoVO> leftContrast) {
        this.leftContrast = leftContrast;
        return this;
    }

    public List<ColumnInfoVO> getRightContrast() {
        return rightContrast;
    }

    public ColumnContrastVO setRightContrast(List<ColumnInfoVO> rightContrast) {
        this.rightContrast = rightContrast;
        return this;
    }

    public List<TableColumnContrastVO> getContrastColumnResults() {
        return contrastColumnResults;
    }

    public ColumnContrastVO setContrastColumnResults(List<TableColumnContrastVO> contrastColumnResults) {
        this.contrastColumnResults = contrastColumnResults;
        return this;
    }
}
