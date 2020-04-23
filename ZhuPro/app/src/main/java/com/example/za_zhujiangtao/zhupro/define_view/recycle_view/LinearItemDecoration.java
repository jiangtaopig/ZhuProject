package com.example.za_zhujiangtao.zhupro.define_view.recycle_view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/4/20
 *
 *
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = LinearItemDecoration.class.getSimpleName();
    private Rect mRect;
    private Paint mPaint;

    public LinearItemDecoration(int left, int top, int right, int bottom) {
        mRect = new Rect(left, top, right, bottom);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#ff0000"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
    }

    /**
     * 是onDraw和onDrawOver所整个ItemDecoration只执行一次的，并不是针对Item的，
     * 所以我们需要在onDraw和onDrawOver中绘图时，一次性将所有Item的ItemDecoration绘制完成。
     * @param c 是指通过getItemOffsets撑开的空白区域所对应的画布，通过这个canvas对象，可以在getItemOffsets所撑出来的区域任意绘图。
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View view = parent.getChildAt(i);
//            int top = view.getTop() - mRect.top; //因为设置了top间距
//            c.drawRect(0, top, 400, top + 15, mPaint);
//        }
    }

    /**
     * ItemDecoration与Item的绘制顺序为：decoration 的 onDraw->item的 onDraw->decoration 的 onDrawOver，这三者是依次发生的。
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    /**
     * getItemOffsets是针对每个Item都会走一次，也就是说每个Item的outRect都可以不同
     *
     * @param outRect 中的 top、left、right、bottom四个点，并不是普通意义的坐标点，而是指的在Item上、左、右、下各撑开的距离，这个值默认是0
     * @param view    指的就是ItemView
     * @param parent  指 RecyclerView
     * @param state   指 RecyclerView 的各种状态
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mRect);
        Log.e(TAG, "leftMargin = " + mRect.left + ", topMargin = " + mRect.top + ", rightMargin = " + mRect.right + ", bottomMargin = " + mRect.bottom);
    }
}
