package com.example.za_zhujiangtao.zhupro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

public class ScrollerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_layout);

        Button button = findViewById(R.id.button1);
        button.setOnClickListener(v ->{
            Toast.makeText(ScrollerActivity.this, "Clicked", Toast.LENGTH_LONG).show();
        });
    }
}
