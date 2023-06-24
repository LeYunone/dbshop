package com.leyunone.dbshop.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.file.FileAppender;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.dbshop.bean.dto.DbShopDbDTO;
import com.leyunone.dbshop.bean.dto.DbTableContrastDTO;
import com.leyunone.dbshop.bean.dto.SqlProductionDTO;
import com.leyunone.dbshop.bean.dto.SqlRuleDTO;
import com.leyunone.dbshop.bean.info.DbInfo;
import com.leyunone.dbshop.bean.info.TableInfo;
import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.query.DBQuery;
import com.leyunone.dbshop.bean.vo.DbTableContrastVO;
import com.leyunone.dbshop.bean.vo.TableColumnContrastVO;
import com.leyunone.dbshop.constant.DbShopConstant;
import com.leyunone.dbshop.service.ConfigService;
import com.leyunone.dbshop.service.ContrastService;
import com.leyunone.dbshop.service.SqlPackService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * :)
 * 支持代码行直接使用的api
 * 不通过页面，走单元测试形式，进行指定的数据库或表比较
 * 将比较的结果打印到文件中
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-18
 */
@Service
@RequiredArgsConstructor
public class DbShopStartAPIServiceImpl implements DbShopStartAPIService{

    private final ConfigService configService;
    private final ContrastService contrastService;
    private final SqlPackService sqlPackService;
    
    @Override
    public void leftRightTable() {
    }

    @Override
    public void leftRightDb(DbShopDbDTO leftDto, DbShopDbDTO rightDto, SqlRuleDTO sqlRuleDTO) {
        DBQuery leftQuery = new DBQuery();
        leftQuery.setUrl(leftDto.getUrl());
        leftQuery.setDbName(leftDto.getDbName());
        leftQuery.setUserName(leftDto.getUserName());
        leftQuery.setPassWord(leftDto.getPassWord());
        DBQuery rightQuery = new DBQuery();
//        rightQuery.setUrl("jdbc:mysql://localhost:3306/test2023-1?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
        rightQuery.setUrl(rightDto.getUrl());
        rightQuery.setDbName(rightDto.getDbName());
        rightQuery.setUserName(rightDto.getUserName());
        rightQuery.setPassWord(rightDto.getPassWord());


        DbInfo leftDbInfo = configService.loadConnectionToData(leftQuery);
        DbInfo rightDbInfo = configService.loadConnectionToData(rightQuery);

        ContrastQuery contrastQuery = new ContrastQuery();
        contrastQuery.setGoRemark(sqlRuleDTO.getGoRemark());
        contrastQuery.setGoDeep(sqlRuleDTO.getGoDeep());
        
        contrastQuery.setLeftDbName(leftQuery.getDbName());
        contrastQuery.setRightDbName(rightQuery.getDbName());
        
        contrastQuery.setLeftUrl(leftQuery.getUrl());
        contrastQuery.setRightUrl(rightQuery.getUrl());
        List<DbTableContrastVO> dbTableContrast = contrastService.dbTableContrast(contrastQuery);
        
        //输出两表对比结果
        System.out.println("=============DBSHOP===============");
        System.out.println("=============DBSHOP===============");
        System.out.println("=============DBSHOP===============");
        System.out.println("=============DBSHOP===============");
        System.out.println("=============对比结果===============");
        System.out.println();
        List<String> newTable = new ArrayList<>();
        for(DbTableContrastVO dbTableContrastVO:dbTableContrast){
            TableInfo mainTable = sqlRuleDTO.getLeftOrRight().equals(0) ? dbTableContrastVO.getLeftTableInfo() : dbTableContrastVO.getRightTableInfo();
            TableInfo anotherTable =  !sqlRuleDTO.getLeftOrRight().equals(0) ? dbTableContrastVO.getLeftTableInfo() : dbTableContrastVO.getRightTableInfo();
            if(dbTableContrastVO.getNameDifference()){
                if(ObjectUtil.isNull(mainTable)){
                    newTable.add("删除表： "+anotherTable.getTableName());
                }else{
                    newTable.add("新增表： "+mainTable.getTableName());
                }
                continue;
            }
            if(dbTableContrastVO.getHasDifference()){
                System.out.println("表： ["+mainTable.getTableName()+"] 中以下字段有差异");
                List<TableColumnContrastVO> columnContrasts = dbTableContrastVO.getColumnContrasts();
                for(TableColumnContrastVO columnContrastVO : columnContrasts){
                    if(columnContrastVO.getNameDifferent() || columnContrastVO.getTypeDifferent() || 
                            columnContrastVO.getSizeDifferent() || columnContrastVO.getAutoincrementDifferent()
                            || columnContrastVO.getPrimaryKeyDifferent() || (DbShopConstant.Rule_Yes.equals(sqlRuleDTO.getGoRemark()) && columnContrastVO.getRemarkDifferent())){
                        String columnName = ObjectUtil.isNotNull(columnContrastVO.getRightColumn()) ?columnContrastVO.getRightColumn().getColumnName():columnContrastVO.getLeftColumn().getColumnName();
                        System.out.println("字段:"+columnName);
                    }

                }
                System.out.println();
            }
        }
        System.out.println("-----------表级操作------------");
        newTable.forEach(System.out::println);
        System.out.println("-----------------------------");
        System.out.println();

        SqlProductionDTO sqlProductionDTO = new SqlProductionDTO();
        sqlProductionDTO.setLeftOrRight(sqlRuleDTO.getLeftOrRight());
        sqlProductionDTO.setGoRemark(contrastQuery.getGoRemark());
        sqlProductionDTO.setTransformReg(sqlRuleDTO.getTransformReg());
        sqlProductionDTO.setDeleteTable(sqlRuleDTO.getDeleteTable());
        sqlProductionDTO.setDbs(BeanUtil.copyToList(dbTableContrast, DbTableContrastDTO.class));
        List<String> resultSql = sqlPackService.tableContrastPack(sqlProductionDTO);
        
        //写文件
        System.out.println("开始写文件，目标文件: /dbshop.sql");
        File file = new File("dbshop.sql");
        file.delete();
        FileAppender  writer = new FileAppender(file,16,true);
        for(String sql:resultSql){
            writer.append(sql);
        }
        writer.flush();
        System.out.println("写入完成");
        
        //打印至控制台
        for(String sql : resultSql){
            System.out.println(sql);
        }
    }
}
