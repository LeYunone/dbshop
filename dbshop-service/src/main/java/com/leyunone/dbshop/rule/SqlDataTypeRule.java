package com.leyunone.dbshop.rule;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.annotate.RuleHandler;
import com.leyunone.dbshop.bean.rule.SqlDataTypeTransformRule;
import com.leyunone.dbshop.enums.DataTypeRegularEnum;
import com.leyunone.dbshop.enums.RuleTypeEnum;
import com.leyunone.dbshop.system.factory.AbstractRuleFactory;
import com.leyunone.dbshop.system.factory.TransformRuleHandlerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * :)
 * 字段类型转换规则
 *
 * @Author LeYunone
 * @Date 2023/6/9 14:20
 */
@RuleHandler(value = "type_transform",type = RuleTypeEnum.RESULT)
public class SqlDataTypeRule extends ResultRule<SqlDataTypeTransformRule> {

    @Autowired
    private TransformRuleHandlerFactory factory;

    @Override
    public AbstractRuleFactory registRuleFactory() {
        return factory;
    }

    @Override
    public String resultHandler(SqlDataTypeTransformRule sqlDataTypeTransformRule) {
        String pendingData = sqlDataTypeTransformRule.getPendingData();
        if (StringUtils.isBlank(pendingData)) return pendingData;
        List<String> sqls = JSONObject.parseArray(pendingData, String.class);
        List<String> result = new ArrayList<>();
        List<DataTypeRegularEnum> transformReg = sqlDataTypeTransformRule.getTransformReg();
        
        //TODO 双重循环 将每一条sql穿过转化流
        sqls.forEach((sql)->{
            String rs = sql;
            for(DataTypeRegularEnum transformEnum : transformReg){
                String reg = transformEnum.getReg();
                Matcher matcher = Pattern.compile(reg).matcher(rs);
                while (matcher.find()){
                    rs = rs.replace(matcher.group(),transformEnum.getToBecome());
                }
            }
            result.add(rs);
        });
        //反填信息，等待下一规则捕获处理
        String json = JSONObject.toJSONString(result);
        sqlDataTypeTransformRule.setTargetData(json);
        sqlDataTypeTransformRule.setPendingData(json);
        return sqlDataTypeTransformRule.getTargetData();
    }
}
