package com.example.za_zhujiangtao.zhupro.api;

import android.util.Log;

import com.example.za_zhujiangtao.zhupro.FileUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by za-zhujiangtao on 2018/8/7.
 */

public class LogUtils {

    private volatile int mCount;
    private String mFileName = "";
    private final int MAX_NUM = 5;
    private final String DIR = "zuifuli2";

    private LogUtils() {

    }

    private static LogUtils mInstance;

    public static LogUtils getmInstance() {
        if (mInstance == null) {
            synchronized (LogUtils.class) {
                if (mInstance == null) {
                    mInstance = new LogUtils();
                }
            }
        }
        return mInstance;
    }

    public synchronized void writeDataToFile(String content) {
        Observable.just(content)
                .observeOn(Schedulers.io())
                .map((String s) -> {
                    synchronized (LogUtils.class) {
                        if (mCount % MAX_NUM == 0) {
                            mFileName = System.currentTimeMillis() + ".log";
                            FileUtils.writeFile(DIR, mFileName, "["+content);
                        }else if (mCount == MAX_NUM -1){
                            FileUtils.writeFile(DIR, mFileName, content.substring(0, content.length() - 2)+"]");
                        }else {
                            FileUtils.writeFile(DIR, mFileName, content);
                        }
                        Log.e("xxxxxxxxxxxxxxx", "mCount = " + mCount + ", mFileName = " + mFileName);
                        mCount++;
                        if (mCount % MAX_NUM == 0) {
                            //TODO...
                            mCount = mCount % MAX_NUM;
                            String dd = FileUtils.readFile(DIR, mFileName);
                            Log.e("xxxxxxxxxxxxxxx", "mCount = " + mCount + ", dd = " + dd);

                            List<LogBean> logBeans = parserArray(dd);
                            Log.e("xxxxxxxxxxxxxxx", "logBeans size = "+logBeans.size());
                        }
                    }
                    return true;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {

                });

    }

    private List<LogBean> parserArray(String data){
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(data).getAsJsonArray();
        Gson gson = new Gson();
        List<LogBean> beans = new ArrayList<>();
        for (JsonElement log : jsonArray) {
            LogBean bean = gson.fromJson(log, LogBean.class);
            beans.add(bean);
        }
        return beans;
    }

}
