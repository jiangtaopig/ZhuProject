package com.example.za_zhujiangtao.zhupro.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by za-zhujiangtao on 2019/1/4.
 */

public class SharePreferenceUtils {
    private static final String DATA_NAME = "recently_using_app";

    public static void putString(Context context, String key, String value) {
        if (key == null || "".equals(key))
            return;
        SharedPreferences sp = getSharedPreferences(context);
        try {
            sp.edit().putString(key.toLowerCase(), value).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        if (null == context) {
            return null;
        }
        return context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        if (sp.contains(key.toLowerCase()))
            return sp.getString(key.toLowerCase(), "");
        else
            return "";
    }

}
