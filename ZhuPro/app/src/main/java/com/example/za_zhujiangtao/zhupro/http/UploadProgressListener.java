package com.example.za_zhujiangtao.zhupro.http;

/**
 * Created by za-zhujiangtao on 2018/8/23.
 */

public interface UploadProgressListener {
    void uploadProgress(long hasWrittenLen, long totalLength);
}
