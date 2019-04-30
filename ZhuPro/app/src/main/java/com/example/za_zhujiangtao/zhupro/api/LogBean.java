package com.example.za_zhujiangtao.zhupro.api;

/**
 * Created by za-zhujiangtao on 2018/8/7.
 */

public class LogBean {
    String url;
    long requestStartTime;
    long requestEndTime;
    String responseCode;
    String requestAccount;
    String requestPlatform;
    String requestNet;
    String requestBody;
    String responseBody;
    String requestHeaders;
    String responseHeaders;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getRequestStartTime() {
        return requestStartTime;
    }

    public void setRequestStartTime(long requestStartTime) {
        this.requestStartTime = requestStartTime;
    }

    public long getRequestEndTime() {
        return requestEndTime;
    }

    public void setRequestEndTime(long requestEndTime) {
        this.requestEndTime = requestEndTime;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getRequestAccount() {
        return requestAccount;
    }

    public void setRequestAccount(String requestAccount) {
        this.requestAccount = requestAccount;
    }

    public String getRequestPlatform() {
        return requestPlatform;
    }

    public void setRequestPlatform(String requestPlatform) {
        this.requestPlatform = requestPlatform;
    }

    public String getRequestNet() {
        return requestNet;
    }

    public void setRequestNet(String requestNet) {
        this.requestNet = requestNet;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(String requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public String getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(String responseHeaders) {
        this.responseHeaders = responseHeaders;
    }
}
