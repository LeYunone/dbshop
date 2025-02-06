package com.leyunone.dbshop.bean.query;


/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-18
 */
public class ContrastQuery {
    
    private String leftUrl;
    
    private String rightUrl;
    
    private String leftDbName;
    
    private String rightDbName;
    
    private String leftTableName;
    
    private String rightTableName;

    /**
     * 是否进行深度比对 true是
     */
    private Boolean goDeep;

    /**
     * 是否进行备注级比对 true是
     */
    private Boolean goRemark;

    public String getLeftUrl() {
        return leftUrl;
    }

    public ContrastQuery setLeftUrl(String leftUrl) {
        this.leftUrl = leftUrl;
        return this;
    }

    public String getRightUrl() {
        return rightUrl;
    }

    public ContrastQuery setRightUrl(String rightUrl) {
        this.rightUrl = rightUrl;
        return this;
    }

    public String getLeftDbName() {
        return leftDbName;
    }

    public ContrastQuery setLeftDbName(String leftDbName) {
        this.leftDbName = leftDbName;
        return this;
    }

    public String getRightDbName() {
        return rightDbName;
    }

    public ContrastQuery setRightDbName(String rightDbName) {
        this.rightDbName = rightDbName;
        return this;
    }

    public String getLeftTableName() {
        return leftTableName;
    }

    public ContrastQuery setLeftTableName(String leftTableName) {
        this.leftTableName = leftTableName;
        return this;
    }

    public String getRightTableName() {
        return rightTableName;
    }

    public ContrastQuery setRightTableName(String rightTableName) {
        this.rightTableName = rightTableName;
        return this;
    }

    public Boolean getGoDeep() {
        return goDeep;
    }

    public ContrastQuery setGoDeep(Boolean goDeep) {
        this.goDeep = goDeep;
        return this;
    }

    public Boolean getGoRemark() {
        return goRemark;
    }

    public ContrastQuery setGoRemark(Boolean goRemark) {
        this.goRemark = goRemark;
        return this;
    }
}
