package com.example.za_zhujiangtao.zhupro.float_window;

import android.os.HandlerThread;
import android.util.SparseArray;

import com.example.za_zhujiangtao.zhupro.BaseActivity;

import java.util.LinkedHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/7/13
 */
public class TestFloatActivity extends BaseActivity {
    @Override
    protected int layoutId() {
        return 0;
    }

    @Override
    protected void onInitLogic() {

        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add("1");
        HandlerThread handlerThread = new HandlerThread("xxx");
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("1", "a");
        map.get("1");
    }
}
