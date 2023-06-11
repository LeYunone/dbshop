package com.leyunone.dbshop.enums;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-11
 */
public enum  RuleTypeEnum {

    /**
     * 定义规则是否有结果集返回
     */
    NONE(0,"无结果集返回"),
    RESULT(1,"有结果集返回")
    ;

    RuleTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private Integer type;

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
}
