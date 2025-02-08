package com.leyunone.dbshop.util;

import java.util.ArrayList;
import java.util.List;

/**
 * :)
 * 注解加载工具
 *
 * @Author pengli
 * @Date 2025/2/8 17:55
 */
public class AnnotateLoadingUtil {

    public static class MybatisPlusExplain {

        public static final String MYBATIS_PLUS_TABLENAME = "com.baomidou.mybatisplus.annotation.TableName";

        public static final String MYBATIS_PLUS_TABLEFIELD = "com.baomidou.mybatisplus.annotation.TableField";

        private static List<AnnotateObject> tableAnnotateClass = new ArrayList<>();

        private static List<AnnotateObject> fieldAnnotateClass = new ArrayList<>();

        public static class TableNameAnnotate extends AnnotateObject {

            public TableNameAnnotate(Class<?> clazz, String value) {
                super(clazz, value);
            }
        }

        public static class TableFieldAnnotate extends AnnotateObject {

            public TableFieldAnnotate(Class<?> clazz, String value) {
                super(clazz, value);
            }
        }

        static class AnnotateObject {
            private Class<?> clazz;

            private String value;

            public AnnotateObject(Class<?> clazz, String value) {
                this.clazz = clazz;
                this.value = value;
            }

            public Class<?> getClazz() {
                return clazz;
            }

            public void setClazz(Class<?> clazz) {
                this.clazz = clazz;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static void addTableAnnotate(AnnotateObject annotateObject) {
            tableAnnotateClass.add(annotateObject);
        }

        public static void addFieldAnnotate(AnnotateObject annotateObject) {
            fieldAnnotateClass.add(annotateObject);
        }
    }


    public static List<Class<?>> traverseTableName() {
        List<Class<?>> classes = new ArrayList<>();
        try {
            //mybatis-plus
            Class<?> aClass = Class.forName(MybatisPlusExplain.MYBATIS_PLUS_TABLENAME);
            MybatisPlusExplain.TableNameAnnotate tableName = new MybatisPlusExplain.TableNameAnnotate(aClass, "value");
            MybatisPlusExplain.addTableAnnotate(tableName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public static List<Class<?>> traverseFieldName() {
        List<Class<?>> classes = new ArrayList<>();
        try {
            //mybatis-plus
            Class<?> aClass = Class.forName(MybatisPlusExplain.MYBATIS_PLUS_TABLEFIELD);
            MybatisPlusExplain.TableFieldAnnotate tableName = new MybatisPlusExplain.TableFieldAnnotate(aClass, "value");
            MybatisPlusExplain.addTableAnnotate(tableName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * 注解值
     *
     * @param clazz 注解
     * @return
     */
    public String annotateValue(Class<?> clazz) {
        return null;
    }
}
