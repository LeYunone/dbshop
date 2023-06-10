package com.leyunone.dbshop.rule;

import com.leyunone.dbshop.annotate.RuleHandler;
import com.leyunone.dbshop.system.factory.AbstractRuleFactory;
import com.leyunone.dbshop.system.factory.TransformRuleHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * :)
 * 字段类型转换规则
 * @Author LeYunone
 * @Date 2023/6/9 14:20
 */
@RuleHandler("type_transform")
public class SqlDataTypeRule extends AbstractRule {

    @Autowired
    private TransformRuleHandlerFactory factory;
    
    @Override
    public AbstractRuleFactory registRuleFactory() {
        return factory;
    }

    @Override
    public void handler() {
        
    }
}
