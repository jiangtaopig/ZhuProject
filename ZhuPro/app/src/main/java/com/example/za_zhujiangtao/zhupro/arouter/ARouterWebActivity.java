package com.example.za_zhujiangtao.zhupro.arouter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.za_zhujiangtao.zhupro.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/test/webview")
public class ARouterWebActivity extends AppCompatActivity {


    @BindView(R.id.web_view)
    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter_web_layout);
        ButterKnife.bind(this);

        mWebView.loadUrl(getIntent().getStringExtra("url"));

    }
}
