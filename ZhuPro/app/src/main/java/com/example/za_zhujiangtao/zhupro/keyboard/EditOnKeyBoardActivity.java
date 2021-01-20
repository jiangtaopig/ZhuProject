package com.example.za_zhujiangtao.zhupro.keyboard;

import android.os.Handler;
import android.os.Message;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/1/12
 */
public class EditOnKeyBoardActivity extends BaseActivity {
    @Override
    protected int layoutId() {
        return R.layout.activity_edit_on_keyboard_layout;
    }

    @Override
    protected void onInitLogic() {

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };

        handler.sendMessage(handler.obtainMessage());


    }
}
