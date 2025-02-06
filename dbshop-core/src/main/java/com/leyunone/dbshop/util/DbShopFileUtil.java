package com.leyunone.dbshop.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * :)
 * dbshop文件工具
 *
 * @Author leyunone
 * @Date 2023/9/18 17:51
 */
public class DbShopFileUtil {

    /**
     * 迭代找到.java 文件
     *
     * @param file
     */
    public static List<Class<?>> iterationForJavaClass(File file) {
        Stack<File> dirFiles = new Stack<>();
        dirFiles.add(file);
        List<Class<?>> result = new ArrayList<>();
        while (!dirFiles.empty()) {
            File pop = dirFiles.pop();
            if (pop.isDirectory()) {
                dirFiles.addAll(CollectionUtil.newArrayList(pop.listFiles()));
            } else {
                try {
                    Class<?> aClass = Class.forName(file.getCanonicalPath());
                    if(ObjectUtil.isNotNull(aClass)){
                        result.add(aClass);
                    }
                }catch (Exception e){
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        iterationForJavaClass(new File("E:\\TheCore\\dbshop"));
    }
}
