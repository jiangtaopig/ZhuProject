package com.example.za_zhujiangtao.zhupro.lock_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.za_zhujiangtao.zhupro.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by za-zhujiangtao on 2018/12/17.
 */

public class LockViewActivity extends AppCompatActivity {

    @BindView(R.id.lock_view)
    LockView mLockView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_view_layout);
        ButterKnife.bind(this);

        mLockView.setOnUnlockListener(new LockView.OnUnlockListener() {
            @Override
            public boolean isUnlockSuccess(String result) {
                if ("1532".equals(result)){
                    return true;
                }
                return false;
            }

            @Override
            public void onSuccess() {
                Toast.makeText(LockViewActivity.this, "unLock success !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(LockViewActivity.this, "unLock failed !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
