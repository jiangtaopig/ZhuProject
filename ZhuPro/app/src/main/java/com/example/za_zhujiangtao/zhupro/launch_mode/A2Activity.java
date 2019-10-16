package com.example.za_zhujiangtao.zhupro.launch_mode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;

import butterknife.BindView;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/7/25
 */
public class A2Activity extends BaseActivity {

    @BindView(R.id.a2_jump)
    Button mJumpBtn;

    @BindView(R.id.a2_txt)
    TextView mTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("A2Activity", "A onCreate");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("A2Activity", "A onNewIntent");
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_a2;
    }

    @Override
    protected void onInitLogic() {
        mTxt.setText("I am A2 Activity");
        mJumpBtn.setText("jump 2 B1Activity");
        mJumpBtn.setOnClickListener(v -> {
            Intent intent = new Intent(A2Activity.this, B1Activity.class);
            startActivity(intent);
        });
    }
}
