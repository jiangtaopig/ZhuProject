package com.example.za_zhujiangtao.zhupro.utils;

import com.example.za_zhujiangtao.zhupro.MainApplication;

public class Platform {
    private final String mPlatform;
    private static Platform mInstance;

    public static Platform getInstance() {
        if (mInstance == null) {
            mInstance = new Platform();
        }
        return mInstance;
    }

    private Platform() {
        mPlatform = AppUtils.getMetaValue(MainApplication.getInstance().getContext(), "APP_PLATFORM");
    }

    public boolean isMain() {
        return "ZJT_MAIN".equalsIgnoreCase(mPlatform);
    }

    public boolean isTest() {
        return "ZJT_TEST".equalsIgnoreCase(mPlatform);
    }
}
