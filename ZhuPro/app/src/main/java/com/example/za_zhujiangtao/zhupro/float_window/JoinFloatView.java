package com.example.za_zhujiangtao.zhupro.float_window;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/8/9
 */
public class JoinFloatView extends View {
    private static final int RADIUS = 200;
    private Paint mPaint;

    public JoinFloatView(Context context) {
        super(context);
        init(context);
    }

    public JoinFloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public JoinFloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            width = DisplayUtil.dip2px(RADIUS);
            height = DisplayUtil.dip2px(RADIUS);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = DisplayUtil.dip2px(RADIUS);
        } else {
            height = DisplayUtil.dip2px(RADIUS);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("JoinFloatView", "onDraw width = " + DisplayUtil.dip2px(RADIUS) + ", screenW = "+DisplayUtil.getScreenWidth(getContext())+", screenH = "+DisplayUtil.getScreenHeight(getContext()));
        RectF mRecF = new RectF(0, 0, DisplayUtil.dip2px(RADIUS), DisplayUtil.dip2px(RADIUS));
        canvas.drawArc(mRecF, 180, 90, true, mPaint);
    }
}
