package com.example.za_zhujiangtao.zhupro.http.api;


import android.widget.Toast;

import com.example.za_zhujiangtao.zhupro.MainApplication;

/**
 * Created by fanjianfeng on 2017/3/7.
 *
 * @param <T>
 */
public abstract class MyFunc1Subscriber<T> extends ApiSubscriber<T> {

    @Override
    protected void preHandleException(ApiException apiException) {
        switch (apiException.getErrorCode()) {
            case 130003:
//                EventBus.getDefault().post(new ReLoginEvent());
                Toast.makeText(MainApplication.getContext(), "请重新登录", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
