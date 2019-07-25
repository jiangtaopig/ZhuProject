package com.example.za_zhujiangtao.zhupro.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/7/25
 */
public class DiskLruCacheUtils {
    private final static int MAX_SIZE = 100 * 1024 * 1024; //100 M
    private final static int VALUE_COUNT = 1;
    private final static String UNIQUE_NAME = "YIYUAN_CACHE";
    private DiskLruCache mDiskLruCache;
    private volatile static DiskLruCacheUtils mInstance;
    private DiskLruCacheUtils(Context context){
        initCache(context);
    }

    public static DiskLruCacheUtils getInstance(Context context){
        if (mInstance == null){
            synchronized (DiskLruCacheUtils.class){
                if (mInstance == null){
                    mInstance = new DiskLruCacheUtils(context);
                }
            }
        }
        return mInstance;
    }

    private void initCache(Context context) {
        try {
            File cacheDir = getDiskCacheDir(context, UNIQUE_NAME);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), VALUE_COUNT, MAX_SIZE);
        } catch (Exception e) {
        }
    }

    private int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return 1;
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (context.getExternalCacheDir() != null) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    public void putString2Cache(String key, String content) {
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

    public String getStringFromCache(String key){
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

    /**
     * 把bitmap写入sd卡，由diskLruCache管理
     *
     * @param key
     * @param bitmap
     */
    private void putBitmap2Cache(String key, Bitmap bitmap) {
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
            }
            mDiskLruCache.flush();
        } catch (Exception e) {
        }
    }

    /**
     * 从SD卡中读取位图缓存
     *
     * @param key
     * @return
     */
    private Bitmap getBitmapFromCache(String key) {
        try {
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
            if (snapShot != null) {
                InputStream is = snapShot.getInputStream(0);
                return BitmapFactory.decodeStream(is);
            }
        } catch (Exception e) {
        }
        return null;
    }
}
