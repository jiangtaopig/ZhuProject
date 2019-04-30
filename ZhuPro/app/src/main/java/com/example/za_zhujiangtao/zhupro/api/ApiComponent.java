package com.example.za_zhujiangtao.zhupro.api;

import com.example.za_zhujiangtao.zhupro.TestSmartRefreshActivity;
import com.example.za_zhujiangtao.zhupro.arouter.ARouterMainActivity;

import dagger.Component;

/**
 * Created by za-zhujiangtao on 2018/6/22.
 */
@Component(dependencies = ApplicationComponent.class, modules = ApiModule.class)
public interface ApiComponent {
    void inject(TestSmartRefreshActivity smartRefreshActivity);
    void inject(ARouterMainActivity aRouterMainActivity);
}
