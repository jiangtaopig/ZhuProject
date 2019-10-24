package com.example.za_zhujiangtao.zhupro.hotfix;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.DexFixUtils;
import com.example.za_zhujiangtao.zhupro.MainApplication;
import com.example.za_zhujiangtao.zhupro.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import butterknife.BindView;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/10/22
 */
public class TestDexFixActivity extends BaseActivity {

    @BindView(R.id.result)
    TextView textView;

    @BindView(R.id.div)
    Button calculateBtn;

    @BindView(R.id.fix)
    Button fixBtn;

    @Override
    protected int layoutId() {
        return R.layout.activity_dex_fix_layout;
    }

    @Override
    protected void onInitLogic() {
        calculateBtn.setOnClickListener(v -> {
            TestHotfix hotfix = new TestHotfix();
            hotfix.testFix(TestDexFixActivity.this);
        });

        fixBtn.setOnClickListener(v -> {
            //先将 生成好的 .dex 文件放到 MainApplication.getContext().getExternalCacheDir() + "/hotfix" 目录下
            dexFix();
        });
    }

    private void dexFix() {
        String dexName = "classes3.dex";
        File fileDir = MainApplication.getContext().getDir("odex", Context.MODE_PRIVATE);
        String filePath = fileDir.getPath() + File.separator + dexName;
        File dexFile = new File(filePath);
        if (dexFile.exists()) {
            dexFile.delete();
        }
        //移置dex文件位置（从sd卡中移置到应用目录）
        InputStream is = null;
        FileOutputStream os = null;
        //sd 卡中 dex 文件的绝对路径加文件名
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
            DexFixUtils.loadFixedDex(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
