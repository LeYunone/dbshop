package com.leyunone.dbshop.bean.dto;

import com.leyunone.dbshop.bean.vo.TableColumnContrastVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/7 15:56
 */
@Getter
@Setter
public class SqlProductionDTO {

    private List<TableColumnContrastDTO> columns;
    
    //左右表主副判断  0 左表  1 右表
    private Integer leftOrRight;
    
    //是否进行备注级解析 0否 1是
    private Integer goRemark;
    
    //sql类型转化规则 [0:datetime为datetime(0)]
    private List<Integer> sqlTransformRule;
}
