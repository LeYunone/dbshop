package com.leyunone.dbshop.util;

import java.util.*;

public class MyCollectionUtils {

    public static <T> List<T> newArrayList(T... t) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, t);
        return list;
    }

    public static <T> T getLast(Collection<T> collection) {
        if (null == collection) {
            return null;
        } else {
            int size = collection.size();
            if (0 == size) {
                return null;
            } else {
                int index = size - 1;

                if (index < size) {
                    if (collection instanceof List) {
                        List<T> list = (List) collection;
                        return list.get(index);
                    }
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public static <T> List<T> newArrayList(Collection<T> t) {
        if (t == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(t);
    }

    /**
     * 将集合元素用分隔符拼接成字符串
     *
     * @param collection 集合（支持 List、Set 等）
     * @param separator  分隔符（如 ", "）
     * @return 拼接后的字符串，如果集合为空或 null，返回空字符串 ""
     */
    public static String join(Iterable<?> collection, String separator) {
        if (collection == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Iterator<?> iterator = collection.iterator();

        // 遍历集合元素
        while (iterator.hasNext()) {
            Object element = iterator.next();
            if (element != null) {
                // 将元素转为字符串（处理 null 元素）
                sb.append(element.toString());
            }
            // 添加分隔符（最后一个元素不添加）
            if (iterator.hasNext()) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }


}