package com.example.za_zhujiangtao.zhupro.hotfix;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;


/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/10/22
 */
public class TestHotfix {
    public void testFix(Context context){
        int divdend =10;
        int divisor = 0;
        int res = divdend/divisor;
        Toast.makeText(context, "res = "+res, Toast.LENGTH_LONG).show();
    }
}
