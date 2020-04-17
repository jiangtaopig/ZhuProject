package com.example.za_zhujiangtao.zhupro.arouter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.za_zhujiangtao.zhupro.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/test/webview")
public class ARouterWebActivity extends AppCompatActivity {


    @BindView(R.id.web_view)
    WebView mWebView;

    @BindView(R.id.back)
    Button back;

    @BindView(R.id.forward)
    Button forward;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter_web_layout);
        ButterKnife.bind(this);

        List<String> s = new ArrayList<>();
        s.add("1");

        mWebView.loadUrl(getIntent().getStringExtra("url"));

        back.setOnClickListener(v -> {
            Log.e("ARouterWebActivity", "canBack = "+mWebView.canGoBack());
            if (mWebView.canGoBack()){
                mWebView.goBack();
            }
        });

        forward.setOnClickListener(v -> {
            if (mWebView.canGoForward()){
                mWebView.goForward();
            }
        });

    }
}
