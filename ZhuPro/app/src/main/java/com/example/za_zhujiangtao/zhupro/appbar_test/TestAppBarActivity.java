package com.example.za_zhujiangtao.zhupro.appbar_test;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.R;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.net.SocketException;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by za-zhujiangtao on 2018/10/17.
 */

public class TestAppBarActivity extends Activity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.appbar_layout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.collapsing)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.img_header)
    ImageView mImgHeader;

    @BindView(R.id.img_layout)
    ImageView mImgLayout;

    @BindView(R.id.layout)
    ViewGroup mHeaderLayout;

    @BindView(R.id.title)
    TextView textView;

    private boolean mIsAppLayoutExpend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appbar_layout);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mIsAppLayoutExpend = false;


        mAppBarLayout.setExpanded(false);//设置默认状态是折叠的, true表示默认是展开的

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            private int lastOffset = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.e("TestAppBarActivity", "onOffsetChanged verticalOffset = " + verticalOffset
                        + ", mCollapsingToolbarLayout height = " + mCollapsingToolbarLayout.getHeight() + ", toolbar height = " + mToolbar.getHeight()
                        + ", appBarLayout height = " + appBarLayout.getHeight() + ", getTotalScrollRange = " + appBarLayout.getTotalScrollRange());//获取appbarlayout最大的滚动区间

                mIsAppLayoutExpend = (verticalOffset >= 0);

                verticalOffset = Math.abs(verticalOffset);
                if (lastOffset == verticalOffset) {
                    return;
                }
                lastOffset = verticalOffset;
                final int totalDifference = Math.abs(mCollapsingToolbarLayout.getHeight() - mToolbar.getHeight());
                float alphaPercent = totalDifference != 0 ? (float) verticalOffset / (float) totalDifference : 0;
                if (alphaPercent > .95f) {
                    alphaPercent = 1f;
                } else if (alphaPercent < .05f) {
                    alphaPercent = 0f;
                }
                mToolbar.setVisibility(View.VISIBLE);
                mToolbar.setAlpha(alphaPercent);
                mImgLayout.setAlpha(alphaPercent);
                mHeaderLayout.setVisibility(View.VISIBLE);
                if (alphaPercent == 1f) {
                    mHeaderLayout.setVisibility(View.GONE);
                } else if (alphaPercent == 0f) {
                    mToolbar.setVisibility(View.GONE);
                }
            }
        });
    }
}

