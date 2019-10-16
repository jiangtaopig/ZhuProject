package com.example.za_zhujiangtao.zhupro.launch_mode;

import android.content.Intent;
import android.widget.Button;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/10/14
 */
public class TestLaunchModeActivity extends BaseActivity {

    private Button mJumpBtn;

    @Override
    protected int layoutId() {
        return R.layout.activity_launch_mode_layout;
    }

    @Override
    protected void onInitLogic() {
        initView();
    }

    private void initView(){
        mJumpBtn = findViewById(R.id.jump_activity);
        mJumpBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TestLaunchModeActivity.this, A1Activity.class);
            startActivity(intent);
        });
    }
}
