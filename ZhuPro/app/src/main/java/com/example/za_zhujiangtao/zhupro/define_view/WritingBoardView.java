package com.example.za_zhujiangtao.zhupro.define_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/4/9
 */
public class WritingBoardView extends View {
    private Paint mPaint;
    private Path mPath;
    private float mPreX;
    private float mPreY;

    public WritingBoardView(Context context) {
        this(context, null);
    }

    public WritingBoardView(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WritingBoardView(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);

        mPath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPreX = event.getX();
                mPreY = event.getY();
                mPath.moveTo(mPreX, mPreY);
                return true;//return true 之后的 move 和 up 才能执行

            case MotionEvent.ACTION_MOVE:
                //画的线不平滑
//                mPath.lineTo(event.getX(), event.getY());
                //二阶贝塞尔曲线 更顺滑
                float endX = (mPreX + event.getX())/2;
                float endY = (mPreY + event.getY())/2;
                mPath.quadTo(mPreX, mPreY, endX, endY);
                mPreX = event.getX();
                mPreY = event.getY();
                postInvalidate();
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#DAE0E6"));
        mPaint.setColor(Color.RED);
        canvas.drawPath(mPath, mPaint);
    }

    public void reset(){
        mPath.reset();
        invalidate();
    }
}
