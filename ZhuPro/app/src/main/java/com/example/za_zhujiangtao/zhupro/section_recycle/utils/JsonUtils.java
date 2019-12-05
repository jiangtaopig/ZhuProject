package com.example.za_zhujiangtao.zhupro.section_recycle.utils;

import android.content.Context;

import com.example.za_zhujiangtao.zhupro.city_select.CityEntity;
import com.example.za_zhujiangtao.zhupro.section_recycle.entity.HotelEntity;
import com.google.gson.Gson;


/**
 * Created by lyd10892 on 2016/8/23.
 */

public class JsonUtils {

    public static HotelEntity analysisJsonFile(Context context, String fileName){
        String content = FileUtils.readJsonFile(context,fileName);
        Gson gson = new Gson();
        HotelEntity entity = gson.fromJson(content, HotelEntity.class);
        return  entity;
    }

    public static CityEntity parseCityDataFromJson(Context context, String fileName){
        String content = FileUtils.readJsonFile(context,fileName);
        Gson gson = new Gson();
        CityEntity cityEntity = gson.fromJson(content, CityEntity.class);
        return  cityEntity;
    }
}
