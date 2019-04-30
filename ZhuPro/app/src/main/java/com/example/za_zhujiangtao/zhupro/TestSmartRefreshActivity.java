package com.example.za_zhujiangtao.zhupro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.za_zhujiangtao.zhupro.api.ApiComponent;
import com.example.za_zhujiangtao.zhupro.api.ApiModule;
import com.example.za_zhujiangtao.zhupro.api.ApplicationComponent;
import com.example.za_zhujiangtao.zhupro.api.ApplicationModule;
import com.example.za_zhujiangtao.zhupro.api.DaggerApiComponent;
import com.example.za_zhujiangtao.zhupro.api.DaggerApplicationComponent;
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


        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        ApiComponent component = DaggerApiComponent.builder()
                .apiModule(new ApiModule())
                .applicationComponent(applicationComponent)
                .build();
        component.inject(this);


    }


}
