package com.example.za_zhujiangtao.zhupro.define_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.za_zhujiangtao.zhupro.R;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/3/13
 */
public class TestView2 extends View {

    private String mText;
    private int mTextSize;
    private Paint mPaint;


    public TestView2(Context context) {
        this(context, null);
    }

    public TestView2(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView2(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TestView2);
        mText = typedArray.getString(R.styleable.TestView2_android_text);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.TestView2_android_textSize, 24);
        typedArray.recycle();//一定记得回收

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(mTextSize);
//        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int cx = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2;
        int cy = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2;
        canvas.drawColor(Color.RED);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(6);
        canvas.drawLine(cx, cy, getWidth(), cy, mPaint);
        if (TextUtils.isEmpty(mText)) {
            return;
        }
        mPaint.setColor(Color.BLACK);
        canvas.drawText(mText, 0, cy, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //wrap_content 对应的测量模式为 AT_MOST，表示 view 希望自己决定宽高，但是最大不能超过父布局的剩余宽高
        //这种情况下 需要程序员自己处理

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        Log.e("TestView2", "onMeasure  widthMode = " + widthMode + ", heightMode = " + heightMode);

        //最后的宽和高
        int resultW = width;
        int resultH = height;

        int contentW = 0;
        int contentH = 0;

        if (widthMode == MeasureSpec.AT_MOST && !TextUtils.isEmpty(mText)) {
            contentW += mPaint.measureText(mText) + getPaddingLeft() + getPaddingRight();
            resultW = contentW < resultW ? contentW : resultH;
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            contentH += mTextSize + getPaddingTop() + getPaddingBottom();
            resultH = contentH < resultH ? contentH : resultH;
        }

        //这里一定要设置
        setMeasuredDimension(resultW, resultH);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
