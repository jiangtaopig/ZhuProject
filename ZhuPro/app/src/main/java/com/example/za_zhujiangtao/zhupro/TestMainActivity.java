package com.example.za_zhujiangtao.zhupro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TestMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main_layout);

        TextView toIpc = findViewById(R.id.txt_to_ipc);
        toIpc.setOnClickListener(v ->{
            Intent intent = new Intent(TestMainActivity.this, IPCActivity.class);
            startActivity(intent);
        });
    }
}
