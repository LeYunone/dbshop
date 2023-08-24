package com.leyunone.dbshop.bean.dto;

import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.rule.SqlDataTypeTransformRule;
import com.leyunone.dbshop.bean.vo.TableColumnContrastVO;
import com.leyunone.dbshop.enums.DataTypeRegularEnum;
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
    
    private List<IndexDTO> leftIndex;
    
    private List<IndexDTO> rightIndex;
    
    private List<DbTableContrastDTO> dbs;
    
    //左右表主副判断  0 左表  1 右表
    private Integer leftOrRight;

    //索引存在差异 0 不存在 true 存在
    private Boolean indexDifference;
    
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

}
