package com.example.za_zhujiangtao.zhupro.http.api;


import android.text.TextUtils;

public class ApiException extends RuntimeException {
    public static final int CODE_UNKNOWN_ERROR = -99999;
    public static final int CODE_NO_NET_ERROR = CODE_UNKNOWN_ERROR - 1;
    public static final int CODE_SERVER_ERROR = CODE_NO_NET_ERROR - 1;
    public static final int CODE_LOCAL_ERROR = CODE_SERVER_ERROR - 1;
    private int errorCode;
    private String errorMessage;

    public ApiException(int errorCode) {
        this(errorCode, null);
    }

    public ApiException(int errorCode, String errorMessage) {
        this(errorCode, errorMessage, null);
    }

    public ApiException(int errorCode, String errorMessage, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.errorMessage = TextUtils.isEmpty(errorMessage) ? "":errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
