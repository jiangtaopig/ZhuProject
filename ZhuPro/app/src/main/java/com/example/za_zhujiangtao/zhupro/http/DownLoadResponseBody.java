package com.example.za_zhujiangtao.zhupro.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/24
 * 下载文件、图片等 进度
 */
public class DownLoadResponseBody extends ResponseBody {

    private ResponseBody mResponseBody;
    private DownLoadProgressListener mProgressListener;
    private BufferedSource mBufferedSource;

    public DownLoadResponseBody(ResponseBody responseBody, DownLoadProgressListener listener){
        mResponseBody = responseBody;
        mProgressListener = listener;
    }

    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null){
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    private Source source(Source source){
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                Log.e("DownLoadResponseBody", "bytesRead = "+bytesRead);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if (mProgressListener != null && mResponseBody.contentLength() != 0){
                    mProgressListener.progress((int) (100 * totalBytesRead / mResponseBody.contentLength()));
                }
                return bytesRead;
            }
        };
    }
}
