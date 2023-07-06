package com.leyunone.dbshop.enums;

import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-13
 */
public enum DataTypeRegularEnum {

    /**
     * 类型转化替换正则
     */
    DATETIME_TO_0(0, "DATETIME[(][0-9]*[)]", "DATETIME(0)", "datetime(?)类型变为datetime(0)"),

    BIT1_TO_TINYINT1(1, "BIT[(][1][)]", "TINYINT(1)", "bit(1)类型变为tinyint(1)"),

    JSON_TO_0(2,"JSON[(][0-9]*[)]","JSON(0)","json(?)类型变为json(0)")
    ;

    DataTypeRegularEnum(Integer type, String reg, String toBecome, String desc) {
        this.type = type;
        this.reg = reg;
        this.toBecome = toBecome;
        this.desc = desc;
    }

    private Integer type;

    private String reg;

    private String toBecome;

    private String desc;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getToBecome() {
        return toBecome;
    }

    public void setToBecome(String toBecome) {
        this.toBecome = toBecome;
    }

    public static List<DataTypeRegularEnum> getEnums(List<Integer> types) {
        List<DataTypeRegularEnum> enums = new ArrayList<>();
        if(CollectionUtil.isEmpty(types)) return enums;
        for(Integer type:types){
            for(DataTypeRegularEnum dataTypeRegularEnum : DataTypeRegularEnum.values()){
                if(dataTypeRegularEnum.getType().equals(type)){
                    enums.add(dataTypeRegularEnum);
                }
            }
        }
        return enums;
    }
}

    
