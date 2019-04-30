package com.example.za_zhujiangtao.zhupro.api;

/**
 * Created by za-zhujiangtao on 2018/11/7.
 */

public class RequestParams {

    /**
     * phone : 13122331347
     * passwd : 123456l
     */

    private String phone;
    private String passwd;

    public RequestParams(String phone, String passwd){
        this.phone = phone;
        this.passwd = passwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
