package com.leyunone.dbshop.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.bean.dto.DbTableContrastDTO;
import com.leyunone.dbshop.bean.dto.SqlProductionDTO;
import com.leyunone.dbshop.bean.dto.TableColumnContrastDTO;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.TableInfo;
import com.leyunone.dbshop.bean.rule.SqlDataTypeTransformRule;
import com.leyunone.dbshop.constant.DbShopConstant;
import com.leyunone.dbshop.enums.DataTypeRegularEnum;
import com.leyunone.dbshop.enums.SqlModelEnum;
import com.leyunone.dbshop.excutor.RulePointExcutor;
import com.leyunone.dbshop.system.factory.TransformRuleHandlerFactory;
import com.leyunone.dbshop.util.SqlPackUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * :)
 * sql语句组装
 *
 * @Author LeYunone
 * @Date 2023/6/7 16:13
 */
@Service
public class SqlPackService {

    @Autowired
    private RulePointExcutor rulePointExcutor;
    @Autowired
    private TransformRuleHandlerFactory factory;

    /**
     * 列字段比对结果 组装sql
     *
     * @param sqlProductionDTO
     */
    public List<String> columnContrastPack(SqlProductionDTO sqlProductionDTO) {
        List<TableColumnContrastDTO> columns = sqlProductionDTO.getColumns();
        if (CollectionUtil.isEmpty(columns) || ObjectUtil.isNull(sqlProductionDTO.getLeftOrRight()))
            return new ArrayList<>();

        List<String> resultSql = this.getColumnCompareSqls(columns, sqlProductionDTO.getLeftOrRight(), sqlProductionDTO.getGoRemark());
        //策略处理流
        return this.strategysDoing(resultSql,sqlProductionDTO.getTransformReg(),sqlProductionDTO.getProductionStrategys());
    }

    /**
     * 两个数据库表的对比后结果sql集
     *
     * @param sqlProductionDTO
     * @return
     */
    public List<String> tableContrastPack(SqlProductionDTO sqlProductionDTO) {
        List<DbTableContrastDTO> dbs = sqlProductionDTO.getDbs();
        if (CollectionUtil.isEmpty(dbs) || ObjectUtil.isNull(sqlProductionDTO.getLeftOrRight()))
            return new ArrayList<>();
        List<String> result = new ArrayList<>();
        for (DbTableContrastDTO db : dbs) {
            if (db.getNameDifference()) {
                //表名字不同 猜疑是新增表或删除表
                TableInfo mainTable = sqlProductionDTO.getLeftOrRight().equals(0) ? db.getLeftTableInfo() : db.getRightTableInfo();
                List<ColumnInfo> columnInfos = sqlProductionDTO.getLeftOrRight().equals(0) ? db.getLeftColumnInfo() : db.getRightColumnInfo();
                if (ObjectUtil.isNull(mainTable) && 
                        ObjectUtil.isNotNull(sqlProductionDTO.getDeleteTable())
                        && DbShopConstant.Rule_Yes.equals(sqlProductionDTO.getDeleteTable())) {
                    //删除
                } else {
                    //新增，封装语句
                    result.add(SqlPackUtil.packing(SqlModelEnum.CREATE_TABLE, mainTable, columnInfos));
                }
                continue;
            }
            if (db.getHasDifference()) {
                //表名相同，但是有差异，则关注里面的字段
                result.addAll(this.getColumnCompareSqls(db.getColumnContrasts(), sqlProductionDTO.getLeftOrRight(), sqlProductionDTO.getGoRemark()));
            }
        }
        //进入类型转化策略流中
        List<String> resultsql = strategysDoing(result, sqlProductionDTO.getTransformReg(), sqlProductionDTO.getProductionStrategys());
        return resultsql;
    }

    private List<String> getColumnCompareSqls(List<TableColumnContrastDTO> columns, Integer leftOrRight, Integer goRemark) {
        List<String> resultSql = new ArrayList<>();

        for (TableColumnContrastDTO columnContrastDTO : columns) {
            ColumnInfo mainColumn = leftOrRight.equals(0) ? columnContrastDTO.getLeftColumn() : columnContrastDTO.getRightColumn();
            if (!columnContrastDTO.getNameDifferent()) {
                //字段名相同 猜疑是修改
                //0 左表主 1 右表主
                //如果表字段有不同
                if (columnContrastDTO.getSizeDifferent()
                        || columnContrastDTO.getTypeDifferent()
                        || (Integer.valueOf(1).equals(goRemark) && columnContrastDTO.getRemarkDifferent())) {
                    resultSql.add(SqlPackUtil.packing(SqlModelEnum.MODIFY_COLUMN, mainColumn));
                }
            } else {
                //字段名不同 新增或删除
                if (ObjectUtil.isNull(mainColumn)) {
                    //主表不存在字段则删除
                    resultSql.add(SqlPackUtil.packing(SqlModelEnum.DELETE_COLUMN, leftOrRight.equals(0) ? columnContrastDTO.getRightColumn() : columnContrastDTO.getLeftColumn()));
                } else {
                    //主表存在字段新增
                    resultSql.add(SqlPackUtil.packing(SqlModelEnum.ADD_COLUMN, mainColumn));
                }
            }
        }
        return resultSql;
    }
    
    private List<String> strategysDoing(List<String> resultSql,List<Integer> transformRegs,List<String> strategys){
        if(CollectionUtil.isEmpty(resultSql)) return resultSql;
        //sql转化规则
        //TODO 暂时指定策略工厂
        SqlDataTypeTransformRule sqlDataTypeTransformRule = SqlDataTypeTransformRule.builder()
                .transformReg(DataTypeRegularEnum.getEnums(transformRegs)).build();
        sqlDataTypeTransformRule.setPendingData(JSONObject.toJSONString(resultSql));
        sqlDataTypeTransformRule.setStrategys(strategys);
        //TODO 测试
        sqlDataTypeTransformRule.setStrategys(CollectionUtil.newArrayList("type_transform"));
        List<String> execute = rulePointExcutor.execute(factory, sqlDataTypeTransformRule);
        //二次处理结果集
        //TODO 最后一条结果为最终转化结果 ； （可能需要考虑到规则的执行顺序）
        String resultJson = CollectionUtil.getLast(execute);
        return JSONObject.parseArray(resultJson, String.class);
    }
}
