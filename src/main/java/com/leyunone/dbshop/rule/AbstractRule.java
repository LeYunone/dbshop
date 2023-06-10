package com.leyunone.dbshop.rule;

import cn.hutool.core.util.ObjectUtil;
import com.leyunone.dbshop.annotate.RuleHandler;
import com.leyunone.dbshop.bean.ResponseCode;
import com.leyunone.dbshop.system.factory.AbstractRuleFactory;
import com.leyunone.dbshop.util.AssertUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Arrays;
import java.util.List;

/**
 * :)
 *
 * @Author pengli
 * @Date 2023/6/9 14:20
 */
public abstract class AbstractRule implements InitializingBean {

    private List<String> setRuleIdentif() {
        RuleHandler annotation = AnnotationUtils.getAnnotation(this.getClass(), RuleHandler.class);
        AssertUtil.isFalse(ObjectUtil.isNull(annotation), ResponseCode.RULE_LOAD_FAIL);
        assert annotation != null;
        return Arrays.asList(annotation.identifs());
    }

    public abstract AbstractRuleFactory registRuleFactory();

    public abstract void handler();
    
    @Override
    public void afterPropertiesSet() {
        AbstractRuleFactory abstractRuleFactory = registRuleFactory();
        if(ObjectUtil.isNull(abstractRuleFactory)) return;
        //没有注册工厂 退回规则
        
        this.setRuleIdentif().forEach((t) -> {
            abstractRuleFactory.register(t, this);
        });
    }
}
