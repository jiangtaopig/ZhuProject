package com.example.za_zhujiangtao.zhupro.view_dispatch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by za-zhujiangtao on 2018/11/5.
 */

public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("view_dispatch", "MyLinearLayout  onInterceptTouchEvent ACTION_DOWN");
//                return true;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("view_dispatch", "MyLinearLayout onInterceptTouchEvent ACTION_MOVE");
                return true;
//                break;
            case MotionEvent.ACTION_UP:
                Log.d("view_dispatch", "MyLinearLayout onInterceptTouchEvent ACTION_UP");
//                return true;
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("view_dispatch", "MyLinearLayout onInterceptTouchEvent ACTION_CANCEL");
                break;
        }
        boolean res = super.onInterceptTouchEvent(ev);
        Log.e("view_dispatch", "MyLinearLayout onInterceptTouchEvent return >>> " + res);
        return res;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("view_dispatch", "MyLinearLayout  dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("view_dispatch", "MyLinearLayout dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("view_dispatch", "MyLinearLayout dispatchTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("view_dispatch", "MyLinearLayout dispatchTouchEvent ACTION_CANCEL");
                break;
        }
        boolean res = super.dispatchTouchEvent(event);
        Log.e("view_dispatch", "MyLinearLayout dispatchTouchEvent return >>> " + res);
        return res;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("view_dispatch", "MyLinearLayout  onTouchEvent ACTION_DOWN");
//                return false;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("view_dispatch", "MyLinearLayout onTouchEvent ACTION_MOVE ");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("view_dispatch", "MyLinearLayout onTouchEvent ACTION_UP ");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("view_dispatch", "MyLinearLayout onTouchEvent ACTION_CANCEL");
                break;
        }
        boolean res = super.onTouchEvent(event);
        Log.e("view_dispatch", "MyLinearLayout onTouchEvent return >>> " + res);
        return res;
    }
}
