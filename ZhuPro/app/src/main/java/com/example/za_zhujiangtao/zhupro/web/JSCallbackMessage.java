package com.example.za_zhujiangtao.zhupro.web;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by za-zhujiangtao on 2018/9/18.
 */

public class JSCallbackMessage<ResponseType> {
    private String action;

    private boolean success;

    private ResponseType params;

    public JSCallbackMessage(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public JSCallbackMessage setAction(String action) {
        this.action = action;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public JSCallbackMessage setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public ResponseType getParams() {
        return params;
    }

    public JSCallbackMessage setParams(ResponseType params) {
        this.params = params;
        return this;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String toString(ExclusionStrategy exclusionStrategy) {
        if (exclusionStrategy != null) {
            return new GsonBuilder().setExclusionStrategies(exclusionStrategy).create().toJson(this);
        } else {
            return new Gson().toJson(this);
        }
    }
}
