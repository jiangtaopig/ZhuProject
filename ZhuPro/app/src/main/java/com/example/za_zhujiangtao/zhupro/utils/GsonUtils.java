package com.example.za_zhujiangtao.zhupro.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/7/29
 */
public class GsonUtils {
    /**
     * 解析字符串字典
     */
    public static <T> T parseJson2Object(String json, Class<T> type) {
        T result = new Gson().fromJson(json, type);
        return result;
    }

    /**
     * 解析数组 泛型在编译期类型擦除导致，
     */
    public static <T> List<T> parseJson2Array(String json, Class<T> type) {
        List<T> list = new Gson().fromJson(json, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }

    public static <T> List<T> jsonToBeanList(String json, Class<T> t) {
        List<T> list = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonArray jsonarray = parser.parse(json).getAsJsonArray();
        for (JsonElement element : jsonarray
        ) {
            list.add(new Gson().fromJson(element, t));
        }
        return list;
    }

    public static String parseString(Object object){
       return new Gson().toJson(object);
    }
}
