package com.example.za_zhujiangtao.zhupro.define_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.float_window.DisplayUtil;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/8/28
 */
public class XfermodeView2 extends View {
    private Bitmap mSrcBm;
    private Bitmap mDestBm;
    private Bitmap mSrcBm2;
    private Bitmap mDestBm2;

    private final static int SIZE = DisplayUtil.dip2px(100);
    private Paint mPaint;

    public XfermodeView2(Context context) {
        this(context, null);
    }

    public XfermodeView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSrcBm = makeSrc(SIZE, SIZE);
        mDestBm = makeDst(SIZE, SIZE);

        mSrcBm2 = makeSrc2(SIZE, SIZE);
        mDestBm2 = makeDst2(SIZE, SIZE);

    }

    /**
     * 创建目标bitmap图像
     *
     * @param w
     * @param h
     */
    private Bitmap makeDst(int w, int h) {
        //首先，新建一个空白的bitmap，这个空白bitmap上每个像素的值都是0x00000000；即全透明
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //然后在bitmap上绘图
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        //在bitmap上画一个圆
        canvas.drawOval(new RectF(0, 0, w, h), paint);
        return bitmap;
    }

    private Bitmap makeDst2(int w, int h) {
        Bitmap a1 = BitmapFactory.decodeResource(getResources(), R.drawable.a1);
        int bmW = a1.getWidth();
        int bmH = a1.getHeight();
        if (bmW < w || bmH < h){
            w = bmW;
            h = bmH;
        }
        Bitmap bitmap = Bitmap.createBitmap(a1, 0, 0, w, h);
        return bitmap;
    }

    private Bitmap makeSrc2(int w, int h) {
        Bitmap a1 = BitmapFactory.decodeResource(getResources(), R.drawable.a2);
        Bitmap bitmap = Bitmap.createBitmap(a1, 0, 0, w, h);
        return bitmap;
    }

    /**
     * 创建源图像
     *
     * @param w
     * @param h
     * @return
     */
    private Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.RED);
        c.drawRect(0, 0, w, h, p);
        return bm;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int layerId = canvas.saveLayer(0, 0, SIZE * 2, SIZE * 2, mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mDestBm2, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT)); // 相交的显示空白像素
//        canvas.drawBitmap(mSrcBm2, SIZE / 2, SIZE / 2, mPaint);
        canvas.drawCircle(SIZE , SIZE, SIZE / 2, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int lastW = width;
        int lastH = height;
        if (widMode == MeasureSpec.AT_MOST) {
            lastW = SIZE;
        }

        if (hMode == MeasureSpec.AT_MOST) {
            lastH = SIZE;
        }
        setMeasuredDimension(lastW, lastH);
    }

}
