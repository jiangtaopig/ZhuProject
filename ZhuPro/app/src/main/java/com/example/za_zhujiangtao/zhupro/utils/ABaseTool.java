package com.example.za_zhujiangtao.zhupro.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("NewApi")
public class ABaseTool {

    /**
     * @param context
     * @return
     * @throws
     * @Title: getScreenWidth
     * @Description: 获取手机屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        if (context != null) {
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            return dm.widthPixels;
        }
        return 0;
    }

    /**
     * 得到状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Activity act) {

		/*
         * 方法一，荣耀3c无效 Rect frame = new Rect();
		 * act.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		 * int statusBarHeight = frame.top; return statusBarHeight;
		 */

		/*
		 * 方法二，荣耀3c无效 Rect rectgle= new Rect(); Window window= act.getWindow();
		 * window.getDecorView().getWindowVisibleDisplayFrame(rectgle); int
		 * StatusBarHeight= rectgle.top; int contentViewTop=
		 * window.findViewById(Window.ID_ANDROID_CONTENT).getTop(); int
		 * statusBar = contentViewTop - StatusBarHeight; return statusBar;
		 */

        //方法三，荣耀3c有效
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = act.getResources().getDimensionPixelSize(x);
            return sbar;
        } catch (Exception e1) {
            Log.e("ABaseTool", "get status bar height fail");
            e1.printStackTrace();
        }
        return 0;
    }

    /**
     * @param context
     * @return
     * @throws
     * @Title: getScreenHeight
     * @Description: 获取手机屏幕高度
     */
    public static int getScreenHeight(Context context) {

        if (context != null) {
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);

            if (Build.VERSION.SDK_INT >= 14) {
                if (ViewConfiguration.get(context).hasPermanentMenuKey()) {
                    return (int) (dm.heightPixels * (1 - 0.06f));
                }
            }
            return dm.heightPixels;
        }
        return 0;
    }


    /**
     * @param context
     * @param dp
     * @return
     * @throws
     * @Title: dpToPx
     * @Description: dp转换为px
     */
    public static int dpToPx(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int pxToDp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 检查网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    /**
     * 判断程序权限
     *
     * @param context
     */
    public void hasContactPermission(Context context) {
        StringBuffer appNameAndPermissions = new StringBuffer();
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm
                .getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo applicationInfo : packages) {
            try {
                PackageInfo packageInfo = pm.getPackageInfo(
                        applicationInfo.packageName,
                        PackageManager.GET_PERMISSIONS);
                appNameAndPermissions.append(packageInfo.packageName + "*:\n");
                // Get Permissions
                String[] requestedPermissions = packageInfo.requestedPermissions;
                if (requestedPermissions != null) {
                    for (int i = 0; i < requestedPermissions.length; i++) {
                        Log.d("test", requestedPermissions[i]);
                        appNameAndPermissions.append(requestedPermissions[i]
                                + "\n");
                    }
                    appNameAndPermissions.append("\n");
                }
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取程序版本号
     *
     * @return 当前应用的版本号
     */
    public static String getAppVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            return info.versionName;
        } catch (Exception e) {
            Log.e("AndroidRuntime",e.getMessage());
            return "";
        }
    }

    public static int getAppNumber(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取当前设备型号
     *
     * @param context
     * @return
     */
    public static String getDeviceModel(Context context) {
        Build bd = new Build();
        String model = bd.MODEL;
        return model;
    }

    /**
     * 获取当前设备系统版本
     *
     * @param context
     * @return 如2.1，4.0.1， 5.0
     */
    public static String getSDKVersion(Context context) {
        String model = Build.VERSION.RELEASE;
        return model;
    }

    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    /**
     * 实现粘贴功能 a
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }


    /**
     * 判断是否位于前台
     *
     * @param context
     * @return
     */
    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
            return true;
        }
        return false;
    }


    /**
     * 显示软键盘
     *
     * @param context
     * @param view
     */
    public static void showKeyboard(Context context, View view) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 0);
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param view
     */
    public static void hideKeyBoard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 组件截图
     *
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
//	    view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//	        view.layout(0, 0, view.getMeasuredWidth() , view.getMeasuredHeight() + view.getPaddingBottom());
//	        view.buildDrawingCache();
//	        Bitmap bitmap = view.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }

    /**
     * 判断指定名称的service是否已经启动
     *
     * @param context
     * @param serviceClassString 要检测匹配的service的全路径字符串
     * @return
     */
    public static boolean isServiceWorked(Context context, String serviceClassString) {
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
                .getRunningServices(1000);
        for (int i = 0; i < runningService.size(); i++) {
            Log.e("ABaseTool", runningService.get(i).service
                    .getClassName()
                    .toString());
            if (runningService.get(i).service
                    .getClassName()
                    .toString()
                    .equals(serviceClassString)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 拷贝文件流
     *
     * @param input
     * @param output
     * @throws IOException
     */
    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            try {
                output.write(buffer, 0, bytesRead);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取当前程序包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.packageName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取程序label
     *
     * @param context
     * @return
     */
    public static String getAppLabel(Context context) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return (String) info.applicationInfo.loadLabel(context.getPackageManager());
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = context.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }


    /**
     * 判断是否有sim卡
     *
     * @param act
     * @return
     */
    private static boolean isSimAvailable(Activity act) {

        try {
            TelephonyManager mgr = (TelephonyManager) act
                    .getSystemService(Context.TELEPHONY_SERVICE);

            return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 向EditText的光标位置插入内容
     */
    public static void insertTextET(EditText editText, String text) {
        int index = editText.getSelectionStart();
        Editable editable = editText.getText();
        editable.insert(index, text);
    }


    /**
     * 调用系统发短信界面
     *
     * @param message
     */
    public static void sendSmsMessage(Context context, String message) {

        Uri smsToUri = Uri.parse("smsto:");
        Intent mIntent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        mIntent.putExtra("sms_body", message);
        context.startActivity(mIntent);
    }

    /**
     * 清除cookie数据
     *
     * @param context
     */
    public static void removeCookie(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }

    /**
     * 判断是否是有效的密码字符串(6到20位字母和数字，以及特殊字符组成)
     *
     * @param password
     * @return
     */
    public static boolean isValidPassword(String password) {
        Pattern pat = Pattern.compile("(?=.*[a-zA-Z])(?=.*\\d)^[\\w\\\\-_-=+~`!-@#$%^&*<>\\(\\)\\[\\],.:;'?/|\\{\\}\\\"]{6,20}");
        Pattern patno = Pattern.compile(".*\\d.*");
        Pattern paten = Pattern.compile(".*[a-zA-Z].*");
        Matcher mat = pat.matcher(password);
        Matcher matno = patno.matcher(password);
        Matcher maten = paten.matcher(password);

        if (matno.matches() && maten.matches() && mat.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 检查是否有权限
     *
     * @param act
     * @param permission  权限描述，如：Manifest.permission.WRITE_CALENDAR
     * @param requestCode 授权动作请求码，onActivityResult()方法中调用
     * @return
     */
    public static void checkPermission(Activity act, String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(act, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(act, new String[]{permission}, requestCode);
        }
    }

    /**
     * 打开"应用程序设置"界面
     *
     * @param context
     */
    public static void openSettingPage(Context context) {
//		Intent intent = new Intent("/");
//		ComponentName cm = new ComponentName("com.android.settings","com.android.settings.ApplicationSettings");
//		intent.setComponent(cm);
//		intent.setAction("android.intent.action.VIEW");
//		act.startActivityForResult( intent , 0);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
        context.startActivity(intent);
    }



    /**
     * 通过反射的方法判断Notification是否可用
     *
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    @TargetApi(19)
    public static boolean isNotificationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            boolean isEnable = false;
            try {
                final String CHECK_OP_NO_THROW = "checkOpNoThrow";
                final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
                AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                ApplicationInfo appInfo = context.getApplicationInfo();
                String pkg = context.getApplicationContext().getPackageName();
                int uid = appInfo.uid;
                Class appOpsClass = null; /* Context.APP_OPS_MANAGER */

                appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
                Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
                int value = (int) opPostNotificationValue.get(Integer.class);
                isEnable = ((int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
            } catch (Exception e) {
                e.printStackTrace();
                isEnable = true;
            }
            return isEnable;
        } else {
            return true;
        }
    }

    /**
     * 压缩图片到指定文件大小
     *
     * @param bitMap     原始图片
     * @param targetSize 目标文件尺寸,单位K
     */
    public static void imageZoom(Bitmap bitMap, int targetSize) {
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length / 1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > targetSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / targetSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i),
                    bitMap.getHeight() / Math.sqrt(i));
        }
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage   ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    private static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                    double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }


//	@SuppressWarnings("unchecked")
//	@TargetApi(19)
//	public static boolean isNotificationsEnabled(Context context) {
//		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
//			ApplicationInfo appInfo = context.getApplicationInfo();
//			String pkg = context.getApplicationContext().getPackageName();
//			int uid = appInfo.uid;
//
//			Class appOpsClass;
//			try {
//				appOpsClass = Class.forName(AppOpsManager.class.getName());
//
//				Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
//
//				Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
//				int value = (int) opPostNotificationValue.get(Integer.class);
//
//				return ((int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
//			} catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException |
//					IllegalAccessException | InvocationTargetException e) {
//				e.printStackTrace();
//			}
//		}
//
//		// Default to assuming notifications are enabled
//		return true;
//	}


//	/**
//	 * 判断是否是有效的密码字符串(6到20位字母和数字，以及特殊字符组成)
//	 * @param password
//	 * @return
//	 */
//	public static boolean isValidPassword2(String password) {
//
//		Pattern pat = Pattern.compile("^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}");
//		Pattern patno = Pattern.compile(".*\\d.*");
//		Pattern paten = Pattern.compile(".*[a-zA-Z].*");
//		Matcher mat = pat.matcher(password);
//		Matcher matno = patno.matcher(password);
//		Matcher maten = paten.matcher(password);
//
//		if (matno.matches() && maten.matches() && mat.matches()) {
//			return true;
//		}
//		return false;
//	}


}

