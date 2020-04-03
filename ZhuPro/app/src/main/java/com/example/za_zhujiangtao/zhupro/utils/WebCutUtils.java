package com.example.za_zhujiangtao.zhupro.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Zjt
 * 2020/3/27
 * 获取H5截图
 */
public class WebCutUtils {
    private static final String TAG = "WebUtils";

    public static Observable<File> captureWebView(Activity activity, WebView webView, String type, LinearLayout rootView) {
        return Observable.just(webView)
                .map(view -> {
                    Bitmap bm = null;
                    view.setDrawingCacheEnabled(true);
                    view.buildDrawingCache();
                    if ("full".equals(type)){ //获取完整的H5截图
                        view.measure(View.MeasureSpec.makeMeasureSpec(
                                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                        view.layout(0, 0, view.getMeasuredWidth(),
                                view.getMeasuredHeight());
                        view.setDrawingCacheEnabled(true);
                        view.buildDrawingCache();
                       bm = Bitmap.createBitmap(webView.getMeasuredWidth(),
                            webView.getMeasuredHeight(), Bitmap.Config.RGB_565);
                    }else if ("page".equals(type)){//获取H5当前页面的截图
                        bm = view.getDrawingCache();
                    }else if ("window".equals(type)){//获取根View的截图
                        bm = Bitmap.createBitmap(
                                rootView.getWidth(),
                                rootView.getHeight(),
                                Bitmap.Config.RGB_565);
                    }
                    return bm;
                })
                .observeOn(Schedulers.io())
                .map(bm -> {
                    File file = null;
                    Canvas canvas = new Canvas(bm);
                    Paint paint = new Paint();
                    canvas.drawBitmap(bm, 0, webView.getMeasuredHeight(), paint);
                    try {
                        //异步发起的测绘，需要等待一段时间
                        Thread.sleep(1500);
                        if ("window".equals(type)){
                            rootView.draw(canvas);
                        }else {
                            webView.draw(canvas);
                        }
                        Log.e(TAG, "canvas size:" + canvas.getWidth() + " * " + canvas.getHeight());
                        OutputStream fOut;
                        file = new File(webView.getContext().getExternalCacheDir(), "capture_" + System.currentTimeMillis() + ".jpg");
                        fOut = new FileOutputStream(file);
                        bm.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
                        fOut.flush();
                        fOut.close();
//                        bm.recycle();
                        if (bm != null && !bm.isRecycled()){
                            bm = null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return file;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(file -> {
                    webView.requestLayout();
                    webView.invalidate();
                    return file;
                });
    }

}
