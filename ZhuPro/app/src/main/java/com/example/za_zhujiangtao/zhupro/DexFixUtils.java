package com.example.za_zhujiangtao.zhupro;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/10/22
 * <p>
 * 利用DexClassLoader的加载原理 实现错误类替换，达到修复目的
 */
public class DexFixUtils {
    public final static String DEX_DIR = "odex";
    private static HashSet<File> loadedDex = new HashSet<File>();

    static {
        loadedDex.clear();
    }

    /*
     * 遍历所有的dex文件存储到成员变量loadedDex中，用于后续合并
     * */
    public static void loadFixedDex(Context context) {
        if (context == null) {
            return;
        }

        File fileDir = context.getDir(DEX_DIR, Context.MODE_PRIVATE);
        File[] listFiles = fileDir.listFiles();
        for (File file : listFiles) {
            if (file.getName().startsWith("classes") && file.getName().endsWith("dex")) {
                loadedDex.add(file);
            }
        }
        //合并之前到dex
        doDexInject(context, fileDir);
    }

    /**
     * 通过PathClassLoader、DexClassLoader合并dex文件，实现类替换修复
     *
     * @param context  上下文环境
     * @param filesDir dex所在的文件目录
     */
    private static void doDexInject(Context context, File filesDir) {
        //dex文件需要被写入的目录
        String optimizeDir = filesDir.getAbsolutePath() + File.separator + "opt_dex";
        File fileOpt = new File(optimizeDir);
        if (!fileOpt.exists()) {
            fileOpt.mkdirs();
        }

        //1.获得加载应用程序dex的PathClassLoader
        PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();

        for (File dex : loadedDex) {
            //2.获得加载指定路径下dex的DexClassLoader
            DexClassLoader dexClassLoader = new DexClassLoader(
                    dex.getAbsolutePath(),
                    fileOpt.getAbsolutePath(),
                    null,
                    pathClassLoader);
            //3.合并dex
            try {
                Object dexObj = getPathList(dexClassLoader);
                Object pathObj = getPathList(pathClassLoader);
                Object fixDexElements = getDexElements(dexObj);
                Object pathDexElements = getDexElements(pathObj);
                //合并两个数组
                Object newDexElements = combineArray(fixDexElements, pathDexElements);
                //重新赋值给PathClassLoader 中的exElements数组
                Object pathList = getPathList(pathClassLoader);
                setField(pathList, pathList.getClass(), "dexElements", newDexElements);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Object getPathList(Object baseDexClassLoader) throws Exception {
        return getField(baseDexClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList");
    }

    private static Object getDexElements(Object obj) throws Exception {
        return getField(obj, obj.getClass(), "dexElements");
    }

    /**
     * 通过反射获得对应类
     *
     * @param obj   Object类对象
     * @param cl    class对象
     * @param field 获得类的字符串名称
     * @return
     */
    private static Object getField(Object obj, Class<?> cl, String field) throws NoSuchFieldException, IllegalAccessException {
        Field localField = cl.getDeclaredField(field);
        localField.setAccessible(true);
        return localField.get(obj);
    }

    /**
     * 通过反射修改值
     *
     * @param obj   待修改值
     * @param cl    class对象
     * @param field 待修改值的字符串名称
     * @param value 修改值
     */
    private static void setField(Object obj, Class<?> cl, String field, Object value) throws Exception {
        Field localField = cl.getDeclaredField(field);
        localField.setAccessible(true);
        localField.set(obj, value);
    }

    /**
     * 两个数组合并
     *
     * @param arrayLhs
     * @param arrayRhs
     * @return
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; ++k) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }

}
