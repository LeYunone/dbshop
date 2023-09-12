package com.leyunone.dbshop.handler.sql;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.annotate.RuleHandler;
import com.leyunone.dbshop.annotate.SqlHandler;
import com.leyunone.dbshop.bean.ResponseCode;
import com.leyunone.dbshop.enums.SqlModelEnum;
import com.leyunone.dbshop.system.factory.AbstractRuleFactory;
import com.leyunone.dbshop.system.factory.AbstractSqlProductionFactory;
import com.leyunone.dbshop.util.AssertUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Arrays;
import java.util.List;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2023/9/12 13:47
 */
public abstract class SqlProductionAbstractHandler implements InitializingBean {

    public abstract String handler(JSONObject json);

    private SqlModelEnum setIdentif() {
        SqlHandler annotation = AnnotationUtils.getAnnotation(this.getClass(), SqlHandler.class);
        AssertUtil.isFalse(ObjectUtil.isNull(annotation), ResponseCode.RULE_LOAD_FAIL);
        return annotation.value();
    }

    abstract AbstractSqlProductionFactory registFactory();

    @Override
    public void afterPropertiesSet() {
        AbstractSqlProductionFactory abstractSqlProductionFactory = registFactory();
        if(ObjectUtil.isNull(abstractSqlProductionFactory)) return;
        //没有注册工厂 退回规则
        abstractSqlProductionFactory.register(setIdentif(), this);
    }
}
