package com.leyunone.dbshop.util;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
     * @param
     */
    public static List<Class<?>> iterationForJavaClass() {
        List<Class<?>> classes = new ArrayList<>();
        try {
            // 获取当前线程的上下文类加载器
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            // 获取类路径
            Enumeration<URL> resources = classLoader.getResources("");
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                String protocol = resource.getProtocol();
                if ("file".equals(protocol)) {
                    // 处理文件系统中的类
                    String filePath = URLDecoder.decode(resource.getFile(), "UTF-8");
                    findClassesInDirectory(new File(filePath), "", classes);
                } else if ("jar".equals(protocol)) {
                    // 处理 JAR 文件中的类
                    String jarFilePath = resource.getPath().substring(5, resource.getPath().indexOf("!"));
                    findClassesInJar(new File(URLDecoder.decode(jarFilePath, "UTF-8")), classes);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * 在指定目录中查找类
     *
     * @param directory   要查找的目录
     * @param packageName 当前包名
     * @param classes     存储类的列表
     * @throws ClassNotFoundException 如果类未找到
     */
    private static void findClassesInDirectory(File directory, String packageName, List<Class<?>> classes) throws ClassNotFoundException {
        if (!directory.exists()) {
            return;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 递归查找子目录
                    findClassesInDirectory(file, packageName + file.getName() + ".", classes);
                } else if (file.getName().endsWith(".class")) {
                    // 获取类的全限定名
                    String className = packageName + file.getName().substring(0, file.getName().length() - 6);
                    // 加载类
                    classes.add(Class.forName(className));
                }
            }
        }
    }

    /**
     * 在指定 JAR 文件中查找类
     *
     * @param jarFile 要查找的 JAR 文件
     * @param classes 存储类的列表
     * @throws IOException            如果发生 I/O 错误
     * @throws ClassNotFoundException 如果类未找到
     */
    private static void findClassesInJar(File jarFile, List<Class<?>> classes) throws IOException, ClassNotFoundException {
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entryName.endsWith(".class")) {
                    // 获取类的全限定名
                    String className = entryName.replace('/', '.').substring(0, entryName.length() - 6);
                    // 加载类
                    classes.add(Class.forName(className));
                }
            }
        }
    }

}
