package com.example.za_zhujiangtao.zhupro.define_view;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/8/28
 */
public class DefineViewActivity2 extends BaseActivity {
    @Override
    protected int layoutId() {
        return R.layout.activity_define_view2_layout;
    }

    @Override
    protected void onInitLogic() {

        HashMap<String, String> map = new HashMap<>();
        map.put("1", "a");

        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("1", "a");
    }
}
