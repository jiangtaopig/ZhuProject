package com.example.za_zhujiangtao.zhupro.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.za_zhujiangtao.zhupro.R;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/11/22
 */
public class MyLayout1 extends RelativeLayout {
    public MyLayout1(Context context) {
        this(context, null);
    }

    public MyLayout1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLayout1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.my_layout1, this, true);
    }
}
