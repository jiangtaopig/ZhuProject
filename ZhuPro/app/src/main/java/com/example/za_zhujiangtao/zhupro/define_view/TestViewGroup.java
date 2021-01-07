package com.example.za_zhujiangtao.zhupro.define_view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.example.za_zhujiangtao.zhupro.float_window.DisplayUtil;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/16
 */
public class TestViewGroup extends ViewGroup {
    private static final String TAG = "TestViewGroup";
    private int mScreenWidth;
    /**
     * resultW 代表最终设置的宽，resultH 代表最终设置的高
     */
    private int resultW;
    private int resultH;

    public TestViewGroup(Context context) {
        this(context, null);
    }

    public TestViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = DisplayUtil.getScreenWidth(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int topStart = getPaddingTop();
        int leftStart = getPaddingLeft();
        int childW = 0;
        int childH = 0;
        MarginLayoutParams layoutParams = null;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            //子元素不可见时，不参与布局，因此不需要将其尺寸计算在内
            if (child.getVisibility() == View.GONE) {
                continue;
            }

            layoutParams = (MarginLayoutParams) child.getLayoutParams();

            childW = child.getMeasuredWidth();
            childH = child.getMeasuredHeight();

            //在 onLayout 中，child.getWidth() 的 大小等于0 ， 所以要使用 getMeasuredWidth;
            // getMeasureWidth()方法在measure()过程结束后就可以获取到了，而getWidth()方法要在layout()过程结束后才能获取到。
            Log.e(TAG, "onLayout width = " + child.getWidth() + ",childW = " + childW);

            leftStart += layoutParams.leftMargin;
            topStart += layoutParams.topMargin;

            child.layout(leftStart, topStart, leftStart + childW, topStart + childH);

            leftStart += childW + layoutParams.rightMargin;
            topStart += childH + layoutParams.bottomMargin;
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return super.checkLayoutParams(p) && p instanceof LayoutParams;
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        Log.e(TAG, "onMeasure widthSize = " + widthSize + ", heightSize = " + heightSize + ", mScreenWidth = " + mScreenWidth);
        resultW = widthSize;
        resultH = heightSize;
        /**计算尺寸的时候要将自身的 padding 考虑进去*/
        int contentW = getPaddingLeft() + getPaddingRight();
        int contentH = getPaddingTop() + getPaddingBottom();

        /**对子元素进行尺寸的测量，这一步必不可少*/
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        MarginLayoutParams layoutParams;

        // 合并所以子view的大小得到ViewGroup的大小
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            layoutParams = (MarginLayoutParams) child.getLayoutParams();
            //子元素不可见时，不参与布局，因此不需要将其尺寸计算在内
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            //要加上 margin，为什么不用加上 padding 呢？因为子View 在计算宽高的时候已经加上了padding值
            contentW += child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            contentH += child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
        }

        /**重点处理 AT_MOST 模式，TestViewGroup 通过子元素的尺寸自主决定数值大小，但不能超过
         *  ViewGroup 给出的建议数值
         * */
        if (widthMode == MeasureSpec.AT_MOST) {
            resultW = contentW < widthSize ? contentW : widthSize;
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            resultH = contentH < heightSize ? contentH : heightSize;
        }

        Log.e(TAG, "resultW = " + resultW + ", resultH = " + resultH);

        //一定要设置这个函数，不然会报错
        setMeasuredDimension(resultW, resultH);
    }


    /**
     * 不这样写会报 cast MarginLayoutParams 出错
     */
    public static class LayoutParams extends MarginLayoutParams {

        public int gravity = -1;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            gravity = Gravity.TOP;
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height);
            this.gravity = gravity;
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

}
