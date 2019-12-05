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
public class MyLayout2 extends RelativeLayout {
    public MyLayout2(Context context) {
        this(context, null);
    }

    public MyLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.my_layout2, this, true);
    }
}
