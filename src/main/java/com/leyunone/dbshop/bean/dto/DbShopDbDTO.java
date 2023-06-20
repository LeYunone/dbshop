package com.leyunone.dbshop.bean.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-19
 */
@Getter
@Setter
public class DbShopDbDTO {


    /**
     * url
     */
    @NotNull(message = "url cant not empty")
    private String url;

    /**
     * 数据库名
     */
    @NotNull(message = "dbName cant not empty")
    private String dbName;

    @NotNull(message = "userName cant not empty")
    private String userName;

    @NotNull(message = "passWord cant not empty")
    private String passWord;
}
