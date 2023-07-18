package com.leyunone.dbshop.util;

import com.leyunone.dbshop.bean.ResponseCode;
import com.leyunone.dbshop.exception.DbShopException;

/**
 * @author leyunone
 * @create 2021-08-13 09:31
 *
 * 报错处理
 */
public class AssertUtil {

    public static void isFalse(boolean condition, ResponseCode code){
        isFalse(condition,code.getDesc());
    }

    public static void isFalse(boolean condition,String message){
        isFalse(condition,new DbShopException(message));
    }

    public static void isFalse(boolean condition){
        isFalse(condition,new DbShopException("system error"));
    }

    public static void isFalse(boolean condition,DbShopException ex){
        isTrue(!condition,ex);
    }

    public static void isTrue(boolean condition,DbShopException ex){
        if(!condition){
            throw ex;
        }
    }

    public static void isTrue(boolean condition,String msg) throws DbShopException {
        if(!condition){
            throw new DbShopException(msg);
        }
    }

    public static void isTrue(boolean condition){
        if(!condition){
            throw new DbShopException("系统异常");
        }
    }

    public static void isTrue(String msg){
        throw new DbShopException(msg);
    }
}
