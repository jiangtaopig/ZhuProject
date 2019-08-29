package com.example.za_zhujiangtao.zhupro.float_window;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.RelativeLayout;

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
    private WindowManager.LayoutParams mCancelViewLayoutParams;
    private View mCollectView;

    private Rect mDeleteRect = new Rect();
    private static final int mViewWidth = 50;
    private int statusBarHeight = 0;
    private CustomCancelView mCustomCancelView;
    private static final int mCancelViewSize = 100;
    private int mScreenWidth;
    private int mScreenHeight;
    private Context mContext;
    private List<String> mData = new ArrayList<>();

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
            showWindow(context, showJoinView);
        } else {
            onPermissionListener.showPermissionDialog();
        }
    }

    @SuppressLint("CheckResult")
    private void showWindow(Context context, boolean showJoinView) {
        Log.e("showWindow", "mWindowManager "+mWindowManager);
        if (null == mWindowManager) {
            mContext = context;
            mWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);

            mCollectViewParams = new WindowManager.LayoutParams();
            mCancelViewLayoutParams = new WindowManager.LayoutParams();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mCollectViewParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                mCancelViewLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                mCollectViewParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                mCancelViewLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }

            mScreenWidth = DisplayUtil.getScreenWidth(context);
            mScreenHeight = DisplayUtil.getScreenHeight(context);

            mCustomCancelView = (CustomCancelView) LayoutInflater.from(mContext).inflate(R.layout.activity_test, null);
            mCancelViewLayoutParams.format = PixelFormat.RGBA_8888;   //窗口透明
            mCancelViewLayoutParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;  //窗口位置
            mCancelViewLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            mCancelViewLayoutParams.width = DisplayUtil.dip2px(mCancelViewSize);
            mCancelViewLayoutParams.height = DisplayUtil.dip2px(mCancelViewSize);
            mWindowManager.addView(mCustomCancelView, mCancelViewLayoutParams);

            if (mDeleteRect.isEmpty()) {
                mDeleteRect.set(mScreenWidth - mCancelViewLayoutParams.width, mScreenHeight - mCancelViewLayoutParams.height, mScreenWidth, mScreenHeight);
            }
            Log.d("xxx", "top = " + mDeleteRect.top + ", left = " + mDeleteRect.left + ", right = " + mDeleteRect.right + ", bottom = " + mDeleteRect.bottom);
            showJoinView();
            initCollectView();
        } else {
            if (showJoinView) {
                showJoinView();
            }
        }
    }

    public void showJoinView() {
        Log.e("zzzzz", "showJoinView "+mCustomCancelView);
        if (mCustomCancelView != null) {
            mCustomCancelView.startAnimate(true);
        }
    }

    public void hideJoinView(){
        Log.e("zzzzz", "hideJoinView "+mCustomCancelView);
        if (mCustomCancelView != null) {
            mCustomCancelView.startAnimate(false);
        }
    }

    private void initCollectView(){
        mCollectView = LayoutInflater.from(mContext).inflate(R.layout.article_window, null);
        initListener(mContext);
        ImageView ivImage = mCollectView.findViewById(R.id.aw_iv_image);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ivImage.getLayoutParams();
        lp.width = DisplayUtil.dip2px(mViewWidth - 5);
        lp.height = DisplayUtil.dip2px(mViewWidth - 5);
        ivImage.setLayoutParams(lp);
//            String imageUrl = SPUtil.getStringDefault(WebViewActivity.ARTICLE_IMAGE_URL, "");
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        requestOptions.placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);
        Glide.with(mContext).load("").apply(requestOptions).into(ivImage);

        mCollectViewParams.format = PixelFormat.RGBA_8888;   //窗口透明
        mCollectViewParams.gravity = Gravity.LEFT | Gravity.TOP;  //窗口位置
        mCollectViewParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mCollectViewParams.width = DisplayUtil.dip2px(mViewWidth);
        mCollectViewParams.height = DisplayUtil.dip2px(mViewWidth);

        // 可以修改View的初始位置---这里是靠在屏幕居中
        mCollectViewParams.x = mScreenWidth - mCollectViewParams.width;
        mCollectViewParams.y = (mScreenHeight - mCollectViewParams.height) / 2;
        mWindowManager.addView(mCollectView, mCollectViewParams);
        mCollectView.setVisibility(View.INVISIBLE);
    }

    public void showCollectView(){
        Log.e("showCollectView", "mCollectView = "+mCollectView);
        if (mCollectView != null){
            mCollectView.setVisibility(View.VISIBLE);
            mCustomCancelView.startAnimate(false);
            mCustomCancelView.isInSide(false);
        }
    }

    public void dismissWindow() {
        Log.e("dismissWindow", "mWindowManager = "+mWindowManager+", mCollectView = "+mCollectView);
        if (mWindowManager != null && mCollectView != null) {
            mWindowManager.removeViewImmediate(mCollectView);
            if (mCustomCancelView != null){
                mWindowManager.removeViewImmediate(mCustomCancelView);
                Log.e("dismissWindow", "..................");
                mWindowManager = null;
                mCustomCancelView = null;
                mCollectView = null;
            }
        }
    }

    private void initListener(final Context context) {
        mCollectView.setOnClickListener(v -> {
//            String jumpUrl = SPUtil.getStringDefault(WebViewActivity.ARTICLE_JUMP_URL, "");
//            if (!jumpUrl.isEmpty()) {
//                WebViewActivity.start(context, jumpUrl);
//            }
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
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //判断是CLICK还是MOVE
                        //只要移动过，就认为不是点击
                        if (Math.abs(startX - event.getX()) >= mTouchSlop || Math.abs(startY - event.getY()) >= mTouchSlop) {
                            isPerformClick = false;
                        }
                        mCollectViewParams.x = (int) (event.getRawX() - startX);
                        //这里修复了刚开始移动的时候，悬浮窗的y坐标是不正确的，要减去状态栏的高度，可以将这个去掉运行体验一下
                        mCollectViewParams.y = (int) (event.getRawY() - startY - statusBarHeight);
                        updateViewLayout();   //更新mView 的位置
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (isPerformClick) {
                            mCollectView.performClick();
                        }
                        //判断mView是在Window中的位置，以中间为界
                        if (mCollectViewParams.x + mCollectView.getMeasuredWidth() / 2 >= mWindowManager.getDefaultDisplay().getWidth() / 2) {
                            finalMoveX = mWindowManager.getDefaultDisplay().getWidth() - mCollectView.getMeasuredWidth();
                        } else {
                            finalMoveX = 0;
                        }
                        stickToSide();
                        return !isPerformClick;
                }
                return false;
            }

            private void stickToSide() {
                ValueAnimator animator = ValueAnimator.ofInt(mCollectViewParams.x, finalMoveX).setDuration(Math.abs(mCollectViewParams.x - finalMoveX));
                animator.setInterpolator(new BounceInterpolator());
                animator.addUpdateListener(animation -> {
                    mCollectViewParams.x = (int) animation.getAnimatedValue();
                    updateViewLayout();
                });
                animator.start();
            }
        });
    }

    public boolean isRemove(int centerX, int centrY) {
        boolean isInFloatView = mDeleteRect.contains(centerX, centrY);
        if (isInFloatView){
            mCustomCancelView.isInSide(true);
        }
        return isInFloatView;
    }

    private void updateViewLayout() {
        if (null != mCollectView && null != mCollectViewParams) {
            mWindowManager.updateViewLayout(mCollectView, mCollectViewParams);
        }
    }

    public void hideWindow() {
        if (mCollectView != null) {
            mCollectView.setVisibility(View.GONE);
        }
    }

    public void visibleWindow() {
        if (mCollectView != null) {
            mCollectView.setVisibility(View.VISIBLE);
        }
    }

    public interface OnPermissionListener {
        void showPermissionDialog();
    }

}