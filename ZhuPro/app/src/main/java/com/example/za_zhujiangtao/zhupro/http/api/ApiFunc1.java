package com.example.za_zhujiangtao.zhupro.http.api;

import android.text.TextUtils;


import rx.functions.Func1;

/**
 * Created by fanjianfeng on 2017/3/7.
 */

public class ApiFunc1<T> implements Func1<BaseApiResult<T>, T> {
    @Override
    public T call(BaseApiResult<T> tBaseApiResult) {
        if (!tBaseApiResult.isSuccess()) {
            throw new ApiException(getErrorCode(tBaseApiResult), getErrorMessage(tBaseApiResult));
        }
        return tBaseApiResult.getResult();
    }

    protected String getErrorMessage(BaseApiResult<T> tBaseApiResult) {
        if (TextUtils.isEmpty(tBaseApiResult.getMessage())){
            return "";
        }
        return tBaseApiResult.getMessage();
    }

    protected int getErrorCode(BaseApiResult<T> tBaseApiResult) {
        return tBaseApiResult.getCode();
    }
}
