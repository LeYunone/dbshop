package com.leyunone.dbshop.system.factory;

import com.leyunone.dbshop.rule.AbstractRule;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/10 17:10
 */
public abstract class AbstractRuleFactory {

    public abstract void register(String identif, AbstractRule handler);

    public abstract AbstractRule getHandler(String identif);
}
