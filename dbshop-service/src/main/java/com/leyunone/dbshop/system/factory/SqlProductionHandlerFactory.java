package com.leyunone.dbshop.system.factory;

import com.leyunone.dbshop.handler.sql.SqlProductionAbstractHandler;
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
public class SqlProductionHandlerFactory extends AbstractSqlProductionFactory{

    private final ConcurrentHashMap<Object, SqlProductionAbstractHandler> handlers = new ConcurrentHashMap<>(16);

    @Override
    public void register(Object identif, SqlProductionAbstractHandler handler) {
        handlers.put(identif, handler);
    }

    @Override
    public SqlProductionAbstractHandler getHandler(Object identif) {
        return handlers.get(identif);
    }
}
