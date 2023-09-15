package com.leyunone.dbshop.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.dbshop.bean.ResponseCell;
import com.leyunone.dbshop.bean.dto.IndexContrastDTO;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.IndexInfo;
import com.leyunone.dbshop.bean.info.TableDetailInfo;
import com.leyunone.dbshop.bean.info.TableInfo;
import com.leyunone.dbshop.bean.query.ContrastQuery;
import com.leyunone.dbshop.bean.vo.DbTableContrastVO;
import com.leyunone.dbshop.bean.vo.IndexContrastVO;
import com.leyunone.dbshop.bean.vo.TableColumnContrastVO;
import com.leyunone.dbshop.bean.vo.TableContrastVO;
import com.leyunone.dbshop.constant.DbShopConstant;
import com.leyunone.dbshop.system.factory.DBDataFactory;
import com.leyunone.dbshop.util.AssertUtil;
import com.leyunone.dbshop.util.CollectionFunctionUtils;
import com.leyunone.dbshop.util.DbStrategyUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 对比服务
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-05-17
 */
@Service
@RequiredArgsConstructor
public class ContrastService {

    private final DBDataFactory dataFactory;
    private final PackInfoService packInfoService;

    public TableContrastVO tableContrastToTable(ContrastQuery contrastQuery) {
        //左表字段数据
        List<ColumnInfo> leftColumn = dataFactory.getColumnData(DbStrategyUtil.getTableStrategy(DbStrategyUtil.loadContrastRule(contrastQuery, true)));
        //右表字段数据
        List<ColumnInfo> rightColumn = dataFactory.getColumnData(DbStrategyUtil.getTableStrategy(DbStrategyUtil.loadContrastRule(contrastQuery, false)));
        //左表索引
        List<IndexInfo> leftIndex = dataFactory.getIndexData(DbStrategyUtil.getTableStrategy(DbStrategyUtil.loadContrastRule(contrastQuery, true)));
        //右表索引
        List<IndexInfo> rightIndex = dataFactory.getIndexData(DbStrategyUtil.getTableStrategy(DbStrategyUtil.loadContrastRule(contrastQuery, false)));

        List<TableColumnContrastVO> columnData = this.columnContrastdoing(leftColumn, rightColumn, contrastQuery.getGoRemark()).getMateData();
        List<IndexContrastVO> indexData = this.indexContrastdoing(leftIndex, rightIndex).getMateData();
        return TableContrastVO.builder().columnContrasts(columnData).indexContrasts(indexData).build();
    }

    /**
     * 两个数据库对比结果集
     *
     * @param contrastQuery
     */
    public List<DbTableContrastVO> dbTableContrast(ContrastQuery contrastQuery) {
        //左边数据库的所有表
        List<TableDetailInfo> leftTables = dataFactory.getTableData(DbStrategyUtil.getDetailStrategy(DbStrategyUtil.loadContrastRule(contrastQuery, true)));
        //右表数据库的所有表
        List<TableDetailInfo> rightTables = dataFactory.getTableData(DbStrategyUtil.getDetailStrategy(DbStrategyUtil.loadContrastRule(contrastQuery, false)));

        List<DbTableContrastVO> dbTableContrastVOS = this.tableContrastdoing(leftTables, rightTables);
//        if (ObjectUtil.isNotNull(contrastQuery.getGoDeep()) && contrastQuery.getGoDeep().equals(DbShopConstant.Rule_Yes)) {
        for (DbTableContrastVO dbTableContrastVO : dbTableContrastVOS) {
            TableInfo leftInfo = null;
            TableInfo rightInfo = null;

            TableDetailInfo leftTableDetailInfo = dbTableContrastVO.getLeftTableDetailInfo();
            TableDetailInfo rightTableDetailInfo = dbTableContrastVO.getRightTableDetailInfo();
            //字段值
            if (ObjectUtil.isNotNull(leftTableDetailInfo)) {
                contrastQuery.setLeftTableName(leftTableDetailInfo.getTableName());
                leftInfo = dataFactory.getTableInfo(DbStrategyUtil.getTableStrategy(DbStrategyUtil.loadContrastRule(contrastQuery, true)));
                //设置左表字段信息
                dbTableContrastVO.setLeftColumnInfo(leftInfo.getColumnInfos());
                dbTableContrastVO.setLeftIndexInfo(leftInfo.getIndexInfos());
            }
            if (ObjectUtil.isNotNull(rightTableDetailInfo)) {
                contrastQuery.setRightTableName(rightTableDetailInfo.getTableName());
                rightInfo = dataFactory.getTableInfo(DbStrategyUtil.getTableStrategy(DbStrategyUtil.loadContrastRule(contrastQuery, false)));
                //设置右表字段信息
                dbTableContrastVO.setRightColumnInfo(rightInfo.getColumnInfos());
                dbTableContrastVO.setRightIndexInfo(rightInfo.getIndexInfos());
            }
            //当两表存在 且名字相同时，进行表字段间的结果对比
            if (ObjectUtil.isNotNull(leftTableDetailInfo) && ObjectUtil.isNotNull(rightTableDetailInfo) && !dbTableContrastVO.getNameDifference()) {
                /**
                 * 字段间对比
                 */
                ResponseCell<Boolean, List<TableColumnContrastVO>> columnDoing = this.columnContrastdoing(leftInfo.getColumnInfos(), rightInfo.getColumnInfos(), contrastQuery.getGoRemark());
                dbTableContrastVO.setColumnContrasts(columnDoing.getMateData());
                //索引间对比
                ResponseCell<Boolean, List<IndexContrastVO>> indexDoing = this.indexContrastdoing(leftInfo.getIndexInfos(), rightInfo.getIndexInfos());
                dbTableContrastVO.setIndexContrasts(indexDoing.getMateData());

                dbTableContrastVO.setIndexDifference(indexDoing.getCellData());
                dbTableContrastVO.setHasDifference(columnDoing.getCellData() || indexDoing.getCellData());
            }

        }
//        }
        return dbTableContrastVOS;
    }

    /**
     * 对比左右表字段
     *
     * @param left
     * @param right
     * @return
     */
    private ResponseCell<Boolean, List<TableColumnContrastVO>> columnContrastdoing(List<ColumnInfo> left, List<ColumnInfo> right, Boolean goRemark) {
        List<TableColumnContrastVO> result = new ArrayList<>();
        //对比表不存在
        AssertUtil.isFalse(CollectionUtil.isEmpty(right) || CollectionUtil.isEmpty(left), "表不存在");
        Map<String, ColumnInfo> rightMap = right.stream().collect(Collectors.toMap(ColumnInfo::getColumnName, Function.identity()));
        boolean different = false;
        //比较相同字段名 并且填充空白
        for (ColumnInfo lc : left) {
            TableColumnContrastVO tableColumnContrastVO = new TableColumnContrastVO();
            tableColumnContrastVO.setLeftColumn(lc);
            if (rightMap.containsKey(lc.getColumnName())) {
                tableColumnContrastVO.setRightColumn(rightMap.get(lc.getColumnName()));
                tableColumnContrastVO.setNameDifferent(DbShopConstant.SAME);
                rightMap.remove(lc.getColumnName());
            } else {
                tableColumnContrastVO.setNameDifferent(DbShopConstant.DIFFERENT);
                different = true;
            }
            result.add(tableColumnContrastVO);
        }
        //处理未匹配集合
        if (CollectionUtil.isNotEmpty(rightMap)) {
            different = true;
            rightMap.values().forEach((t) -> {
                TableColumnContrastVO tableColumnContrastVO = new TableColumnContrastVO();
                tableColumnContrastVO.setRightColumn(t);
                tableColumnContrastVO.setNameDifferent(DbShopConstant.DIFFERENT);
                result.add(tableColumnContrastVO);
            });
        }
        //比较相同字段名下的字段类型 - size和type和remark
        for (TableColumnContrastVO contrast : result) {
            //名字相同 进行字段属性级比较
            boolean hasDifferent = false;
            if (!contrast.getNameDifferent()) {
                ColumnInfo leftColumn = contrast.getLeftColumn();
                ColumnInfo rightColumn = contrast.getRightColumn();
                //比较size
                contrast.setSizeDifferent(!leftColumn.getColumnSize().equals(rightColumn.getColumnSize()));
                //比较type
                contrast.setTypeDifferent(!leftColumn.getDataType().equals(rightColumn.getDataType()));
                //比较remark
                contrast.setRemarkDifferent(!leftColumn.getRemarks().equals(rightColumn.getRemarks()));
                contrast.setPrimaryKeyDifferent(!leftColumn.getPrimaryKey().equals(rightColumn.getPrimaryKey()));
                contrast.setAutoincrementDifferent(!leftColumn.getAutoincrement().equals(rightColumn.getAutoincrement()));
                /**
                 * 基础属性确认
                 * sizeDifferent | typeDifferent | primaryKeyDifferent | autoincrementDifferent
                 */
                if (contrast.getSizeDifferent() || contrast.getTypeDifferent() || contrast.getPrimaryKeyDifferent() || contrast.getAutoincrementDifferent()) {
                    different = true;
                    hasDifferent = true;
                }
                //规则级确认 goRemark = 1 备注
                if (ObjectUtil.isNotNull(goRemark) && DbShopConstant.Rule_Yes.equals(goRemark) && contrast.getRemarkDifferent()) {
                    different = true;
                    hasDifferent = true;
                }
            }
            contrast.setHasDifferent(hasDifferent);
        }
        return ResponseCell.build(different, result);
    }

    /**
     * 对比左右表名
     *
     * @param left
     * @param right
     */
    private List<DbTableContrastVO> tableContrastdoing(List<TableDetailInfo> left, List<TableDetailInfo> right) {
        List<DbTableContrastVO> result = new ArrayList<>();
        if (CollectionUtil.isEmpty(right)) {
            //对比数据库中没有表存在
        }
        Map<String, TableDetailInfo> rightMap = right.stream().collect(Collectors.toMap(TableDetailInfo::getTableName, Function.identity()));
        for (TableDetailInfo lt : left) {
            DbTableContrastVO dbTableContrastVO = new DbTableContrastVO();
            dbTableContrastVO.setLeftTableDetailInfo(lt);
            boolean hasDifference = DbShopConstant.DIFFERENT;
            if (rightMap.containsKey(lt.getTableName())) {
                TableDetailInfo rightTable = rightMap.get(lt.getTableName());
                dbTableContrastVO.setRightTableDetailInfo(rightTable);
                dbTableContrastVO.setNameDifference(DbShopConstant.SAME);
                rightMap.remove(lt.getTableName());
            } else {
                dbTableContrastVO.setNameDifference(DbShopConstant.DIFFERENT);
            }
            dbTableContrastVO.setHasDifference(hasDifference);
            result.add(dbTableContrastVO);
        }
        if (CollectionUtil.isNotEmpty(rightMap)) {
            //左表不存在的表
            rightMap.values().forEach((t) -> {
                DbTableContrastVO dbTableContrastVO = new DbTableContrastVO();
                dbTableContrastVO.setNameDifference(DbShopConstant.DIFFERENT);
                dbTableContrastVO.setRightTableDetailInfo(t);
                dbTableContrastVO.setHasDifference(DbShopConstant.SAME);
                result.add(dbTableContrastVO);
            });
        }
        return result;
    }

    private ResponseCell<Boolean, List<IndexContrastVO>> indexContrastdoing(List<IndexInfo> left, List<IndexInfo> right) {
        List<IndexContrastVO> result = new ArrayList<>();
        Map<String, IndexInfo> rightMap = CollectionFunctionUtils.mapTo(right, IndexInfo::getIndexName);
        boolean different = false;
        //左右表 标示相同索引名
        for (IndexInfo leftIndex : left) {
            IndexContrastVO indexContrastVO = new IndexContrastVO();
            indexContrastVO.setLeftIndex(leftIndex);
            if (CollectionUtil.isNotEmpty(rightMap) && rightMap.containsKey(leftIndex.getIndexName())) {
                indexContrastVO.setRightIndex(rightMap.get(leftIndex.getIndexName()));
                indexContrastVO.setNameDifferent(DbShopConstant.SAME);
                rightMap.remove(leftIndex.getIndexName());
            } else {
                indexContrastVO.setNameDifferent(DbShopConstant.DIFFERENT);
                different = true;
            }
            result.add(indexContrastVO);
        }
        //剩余未匹配集合
        if (CollectionUtil.isNotEmpty(rightMap)) {
            different = true;
            rightMap.values().forEach((t) -> {
                IndexContrastVO indexContrastVO = new IndexContrastVO();
                indexContrastVO.setRightIndex(t);
                indexContrastVO.setNameDifferent(DbShopConstant.DIFFERENT);
                result.add(indexContrastVO);
            });
        }

        //相同的
        for (IndexContrastVO indexContrastVO : result) {
            boolean hasDifferent = false;
            if (!indexContrastVO.getNameDifferent()) {
                //名称相同 比较索引内容
                IndexInfo leftIndex = indexContrastVO.getLeftIndex();
                IndexInfo rightIndex = indexContrastVO.getRightIndex();
                //如果两边的hashcode值不相同 则说明索引不相同
                hasDifferent = this.indexCompare(leftIndex, rightIndex);
            }
            indexContrastVO.setHasDifferent(hasDifferent);
            different = hasDifferent;
        }

        return ResponseCell.build(different, result);
    }

    private boolean indexCompare(IndexInfo leftIndex, IndexInfo rightIndex) {
        boolean c = leftIndex.getColumns().hashCode() == rightIndex.getColumns().hashCode();
        boolean type = leftIndex.getType().equals(rightIndex.getType());
        boolean un = leftIndex.isUniqueIndex() == rightIndex.isUniqueIndex();
        boolean o = true;
        if(StringUtils.isNotBlank(leftIndex.getAscOrDesc())){
            o = leftIndex.getAscOrDesc().equals(rightIndex.getAscOrDesc());
        }else if(StringUtils.isNotBlank(rightIndex.getAscOrDesc())){
            o = rightIndex.getAscOrDesc().equals(leftIndex.getAscOrDesc());
        }
        return !(c && type && un && o);
    }
}
