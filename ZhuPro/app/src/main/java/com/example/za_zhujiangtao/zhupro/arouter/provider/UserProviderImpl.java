package com.example.za_zhujiangtao.zhupro.arouter.provider;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.za_zhujiangtao.zhupro.arouter.User;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/7/25
 */
@Route(path = "/com/user")
public class UserProviderImpl implements UserProvider {
    private User mUser;
    @Override
    public String getName() {
        return mUser.getName();
    }

    @Override
    public void init(Context context) {
        mUser = new User("zjt_zcy");
    }
}
