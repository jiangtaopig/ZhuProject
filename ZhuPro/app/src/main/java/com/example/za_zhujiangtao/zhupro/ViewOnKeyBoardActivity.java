package com.example.za_zhujiangtao.zhupro;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by za-zhujiangtao on 2018/12/14.
 */

public class ViewOnKeyBoardActivity extends AppCompatActivity {

    @BindView(R.id.root_layout)
    RelativeLayout mRlRoot;

    @BindView(R.id.input_edit)
    EditText mEdit;

    @BindView(R.id.bottom_layout)
    RelativeLayout mBottomLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_on_keyboard_layout);
        ButterKnife.bind(this);

        mBottomLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                ViewOnKeyBoardActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = ViewOnKeyBoardActivity.this.getWindow().getDecorView().getContext().getResources().getDisplayMetrics().heightPixels;
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                Log.d("Keyboard Size", "Size: " + heightDifference);
                showAViewOverKeyBoard(heightDifference);

            }
        });

        mRlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(mEdit);
            }
        });

//        showSoftInputFromWindow(this, mEdit);
        mEdit.postDelayed(new Runnable() {
            @Override
            public void run() {
                showKeyboard(mEdit);
            }
        }, 100);

        List<String> list = new ArrayList<>(5);
        for (int i = 0; i< 10; i++){
            list.add(""+(i+1));
        }

        for (String s : list){
            Log.e("zzjjtt", "ss = "+s);
        }

        String sstr = String.join(",", list);
        Log.e("zzjjtt", "sstr = "+sstr);

        sstr = "{\n" +
                "  \"status\": 1,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"Tony老师聊shell——环境变量配置文件\",\n" +
                "      \"picSmall\": \"http://img.mukewang.com/55237dcc0001128c06000338-300-170.jpg\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/55237dcc0001128c06000338.jpg\",\n" +
                "      \"description\": \"为你带来shell中的环境变量配置文件\",\n" +
                "      \"learner\": 12312\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"name\": \"数学知识在CSS动画中的应用\",\n" +
                "      \"picSmall\": \"http://img.mukewang.com/55249cf30001ae8a06000338-300-170.jpg\",\n" +
                "      \"picBig\": \"http://img.mukewang.com/55249cf30001ae8a06000338.jpg\",\n" +
                "      \"description\": \"数学知识与CSS结合实现酷炫效果\",\n" +
                "      \"learner\": 45625\n" +
                "    }\n" +
                "  ],\n" +
                "  \"msg\": \"成功\"\n" +
                "}";

    }

    private void showAViewOverKeyBoard(int heightDifference) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBottomLayout.getLayoutParams();
        if (heightDifference > 0) {//显示
            params.bottomMargin = heightDifference;
            mBottomLayout.setLayoutParams(params);
//            mBottomLayout.setPadding(24, 24, 24, heightDifference);
        } else {//隐藏
            params.bottomMargin = 0;
            mBottomLayout.setLayoutParams(params);
//            mBottomLayout.setPadding(24, 24, 24, 24);
        }
    }

    private void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public void showKeyboard(final View view) {
        view.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }

    public void hideKeyboard(final View view) {
        InputMethodManager imm =
                (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
