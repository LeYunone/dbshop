package com.leyunone.dbshop.system.factory;

/**
 * :)
 *
 * @Author leyunone
 * @Date 2023/9/12 14:31
 */
public interface AbstractFactory<T> {

    void register(Object identif, T handler);

    T getHandler(Object identif);
}
