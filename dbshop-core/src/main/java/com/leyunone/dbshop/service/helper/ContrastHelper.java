package com.leyunone.dbshop.service.helper;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.bean.ResponseCell;
import com.leyunone.dbshop.bean.dto.SqlProductionDTO;
import com.leyunone.dbshop.bean.dto.TableContrastDTO;
import com.leyunone.dbshop.bean.info.TableDetailInfo;
import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.rule.SqlCompareRule;
import com.leyunone.dbshop.bean.vo.DbTableContrastVO;
import com.leyunone.dbshop.bean.vo.TableColumnContrastVO;
import com.leyunone.dbshop.constant.DbShopConstant;
import com.leyunone.dbshop.service.core.ContrastService;
import com.leyunone.dbshop.service.core.SqlPackService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * :)
 * 比较者
 *
 * @Author pengli
 * @Date 2025/2/8 11:26
 */
@Service
public class ContrastHelper {

    private final ContrastService contrastService;
    private final SqlPackService sqlPackService;

    public ContrastHelper(ContrastService contrastService, SqlPackService sqlPackService) {
        this.contrastService = contrastService;
        this.sqlPackService = sqlPackService;
    }

    public ResponseCell<List<DbTableContrastVO>, List<String>> dbCompare(String leftUrl, String rightUrl, String leftDb, String rightDb, SqlCompareRule compareRule) {
        ContrastQuery contrastQuery = new ContrastQuery();
        contrastQuery.setLeftUrl(leftUrl);
        contrastQuery.setRightUrl(rightUrl);
        contrastQuery.setLeftDbName(leftDb);
        contrastQuery.setRightDbName(rightDb);
        contrastQuery.setSqlCompareRule(compareRule);
        //比较结果
        List<DbTableContrastVO> dbTableContrasts = contrastService.dbTableContrast(contrastQuery);

        SqlProductionDTO sqlProductionDTO = new SqlProductionDTO();
        sqlProductionDTO.setLeftOrRight(compareRule.getLeftOrRight());
        sqlProductionDTO.setGoRemark(compareRule.getGoRemark());
        sqlProductionDTO.setTransformReg(compareRule.getTransformReg());
        sqlProductionDTO.setDeleteTable(compareRule.getDeleteTable());
        //TODO HUTOOL 版本问题 谨慎使用BeanUtil.copyToList
        sqlProductionDTO.setTables(JSONObject.parseArray(JSONObject.toJSONString(dbTableContrasts), TableContrastDTO.class));
        List<String> resultSql = sqlPackService.tableContrastPack(sqlProductionDTO);
        return ResponseCell.build(dbTableContrasts, resultSql);
    }

    public void printComparerResult(List<DbTableContrastVO> dbTableContrasts, SqlCompareRule compareRule) {

        //输出两表对比结果
        System.out.println("=============对比结果===============");
        System.out.println();
        List<String> newTable = new ArrayList<>();
        for (DbTableContrastVO dbTableContrastVO : dbTableContrasts) {
            TableDetailInfo mainTable = compareRule.getLeftOrRight().equals(0) ? dbTableContrastVO.getLeftTableDetailInfo() : dbTableContrastVO.getRightTableDetailInfo();
            TableDetailInfo anotherTable = !compareRule.getLeftOrRight().equals(0) ? dbTableContrastVO.getLeftTableDetailInfo() : dbTableContrastVO.getRightTableDetailInfo();
            if (dbTableContrastVO.getNameDifference()) {
                if (null == mainTable) {
                    newTable.add("删除表： " + anotherTable.getTableName());
                } else {
                    newTable.add("新增表： " + mainTable.getTableName());
                }
                continue;
            }
            if (dbTableContrastVO.getHasDifference()) {
                System.out.println("表： [" + mainTable.getTableName() + "] 中以下字段有差异");
                List<TableColumnContrastVO> columnContrasts = dbTableContrastVO.getColumnContrasts();
                for (TableColumnContrastVO columnContrastVO : columnContrasts) {
                    if (columnContrastVO.getNameDifferent() || columnContrastVO.getTypeDifferent() ||
                            columnContrastVO.getSizeDifferent() || columnContrastVO.getAutoincrementDifferent()
                            || columnContrastVO.getPrimaryKeyDifferent() || (DbShopConstant.RULE_YES.equals(compareRule.getGoRemark()) && columnContrastVO.getRemarkDifferent())) {
                        String columnName = null != columnContrastVO.getRightColumn() ? columnContrastVO.getRightColumn().getColumnName() : columnContrastVO.getLeftColumn().getColumnName();
                        System.out.println("字段:" + columnName);
                    }

                }
                System.out.println();
            }
        }
        System.out.println("-----------表级操作------------");
        newTable.forEach(System.out::println);
        System.out.println("-----------------------------");
        System.out.println();
    }

}
