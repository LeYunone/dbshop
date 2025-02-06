package com.leyunone.dbshop.bean.dto;

import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.enums.DataTypeRegularEnum;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/7 15:56
 */
public class SqlProductionDTO {

    private List<TableContrastDTO> tables;

    //左右表主副判断  0 左表  1 右表
    private Integer leftOrRight;

    //是否进行备注级解析 0否 1是
    private Boolean goRemark;

    //是否删除表 0否 1是
    private Boolean deleteTable;

    /**
     * 以下为sql类型转化规则
     * [0：tinyInt(1)变成tinyInt(1)]
     * [0：datetime为datetime(0)]
     */
    private List<DataTypeRegularEnum> transformReg;
    /**
     * 策略
     * [type_transform 类型转换] 
     */
    private List<String> productionStrategys;

    /**
     * 对比查询值
     */
    private ContrastQuery contrastQuery;

    public List<TableContrastDTO> getTables() {
        return tables;
    }

    public SqlProductionDTO setTables(List<TableContrastDTO> tables) {
        this.tables = tables;
        return this;
    }

    public Integer getLeftOrRight() {
        return leftOrRight;
    }

    public SqlProductionDTO setLeftOrRight(Integer leftOrRight) {
        this.leftOrRight = leftOrRight;
        return this;
    }

    public Boolean getGoRemark() {
        return goRemark;
    }

    public SqlProductionDTO setGoRemark(Boolean goRemark) {
        this.goRemark = goRemark;
        return this;
    }

    public Boolean getDeleteTable() {
        return deleteTable;
    }

    public SqlProductionDTO setDeleteTable(Boolean deleteTable) {
        this.deleteTable = deleteTable;
        return this;
    }

    public List<DataTypeRegularEnum> getTransformReg() {
        return transformReg;
    }

    public SqlProductionDTO setTransformReg(List<DataTypeRegularEnum> transformReg) {
        this.transformReg = transformReg;
        return this;
    }

    public List<String> getProductionStrategys() {
        return productionStrategys;
    }

    public SqlProductionDTO setProductionStrategys(List<String> productionStrategys) {
        this.productionStrategys = productionStrategys;
        return this;
    }

    public ContrastQuery getContrastQuery() {
        return contrastQuery;
    }

    public SqlProductionDTO setContrastQuery(ContrastQuery contrastQuery) {
        this.contrastQuery = contrastQuery;
        return this;
    }
}
