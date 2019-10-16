package com.example.za_zhujiangtao.zhupro.launch_mode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;

import butterknife.BindView;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/7/25
 */
public class B1Activity extends BaseActivity {

    @BindView(R.id.jump_2_c)
    Button jump2C;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("B1Activity", "B onCreate");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("B1Activity", "B onNewIntent");
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_b;
    }

    @Override
    protected void onInitLogic() {
        jump2C.setOnClickListener(v -> {
            Intent intent = new Intent(B1Activity.this, A1Activity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            //Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK 的作用
            // 如果目标栈（即有没有和 A1Activity 的taskAffinity一样的栈）存在，则清空栈，否则就新建一个栈，然后new一个Activity作为根Activity入栈
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}
