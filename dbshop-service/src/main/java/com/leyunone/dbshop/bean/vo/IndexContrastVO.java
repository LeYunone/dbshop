package com.leyunone.dbshop.bean.vo;

import com.leyunone.dbshop.bean.info.IndexInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * :)
 * 索引对比结果
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-08-31
 */
@Getter
@Setter
public class IndexContrastVO {

    private IndexInfo leftIndex;

    private IndexInfo rightIndex;

    //名 true为有差异
    private Boolean nameDifferent;
}
