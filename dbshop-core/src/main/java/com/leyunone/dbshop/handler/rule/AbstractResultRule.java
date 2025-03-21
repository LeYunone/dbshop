package com.leyunone.dbshop.handler.rule;

import com.leyunone.dbshop.bean.rule.TargetRule;

/**
 * :)
 * 有结果集返回的策略
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-11
 */
public abstract class AbstractResultRule<T extends TargetRule> extends AbstractRule<T> {

    //无处理
    @Override
    protected void handler(T t){
        return;
    }
}
