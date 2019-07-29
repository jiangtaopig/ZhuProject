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
public class AActivity extends BaseActivity {

    @BindView(R.id.jump_2_b)
    Button jump2B;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("AActivity", "A onCreate");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("AActivity", "A onNewIntent");
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_a;
    }

    @Override
    protected void onInitLogic() {
        jump2B.setOnClickListener(v -> {
            Intent intent = new Intent(AActivity.this, BActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}
