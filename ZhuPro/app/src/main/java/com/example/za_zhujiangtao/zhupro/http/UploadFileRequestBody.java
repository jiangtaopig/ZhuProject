package com.example.za_zhujiangtao.zhupro.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by za-zhujiangtao on 2018/8/23.
 */

public class UploadFileRequestBody extends RequestBody {
    private RequestBody mRequestBody;
    private BufferedSink bufferedSink;
    private UploadProgressListener listener;

    public UploadFileRequestBody(String jsonData, UploadProgressListener listener){
        mRequestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonData);
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (sink instanceof Buffer){
            //因为项目重写了日志拦截器，而日志拦截器里面调用了 RequestBody.writeTo方法，但是 它的sink类型是Buffer类型，所以直接写入
            //如果不这么做的话，上传进度最终会达到200%，因为被调用2次，而且日志拦截的writeTo是直接写入到 buffer 对象中，所以会很快；
            mRequestBody.writeTo(sink);
            return;
        }
        if (bufferedSink == null) {
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        mRequestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                Log.e("LogUtils", "bytesWritten = "+bytesWritten+", contentLength = "+contentLength);
                //回调
                if (listener != null){
                    listener.uploadProgress(bytesWritten, contentLength);
                }
            }
        };
    }
}
