package com.leyunone.dbshop.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2025/2/9
 */
public class MyClassUtils {

    /**
     * 获取注解的字段值
     *
     * @param fileClass         文件字节码
     * @param annotate          注解字节码
     * @param annotateValueName 注解字段值
     * @return
     */
    public static String getAnnotateValue(Class<?> fileClass, Class<? extends Annotation> annotate, String annotateValueName) {
        Annotation annotation = fileClass.getAnnotation(annotate);
        if (null != annotation) {
            try {
                Method method = annotation.getClass().getMethod(annotateValueName);
                return method.invoke(annotation).toString();
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            }
        }
        return null;
    }

    /**
     * 获取注解的字段值
     *
     * @param fileClass         文件字节码
     * @param annotate          注解字节码
     * @param annotateValueName 注解字段值
     * @return
     */
    public static Method getAnnotateField(Class<?> fileClass, Class<? extends Annotation> annotate, String annotateValueName) {
        Annotation annotation = fileClass.getAnnotation(annotate);
        if (null != annotation) {
            try {
                return annotation.getClass().getMethod(annotateValueName);
            } catch (NoSuchMethodException e) {
            }
        }
        return null;
    }
}
