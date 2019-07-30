package com.example.za_zhujiangtao.zhupro;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.za_zhujiangtao.zhupro.arouter.provider.UserProvider;
import com.example.za_zhujiangtao.zhupro.factory.Shape;
import com.example.za_zhujiangtao.zhupro.factory.ShapeFactory;
import com.example.za_zhujiangtao.zhupro.launch_mode.AActivity;
import com.example.za_zhujiangtao.zhupro.utils.CatchUtils;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class DefineViewActivity extends BaseActivity {

    private final static String TAG = "DefineViewActivity";
    @BindView(R.id.animation_tv)
    TextView mTv;

    private JoinMeetingDialog mInputDialog;
    /**
     * 图片硬盘缓存核心类
     */
    private DiskLruCache mDiskLruCache;

    //通过ARouter 注解来获取变量，不需要new出来， 但是要 ARouter.getInstance().inject(this);
    @Autowired
    UserProvider userProvider;

    @BindView(R.id.start_service)
    Button startService;

    @BindView(R.id.stop_service)
    Button stopService;

    @BindView(R.id.service_jump_activity)
    Button serviceJump2AActivity;

    @BindView(R.id.service_jump_b_activity)
    Button serviceJump2BActivity;

    @BindView(R.id.test_okhttp)
    Button testOkHttp;

    @BindView(R.id.put_2_cache)
    Button put2Cache;

    @BindView(R.id.get_from_cache)
    Button getFromCache;

    private MyService.MyBinder myBinder;

    @Override
    protected int layoutId() {
        return R.layout.activity_define_view_layout;
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService.MyBinder) service;
        }
    };


    @Override
    protected void onInitLogic() {

        ARouter.getInstance().inject(this);
        //测试编译时注解
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape shape = shapeFactory.create("Rectangle");
        shape.draw();

        String name = userProvider.getName();
        Log.e("xxx", name);

        testOkHttp.setOnClickListener(v -> {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor(msg -> {
                        Log.e("zjt test okhttp ", "msg >>> " + msg);
                    }).setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
            new Thread() {
                @Override
                public void run() {
                    Request request = new Request.Builder()
                            .url("https://www.imooc.com/api/teacher?type=4&num=1")
                            .build();
                    try {
                        Response response = client.newCall(request).execute();
                        Log.e("xxx", "response = " + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        });
        mTv.setOnClickListener(v -> {
//            performAnimation(mTv, mTv.getWidth(), 600);
////            displayInputDialog();

            Intent intent = new Intent(this, AActivity.class);
            //如果AActivity属于当前的任务栈，则清空当前的任务栈，然后新建AActivity作为该任务栈的根Activity；
            //如果AActivity不属于当前的任务栈，即AActivity设置了taskAffinity="com.zjt.A"，那么会新建一个任务栈然后new AActivity作为根Activity
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        put2Cache.setOnClickListener(v -> {
            TestCache testCache = new TestCache("zhujiangtao", "i am good man", 33, new Stu("HHH", 1));
            TestCache testCache2 = new TestCache("zhujiangtao2", "i am good man 2", 34, new Stu("HHH", 2));
            TestCache testCache3 = new TestCache("zhujiangtao3", "i am good man 3", 35, new Stu("HHH", 3));

            List<TestCache> data = new ArrayList<>();
            data.add(testCache);
            data.add(testCache2);
            data.add(testCache3);

            CatchUtils.getInstance(DefineViewActivity.this).putList2Cache("zcy", data);
        });

        getFromCache.setOnClickListener(v -> {
            List<TestCache> data = CatchUtils.getInstance(DefineViewActivity.this).getListFromCache("zcy", TestCache.class);
            for (TestCache cache : data){
                Log.e("xxxxxx", "cache = "+cache.toString());
            }
        });

        startService.setOnClickListener(v -> {
            Intent bindIntent = new Intent(this, MyService.class);
            bindService(bindIntent, connection, BIND_AUTO_CREATE);
        });

        stopService.setOnClickListener(v -> {
            unbindService(connection);
        });

        serviceJump2AActivity.setOnClickListener(v -> {
            myBinder.jump2AActivity();
        });

        serviceJump2BActivity.setOnClickListener(v -> {
            myBinder.jump2BActivity();
        });
    }

    private void displayInputDialog() {
        if (mInputDialog == null) {
            mInputDialog = new JoinMeetingDialog
                    .Builder()
                    .setShowInputMeetingIdView(true)
                    .setOnConfirmListener(new JoinMeetingDialog.OnConfirmListener() {
                        @Override
                        public void onConfirmMeetingNo(String meetingNo) {
                            mInputDialog.showPwdView();
                        }

                        @Override
                        public void onConfirmPwd(String pwd) {

                        }
                    })
                    .build(this, R.style.LiveBottomDialogTheme);
        }
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mInputDialog.getWindow().getAttributes();
        lp.width = (display.getWidth()); //设置宽度
        mInputDialog.getWindow().setAttributes(lp);
        mInputDialog.setCancelable(true);
        mInputDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mInputDialog.show();
    }

    private void performAnimation(View targetView, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        IntEvaluator intEvaluator = new IntEvaluator();
        animator.addUpdateListener(animation -> {
            int currentValue = (int) animation.getAnimatedValue();
            float fraction = animation.getAnimatedFraction();
            Log.e(TAG, "currentValue = " + currentValue + ", fraction = " + fraction);
            int value = intEvaluator.evaluate(fraction, start, end);
            targetView.getLayoutParams().width = value;
            targetView.requestLayout();
        });
        animator.setDuration(3000);
        animator.start();
    }

    public static class TestCache{
        private String name;
        private String content;
        private int size;
        private Stu stu;

        public TestCache(String name, String content, int size , Stu stu){
            this.name = name;
            this.content = content;
            this.size = size;
            this.stu = stu;
        }

        @Override
        public String toString() {
            return "name : "+name+", content = "+content+", size = "+size+", stu = "+stu.toString();
        }
    }

    public static class Stu{
        private String name;
        private int age;

        public Stu(String name, int age){
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return ">> stu name "+name+", age = "+age;
        }
    }

}
