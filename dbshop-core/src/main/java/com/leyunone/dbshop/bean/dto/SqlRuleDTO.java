package com.leyunone.dbshop.bean.dto;

import com.leyunone.dbshop.enums.DataTypeRegularEnum;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * :)
 * SQL规则体
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-19
 */
public class SqlRuleDTO {


    /**
     * 是否进行深度比对 true是
     */
    private Boolean goDeep;

    /**
     * 是否进行备注级比对 true是
     */
    private Boolean goRemark;


    //左右表主副判断  0 左表  1 右表
    @NotNull(message = "must be setting a main dbinfo")
    private Integer leftOrRight;

    //是否删除表 true是
    private Boolean deleteTable;

    /**
     * 以下为sql类型转化规则
     * [0：tinyInt(1)变成tinyInt(1)]
     * [0：datetime为datetime(0)]
     */
    private List<DataTypeRegularEnum> transformReg;

    public Boolean getGoDeep() {
        return goDeep;
    }

    public SqlRuleDTO setGoDeep(Boolean goDeep) {
        this.goDeep = goDeep;
        return this;
    }

    public Boolean getGoRemark() {
        return goRemark;
    }

    public SqlRuleDTO setGoRemark(Boolean goRemark) {
        this.goRemark = goRemark;
        return this;
    }

    public Integer getLeftOrRight() {
        return leftOrRight;
    }

    public SqlRuleDTO setLeftOrRight(Integer leftOrRight) {
        this.leftOrRight = leftOrRight;
        return this;
    }

    public Boolean getDeleteTable() {
        return deleteTable;
    }

    public SqlRuleDTO setDeleteTable(Boolean deleteTable) {
        this.deleteTable = deleteTable;
        return this;
    }

    public List<DataTypeRegularEnum> getTransformReg() {
        return transformReg;
    }

    public SqlRuleDTO setTransformReg(List<DataTypeRegularEnum> transformReg) {
        this.transformReg = transformReg;
        return this;
    }
}
