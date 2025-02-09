package com.leyunone.dbshop.manager;

import com.leyunone.dbshop.util.MyClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * :)
 * 注解加载工具
 *
 * @Author pengli
 * @Date 2025/2/8 17:55
 */
@Component
public class AnnotateLoadingManager {

    public AnnotateLoadingManager() {
        this.traverseFieldName();
        this.traverseTableName();
    }

    public static class AnnotateObject {
        //注解的字节码
        private Class<? extends Annotation> clazz;
        /**
         * 取这个注解的哪个值 比如@TableName 中的value
         */
        private String value;

        public AnnotateObject(Class<? extends Annotation> clazz, String value) {
            this.clazz = clazz;
            this.value = value;
        }

        public Class<? extends Annotation> getClazz() {
            return clazz;
        }

        public void setClazz(Class<? extends Annotation> clazz) {
            this.clazz = clazz;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class MybatisPlusExplain {

        public static final String MYBATIS_PLUS_TABLENAME = "com.baomidou.mybatisplus.annotation.TableName";

        public static final String MYBATIS_PLUS_TABLEFIELD = "com.baomidou.mybatisplus.annotation.TableField";

        private static List<AnnotateObject> tableAnnotateClass = new ArrayList<>();

        private static List<AnnotateObject> fieldAnnotateClass = new ArrayList<>();

        public static class TableNameAnnotate extends AnnotateObject {

            public TableNameAnnotate(Class<? extends Annotation> clazz, String value) {
                super(clazz, value);
            }
        }

        public static class TableFieldAnnotate extends AnnotateObject {

            public TableFieldAnnotate(Class<? extends Annotation> clazz, String value) {
                super(clazz, value);
            }
        }

        public static void addTableAnnotate(AnnotateObject annotateObject) {
            tableAnnotateClass.add(annotateObject);
        }

        public static void addFieldAnnotate(AnnotateObject annotateObject) {
            fieldAnnotateClass.add(annotateObject);
        }
    }


    private void traverseTableName() {
        try {
            //mybatis-plus
            Class<?> aClass = Class.forName(MybatisPlusExplain.MYBATIS_PLUS_TABLENAME);
            MybatisPlusExplain.TableNameAnnotate tableName = new MybatisPlusExplain.TableNameAnnotate((Class<? extends Annotation>) aClass, "value");
            MybatisPlusExplain.addTableAnnotate(tableName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void traverseFieldName() {
        try {
            //mybatis-plus
            Class<?> aClass = Class.forName(MybatisPlusExplain.MYBATIS_PLUS_TABLEFIELD);
            MybatisPlusExplain.TableFieldAnnotate tableName = new MybatisPlusExplain.TableFieldAnnotate((Class<? extends Annotation>) aClass, "value");
            MybatisPlusExplain.addTableAnnotate(tableName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 表名注解值
     *
     * @param clazz 待检查文件字节码
     * @return
     */
    public String tableNameAnnotateValue(Class<?> clazz) {
        /**
         * 遍历工具事先加载的注解信息
         * 1 - mybatis-plus
         */
        for (AnnotateObject tableAnnotateClass : MybatisPlusExplain.tableAnnotateClass) {
            String annotateValue = MyClassUtils.getAnnotateValue(clazz, tableAnnotateClass.getClazz(), tableAnnotateClass.getValue());
            if (StringUtils.isNotBlank(annotateValue)) {
                return annotateValue;
            }
        }
        return null;
    }

    /**
     * 检查是否是表列字段
     *
     * @param clazz 待检查文件字节码
     * @return
     */
    public String checkIfField(Class<?> clazz) {
        /**
         * 遍历工具事先加载的注解信息
         * 1 - mybatis-plus
         */
        for (AnnotateObject tableAnnotateClass : MybatisPlusExplain.fieldAnnotateClass) {
            Method annotateField = MyClassUtils.getAnnotateField(clazz, tableAnnotateClass.getClazz(), tableAnnotateClass.getValue());
            if (null == annotateField) {
                continue;
            }
            try {
                Annotation annotation = clazz.getAnnotation(tableAnnotateClass.getClazz());
                Object o = annotateField.invoke(annotation);
                if (null != o && StringUtils.isNotBlank(o.toString())) {
                    return o.toString();
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
