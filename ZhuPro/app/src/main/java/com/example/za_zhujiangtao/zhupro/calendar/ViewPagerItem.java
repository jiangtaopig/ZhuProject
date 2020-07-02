package com.example.za_zhujiangtao.zhupro.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.za_zhujiangtao.zhupro.R;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/7/2
 */
public class ViewPagerItem extends LinearLayout {

    private TextView mTitleTxt;

    public ViewPagerItem(Context context) {
        this(context, null);
    }

    public ViewPagerItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.view_pager_item_view, this, true);
        mTitleTxt = findViewById(R.id.txt_title);
    }

    public void setTitle(String data){
        mTitleTxt.setText(data);
    }
}
