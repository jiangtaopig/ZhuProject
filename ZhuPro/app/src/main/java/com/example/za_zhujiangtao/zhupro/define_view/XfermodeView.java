package com.example.za_zhujiangtao.zhupro.define_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/4/10
 */
public class XfermodeView extends View {
    private int width = 400;
    private int height = 400;
    private Bitmap dstBm;
    private Bitmap srcBm;
    private Paint mPaint;
    public XfermodeView(Context context) {
        this(context, null);
    }

    public XfermodeView(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeView(Context context, @Nullable @android.support.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        dstBm = makeDst(width, height);
        srcBm = makeSrc(width, height);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * 创建目标bitmap图像
     * @param w
     * @param h
     */
    private Bitmap makeDst(int w, int h){
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

    /**
     * 创建源图像
     * @param w
     * @param h
     * @return
     */
    private Bitmap makeSrc(int w, int h){
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.RED);
        c.drawRect(0, 0,w,h, p);
        return bm;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int layerId = canvas.saveLayer(0, 0, width * 2, height * 2, mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(dstBm, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBm, width/2, height/2, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);

    }
}
