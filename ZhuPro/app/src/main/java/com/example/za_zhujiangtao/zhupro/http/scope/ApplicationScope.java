package com.example.za_zhujiangtao.zhupro.http.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Application生命周期
 * Created by fanjianfeng on 2017/3/30.
 */


@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationScope {
}
