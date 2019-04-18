package com.example.za_zhujiangtao.zhupro;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.transitionseverywhere.TransitionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransitionActivity extends AppCompatActivity {


    @BindView(R.id.group_top)
    LinearLayout mGroupTop;

    @BindView(R.id.image_view)
    ImageView mImageView;

    private boolean mIsImageVisible = true;

    public static void enter(Context context){

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //过渡动画的设置
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_transition_layout);
        ButterKnife.bind(this);

        //使用过渡动画Transition来作为Activity的出、入场动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide().setDuration(2000));
            getWindow().setExitTransition(new Slide().setDuration(2000));
        }
    }

    @OnClick(R.id.click_btn)
    protected void testTransition(){
        TransitionManager.beginDelayedTransition(mGroupTop);
        if (mIsImageVisible){
            mImageView.setVisibility(View.GONE);
        }else {
            mImageView.setVisibility(View.VISIBLE);
        }
        mIsImageVisible = !mIsImageVisible;
    }
}
