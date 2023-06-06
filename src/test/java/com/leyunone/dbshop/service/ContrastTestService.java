package com.leyunone.dbshop.service;

import com.leyunone.dbshop.bean.query.ContrastQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-06
 */
@SpringBootTest
public class ContrastTestService {

    @Autowired
    private ContrastService contrastService;
    @Autowired
    private ConnectService connectService;
    
    @Test
    public void columnContrast(){
        ContrastQuery contrastQuery = new ContrastQuery();
        contrastQuery.setLeftTablName();
        contrastQuery.setRightTableName();
        
        contrastQuery.setLeftDbName();
        contrastQuery.setRightDbName();
        
        contrastQuery.setLeftUrl();
        contrastQuery.setRightUrl();
        contrastService.columnContrastToTable()
    }
}
