package com.leyunone.dbshop.bean.dto;

import com.leyunone.dbshop.enums.DataTypeRegularEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * :)
 * SQL规则体
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-19
 */
@Getter
@Setter
public class SqlRuleDTO {


    /**
     * 是否进行深度比对 0否 1是
     */
    private Integer goDeep;

    /**
     * 是否进行备注级比对 0否 1是
     */
    private Integer goRemark;


    //左右表主副判断  0 左表  1 右表
    @NotNull(message = "must be setting a main dbinfo")
    private Integer leftOrRight;

    //是否删除表 0否 1是
    private Integer deleteTable;

    /**
     * 以下为sql类型转化规则
     * [0：tinyInt(1)变成tinyInt(1)]
     * [0：datetime为datetime(0)]
     */
    private List<DataTypeRegularEnum> transformReg;
}
