package com.example.za_zhujiangtao.zhupro.view_dispatch;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.za_zhujiangtao.zhupro.R;


/**
 * Created by za-zhujiangtao on 2018/11/5.
 */

public class TestViewDispatchActivity extends Activity {

    MyLinearLayout myLinearLayout;
    MyButton myButton;
    MyTextView myTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_dispatch_layout);

        myLinearLayout = findViewById(R.id.my_layout);
        myButton = findViewById(R.id.my_btn);
        myTextView = findViewById(R.id.my_txt);

        myLinearLayout.setOnClickListener(v -> {
            Toast.makeText(this, "My Layout", Toast.LENGTH_LONG).show();
        });

        myButton.setOnClickListener(v -> {
            Toast.makeText(this, "My Button", Toast.LENGTH_LONG).show();
        });
    }
}
