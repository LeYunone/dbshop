package com.leyunone.dbshop.util;

import cn.hutool.core.util.ObjectUtil;
import com.leyunone.dbshop.constant.DbShopConstant;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-08
 */
public class TextFillUtil {

    /**
     * {} 消息填充
     * @param fill
     * @param content
     * @return
     */
    public static String fillStr(String fill, String[] content) {
        if (ObjectUtil.isEmpty(content)) return fill;
        StringBuilder sbuf = new StringBuilder(fill.length() + 50);
        int j;
        int r = 0;
        for (int i = 0; i < content.length; i++) {
            j = fill.indexOf(DbShopConstant.DELIM_STR, r);
            if (j == -1) {
                //没有填充内容
                sbuf.append(fill);
                break;
            } else {
                sbuf.append(fill, r, j);
                sbuf.append(content[i]);
            }
            r = j + DbShopConstant.DELIM_STR.length();
        }
        return sbuf.append(fill, r, fill.length()).toString();
    }
}
