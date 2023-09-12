package com.leyunone.dbshop.system.factory;

import com.leyunone.dbshop.handler.rule.AbstractRule;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * :)
 * 规则 策略工厂
 * 转化策略工厂
 *
 * @Author LeYunone
 * @Date 2023/6/9 11:23
 */
@Component
public class TransformRuleHandlerFactory extends AbstractRuleFactory{

    private final ConcurrentHashMap<Object, AbstractRule<?>> handlers = new ConcurrentHashMap<>(16);

    @Override
    public void register(Object identif, AbstractRule<?> handler) {
        handlers.put(identif, handler);
    }

    @Override
    public AbstractRule<?> getHandler(Object identif) {
        return handlers.get(identif);
    }
}
