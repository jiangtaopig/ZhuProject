package com.example.za_zhujiangtao.zhupro;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.za_zhujiangtao.zhupro.factory.Shape;
import com.example.za_zhujiangtao.zhupro.factory.ShapeFactory;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

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

    @Override
    protected int layoutId() {
        return R.layout.activity_define_view_layout;
    }

    @Override
    protected void onInitLogic() {
        mTv.setOnClickListener(v -> {
//            performAnimation(mTv, mTv.getWidth(), 600);
////            displayInputDialog();
//            OkHttpClient client = new OkHttpClient.Builder()
//                    .addInterceptor(new HttpLoggingInterceptor(msg -> {
//                        Log.e("zjt test okhttp ", "msg >>> "+msg);
//                    }).setLevel(HttpLoggingInterceptor.Level.BODY))
//                    .build();
//            new Thread() {
//                @Override
//                public void run() {
//                    Request request = new Request.Builder()
//                            .url("https://www.imooc.com/api/teacher?type=4&num=1")
//                            .build();
//                    try {
//                        Response response = client.newCall(request).execute();
//                        Log.e("xxx", "response = " + response.body().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }.start();

            putBitmapCacheToSDCard("json", "我是中国人，i love china");
            String data = getCacheData("json");
        });

        ShapeFactory shapeFactory = new ShapeFactory();
        Shape shape = shapeFactory.create("Rectangle");
        shape.draw();

        initCache();
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

    public static File dir = new File(Environment.getExternalStorageDirectory() + "/.Imageloader/json/");

    public void saveToSDCard(Activity mActivity, String filename, String content) {
        String en = Environment.getExternalStorageState();
        //获取SDCard状态,如果SDCard插入了手机且为非写保护状态
        if (en.equals(Environment.MEDIA_MOUNTED)) {
            try {
                dir.mkdirs(); //create folders where write files
                File file = new File(dir, filename);

                OutputStream out = new FileOutputStream(file);
                out.write(content.getBytes());
                out.close();
                Toast.makeText(DefineViewActivity.this, "保存成功", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(DefineViewActivity.this, "保存失败", Toast.LENGTH_LONG).show();
            }
        } else {
            //提示用户SDCard不存在或者为写保护状态
            Toast.makeText(DefineViewActivity.this, "SDCard不存在或者为写保护状态", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 把bitmap写入sd卡，由diskLruCache管理
     *
     * @param key
     */
    private void putBitmapCacheToSDCard(String key, String content) {
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                outputStream.write(content.getBytes());
                editor.commit();
            }
            mDiskLruCache.flush();
        } catch (Exception e) {
        }
    }

    private void initCache() {
        try {
            File cacheDir = getDiskCacheDir(this, "zzjjtt");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(this), 1, 100 * 1024 * 1024);
        } catch (Exception e) {
        }
    }

    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (context.getExternalCacheDir() != null) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return 1;
    }

    private String getCacheData(String key) {
        DiskLruCache.Snapshot snapshot = null;
        String res = "";
        try {
            snapshot = mDiskLruCache.get(key);
            InputStream inputStream = snapshot.getInputStream(0);
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            res = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
