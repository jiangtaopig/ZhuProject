package com.example.za_zhujiangtao.zhupro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class TestLayoutInflaterActivity extends AppCompatActivity {

    private LinearLayout rootLayout;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inflater_layout);

        rootLayout = findViewById(R.id.root_layout);
        inflater = LayoutInflater.from(this);
//        test1(R.layout.inflater_layout, rootLayout, true);

        //false 那么不将inflater_layout 添加到 rootLayout中，要想显示只能手动添加
//        View view = test1(R.layout.inflater_layout, rootLayout, false);
//        rootLayout.addView(view);

        //如果 root 为 null, 无论 attachToRoot 是否为 true,则 R.layout.inflater_layout 的根布局设置的大小无效，
        View view = test1(R.layout.inflater_layout, null, false);
        rootLayout.addView(view);

    }

    /**
     *
     * @param resource 要转化的为View的xml布局
     * @param root 如果root布局传入的为null, 那么，resource 的 xml布局的根布局是无效的，例如这里的 android:layout_width="200dp"
     *     android:layout_height="200dp" 将是无效的
     * @param attachToRoot 是否将 resource 布局添加到root 的布局
     */
    private View test1(@Nullable int resource, ViewGroup root, boolean attachToRoot){
        return inflater.inflate(resource, root, attachToRoot);
    }
}
