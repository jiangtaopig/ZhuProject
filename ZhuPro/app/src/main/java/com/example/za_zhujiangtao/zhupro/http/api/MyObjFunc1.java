package com.example.za_zhujiangtao.zhupro.http.api;

import android.text.TextUtils;


/**
 * Created by fanjianfeng on 2019/7/11.
 */

public class MyObjFunc1<T> extends ApiFunc1<T> {

    @Override
    protected String getErrorMessage(BaseApiResult<T> tBaseApiResult) {
        String errorMsg;
        if (tBaseApiResult.getCode() == 130003) {
            errorMsg = "登录超时，请重新登录";
        } else {
            errorMsg = super.getErrorMessage(tBaseApiResult);
        }
        if (TextUtils.isEmpty(errorMsg)) {
            errorMsg = "请求异常，请稍后重试";
        }
        return errorMsg;
    }
}
