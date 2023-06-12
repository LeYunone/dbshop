package com.leyunone.dbshop.bean.rule;

import lombok.Getter;
import lombok.Setter;

/**
 * :)
 * sql类型转化规则
 * @Author LeYunone
 * @Date 2023/6/10 17:22
 */
@Getter
@Setter
public class SqlDataTypeTransformRule extends TargetRule{

    //datetime字段定位时 长度为0
    private Integer dateTimeTo_0;

    private String sql;
}
