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
public class BActivity extends BaseActivity {

    @BindView(R.id.jump_2_c)
    Button jump2C;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("BActivity", "B onCreate");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("BActivity", "B onNewIntent");
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_b;
    }

    @Override
    protected void onInitLogic() {
        jump2C.setOnClickListener(v -> {
            Intent intent = new Intent(BActivity.this, AActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }
}
