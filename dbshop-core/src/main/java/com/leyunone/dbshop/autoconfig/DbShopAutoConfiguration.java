package com.leyunone.dbshop.autoconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author leyunone
 * @date 2022-04-07
 */
@Configuration()
@ComponentScan({"com.leyunone.dbshop"})
@EnableConfigurationProperties(DbShopProperties.class)
public class DbShopAutoConfiguration {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 读取配置文件式 自启动
     * @param dbShopProperties
     */
    public DbShopAutoConfiguration(DbShopProperties dbShopProperties) {
        
    }
}
