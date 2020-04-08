package com.example.za_zhujiangtao.zhupro.http.manager;

import java.util.HashMap;
import java.util.Map;

public class HeaderManager {
    private Map<String, String> mHeaders;
    private volatile static HeaderManager mInstance;

    public static HeaderManager getInstance() {
        if(mInstance == null) {
            mInstance = new HeaderManager();
        }
        return mInstance;
    }

    private HeaderManager() {
        mHeaders = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        mHeaders.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }
}
