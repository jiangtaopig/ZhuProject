package com.example.za_zhujiangtao.zhupro.arouter;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

@Interceptor(priority = 2)
public class TestInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Log.e("TestInterceptor", "process"+", path = "+postcard.getPath());
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        Log.e("TestInterceptor", "has init");
    }
}
