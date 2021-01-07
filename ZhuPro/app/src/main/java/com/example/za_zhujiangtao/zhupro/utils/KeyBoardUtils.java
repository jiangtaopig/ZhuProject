package com.example.za_zhujiangtao.zhupro.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/7/28
 */
public class KeyBoardUtils {
    private Activity activity;
    private OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener;
    private int screenHeight;
    // 空白高度 = 屏幕高度 - 当前 Activity 的可见区域的高度
    // 当 blankHeight 不为 0 即为软键盘高度。
    private int blankHeight = 0;

    /**
     * 获取屏幕高度，设置竖屏，设置根据输入法调整View移动
     *
     * @param activity
     */
    @SuppressLint("SourceLockedOrientationActivity")
    public KeyBoardUtils(Activity activity) {
        this.activity = activity;
        //widthPixels及heightPixelst就为屏幕分辨率（为绝对宽度与高度）
        screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
        //根据输入法调整View移动
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //如果不是竖屏设置为竖屏
        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    /**
     * 获取当前可见视图view,视图的可视状态发生改变监听
     */
    public void onCreate() {
        //获取当前activiy的view
        View content = activity.findViewById(android.R.id.content);
        // content.addOnLayoutChangeListener(listener); 这个方法有时会出现一些问题
        content.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    public void onDestory() {
        View content = activity.findViewById(android.R.id.content);
        content.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    //当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            //Rect 其实就是左上右下
            Rect rect = new Rect();
            //获取当前窗口可视区域大小的
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int newBlankhHeight = screenHeight - rect.bottom;
            Log.e("test", "newBlankhHeight = "+newBlankhHeight+", screenHeight = "+screenHeight);
            if (newBlankhHeight != blankHeight) {//软键盘高度和上一次高度比较,首次blankHeight =0
                if (newBlankhHeight > blankHeight) {//说明弹出软键盘
                    // keyboard pop
                    if (onKeyBoardStatusChangeListener != null) {
                        onKeyBoardStatusChangeListener.OnKeyBoardPop(newBlankhHeight);
                    }
                } else { // newBlankheight <= blankHeight
                    //说明隐藏软键盘
                    if (onKeyBoardStatusChangeListener != null) {
                        onKeyBoardStatusChangeListener.OnKeyBoardClose(blankHeight);
                    }
                }
            }
            blankHeight = newBlankhHeight;
        }
    };

    public void setOnKeyBoardStatusChangeListener(OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener) {
        this.onKeyBoardStatusChangeListener = onKeyBoardStatusChangeListener;
    }

    public interface OnKeyBoardStatusChangeListener {

        void OnKeyBoardPop(int keyBoardheight);

        void OnKeyBoardClose(int oldKeyBoardheight);
    }
}
