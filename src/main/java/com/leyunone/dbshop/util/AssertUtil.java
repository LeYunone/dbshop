package com.leyunone.dbshop.util;

/**
 * @author leyunone
 * @create 2021-08-13 09:31
 *
 * 报错处理
 */
public class AssertUtil {

    public static void isFalse(boolean condition,String message){
        isFalse(condition,new RuntimeException(message));
    }

    public static void isFalse(boolean condition,RuntimeException ex){
        isTrue(!condition,ex);
    }

    public static void isTrue(boolean condition,RuntimeException ex){
        if(!condition){
            throw ex;
        }
    }

    public static void isTrue(boolean condition,String msg){
        if(!condition){
            throw new RuntimeException(msg);
        }
    }

    public static void isTrue(boolean condition){
        if(!condition){
            throw new RuntimeException("系统异常");
        }
    }

    public static void isTrue(String msg){
        throw new RuntimeException(msg);
    }
}
