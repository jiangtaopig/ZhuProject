package com.example.za_zhujiangtao.zhupro.calendar;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/7/2
 */
public class PagerScroller extends Scroller {
    private int mDuration = 150;

    public PagerScroller(Context context) {
        super(context);
    }

    public PagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public PagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setDuration(int time) {
        mDuration = time;
    }
}
