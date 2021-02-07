package com.example.za_zhujiangtao.zhupro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.za_zhujiangtao.zhupro.api.MyApi;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class TestSmartRefreshActivity extends AppCompatActivity {

    @Inject
    MyApi myApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_refresh_layout);
        ButterKnife.bind(this);




    }


}
