package com.leyunone.dbshop.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author leyunone
 * @create 2022/9/6
 */
public class CollectionFunctionUtils {

    /**
     * 将list转换为map
     * @param ls
     * @param rule 转换key规则
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T,R> Map<R,T> mapTo(List<T> ls , Function<T,R> rule){
        if(ls == null || ls.size() ==0){
            return null;
        }
        return ls.stream().collect(Collectors.toMap(rule,x->x,(k1,k2)->k1));
    }
    
    public static <T,R>Set<R> setTo(List<T> ls, Function<T,R> rule){
        if(ls == null || ls.size() ==0){
            return null;
        }
        return ls.stream().map(rule).collect(Collectors.toSet());
    }
}
