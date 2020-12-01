package com.example.za_zhujiangtao.zhupro.http.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 *  接口的统一封装
 * @param <T>
 */
public class BaseApiResult<T> implements Serializable {
    private static final long serialVersionUID = -6182189632617616248L;
    /**
     * 成功状态码
     */
    private static final int CODE_SUCCESS = 0; //一般状态码 0 表示成功
    private static final int CODE_SUCCESS_1 = 1; //一般状态码 0 表示成功

    @SerializedName("status")
    private int code; //状态描述

    @SerializedName("msg")
    private String message;

    @SerializedName("result") // 慕课网这里改为data
    private T result;

    public T getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return code == CODE_SUCCESS || code == CODE_SUCCESS_1;
    }
}
