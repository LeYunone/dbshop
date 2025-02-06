package com.leyunone.dbshop.bean.vo;

import com.leyunone.dbshop.bean.info.IndexInfo;

/**
 * :)
 * 索引对比结果
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-08-31
 */
public class IndexContrastVO {

    private IndexInfo leftIndex;

    private IndexInfo rightIndex;

    //名 true为有差异
    private Boolean nameDifferent;
    
    //有差异
    private Boolean hasDifferent;

    public IndexInfo getLeftIndex() {
        return leftIndex;
    }

    public IndexContrastVO setLeftIndex(IndexInfo leftIndex) {
        this.leftIndex = leftIndex;
        return this;
    }

    public IndexInfo getRightIndex() {
        return rightIndex;
    }

    public IndexContrastVO setRightIndex(IndexInfo rightIndex) {
        this.rightIndex = rightIndex;
        return this;
    }

    public Boolean getNameDifferent() {
        return nameDifferent;
    }

    public IndexContrastVO setNameDifferent(Boolean nameDifferent) {
        this.nameDifferent = nameDifferent;
        return this;
    }

    public Boolean getHasDifferent() {
        return hasDifferent;
    }

    public IndexContrastVO setHasDifferent(Boolean hasDifferent) {
        this.hasDifferent = hasDifferent;
        return this;
    }
}
