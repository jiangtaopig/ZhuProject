package com.example.za_zhujiangtao.zhupro.swipe_back.app;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.za_zhujiangtao.zhupro.swipe_back.SwipeBackLayout;
import com.example.za_zhujiangtao.zhupro.swipe_back.Utils;

import java.lang.ref.WeakReference;


/**
 * Created by laysionqet on 2018/4/24.
 */
public class SwipeBackListenerActivityAdapter implements SwipeBackLayout.SwipeListenerEx {
    private final WeakReference<Activity> mActivity;

    public SwipeBackListenerActivityAdapter(@NonNull Activity activity) {
        mActivity = new WeakReference<>(activity);
    }

    @Override
    public void onScrollStateChange(int state, float scrollPercent) {

    }

    @Override
    public void onEdgeTouch(int edgeFlag) {
        Activity activity = mActivity.get();
        if (null != activity) {
            Utils.convertActivityToTranslucent(activity);
        }
    }

    @Override
    public void onScrollOverThreshold() {

    }

    @Override
    public void onContentViewSwipedBack() {
        Activity activity = mActivity.get();
        if (null != activity && !activity.isFinishing()) {
            activity.finish();
            activity.overridePendingTransition(0, 0);
        }
    }
}
