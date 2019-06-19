package com.example.za_zhujiangtao.zhupro.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.scwang.smartrefresh.layout.util.DensityUtil;

public class ProgressBar extends View {

    private final static String TAG = "ProgressBar";
    private Paint mBackgroundPaint;

    public ProgressBar(Context context) {
        super(context);
        init(context);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(Color.RED);
        mBackgroundPaint.setStrokeWidth(DensityUtil.dp2px(1));
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.e(TAG, "onDraw width = "+getWidth()+", height = "+getHeight());

        RectF r1 = new RectF();
        r1.left = 50;
        r1.right = 250;
        r1.top = 50 ;
        r1.bottom = 150;
        canvas.drawRoundRect(r1, 10, 10, mBackgroundPaint);
    }
}
