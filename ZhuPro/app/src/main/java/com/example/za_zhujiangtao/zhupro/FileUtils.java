package com.example.za_zhujiangtao.zhupro;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Locale;

import static android.content.Context.BATTERY_SERVICE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_CDMA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSDPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPAP;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSUPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_IDEN;
import static android.telephony.TelephonyManager.NETWORK_TYPE_LTE;

/**
 * Created by za-zhujiangtao on 2018/8/6.
 */

public class FileUtils {

    private static File cacheDir = !isExternalStorageWritable() ? getInnerCacheDir() : getExternalCacheDir();

    public static File getInnerCacheDir() {
        return MainApplication.getContext().getFilesDir();
    }

    public static File getExternalCacheDir() {
        return MainApplication.getContext().getExternalCacheDir();
    }


    public static File getCacheDir(String dirName) {
        File result;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cacheDir = MainApplication.getContext().getExternalCacheDir();
            if (cacheDir == null) {
                result = new File(Environment.getExternalStorageDirectory(),
                        "Android/data/" + MainApplication.getContext().getPackageName() + "/cache/" + dirName);
            } else {
                result = new File(cacheDir, dirName);
            }
        } else {
            result = new File(MainApplication.getContext().getCacheDir(), dirName);
        }
        if (result.exists() || result.mkdirs()) {
            return result;
        } else {
            return null;
        }
    }

    public static String readFile(String dirName, String fileName) {
        String fileContent = "";
        try {
            File f = new File(cacheDir, dirName + "/" + fileName);
            if (f.isFile() && f.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(f), "utf-8");
                BufferedReader reader = new BufferedReader(read);
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent += line + "\n";
                }
                read.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    public static void writeFile(String dirName, String fileName, String fileContent) {
        try {
            File dir = new File(cacheDir, dirName);
            if (!dir.exists()) {
                dir.mkdirs();//创建目录即文件夹
            }
            File f = new File(dir.getAbsoluteFile(), fileName);
            if (!f.exists()) {
                f.createNewFile();//创建文件
            }
            OutputStreamWriter write = new OutputStreamWriter(
                    new FileOutputStream(f, true), "utf-8");
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(fileContent);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void deleteFile(String fileName) {
        File ff = new File(cacheDir, fileName);
        if (!ff.exists()) {
            return;
        }
        if (ff.isFile()) {
            ff.delete();
            return;
        }
    }

    /**
     * 判断外部存储是否可用
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static String getNetWorkClass() {
        int type = -11;
        String net = "";
        ConnectivityManager connectivityManager = (ConnectivityManager)
                MainApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.getState() == NetworkInfo.State.CONNECTED) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                TelephonyManager telephonyManager = (TelephonyManager) MainApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
                type = telephonyManager.getNetworkType();
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                type = ConnectivityManager.TYPE_WIFI;
            }

            if (type == NETWORK_TYPE_CDMA || type == NETWORK_TYPE_IDEN) {
                net = "2G";
            } else if (type == NETWORK_TYPE_LTE) {
                net = "4G";
            } else if (type == NETWORK_TYPE_HSDPA || type == NETWORK_TYPE_HSPA || type == NETWORK_TYPE_HSPAP || type == NETWORK_TYPE_HSUPA) {
                net = "3G";
            } else if (type == ConnectivityManager.TYPE_WIFI) {
                net = "WIFI";
            }

        }
        return net;
    }

    public static String getNetWork() {
        TelephonyManager telephonyManager = (TelephonyManager) MainApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String imsi = telephonyManager.getSubscriberId();
        if (imsi != null) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {//因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
                //中国移动
            } else if (imsi.startsWith("46001")) {
                //中国联通
            } else if (imsi.startsWith("46003")) {
                //中国电信
            }
        }
        return "";
    }

    public static int getSystemBrightness() {
        int systemBrightness = 0;
        try {
            systemBrightness = Settings.System.getInt(MainApplication.getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return systemBrightness;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static int getBattery() {
        int battery = 0;
        BatteryManager batteryManager = (BatteryManager) MainApplication.getContext().getSystemService(BATTERY_SERVICE);
        battery = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        return battery;
    }

    public static String getAppVersion() {
        try {
            PackageManager manager = MainApplication.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(MainApplication.getContext().getPackageName(), 0);
            return String.valueOf(info.versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1.0";
    }

    public static boolean isRoot() {
        String binPath = "/system/bin/su";
        String xBinPath = "/system/xbin/su";

        if (new File(binPath).exists() && isCanExecute(binPath)) {
            return true;
        }
        if (new File(xBinPath).exists() && isCanExecute(xBinPath)) {
            return true;
        }
        return false;
    }


    private static boolean isCanExecute(String filePath) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("ls -l " + filePath);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String str = in.readLine();
            if (str != null && str.length() >= 4) {
                char flag = str.charAt(3);
                if (flag == 's' || flag == 'x')
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }


    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    public static String getDeviceHardWare() {
        return Build.HARDWARE;
    }

    public static String getDisplay() {
        return Build.DISPLAY;
    }

    public static String getMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int h = dm.heightPixels;
        int w = dm.widthPixels;
        return h + "*" + w;
    }

    /**
     * 获取手机内部空间总大小
     *
     * @return 大小，字节为单位
     */
    static public long getTotalInternalMemorySize() {
        //获取内部存储根目录
        File path = Environment.getDataDirectory();
        //系统的空间描述类
        StatFs stat = new StatFs(path.getPath());
        //每个区块占字节数
        long blockSize = stat.getBlockSize();
        //区块总数
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * 获取手机内部可用空间大小
     *
     * @return 大小，字节为单位
     */
    static public long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        //获取可用区块数量
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取手机外部总空间大小
     *
     * @return 总大小，字节为单位
     */
    static public long getTotalExternalMemorySize() {
        if (isSDCardEnable()) {
            //获取SDCard根目录
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return -1;
        }
    }

    /**
     * 获取SD卡剩余空间
     *
     * @return SD卡剩余空间
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getFreeSpace() {
        if (!isSDCardEnable()) return "sdcard unable!";
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long blockSize, availableBlocks;
        availableBlocks = stat.getAvailableBlocksLong();
        blockSize = stat.getBlockSizeLong();
        long size = availableBlocks * blockSize / 1024L;
        return String.valueOf(size);
    }

    /* 获取系统总内存
     *
        * @param context 可传入应用程序上下文。
        * @return 总内存大单位为B。
       */
    public static long getTotalMemorySize() {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            return Integer.parseInt(subMemoryLine.replaceAll("\\D+", "")) * 1024l;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
