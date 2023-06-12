package com.leyunone.dbshop.rule;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.annotate.RuleHandler;
import com.leyunone.dbshop.bean.rule.SqlDataTypeTransformRule;
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
        if (ObjectUtil.isNotNull(sqlDataTypeTransformRule.getDateTimeTo_0())) {
            //datetime类型 不管字符长度转为 datetime(0);
            String reg = "DATETIME[(][0-9]*[)]";
            String datetime_o = "DATETIME(0)";
            Pattern pattern = Pattern.compile(reg);
            //处理正则匹对
            sqls.forEach((sql) -> {
                Matcher matcher = pattern.matcher(sql);
                while(matcher.find()){
                    String datetime_ = matcher.group();
                    sql = sql.replace(datetime_,datetime_o);
                }
                result.add(sql);
            });
        }
        //反填信息，等待下一规则捕获处理
        String json = JSONObject.toJSONString(result);
        sqlDataTypeTransformRule.setTargetData(json);
        sqlDataTypeTransformRule.setPendingData(json);
        return sqlDataTypeTransformRule.getTargetData();
    }
}
