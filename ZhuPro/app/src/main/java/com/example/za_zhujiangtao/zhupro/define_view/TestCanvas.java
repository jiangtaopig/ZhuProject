package com.example.za_zhujiangtao.zhupro.define_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.float_window.DisplayUtil;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/4/3
 */
public class TestCanvas extends View {
    private Paint mPaint;

    public TestCanvas(Context context) {
        this(context, null);
    }

    public TestCanvas(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestCanvas(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(context.getResources().getColor(R.color.color_46d286));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);

        int high = MeasureSpec.getSize(heightMeasureSpec);
        int highSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int resultW = width, resultH = high;
        if (widthSpecMode == MeasureSpec.AT_MOST) {
            resultW = DisplayUtil.dip2px(300);
        }

        if (highSpecMode == MeasureSpec.AT_MOST) {
            resultH = DisplayUtil.dip2px(300);
        }

        setMeasuredDimension(resultW, resultH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // STROKE 表示描边 Paint.Style.FILL 表示填充
//        mPaint.setStyle(Paint.Style.STROKE);

        int width = getWidth();
        int height = getHeight();

        RectF rect = new RectF(0, 0, width / 2, height / 2);
        canvas.drawRect(rect, mPaint);

        mPaint.setColor(Color.WHITE);
        //startAngle 表示绘制的起始角度， sweepAngle 表示 要扫过的角度， 正数 是按顺时针， 负数是逆时针
        canvas.drawArc(rect, 90, -45, true, mPaint);

        //-----------------------------------Canvas画布的操作------------------------------------------
//        testTranslate(canvas);
//        testScale(canvas);
//        testRotate(canvas, width, height);

        canvas.clipRect(0, 0, 200, 200);
        canvas.drawColor(Color.BLUE);

    }

    private void testRotate(Canvas canvas, int width, int height) {
        //rotate: 默认以坐标原点进行旋转
        canvas.translate(width / 2, height/ 2);
        RectF rectF = new RectF(0, - width/2, width/2, 0);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(rectF, mPaint);

        canvas.rotate(180);
        mPaint.setColor(Color.RED);
        canvas.drawRect(rectF, mPaint);
    }

    private void testScale(Canvas canvas) {
        RectF rectF = new RectF(0, -DisplayUtil.dip2px(150), DisplayUtil.dip2px(150), 0);
        canvas.drawRect(rectF, mPaint);
        canvas.scale(0.5f, 0.5f, 100, 0); //以画布中心向右移动100个像素作为缩放中心进行缩放，x 和 y 各缩放一半;
        mPaint.setColor(Color.RED);
        canvas.drawRect(rectF, mPaint);
    }

    private void testTranslate(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(0, 0, DisplayUtil.dip2px(50), mPaint);
        canvas.translate(DisplayUtil.dip2px(150), DisplayUtil.dip2px(150)); //画布向下、向右150dp 然后在移动后的画布的(0, 0)画一个圆
        canvas.drawCircle(0, 0, DisplayUtil.dip2px(50), mPaint);
    }
}
