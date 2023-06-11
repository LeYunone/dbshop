package com.leyunone.dbshop.rule;

import cn.hutool.core.util.ObjectUtil;
import com.leyunone.dbshop.annotate.RuleHandler;
import com.leyunone.dbshop.bean.rule.SqlDataTypeTransformRule;
import com.leyunone.dbshop.system.factory.AbstractRuleFactory;
import com.leyunone.dbshop.system.factory.TransformRuleHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * :)
 * 字段类型转换规则
 *
 * @Author LeYunone
 * @Date 2023/6/9 14:20
 */
@RuleHandler("type_transform")
public class SqlDataTypeRule extends ResultRule<SqlDataTypeTransformRule> {

    @Autowired
    private TransformRuleHandlerFactory factory;

    @Override
    public AbstractRuleFactory registRuleFactory() {
        return factory;
    }

    @Override
    public String resultHandler(SqlDataTypeTransformRule sqlDataTypeTransformRule) {
        String sql = sqlDataTypeTransformRule.getSql();
        if (ObjectUtil.isNotNull(sqlDataTypeTransformRule.getDateTimeTo_0())) {
            //datetime类型 不管字符长度转为 datetime(0);
            String reg = "datetime([0-9]+)";
            if(sql.matches(reg)){
                //匹配成功 强行替换成datetime(0);
                
            }
        }
        return null;
    }
}
