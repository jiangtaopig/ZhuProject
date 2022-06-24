package com.example.za_zhujiangtao.zhupro.float_window;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.za_zhujiangtao.zhupro.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.WINDOW_SERVICE;


/**
 * Created by manji
 * Date：2018/9/29 下午4:29
 * Desc：
 */
public class WindowUtil {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mCollectViewParams;
    private View mCollectView;
    private View mNormalView;
    private WindowManager.LayoutParams mNormalViewParams;

    private int statusBarHeight = 0;
    private int mScreenWidth;
    private int mScreenHeight;
    private Context mContext;

    private WindowUtil() {

    }

    private static class SingletonInstance {
        @SuppressLint("StaticFieldLeak")
        private static final WindowUtil INSTANCE = new WindowUtil();
    }

    public static WindowUtil getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public void showPermissionWindow(Context context, OnPermissionListener onPermissionListener, boolean showJoinView) {
        if (RomUtils.checkFloatWindowPermission(context)) {
            showWindow(context);
        } else {
            onPermissionListener.showPermissionDialog();
        }
    }

    @SuppressLint("CheckResult")
    private void showWindow(Context context) {
        Log.e("showWindow", "mWindowManager " + mWindowManager);
        if (null == mWindowManager) {
            mContext = context;
            mWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);

            mCollectViewParams = new WindowManager.LayoutParams();
            mNormalViewParams = new WindowManager.LayoutParams();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mCollectViewParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                mNormalViewParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                mCollectViewParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                mNormalViewParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }
            mScreenWidth = DisplayUtil.getScreenWidth(context);
            mScreenHeight = DisplayUtil.getScreenHeight(context);
            initView();
        }
    }

    @SuppressLint("CheckResult")
    private void initView() {
        mCollectView = LayoutInflater.from(mContext).inflate(R.layout.article_window, null);
        initListener(mContext);

        mCollectViewParams.format = PixelFormat.RGBA_8888;   //窗口透明
        mCollectViewParams.gravity = Gravity.START | Gravity.TOP;  //窗口位置
        mCollectViewParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mCollectViewParams.width = WindowManager.LayoutParams.WRAP_CONTENT;//DisplayUtil.dip2px(mCollectView.getWidth());
        mCollectViewParams.height = WindowManager.LayoutParams.WRAP_CONTENT;//DisplayUtil.dip2px(mCollectView.getHeight());

        // 可以修改View的初始位置---这里 y 是靠在屏幕居中
        mCollectViewParams.x = mScreenWidth - mCollectView.getWidth();
        mCollectViewParams.y = (mScreenHeight - mCollectView.getHeight()) / 2;

        Log.e("mCollectView", "mScreenWidth = " + mScreenWidth + ", mScreenHeight = " + mScreenHeight
                +", mCollectViewParams.x = " +mCollectViewParams.x + ", mCollectViewParams.y = " + mCollectViewParams.y
                + "mCollectView.getWidth() = " + mCollectView.getWidth() +", mCollectView.getHeight() = " + mCollectView.getHeight());

        // 关键点，一定要把view加到 WindowManager.addView 把 view 加到 window 中
        mWindowManager.addView(mCollectView, mCollectViewParams);
        mCollectView.setVisibility(View.VISIBLE);


        mNormalView = LayoutInflater.from(mContext).inflate(R.layout.test_window_layout, null);

        ImageView ivImage = mNormalView.findViewById(R.id.aw_iv_image);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ivImage.getLayoutParams();
        lp.width = DisplayUtil.dip2px(100);
        lp.height = DisplayUtil.dip2px(100);
        ivImage.setLayoutParams(lp);
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        requestOptions
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(mContext).load("https://tenfei05.cfp.cn/creative/vcg/veer/1600water/veer-104673459.jpg")
                .apply(requestOptions)
                .into(ivImage);

        ivImage.setOnClickListener(v -> {
            Toast.makeText(mContext, "normalview imageview click", Toast.LENGTH_LONG).show();
        });

        mNormalViewParams.format = PixelFormat.RGBA_8888;   //窗口透明
        mNormalViewParams.gravity = Gravity.START | Gravity.TOP;  //窗口位置
        mNormalViewParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mNormalViewParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mNormalViewParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        // 可以修改View的初始位置---这里 y 是靠在屏幕居中
        mNormalViewParams.x = 0;
        mNormalViewParams.y = (mScreenHeight - mNormalViewParams.height) / 2;

        // 关键点，一定要把view加到 WindowManager.addView 把 view 加到 window 中
//        mWindowManager.addView(mNormalView, mNormalViewParams);
//        mNormalView.setVisibility(View.VISIBLE);

        mNormalView.setOnClickListener(v -> {
            Toast.makeText(mContext, "normal view", Toast.LENGTH_LONG).show();
        });
    }

    public void showCollectView() {
        Log.e("showCollectView", "mCollectView = " + mCollectView);
        if (mCollectView != null) {
            mCollectView.setVisibility(View.VISIBLE);
        }
    }

    public void dismissWindow() {
        Log.e("dismissWindow", "mWindowManager = " + mWindowManager + ", mCollectView = " + mCollectView);
        if (mWindowManager != null && mCollectView != null) {
            mWindowManager.removeViewImmediate(mCollectView);
            mWindowManager = null;
            mCollectView = null;
        }
    }

    private void initListener(final Context context) {
        mCollectView.setOnClickListener(v -> {
            Toast.makeText(mContext, "click!", Toast.LENGTH_SHORT).show();
        });


        final int mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        //设置触摸滑动事件
        mCollectView.setOnTouchListener(new View.OnTouchListener() {
            int startX, startY;  //起始点
            boolean isPerformClick;  //是否点击
            int finalMoveX;  //最后通过动画将mView的X轴坐标移动到finalMoveX

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        isPerformClick = true;
                        int w = mCollectView.getWidth();
                        int h = mCollectView.getHeight();
                        Log.e("mCollectView", "ACTION_DOWN : x = " + mCollectViewParams.x + ", y = " + mCollectViewParams.y+", w = "+w+", h = "+h);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //判断是CLICK还是MOVE
                        //只要移动过，就认为不是点击
                        if (Math.abs(startX - event.getX()) >= mTouchSlop || Math.abs(startY - event.getY()) >= mTouchSlop) {
                            isPerformClick = false;
                        }
                        if (isPortrait()) {
                            mCollectViewParams.x = (int) (event.getRawX() - startX);
                            //这里修复了刚开始移动的时候，悬浮窗的y坐标是不正确的，要减去状态栏的高度，可以将这个去掉运行体验一下
                            mCollectViewParams.y = (int) (event.getRawY() - startY - statusBarHeight);
                        } else {
                            mCollectViewParams.x = (int) (event.getRawX() - startX - statusBarHeight);
                            mCollectViewParams.y = (int) (event.getRawY() - startY);
                        }
                        updateCollectViewLayout();
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (isPerformClick) {
                            mCollectView.performClick();
                        }
                        //判断mView是在Window中的位置，以中间为界
                        if (mCollectViewParams.x + mCollectView.getMeasuredWidth() / 2 >= mScreenWidth / 2) {
                            finalMoveX = mScreenWidth - mCollectView.getMeasuredWidth();
                        } else {
                            finalMoveX = 0;
                        }
//                        stickToSide(); // 吸边的效果
                        return !isPerformClick;
                }
                return false;
            }

            private void stickToSide() {
                ValueAnimator animator = ValueAnimator.ofInt(mCollectViewParams.x, finalMoveX).setDuration(Math.abs(mCollectViewParams.x - finalMoveX));
                animator.setInterpolator(new BounceInterpolator());
                animator.addUpdateListener(animation -> {
                    mCollectViewParams.x = (int) animation.getAnimatedValue();
                    updateCollectViewLayout();
                });
                animator.start();
            }
        });
    }

    private boolean isPortrait () {
        Configuration configuration = mContext.getResources().getConfiguration();
        int orientation = configuration.orientation;
        return Configuration.ORIENTATION_PORTRAIT == orientation;
    }

    private void updateCollectViewLayout() {
        if (null != mCollectView && null != mCollectViewParams) {
            mWindowManager.updateViewLayout(mCollectView, mCollectViewParams);
        }
    }

    public interface OnPermissionListener {
        void showPermissionDialog();
    }

}