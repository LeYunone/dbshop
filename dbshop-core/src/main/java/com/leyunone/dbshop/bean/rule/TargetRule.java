package com.leyunone.dbshop.bean.rule;


import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/10 17:23
 */
public class TargetRule {
    
    private List<String> strategys;
    
    //待处理数据
    private String pendingData;
    //目标数据
    private String targetData;

    public List<String> getStrategys() {
        return strategys;
    }

    public TargetRule setStrategys(List<String> strategys) {
        this.strategys = strategys;
        return this;
    }

    public String getPendingData() {
        return pendingData;
    }

    public TargetRule setPendingData(String pendingData) {
        this.pendingData = pendingData;
        return this;
    }

    public String getTargetData() {
        return targetData;
    }

    public TargetRule setTargetData(String targetData) {
        this.targetData = targetData;
        return this;
    }
}
