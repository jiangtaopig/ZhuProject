package com.example.za_zhujiangtao.zhupro.launch_mode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.utils.FileUtil;
import com.example.za_zhujiangtao.zhupro.utils.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

    @BindView(R.id.img_poster)
    ImageView mPoster;

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

    @SuppressLint("SetTextI18n")
    @Override
    protected void onInitLogic() {
        mTxt.setText("I am A2 Activity");
        mJumpBtn.setText("jump 2 B1Activity");

        mJumpBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, A1Activity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

//            TestMemoryLeakActivity.enter(this);

            compressImg();
        });

    }

    private void compressImg() {
//        mPoster.setImageResource(R.drawable.a2);

      Bitmap originBm = BitmapFactory.decodeResource(getResources(), R.drawable.a2);
        Log.e("A2Activity", "originBm  width = " + originBm.getWidth() + " , height = " + originBm.getHeight());

        Bitmap bm = ImageUtils.decodeSampledBitmapFromRes(R.drawable.a2, mPoster.getWidth(), mPoster.getHeight());
        Log.e("A2Activity", "compressBm  width = " + bm.getWidth() + " , height = " + bm.getHeight());
        mPoster.setImageBitmap(bm);

        save2Local(bm);
    }

    private void save2Local(Bitmap bm){
        String filePath = FileUtil.ZUIFULI_DIR_PATH;
        File file = new File(filePath + "zhujiangtao");
        if (!file.exists()){
            file.mkdirs();
        }
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        File saveFile = new File(file.getAbsolutePath(), "res.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("A2Activity", "A onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("A2Activity", "A onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("A2Activity", "A onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("A2Activity", "A onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("A2Activity", "A onDestroy");
    }
}
