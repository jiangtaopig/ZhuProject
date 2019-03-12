package com.example.za_zhujiangtao.zhupro.lock_view;

/**
 * Created by za-zhujiangtao on 2018/12/17.
 */

public class CircleRect {
    // 圆圈所代表的数字（1~9）
    private int code;
    // 圆心的X坐标
    private int x;
    // 圆心的Y坐标
    private int y;
    // 圆圈的当前状态
    private int state;

    public CircleRect() {
    }

    public CircleRect(int code, int x, int y, int state) {
        this.code = code;
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
