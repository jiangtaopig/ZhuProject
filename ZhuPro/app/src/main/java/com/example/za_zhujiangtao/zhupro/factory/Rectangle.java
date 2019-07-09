package com.example.za_zhujiangtao.zhupro.factory;

import com.zjt.annotation_my.Factory;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/7/9
 */
@Factory(id = Constants.RECTANGLE_SHAPE, type = Shape.class)
public class Rectangle implements Shape {

    @Override
    public void draw() {
        System.out.println("i am " + Constants.RECTANGLE_SHAPE);
    }
}
