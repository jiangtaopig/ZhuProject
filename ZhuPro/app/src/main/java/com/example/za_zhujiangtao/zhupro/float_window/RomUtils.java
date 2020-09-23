/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.example.za_zhujiangtao.zhupro.float_window;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;

/**
 * Description:
 *
 * @author zhaozp
 * @since 2016-05-23
 */
public class RomUtils {

    public static final int REQUEST_PERMISSION_CODE = 0x0110;

    public static OnSuspensionPermissionListener mOnSuspensionPermissionListener;

    public static boolean checkFloatWindowPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        }
        return true;
    }

    public static void applyPermission(Context context, OnSuspensionPermissionListener onSuspensionPermissionListener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            ((Activity) context).startActivityForResult(intent, REQUEST_PERMISSION_CODE);
            mOnSuspensionPermissionListener = onSuspensionPermissionListener;
        }
    }

    public static void onActivityResult(Context context, int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (null != mOnSuspensionPermissionListener) {
                    mOnSuspensionPermissionListener.onPermissionGranted();
                }
            }
        }
    }

    public interface OnSuspensionPermissionListener {

        /**
         * 当权限请求完毕后的回调
         */
        void onPermissionGranted();
    }
}
