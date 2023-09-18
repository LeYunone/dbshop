package com.leyunone.dbshop.util;

import cn.hutool.core.io.FileUtil;

import java.io.File;
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
     * 迭代找到.java或.class 文件
     *
     * @param file
     */
    public static void iterationForJavaOrClass(File file, List<File> files) {
        Stack<File> dirFiles = new Stack<>();
        dirFiles.add(file);
        while(!dirFiles.empty()){
            File pop = dirFiles.pop();
            if(pop.isDirectory()){

            }else{
                if()
            }
        }
    }
}
