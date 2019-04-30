package com.example.za_zhujiangtao.zhupro.api;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by za-zhujiangtao on 2018/7/11.
 */

@Module
public class ApplicationModule {
    private Context mContext;

    public ApplicationModule(Context context){
        mContext = context;
    }

    @Provides
    Context provideContext(){
        return mContext;
    }
}
