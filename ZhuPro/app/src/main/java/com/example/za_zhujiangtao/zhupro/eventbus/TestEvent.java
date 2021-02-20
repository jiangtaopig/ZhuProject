package com.example.za_zhujiangtao.zhupro.eventbus;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/2/20
 */
class TestEvent {
    private String action;

    public TestEvent(String action){
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
