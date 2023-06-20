package com.leyunone.dbshop.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.dbshop.api.DbShopStartAPIService;
import com.leyunone.dbshop.bean.dto.DbShopDbDTO;
import com.leyunone.dbshop.bean.dto.DbTableContrastDTO;
import com.leyunone.dbshop.bean.dto.SqlProductionDTO;
import com.leyunone.dbshop.bean.dto.SqlRuleDTO;
import com.leyunone.dbshop.bean.info.DbInfo;
import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.query.DBQuery;
import com.leyunone.dbshop.bean.vo.DbTableContrastVO;
import com.leyunone.dbshop.constant.DbShopConstant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-19
 */
@SpringBootTest
public class ApiTestService {

    @Autowired
    private DbShopStartAPIService dbShopStartAPIService;
    
    @Test
    public void startTest(){
        DbShopDbDTO leftQuery = new DbShopDbDTO();
        leftQuery.setUrl("jdbc:mysql://localhost:3306/test2023?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
        leftQuery.setDbName("test2023");
        leftQuery.setUserName("root");
        leftQuery.setPassWord("root");
        DbShopDbDTO rightQuery = new DbShopDbDTO();
        rightQuery.setUrl("jdbc:mysql://localhost:3306/test2023-1?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
        rightQuery.setDbName("test2023-1");
        rightQuery.setUserName("root");
        rightQuery.setPassWord("root");
        SqlRuleDTO sqlRuleDTO = new SqlRuleDTO();
        sqlRuleDTO.setGoRemark(1);
        sqlRuleDTO.setGoDeep(1);
        sqlRuleDTO.setLeftOrRight(1);
        sqlRuleDTO.setTransformReg(CollectionUtil.newArrayList(0,1));
        dbShopStartAPIService.leftRightDb(leftQuery,rightQuery,sqlRuleDTO);
    }
}
