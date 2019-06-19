package com.example.za_zhujiangtao.zhupro.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.DimenRes;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.MainApplication;

import java.lang.reflect.Field;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/6/19
 */
public class Utils {
    public static int getStatusBarHeight(View view) {
        return Utils.getStatusBarHeight(getActivity(view));
    }

    public static Activity getActivity(View view) {
        Activity activity = null;
        if (view.getContext().getClass().getName().contains("com.android.internal.policy.DecorContext")) {
            try {
                Field field = view.getContext().getClass().getDeclaredField("mPhoneWindow");
                field.setAccessible(true);
                Object obj = field.get(view.getContext());
                java.lang.reflect.Method m1 = obj.getClass().getMethod("getContext");
                activity = (Activity) (m1.invoke(obj));
            } catch (Exception ignore) {
            }
        } else {
            activity = (Activity) view.getContext();
        }
        return activity;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Activity activity) {
        int statusBarHeight = 0;

        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = activity.getResources().getDimensionPixelSize(x);
           Log.d("statusbar", "height:" + statusBarHeight);
        } catch (Exception e1) {
            try {
                Rect frame = new Rect();
                activity.getWindow().getDecorView()
                        .getWindowVisibleDisplayFrame(frame);
                statusBarHeight = frame.top;
            } catch (Exception e) {
                statusBarHeight = 60;
            }
        }
        return statusBarHeight;
    }

    public static int getDimen(@DimenRes int id) {
        int dimen = 0;
        if (MainApplication.getContext() != null) {
            dimen = MainApplication.getContext().getResources().getDimensionPixelSize(id);
        }
        return dimen;
    }

    /**
     * 设置TextView的字体风格
     *
     * @param textView
     * @param style
     */
    public static void setTextAppearance(TextView textView, @StyleRes int style) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextAppearance(style);
        } else {
            textView.setTextAppearance(textView.getContext(), style);
        }
    }


    /**
     * 显示软键盘
     *
     * @param view
     */
    public static void showKeyboard(View view) {
        ((InputMethodManager) MainApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 0);
    }

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager)  MainApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
