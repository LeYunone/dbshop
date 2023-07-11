package com.leyunone.dbshop.bean.vo;

import com.leyunone.dbshop.bean.info.ColumnInfoVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 * 字段对比结果集
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-07-11
 */
@Getter
@Setter
public class ColumnContrastVO {

    /**
     * 左表结果集
     */
    private List<ColumnInfoVO> leftContrast;

    /**
     * 右表结果集
     */
    private List<ColumnInfoVO> rightContrast;
}
