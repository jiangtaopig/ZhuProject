package com.example.za_zhujiangtao.zhupro;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by za-zhujiangtao on 2018/5/24.
 */

public class TestActivity extends Activity {

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_im_name_card);

        Map<String, String> map = new LinkedHashMap<>();
        map.put("11", "12");

        editText = findViewById(R.id.edit_search);
        button = findViewById(R.id.test_executors);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId){
                    case EditorInfo.IME_ACTION_SEND:
                        Toast.makeText(getBaseContext(), "send", Toast.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                return null;
            }
        };

        button.setOnClickListener(v -> {
            ExecutorService executorService = Executors.newFixedThreadPool(3);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Log.e("xxx", "线程池执行完毕");
                }
            });
            executorService.shutdown();
        });


    }
}
