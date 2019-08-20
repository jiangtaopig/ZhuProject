package com.example.za_zhujiangtao.zhupro.swipe_back.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.float_window.DisplayUtil;
import com.example.za_zhujiangtao.zhupro.float_window.RomUtils;
import com.example.za_zhujiangtao.zhupro.float_window.WindowUtil;
import com.example.za_zhujiangtao.zhupro.swipe_back.SwipeBackLayout;
import com.example.za_zhujiangtao.zhupro.swipe_back.ViewDragHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jake.share.frdialog.dialog.FRDialog;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/8/13
 */
public class TestSwipeBackActivity extends SwipeBackActivity {

    private final static String TAG = "TestSwipeBackActivity";
    private SwipeBackLayout mSwipeBackLayout;
    private Context mContext;

//    @BindView(R.id.my_img)
//    ImageView joinFloatView;

    @BindView(R.id.click_on)
    TextView myTextV;

    private int screenWidth;
    private int screenHeight;
    private int threshold;
    private boolean mJoinViewVisiable;
    private boolean mIsCollectViewVisiable;
    private boolean mIsInCollectView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_back_layout);
        ButterKnife.bind(this);

        mContext = this;
        screenWidth = DisplayUtil.getScreenWidth(this);
        screenHeight = DisplayUtil.getScreenHeight(this);
        threshold = screenWidth * 3 / 10;
        Log.e(TAG, "screenWidth : " + screenWidth + ", screenHeight : " + screenHeight + ", screenWidth : " + threshold);

        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.setOnScrollListener(new ViewDragHelper.OnScrollListener() {
            @Override
            public void onScroll(float x, float y) {
                mIsInCollectView = WindowUtil.getInstance().isRemove((int)x, (int)y);
            }

            @Override
            public void actionUp(float x, float y) {
                if (!mIsCollectViewVisiable){
                    Log.e("xxx", "x = "+x+", y = "+y+", mIsInCollectView = "+mIsInCollectView);
                    if (mIsInCollectView ){
                        mIsCollectViewVisiable = true;
                        WindowUtil.getInstance().showCollectView();
                    }
                }
            }
        });

        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {
                if (scrollPercent > 0.3){
                    if (!mJoinViewVisiable){
                        mJoinViewVisiable = true;
                        WindowUtil.getInstance().showPermissionWindow(mContext, () -> {
                            showDialog();
                        }, true);
                    }
                }else if (mJoinViewVisiable){
                    WindowUtil.getInstance().hideJoinView();
                }
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {

            }

            @Override
            public void onScrollOverThreshold() {

            }
        });
    }

    private void showDialog() {
        FRDialog dialog = new FRDialog.MDBuilder(this)
                .setTitle("悬浮窗权限")
                .setMessage("您的手机没有授予悬浮窗权限，请开启后再试")
                .setPositiveContentAndListener("现在去开启", view -> {
                    RomUtils.applyPermission(this, () -> {
                        new Handler().postDelayed(() -> {
                            if (!RomUtils.checkFloatWindowPermission(this)) {
                                // 授权失败
                                showDialog();
                            } else {
                                WindowUtil.getInstance().showPermissionWindow(mContext, null, false);
                            }
                        }, 500);
                    });
                    return true;
                }).setNegativeContentAndListener("暂不开启", view -> true).create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("xxx", "..................onDestroy...................");
    }
}
