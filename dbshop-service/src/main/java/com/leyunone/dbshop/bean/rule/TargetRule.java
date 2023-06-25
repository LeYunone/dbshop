package com.leyunone.dbshop.bean.rule;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/10 17:23
 */
@Getter
@Setter
public class TargetRule {
    
    private List<String> strategys;
    
    //待处理数据
    private String pendingData;
    //目标数据
    private String targetData;
}
