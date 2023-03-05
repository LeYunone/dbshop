package com.leyunone.dbshop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-12-26
 *
 * 数据库连接信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DBInfo {

    private String url;

    private String userName;

    private String passWord;
}
