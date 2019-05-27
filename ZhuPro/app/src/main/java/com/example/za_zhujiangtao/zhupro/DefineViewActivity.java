package com.example.za_zhujiangtao.zhupro;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;

public class DefineViewActivity extends BaseActivity {

    private final static String TAG = "DefineViewActivity";
    @BindView(R.id.animation_tv)
    TextView mTv;

    @Override
    protected int layoutId() {
        return R.layout.activity_define_view_layout;
    }

    @Override
    protected void onInitLogic() {
        mTv.setOnClickListener(v -> {
            performAnimation(mTv, mTv.getWidth(), 600);
        });
    }

    private void performAnimation(View targetView, int start, int end){
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        IntEvaluator intEvaluator = new IntEvaluator();
        animator.addUpdateListener(animation -> {
            int currentValue = (int) animation.getAnimatedValue();
            float fraction = animation.getAnimatedFraction();
            Log.e(TAG, "currentValue = "+currentValue+", fraction = "+fraction);
            int value = intEvaluator.evaluate(fraction, start, end);
            targetView.getLayoutParams().width = value;
            targetView.requestLayout();
        });
        animator.setDuration(3000);
        animator.start();
    }
}
