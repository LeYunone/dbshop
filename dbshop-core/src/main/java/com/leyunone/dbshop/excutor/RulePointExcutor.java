package com.leyunone.dbshop.excutor;

import com.leyunone.dbshop.annotate.RuleHandler;
import com.leyunone.dbshop.bean.rule.TargetRule;
import com.leyunone.dbshop.enums.RuleTypeEnum;
import com.leyunone.dbshop.handler.rule.AbstractRule;
import com.leyunone.dbshop.system.factory.AbstractRuleFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * :)
 * 规则执行人
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-11
 */
@Service
public class RulePointExcutor {

    /**
     * 遍历所有策略工厂
     */
    public void execute() {

    }

    /**
     * 指定策略
     *
     * @param factory
     */
    public <T extends TargetRule> List<String> execute(AbstractRuleFactory factory, T t) {
        List<String> strategys = t.getStrategys();
        //无策略
        if (CollectionUtils.isEmpty(strategys)) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        strategys.forEach((strategy) -> {
            AbstractRule abstractRule = factory.getHandler(strategy);
            if (null != abstractRule) {
                RuleHandler annotation = AnnotationUtils.getAnnotation(abstractRule.getClass(), RuleHandler.class);
                if (null == annotation) {
                    return;
                }
                RuleTypeEnum type = annotation.type();
                switch (type) {
                    case NONE:
                        abstractRule.runHandler(t);
                        break;
                    case RESULT:
                        result.add(abstractRule.runResultHandler(t));
                        break;
                    default:
                }
            }
        });
        return result;
    }
}
