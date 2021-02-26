package com.example.za_zhujiangtao.zhupro.define_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.za_zhujiangtao.zhupro.R;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/16
 */
public class TestView extends View {

    private int mTextSize;
    private String mText;

    private Paint mPaint;

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TestView);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.TestView_android_textSize, 22);
        mText = typedArray.getString(R.styleable.TestView_android_text);

        typedArray.recycle(); //一定要回收掉

        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.color_46d286));
        mPaint.setTextSize(mTextSize);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int cx = getPaddingLeft();
        int cy = getHeight() - getPaddingBottom();

        canvas.drawColor(Color.RED);
        if (TextUtils.isEmpty(mText)) {
            return;
        }
        canvas.drawText(mText, cx, cy, mPaint);  //cy 指的是绘制字符串的 baseline 线

        Log.e("xxx", "mText = "+mText);

        mPaint.setColor(getResources().getColor(R.color.color_333333));
        canvas.drawLine(getPaddingLeft(), (float) getHeight() / 2, (float) getWidth() - getPaddingRight(), (float) getHeight() / 2, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int maxWidth, maxHeight;
        int resultW = width, resultH = height;


        if (widthMode == MeasureSpec.AT_MOST) {
            maxWidth = (int) (getPaddingLeft() + getPaddingRight() + mPaint.measureText(mText));
            resultW = Math.min(maxWidth, width);
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            maxHeight = getPaddingBottom() + getPaddingTop() + mTextSize;
            resultH = Math.min(maxHeight, height);
        }

        setMeasuredDimension(resultW, resultH);
    }
}
