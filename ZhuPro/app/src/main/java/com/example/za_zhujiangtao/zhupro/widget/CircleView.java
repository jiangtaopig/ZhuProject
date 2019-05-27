package com.example.za_zhujiangtao.zhupro.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CircleView extends View {

    private Paint mPaint;
    private int mCurrentX = 100;
    private int mCurrentY = 100;

    public CircleView(Context context) {
        super(context);
        init(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            width = dp2px(200);
            height = dp2px(100);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = dp2px(200);
        } else {
            height = dp2px(100);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //margin 是由父布局来处理的，所以子View不用处理，而padding如果子View不处理的话则没效果
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        int radius = width / 2 > height / 2 ? height / 2 : width / 2;
        mCurrentX = paddingLeft + width / 2;
        mCurrentY = paddingTop + height / 2;
        canvas.drawCircle(mCurrentX, mCurrentY, radius, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        mCurrentX = (int) event.getRawX();
//        mCurrentY = (int) event.getRawY();
//        invalidate();
        return true;
    }

    private int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density; //当前屏幕密度因子
        return (int) (dp * scale + 0.5f);
    }
}
