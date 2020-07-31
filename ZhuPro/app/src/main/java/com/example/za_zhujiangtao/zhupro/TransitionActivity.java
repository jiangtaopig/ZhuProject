package com.example.za_zhujiangtao.zhupro;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.transitionseverywhere.TransitionManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class TransitionActivity extends AppCompatActivity {


    @BindView(R.id.group_top)
    LinearLayout mGroupTop;

    @BindView(R.id.image_view)
    ImageView mImageView;

    @BindView(R.id.root2)
    LinearLayout mRoot2;

    @BindView(R.id.tv1)
    TextView mTv1;

    @BindView(R.id.tv2)
    TextView mTv2;

    @BindView(R.id.txt_trans)
    TextView mTransitionTv;

    @BindView(R.id.txt_ok)
    TextView mStartTrans;

    @BindView(R.id.root_layout)
    ViewGroup mRootView;

    @BindView(R.id.lll_layout)
    RelativeLayout mTvLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //过渡动画的设置
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_transition_layout);
        ButterKnife.bind(this);

        //使用过渡动画Transition来作为Activity的出、入场动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide().setDuration(500));
            getWindow().setExitTransition(new Slide().setDuration(500));
        }

        mStartTrans.setOnClickListener(v -> {
            com.transitionseverywhere.Slide slide = new com.transitionseverywhere.Slide(Gravity.BOTTOM);
            TransitionManager.beginDelayedTransition(mRootView, slide);
            if (mTvLayout.getVisibility() == View.VISIBLE){
                mTvLayout.setVisibility(View.GONE);
            }else {
                mTvLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick(R.id.click_btn)
    protected void testTransition() {
        TransitionManager.beginDelayedTransition(mGroupTop);
        if (mImageView.getVisibility() == View.VISIBLE) {
            mImageView.setVisibility(View.GONE);
        } else {
            mImageView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.tv1)
    protected void horizontalTransition(){
        com.transitionseverywhere.Slide slide = new com.transitionseverywhere.Slide(Gravity.RIGHT);
        TransitionManager.beginDelayedTransition(mRoot2, slide);
        mTv1.setVisibility(View.GONE);
        mTv2.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv2)
    protected void horizontalTransition2(){
        com.transitionseverywhere.Slide slide = new com.transitionseverywhere.Slide(Gravity.RIGHT);
        TransitionManager.beginDelayedTransition(mRoot2, slide);
        mTv1.setVisibility(View.VISIBLE);
        mTv2.setVisibility(View.GONE);
    }
}


