package com.leyunone.dbshop.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.bean.bo.AnalysisSqlBO;
import com.leyunone.dbshop.bean.dto.DbTableContrastDTO;
import com.leyunone.dbshop.bean.dto.IndexDTO;
import com.leyunone.dbshop.bean.dto.SqlProductionDTO;
import com.leyunone.dbshop.bean.dto.TableColumnContrastDTO;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.TableDetailInfo;
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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    @Autowired
    private AnalysisSqlService analysisSqlService;

    /**
     * 列字段比对结果 组装sql
     * Alter table 表名 drop primary key;--删除表主键
     * Alter table 表名 add primary key(`字段`);  --修改某列为主键
     * Alter table 表名 column id int auto_increment=1; --设置自增，默认值为1
     *
     * @param sqlProductionDTO
     */
    public List<String> columnContrastPack(SqlProductionDTO sqlProductionDTO) {
        List<TableColumnContrastDTO> columns = sqlProductionDTO.getTables();
        if (CollectionUtil.isEmpty(columns) || ObjectUtil.isNull(sqlProductionDTO.getLeftOrRight()))
            return new ArrayList<>();
        if (ObjectUtil.isNotNull(sqlProductionDTO.getIndexDifference()) && DbShopConstant.Rule_Yes.equals(sqlProductionDTO.getIndexDifference())) {
            //索引差异

        }

        List<String> resultSql = this.getColumnCompareSqls(columns, sqlProductionDTO);
        //策略处理流
        return this.strategysDoing(resultSql, sqlProductionDTO.getTransformReg(), sqlProductionDTO.getProductionStrategys());
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
                TableDetailInfo mainTable = sqlProductionDTO.getLeftOrRight().equals(0) ? db.getLeftTableDetailInfo() : db.getRightTableDetailInfo();
                TableDetailInfo anotherTable = !sqlProductionDTO.getLeftOrRight().equals(0) ? db.getLeftTableDetailInfo() : db.getRightTableDetailInfo();
                List<ColumnInfo> columnInfos = sqlProductionDTO.getLeftOrRight().equals(0) ? db.getLeftColumnInfo() : db.getRightColumnInfo();
                if (ObjectUtil.isNull(mainTable) &&
                        ObjectUtil.isNotNull(sqlProductionDTO.getDeleteTable())
                        && DbShopConstant.Rule_Yes.equals(sqlProductionDTO.getDeleteTable())) {
                    //删除
                    if (ObjectUtil.isNotNull(anotherTable)) {
                        result.add(SqlPackUtil.packing(SqlModelEnum.DROP_TABLE, anotherTable, null));
                    }
                } else {
                    //新增，封装语句
                    if (ObjectUtil.isNotNull(mainTable)) {
                        result.add(SqlPackUtil.packing(SqlModelEnum.CREATE_TABLE, mainTable, columnInfos));
                    }
                }
                continue;
            }
            if (db.getHasDifference()) {
                //表名相同，但是有差异，则关注里面的字段
                result.addAll(this.getColumnCompareSqls(db.getColumnContrasts(), sqlProductionDTO));
            }
        }
        //进入类型转化策略流中
        List<String> resultsql = strategysDoing(result, sqlProductionDTO.getTransformReg(), sqlProductionDTO.getProductionStrategys());
        return resultsql;
    }

    /**
     * 对比思路：
     *
     * @param columns
     * @param sqlProductionDTO
     * @return
     */
    private List<String> getColumnCompareSqls(List<TableColumnContrastDTO> columns, SqlProductionDTO sqlProductionDTO) {
        List<String> resultSql = new ArrayList<>();
        List<String> deleteAutoincrement = new ArrayList<>();
        List<String> deletePrimaryKey = new ArrayList<>();
        for (TableColumnContrastDTO columnContrastDTO : columns) {
            //0 左表主 1 右表主
            ColumnInfo mainColumn = sqlProductionDTO.getLeftOrRight().equals(0) ? columnContrastDTO.getLeftColumn() : columnContrastDTO.getRightColumn();
            ColumnInfo anotherColumn = !sqlProductionDTO.getLeftOrRight().equals(0) ? columnContrastDTO.getLeftColumn() : columnContrastDTO.getRightColumn();

            if (!columnContrastDTO.getNameDifferent()) {
                //修改字段sql集
                List<AnalysisSqlBO> modifySqls = analysisSqlService.modifyColumnSql(columnContrastDTO, sqlProductionDTO);
                if (CollectionUtil.isNotEmpty(modifySqls)) {
                    /**
                     * 单表规则
                     * 删除自增sql和删除主键在最前面 ，  删除自增sql在删除主键前面
                     */
                    modifySqls.forEach((t) -> {
                        switch (t.getSqlModel()) {
                            case DELETE_AUTOINCREMENT:
                                deleteAutoincrement.add(t.getSql());
                                break;
                            case DELETE_PRIMARY_KEY:
                                deletePrimaryKey.add(t.getSql());
                                break;
                            default:
                                resultSql.add(t.getSql());
                        }
                    });
                }
            } else {
                //字段名不同 新增或删除
                if (ObjectUtil.isNull(mainColumn)) {
                    //主表不存在字段则删除
                    resultSql.add(SqlPackUtil.packing(SqlModelEnum.DELETE_COLUMN, anotherColumn));
                } else {
                    //主表存在字段新增
                    resultSql.add(SqlPackUtil.packing(SqlModelEnum.ADD_COLUMN, mainColumn));
                }
            }
        }
        resultSql.addAll(0, deletePrimaryKey);
        resultSql.addAll(0, deleteAutoincrement);
        return resultSql;
    }

    private List<String> getIndexCompareSqls(SqlProductionDTO sqlProductionDTO) {
        sqlProductionDTO.getTables();
        //0 左表主 1 右表主
        List<IndexDTO> mainIndex = sqlProductionDTO.getLeftOrRight().equals(0) ? leftIndex : rightIndex;
        List<IndexDTO> anotherIndex= !sqlProductionDTO.getLeftOrRight().equals(0) ? leftIndex : rightIndex;
        Map<String, IndexDTO> anotherIndexMap = anotherIndex.stream().collect(Collectors.toMap(IndexDTO::getIndexName, Function.identity()));

        //以主表遍历解析sql语句
        for(IndexDTO main:mainIndex){
            IndexDTO indexDTO = anotherIndexMap.get(main.getIndexName());
            if(ObjectUtil.isNull(indexDTO)){
                //新增索引
            }
        }
    }

    private List<String> strategysDoing(List<String> resultSql, List<DataTypeRegularEnum> transformRegs, List<String> strategys) {
        if (CollectionUtil.isEmpty(resultSql) || CollectionUtil.isEmpty(transformRegs)) return resultSql;
        //sql转化规则
        //TODO 暂时指定策略工厂
        SqlDataTypeTransformRule sqlDataTypeTransformRule = SqlDataTypeTransformRule.builder()
                .transformReg(transformRegs).build();
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
