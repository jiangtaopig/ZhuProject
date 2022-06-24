package com.example.za_zhujiangtao.zhupro.launch_mode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.MessageQueue;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.FileUtils;
import com.example.za_zhujiangtao.zhupro.MainApplication;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.float_window.FloatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;


/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/10/14
 */
public class TestLaunchModeActivity extends BaseActivity {

    private Button mJumpBtn;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String s = (String) msg.obj;
            Log.e("TestLaunchModeActivity", "s = " + s + ", time = " + System.currentTimeMillis());
            if (msg.arg1 == 1){
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("TestLaunchModeActivity", "msg.arg1 = " + msg.arg1 + ", time = " + System.currentTimeMillis());
            }

        }
    };

    @Override
    protected int layoutId() {
        return R.layout.activity_launch_mode_layout;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onInitLogic() {
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {
        mJumpBtn = findViewById(R.id.jump_activity);
        mJumpBtn.setOnClickListener(v -> {
            Intent intent = new Intent(TestLaunchModeActivity.this, A2Activity.class);
            startActivity(intent);
//            testHandler();

//            FileUtils.writeFile("hotfix", "zjt2.txt", "i am zjt");
//            moveFileFromSdcardToAppDir();
        });

//        testAsyncTask();

//        stringThreadLocal.remove();
    }

    private void moveFileFromSdcardToAppDir() {
        String dexName = "class2.dex";
        File fileDir = MainApplication.getContext().getDir("odex", Context.MODE_PRIVATE);
        String filePath = fileDir.getPath() + File.separator + dexName;
        File dexFile = new File(filePath);
        if (dexFile.exists()) {
            dexFile.delete();
        }
        //移置dex文件位置（从sd卡中移置到应用目录）
        InputStream is = null;
        FileOutputStream os = null;
        String sdPath = MainApplication.getContext().getExternalCacheDir() + "/hotfix" + File.separator + dexName;
        try {
            is = new FileInputStream(sdPath);
            os = new FileOutputStream(filePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }

            //测试是否成功写入
            File fileTest = new File(filePath);
            if (fileTest.exists()) {
                Toast.makeText(this, "dex重写成功", Toast.LENGTH_SHORT).show();
            }

            //获取到已修复的dex文件，进行热修复操作

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testAsyncTask() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }
        };
        task.execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void testIdleHandler() {
        Message message = Message.obtain();
        message.obj = "i am good man, delay task";
        Log.e("TestLaunchModeActivity", "---------------------");
        mHandler.sendMessageDelayed(message, 3_000);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //这是是UI线程空闲时会执行
        mHandler.getLooper().getQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                Log.e("TestLaunchModeActivity", "UI线程空闲才能执行  " );
                return false;
            }
        });

        Message msg = mHandler.obtainMessage();
        msg.obj = "我不是延迟任务";
        msg.arg1 = 1;

        mHandler.sendMessage(msg);
    }

    private void testLeakCanary() {
        mHandler.postDelayed(() -> {
            Log.e("TestLaunchModeActivity", "test leakCanary");
        }, 20 * 1000);
    }

    //解决Handler 内存泄漏的方法，弱引用 TestLaunchModeActivity，弱引用GC的时候一定会被回收
    static class MyHandler extends Handler {
        private WeakReference<TestLaunchModeActivity> myActivity;

        public MyHandler(TestLaunchModeActivity activity) {
            myActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TestLaunchModeActivity activity = myActivity.get();
            if (activity != null) {
                activity.doTask();
            }
        }
    }

    private void doTask() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TestLaunchModeActivity", " onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("TestLaunchModeActivity", " onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("TestLaunchModeActivity", " onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TestLaunchModeActivity", " onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TestLaunchModeActivity", " onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TestLaunchModeActivity", " onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TestLaunchModeActivity", " onDestroy");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("TestLaunchModeActivity", " onNewIntent");
    }
}
