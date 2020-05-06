package com.example.za_zhujiangtao.zhupro.h5_acc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.za_zhujiangtao.zhupro.MainApplication;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.h5_acc.utils.FileDownUtils;
import com.example.za_zhujiangtao.zhupro.h5_acc.utils.ZipUtils;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5_index);
    }

    public void yugangshuoBeforeOptimize(View view) {
        WebAccumulateActivity.go2YuGangShuoActivity(this, false);
    }

    public void yugangshuoAfterOptimize(View view) {
        WebAccumulateActivity.go2YuGangShuoActivity(this, true);
    }

    public void downLoadFiles(View view) {
        Toast.makeText(this, "start downLoad", Toast.LENGTH_LONG).show();
        FileDownUtils fileDownUtils = FileDownUtils.getInstance();
        String saveZipFilePath = ZipUtils.getCacheDirPath() + File.separator + "zjt" + File.separator + "h5_src";
        fileDownUtils.setListener(new OnDownLoadCompleteListener() {
            @Override
            public void onComplete(String fileName) {
                //下载完成后直接去解压，注意 这个回调是在 子UI线程中执行的
                Log.e("FileDownUtils", "IndexActivity -- onComplete fileName = " + fileName + ", ThreadName = " + Thread.currentThread().getName());
                ZipUtils.unZipFile(new File(saveZipFilePath + File.separator + fileName), saveZipFilePath);
            }
        });
        fileDownUtils.startDownload(saveZipFilePath);
    }
}
