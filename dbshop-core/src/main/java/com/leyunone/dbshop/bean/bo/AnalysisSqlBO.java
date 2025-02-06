package com.leyunone.dbshop.bean.bo;

import com.leyunone.dbshop.enums.SqlModelEnum;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-24
 */
public class AnalysisSqlBO {

    private String sql;

    private SqlModelEnum sqlModel;

    public static AnalysisSqlBO buildAnalysis(String sql, SqlModelEnum sqlModel) {
        return new AnalysisSqlBO().setSql(sql).setSqlModel(sqlModel);
    }

    public String getSql() {
        return sql;
    }

    public AnalysisSqlBO setSql(String sql) {
        this.sql = sql;
        return this;
    }

    public SqlModelEnum getSqlModel() {
        return sqlModel;
    }

    public AnalysisSqlBO setSqlModel(SqlModelEnum sqlModel) {
        this.sqlModel = sqlModel;
        return this;
    }
}
