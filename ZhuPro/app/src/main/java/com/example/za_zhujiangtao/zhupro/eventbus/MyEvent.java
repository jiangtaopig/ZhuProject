package com.example.za_zhujiangtao.zhupro.eventbus;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/2/20
 */
class MyEvent {
    private String action;

    public MyEvent(String action){
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
