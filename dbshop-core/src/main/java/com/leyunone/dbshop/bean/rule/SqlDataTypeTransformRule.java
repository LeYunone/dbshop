package com.leyunone.dbshop.bean.rule;

import com.leyunone.dbshop.enums.DataTypeRegularEnum;

import java.util.List;

/**
 * :)
 * sql类型转化规则
 * @Author LeYunone
 * @Date 2023/6/10 17:22
 */
public class SqlDataTypeTransformRule extends TargetRule{
    
    /**
     * [0:datetime字段定位时 长度为0]
     * [1:bit1 转为原本的tinyint(1) ]
     */
    private List<DataTypeRegularEnum> transformReg;

    public List<DataTypeRegularEnum> getTransformReg() {
        return transformReg;
    }

    public SqlDataTypeTransformRule setTransformReg(List<DataTypeRegularEnum> transformReg) {
        this.transformReg = transformReg;
        return this;
    }
}
