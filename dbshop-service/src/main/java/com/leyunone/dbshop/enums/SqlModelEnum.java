package com.leyunone.dbshop.enums;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-07
 */
public enum SqlModelEnum {

    //SQL语句模板
    //采用{}进行内容填充

    ADD_COLUMN("ALTER TABLE {} ADD COLUMN {} {}({}) {} COMMENT '{}' ;", "新增字段"),

    MODIFY_COLUMN("ALTER TABLE {} modify column {} {}({}) {} COMMENT '{}' ;", "修改字段"),

    DELETE_COLUMN("ALTER TABLE {} DROP COLUMN {} ;", "删除字段"),

    PRIMARY_COLUMN("ALTER TABLE `{}` DROP PRIMARY KEY, ADD PRIMARY KEY ({}) USING BTREE;", "主键字段"),

    /**
     * 从上自下
     * 表名
     * 字段
     * 主键
     * 索引
     */
    CREATE_TABLE("\n" +
            "CREATE TABLE {} (" +
            "\n{}" +
            "{}" +
            "{}" +
            "\n )  ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;", "创建表"),
    //末尾有逗号 当后续无语句时 需自行摘除
    CREATE_TABLE_COLUMN("`{}` {}({}) {} COMMENT '{}'", "表中的字段"),

    CREATE_TABLE_PRIMARY_KEY("PRIMARY KEY ({}) USING BTREE ", "创建表时设置主键"),

    CREATE_TABLE_INDEX("{} `{}` ({}) USING BTREE ", "创建表时设置索引"),

    DROP_TABLE("DROP TABLE {} ;", "删除表"),

    DELETE_PRIMARY_KEY("ALTER TABLE {} DROP PRIMARY KEY ;", "删除表主键"),

    DELETE_AUTOINCREMENT("ALTER TABLE {} CHANGE {} {} {}({}) ;", "删除字段自增"),

    ADD_PRIMARY_KEY("ALTER TABLE {} ADD PRIMARY KEY({});", "新增主键"),

    ADD_AUTOINCREMENT("ALTER TABLE {} CHANGE {} {} {}({}) NOT NULL AUTO_INCREMENT ;", "新增自增"),

    DELETE_INDEX("ALTER TABLE {} DROP INDEX {} ;", "删除索引"),


    /**
     * ALTER TABLE `smarthome`.`test`
     * ADD INDEX `ad11`(`test`) USING BTREE;
     * <p>
     * ALTER TABLE `smarthome`.`test`
     * ADD FULLTEXT INDEX `GGGGGA`(`test`);
     * <p>
     * ALTER TABLE `smarthome`.`test`
     * ADD UNIQUE INDEX `GGGGGA`(`test`) USING BTREE;
     */
    ADD_INDEX("ALTER TABLE {} ADD {} `{}`({}) ;", "新增索引"),

    UPDATE_INDEX("## ===UPDATE INDEX ===== \n{}" + "\n" + "{}\n ## ===UPDATE INDEX =====", "更新索引");

    SqlModelEnum(String sqlModel, String desc) {
        this.sqlModel = sqlModel;
        this.desc = desc;
    }

    public String getSqlModel() {
        return sqlModel;
    }

    public void setSqlModel(String sqlModel) {
        this.sqlModel = sqlModel;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String sqlModel;

    private String desc;
}
