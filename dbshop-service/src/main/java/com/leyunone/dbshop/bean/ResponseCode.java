package com.leyunone.dbshop.bean;

/**
 * @author leyunone
 * @date  2021-08-13 15:07
 *
 * 响应control的编码
 */
public enum  ResponseCode {
    /**
     * 响应结果集
     */
    SUCCESS("200", "操作成功"),
    ERROR("404", "操作失败"),
    RPC_UNKNOWN_ERROR("100017", "远程服务调用未知错误"),
    RPC_TIMEOUT("100016", "远程服务调用超时"),
    RPC_ERROR_503("100015", "远程服务不可用"),
    
    
    //==========1000000001
    RULE_LOAD_FAIL("1000000001","规则加载失败"),
    INFO_NOT_FOUND("1000000002","数据库信息找不到或不存在")


;
    private final String code;
    private final String desc;

    ResponseCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

}
