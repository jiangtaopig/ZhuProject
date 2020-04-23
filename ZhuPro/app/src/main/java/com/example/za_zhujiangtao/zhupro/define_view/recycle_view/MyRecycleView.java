package com.example.za_zhujiangtao.zhupro.define_view.recycle_view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/4/21
 */
public class MyRecycleView extends RecyclerView {

    private OnLayoutListener layoutListener;

    public MyRecycleView(@NonNull Context context) {
        super(context);
    }

    public MyRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setLayoutListener(OnLayoutListener layoutListener) {
        this.layoutListener = layoutListener;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("MyRecycleView", "onLayout layoutListener = "+layoutListener);
        if (layoutListener != null){
            layoutListener.beforeLayout();
        }
        super.onLayout(changed, l, t, r, b);

        if (layoutListener != null){
            layoutListener.afterLayout();
        }
    }

    public interface OnLayoutListener{
        void beforeLayout();
        void afterLayout();
    }
}
