package com.example.za_zhujiangtao.zhupro.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import com.example.za_zhujiangtao.zhupro.MainApplication;

import java.io.File;
import java.io.IOException;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/1/15
 */
public class ImageUtils {

    /**
     * 从xml文件等得到指定宽、高的图片
     *
     * @param path
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
        if (path == null) {
            return null;
        }
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        //旋转图片
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return rotateBitmap(bitmap, path);
    }

    public static Bitmap decodeSampledBitmapFromRes(int resId, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // inJustDecodeBounds = true ,再使用 BitmapFactory.decodeFile() 等方法，并不会真正分配空间，即解码出来的Bitmap为null，
        // 但是可计算出原始图片的宽度和高度，即options.outWidth和options.outHeight。
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(MainApplication.getContext().getResources(), resId, options);
//        BitmapFactory.decodeFile(path, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(MainApplication.getContext().getResources(), resId, options);
        return bitmap;
    }


    /**
     * 根据图片的options和希望输出的宽、高来计算inSampleSize
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 修正被旋转的bitmap
     */
    public static Bitmap rotateBitmap(Bitmap bm, String filePath) {
        int degree = getPictureFileOritation(new File(filePath));
        if (degree == 0) {
            Log.e("ImageUtil", "图片方向正确，无需修正");
            return bm;
        } else {
            Log.e("ImageUtil", "纠正图片旋转," + degree + "度");
//			BitmapFactory.Options bounds = new BitmapFactory.Options();
//	        bounds.inJustDecodeBounds = true;
//	        BitmapFactory.decodeFile(filePath, bounds);

            Matrix matrix = new Matrix();
            matrix.setRotate(degree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
//	        if (null != bm) {
//	        	bm.recycle();
//	        }
            return rotatedBitmap;

        }
    }

    /**
     * 得到拍照得到的图片的方向
     */
    public static int getPictureFileOritation(File mFileTemp) {
        int picture_oritation = 0;
        ExifInterface exif;
        try {
            exif = new ExifInterface(mFileTemp.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            Log.e("ImageUtil", orientation + "");

            if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                picture_oritation = 270;
            }
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                picture_oritation = 180;
            }
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                picture_oritation = 90;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return picture_oritation;
    }

}
