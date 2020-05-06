package com.example.za_zhujiangtao.zhupro.h5_acc.utils;

import android.util.Log;

import com.example.za_zhujiangtao.zhupro.MainApplication;
import com.example.za_zhujiangtao.zhupro.h5_acc.OnDownLoadCompleteListener;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/5/6
 */
public class FileDownUtils {
    private final static String TAG = FileDownUtils.class.getSimpleName();
    private BaseDownloadTask singleTask;
    private int singleTaskId = 0;
    //文件下载地址
    private String downloadUrl = "https://image.zuifuli.com/14/20200506/b5d3b3762ccb65a4f48ed3f7ba87724f.zip";
    //下载下来的文件名称
    private String fileName;
    private OnDownLoadCompleteListener listener;

    private static FileDownUtils mInstance;

    private FileDownUtils() {
        FileDownloader.setup(MainApplication.getContext());
    }

    public static FileDownUtils getInstance() {
        if (mInstance == null) {
            synchronized (FileDownUtils.class) {
                if (mInstance == null) {
                    mInstance = new FileDownUtils();
                }
            }
        }
        return mInstance;
    }

    public void setListener(OnDownLoadCompleteListener listener){
        this.listener = listener;
    }

    /**
     * @param saveZipFilePath 文件保存路径
     */
    public void startDownload(String saveZipFilePath) {
        Log.e(TAG, "startDownload saveZipFilePath = " + saveZipFilePath);
        singleTask = FileDownloader.getImpl().create(downloadUrl)
                .setPath(saveZipFilePath, true)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setListener(new FileDownloadSampleListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.pending(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e(TAG, "----->progress taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes
                                + ",percent:" + soFarBytes * 1.0 / totalBytes + ",speed:" + task.getSpeed());
                        super.progress(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        Log.e(TAG, "----------->blockComplete taskId:" + task.getId() + ",filePath:" + task.getPath() +
                                ",fileName:" + task.getFilename() + ",speed:" + task.getSpeed() + ",isReuse:" + task.reuse());
                        fileName = task.getFilename();
                        if (listener != null){
                            listener.onComplete(fileName);
                        }
                        super.blockComplete(task);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.e(TAG, "---------->completed taskId:" + task.getId() + ",isReuse:" + task.reuse());
                        super.completed(task);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.paused(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.e(TAG, "--------->error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());
                        super.error(task, e);
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                    }
                });
        singleTaskId = singleTask.start();
    }

}
