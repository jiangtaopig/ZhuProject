package com.example.za_zhujiangtao.zhupro;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.float_window.DisplayUtil;

import butterknife.BindView;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/11/8
 */
public class TestTabSelectActivity extends BaseActivity {


    @BindView(R.id.tab_layout)
    LinearLayout mTabLayout;

    @BindView(R.id.domestic)
    TextView mDomesticTv;

    @BindView(R.id.international)
    TextView mInternationalTv;

    @BindView(R.id.tab_line)
    View mTabLine;

    private int mDomesticW; //mDomesticTv的长度
    private int mDomesticTextW;//mDomesticTv 中文本的长度
    private int mInternationalW;
    private int mInternationalTextW;

    private int mDomesticTabLineStartX;// tabLine 开始的位置
    private int mTextDiffX ; //mInternationalW  中文本的开始的位置与 mDomesticTv 中文本的结束位置 的差


    @Override
    protected int layoutId() {
        return R.layout.activity_tab_select_layout;
    }

    private int getTextWidth(TextView textView){
        Paint paint = new Paint();
        paint.setTextSize(textView.getTextSize());
        float width = paint.measureText(textView.getText().toString());
        return (int) width;
    }

    @Override
    protected void onInitLogic() {
        mTabLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mTabLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                mDomesticW = mDomesticTv.getWidth();
                mDomesticTextW = getTextWidth(mDomesticTv);
                mInternationalW = mInternationalTv.getWidth();
                mInternationalTextW = getTextWidth(mInternationalTv);
                Log.e("xxxxx", "mDomesticW = " + mDomesticW + " , mInternationalW = " + mInternationalW + ", mDomesticTextW = "+mDomesticTextW+", mInternationalTextW = "+mInternationalTextW);

                initTabLine();
                return true;
            }
        });

        mDomesticTv.setOnClickListener(v -> {
            ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(mTabLine, "translationX",  mDomesticTextW + mTextDiffX, 0);
            ValueAnimator changeTabLengthAnimator = ValueAnimator.ofInt(mInternationalTextW, mDomesticTextW);
            changeTabLengthAnimator.addUpdateListener(animation -> {
                int value = (int) animation.getAnimatedValue();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTabLine.getLayoutParams();
                params.width = value;
                mTabLine.setLayoutParams(params);

            });

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(translateAnimator, changeTabLengthAnimator);
            animatorSet.setDuration(200);
            animatorSet.start();


        });

        mInternationalTv.setOnClickListener(v -> {
            ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(mTabLine, "translationX", 0, mDomesticTextW + mTextDiffX);
            ValueAnimator changeTabLengthAnimator = ValueAnimator.ofInt(mDomesticTextW, mInternationalTextW);
            changeTabLengthAnimator.addUpdateListener(animation -> {
                int value = (int) animation.getAnimatedValue();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTabLine.getLayoutParams();
                params.width = value;
                mTabLine.setLayoutParams(params);

            });

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(translateAnimator, changeTabLengthAnimator);
            animatorSet.setDuration(200);
            animatorSet.start();

        });
    }

    private void initTabLine(){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTabLine.getLayoutParams();
        params.width = mDomesticTextW;
        mDomesticTabLineStartX = (mDomesticW - mDomesticTextW) / 2;
        mTextDiffX = mDomesticTabLineStartX + (mInternationalW - mInternationalTextW) / 2;
        params.leftMargin = mDomesticTabLineStartX;
        mTabLine.setLayoutParams(params);
    }
}