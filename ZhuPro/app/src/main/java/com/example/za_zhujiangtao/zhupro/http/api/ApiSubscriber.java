package com.example.za_zhujiangtao.zhupro.http.api;

import android.accounts.NetworkErrorException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.za_zhujiangtao.zhupro.MainApplication;

import rx.Subscriber;

/**
 * Created by fanjianfeng on 2017/3/7.
 *
 * @param <T>
 */
public abstract class ApiSubscriber<T> extends Subscriber<T> {
    private static final String TAG = "ApiSubscriber";
    @Override
    public void onCompleted() {
        onFinallyFinished();
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "", e);
        ApiException apiException = measureApiException(e);
        preHandleException(apiException);
        toastErrMsg(apiException);
        onApiFailed(apiException);
        onFinallyFinished();
    }

    /**
     * 根据错误创建不同的ApiException
     * @param e
     * @return
     */
    @NonNull
    private ApiException measureApiException(Throwable e) {
        ApiException exception;
        if (e instanceof ApiException) {
            exception = (ApiException) e;
        } else if (e instanceof NetworkErrorException) {
            exception = new ApiException(ApiException.CODE_NO_NET_ERROR, "网络连接异常，请稍后再试");
        } else {
            exception = new ApiException(ApiException.CODE_SERVER_ERROR, "服务器偷懒啦，请稍后再试");
        }
        return exception;
    }

    /**
     * 预处理错误
     * @param apiException
     */
    protected void preHandleException(ApiException apiException) {

    }

    protected void toastErrMsg(@NonNull ApiException apiException) {
        if (!TextUtils.isEmpty(apiException.getErrorMessage())) {
            Toast.makeText(MainApplication.getContext(), apiException.getErrorMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainApplication.getContext(), "请求异常，请稍后重试", Toast.LENGTH_LONG).show();
        }
    }

    public void onApiFailed(@NonNull ApiException apiException) {
        Log.e(TAG, "errorCode = " + apiException.getErrorCode() + ",message = " + apiException.getErrorMessage());
    }

    /**
     * invoked finally , after{@link #onCompleted()} or {@link #onError(Throwable)} is invoked
     */
    public void onFinallyFinished() {
        // do nothing by default
        unsubscribe();
    }
}
