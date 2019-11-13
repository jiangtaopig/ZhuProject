package com.zaaach.citypicker.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zaaach.citypicker.R;
import com.zaaach.citypicker.util.ScreenUtil;


/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/11/13
 */
public class TabLayout extends RelativeLayout{

    public static final int LEFT_TAB_TYPE = 0;
    public static final int RIGHT_TAB_TYPE = LEFT_TAB_TYPE + 1;

    private LinearLayout mTabLayout;
    private TextView mLeftTab;
    private TextView mRightTab;
    private View mTabLine;

    private int mLeftTabWidth; //mLeftTab 的长度
    private int mLeftTabTextWidth;//mLeftTab 中文本的长度
    private int mRightTabWidth;
    private int mRightTabTextWidth;

    private int mLeftTabLineStartX;// tabLine 开始的位置
    private int mTextDiffX; //mRightTab  中文本的开始的位置与 mLeftTab 中文本的结束位置 的差
    private OnTabSelectListener mTabSelectListener;
    private int mCurTab = LEFT_TAB_TYPE;

    private int mLeftTextAppearance;
    private String mLeftTxt;

    private int mRightTextAppearance;
    private String mRightTxt;


    public TabLayout(Context context) {
      this(context, null);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.tab_layout, this, true);
        if (attrs != null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabLayout);
            mLeftTxt = typedArray.getString(R.styleable.TabLayout_left_title);
            mLeftTextAppearance = typedArray.getResourceId(R.styleable.TabLayout_left_title_appearance, R.style.font_16sp_333333);
            mRightTxt = typedArray.getString(R.styleable.TabLayout_right_title);
            mRightTextAppearance = typedArray.getResourceId(R.styleable.TabLayout_right_title_appearance, R.style.font_16sp_999999);
        }else {
            mLeftTxt = "left tab";
            mLeftTextAppearance = R.style.font_16sp_333333;
            mRightTxt = "right tab";
            mRightTextAppearance = R.style.font_16sp_999999;
        }
    }

    public void setTabSelectListener(OnTabSelectListener listener) {
        this.mTabSelectListener = listener;
    }

    public void setLeftTabText(String text){
        mLeftTab.setText(text);
    }

    @SuppressLint("NewApi")
    public void setLeftTextAppearance(int appearance){
        this.mLeftTextAppearance = appearance;
        mLeftTab.setTextAppearance(mLeftTextAppearance);
    }

    public void setRightTxt(String text){
        mRightTab.setText(text);
    }

    @SuppressLint("NewApi")
    public void setRightTextAppearance(int appearance){
        this.mRightTextAppearance = appearance;
        mRightTab.setTextAppearance(mRightTextAppearance);
    }

    @SuppressLint("NewApi")
    private void init(){
        mTabLayout = findViewById(R.id.tab_layout);
        mLeftTab = findViewById(R.id.left_tab);
        mRightTab = findViewById(R.id.right_tab);
        mTabLine = findViewById(R.id.tab_line);

        mLeftTab.setText(mLeftTxt);
        mLeftTab.setTextAppearance(mLeftTextAppearance);
        mRightTab.setText(mRightTxt);
        mRightTab.setTextAppearance(mRightTextAppearance);


        mLeftTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTabSelectListener != null){
                    if (mCurTab == LEFT_TAB_TYPE){
                        return;
                    }
                    mCurTab = LEFT_TAB_TYPE;
                    mTabSelectListener.onTabSelect(LEFT_TAB_TYPE);
                    toRightAnim();
                    mLeftTab.setTextAppearance(mLeftTextAppearance);
                    mRightTab.setTextAppearance(mRightTextAppearance);
                }
            }
        });
        mRightTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTabSelectListener != null){
                    if (mCurTab == RIGHT_TAB_TYPE){
                        return;
                    }
                    mCurTab = RIGHT_TAB_TYPE;
                    mTabSelectListener.onTabSelect(RIGHT_TAB_TYPE);
                    toLeftAnim();
                    mLeftTab.setTextAppearance(mRightTextAppearance);
                    mRightTab.setTextAppearance(mLeftTextAppearance);
                }
            }
        });
        initTabView();
    }

    private void toLeftAnim() {
        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(mTabLine, "translationX", 0, mLeftTabTextWidth + mTextDiffX);
        ValueAnimator changeTabLengthAnimator = ValueAnimator.ofInt(mLeftTabTextWidth, mRightTabTextWidth);
        changeTabLengthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                LayoutParams params = (LayoutParams) mTabLine.getLayoutParams();
                params.width = value;
                mTabLine.setLayoutParams(params);
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateAnimator, changeTabLengthAnimator);
        animatorSet.setDuration(200);
        animatorSet.start();
    }

    private void toRightAnim() {
        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(mTabLine, "translationX", mLeftTabTextWidth + mTextDiffX, 0);
        ValueAnimator changeTabLengthAnimator = ValueAnimator.ofInt(mRightTabTextWidth, mLeftTabTextWidth);

        changeTabLengthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                LayoutParams params = (LayoutParams) mTabLine.getLayoutParams();
                params.width = value;
                mTabLine.setLayoutParams(params);
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateAnimator, changeTabLengthAnimator);
        animatorSet.setDuration(200);
        animatorSet.start();
    }

    private void initTabView() {
        mTabLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mTabLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                mLeftTabWidth = mLeftTab.getWidth();
                mLeftTabTextWidth = getTextWidth(mLeftTab) + ScreenUtil.dpToPx(getContext(), 8);
                mRightTabWidth = mRightTab.getWidth();
                mRightTabTextWidth = getTextWidth(mRightTab) + ScreenUtil.dpToPx(getContext(), 8);
                Log.e("xxxxx", "mLeftTabWidth = " + mLeftTabWidth + " , mLeftTabTextWidth = " + mLeftTabTextWidth + ", mRightTabWidth = " + mRightTabWidth + ", mRightTabTextWidth = " + mRightTabTextWidth);
                initTabLine();
                return true;
            }
        });
    }

    private void initTabLine() {
        LayoutParams params = (LayoutParams) mTabLine.getLayoutParams();
        params.width = mLeftTabTextWidth;
        mLeftTabLineStartX = (mLeftTabWidth - mLeftTabTextWidth) / 2;
        mTextDiffX = mLeftTabLineStartX + (mRightTabWidth - mRightTabTextWidth) / 2;
        params.leftMargin = mLeftTabLineStartX;
        mTabLine.setLayoutParams(params);
    }

    private int getTextWidth(TextView textView) {
        Paint paint = new Paint();
        paint.setTextSize(textView.getTextSize());
        float width = paint.measureText(textView.getText().toString());
        return (int) width;
    }

    public static interface OnTabSelectListener{
        void onTabSelect(int type);
    }
}
