package com.leyunone.dbshop.util;

import com.alibaba.fastjson.JSONObject;
import com.leyunone.dbshop.bean.info.ColumnInfo;
import com.leyunone.dbshop.bean.info.IndexInfo;
import com.leyunone.dbshop.bean.info.TableDetailInfo;

import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-06-08
 */
public class SqlPackUtil {
    
    public static final String COLUMN = ColumnInfo.class.getSimpleName();
    
    public static final String TABLE = TableDetailInfo.class.getSimpleName();

    public static final String INDEX = IndexInfo.class.getSimpleName();

    public static final String COLUMNS = ColumnInfo.class.getSimpleName()+"s";
    
    public static final String INDEXS = IndexInfo.class.getSimpleName()+"s";
    
    public static <T>T resoleJsonData (JSONObject json,Class<T> tClass) {
        return JSONObject.parseObject(json.getString(tClass.getSimpleName()),tClass);
    }

    public static <T>List<T> resoleJsonDatas (JSONObject json,Class<T> tClass) {
        return JSONObject.parseArray(json.getString(tClass.getSimpleName()+"s"),tClass);
    }

}
